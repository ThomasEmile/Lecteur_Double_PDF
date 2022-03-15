import java.io.*;

/**
 * Classe qui gère l'attribution des touches pour chacune des fonctions
 */
public class RaccourciClavier {

        public static final String CHEMIN_FICH_CONFIG_DEFAULT = "raccourciClavier.config";

        /**
         * Liste des associations touches-fonctions constantes par défaut.
         */
        public static final String[][] ASSOCIATION_TOUCHE_FONCTION_DEFAULT = {
                // 0 Passe en mode unifié (ou différencié si deja unifié) s'il y a 2 fenêtre
                {"changementModeAffich","U"},
                // 1 Passe à la page suivante de la fenêtre 2
                {"pageSuivFenetre2","D"},
                // 2 Passe à la page précédente de la fenêtre 2
                {"pagePrecFenetre2","Q"},
                // 3 Monte dans le document de la fenêtre 2
                {"monteDocFenetre2","Z"},
                // 4 descend dans le document de la fenêtre 2
                {"descendDocFenetre2","S"},
                // 5 passe à la page suivante
                {"pageSuivante","RIGHT"},
                // 6 passe à la page précédente
                {"pagePrecedente","LEFT"},
                // 7 scroll vers le bas
                {"descendre","DOWN"},
                // 8 scroll vers le haut
                {"monter","UP"},
                /*
                 * 9 sélectionne le champ de choix de page de la fenêtre 1
                 * présélectionne le texte qui y est
                 */
                {"choixFenetrePremiere","R"},
                /*
                 * 10 sélectionne le champ de choix de page de la fenêtre 2
                 * présélectionne le texte qui y est
                 */
                {"choixFenetreDeuxieme","T"},
                // 11 zoom ou dézoome la fenêtre 1
                {"inverseZoomFenetrePremiere","N1"},
                // 12 zoom ou dézoome la fenêtre 2
                {"inverseZoomFenetreDeuxieme","N2"}
        };

        /** Liste des touches paramétrables (43 touches au total). */
        public static final String[] LABELS_TOUCHES = {
                "A","Z","E","R","T","Y","U","I","O","P",
                "Q","S","D","F","G","H","J","K","L","M",
                "W","X","C","V","B","N","NONE","ECHAP","ENTREE",
                "N0","N1","N2","N3","N4","N5","N6","N7","N8","N9",
                "UP","DOWN","RIGHT","LEFT"
        };

        /**
         * Tableau de deux chaines des associations des touches avec leurs fonctions.
         */
        public String[][] associationsTouchesFonctions;

        /**
         * Constructeur par défaut :
         * permet de créer une instance si aucune sauvegarde de touche n'est donnée
         */
        public RaccourciClavier() {
            associationsTouchesFonctions = ASSOCIATION_TOUCHE_FONCTION_DEFAULT;
            // créer un fichier au format touche fonction
            try {
                // declaration et création de l'objet fichier
                PrintWriter fichierD = new PrintWriter(new FileWriter(CHEMIN_FICH_CONFIG_DEFAULT));

                for (String[] associationsTouchesFonction : associationsTouchesFonctions) {
                    // écrit la fonction
                    fichierD.println(associationsTouchesFonction[0]);
                    // écrit la touche
                    fichierD.println(associationsTouchesFonction[1]);
                }
                // fermeture du fichier
                fichierD.close();
            } catch (IOException e) {
                System.out.println("Problème d'accès au fichier");
            }

        }

        /**
         * Constructeur avec fichier de sauvegarde des touches :
         * Permet de créer une instance avec un fichier contenant les sauvegardes des touches déjà configurées
         * @param nomFich chemin fichier de sauvegarde des touches
         */
        public RaccourciClavier(String nomFich) throws IllegalArgumentException {
            // éviter l'exception NullPointerException
            associationsTouchesFonctions = ASSOCIATION_TOUCHE_FONCTION_DEFAULT;
            // lire le fichier texte contenant les associations touches-fonctions
            try {
                // declaration et création de l'objet fichier
                BufferedReader fichierS = new BufferedReader(new FileReader(nomFich));
                String ligne,fonction = "",touche = "";
                int i = 0;
                do {
                    ligne = fichierS.readLine(); // lecture d'une ligne
                    if(ligne != null && !ligne.isEmpty()) {
                        if(i%2==0)
                            fonction = ligne;
                        else
                            touche = ligne;
                        if(i > 1 && i%2 == 0)
                            definirToucheFonction(touche,fonction);
                    }
                    i++;
                } while(ligne != null);

                fichierS.close(); // fermeture du fichier
            } catch (IOException e) {
                System.out.println("Problème d'accès au fichier " + nomFich);
            }
        }

        /**
         * permet de définir la fonction reliée à la touche que l'on souhaite parmi celles disponibles.
         * @param lblTouche label touche
         * @param lblFonction label fonction
         */
        public void definirToucheFonction(String lblTouche, String lblFonction) {
            // on teste si la touche et la fonction existent
            getTouche(lblFonction);
            getFonction(lblTouche);
            // on parcours les associations
            for (String[] associationsTouchesFonction : associationsTouchesFonctions)
            {
                // on trouve la fonction
                if(associationsTouchesFonction[0].equals(lblFonction))
                        // on associe la touche
                        associationsTouchesFonction[1] = lblTouche;
            }
        }

        /**
         * Récupérer la touche qui est liée à une fonction paramètre.
         * @param fonction fonction liée à la touche
         * @return touche liée à la fonction donnée
         * @throws IllegalArgumentException si la fonction n'existe pas parmi celles définies
         */
        public String getTouche(String fonction) throws IllegalArgumentException {
                // on cherche parmi les fonctions définies
                for (String[] associationsTouchesFonction : associationsTouchesFonctions)
                        if (associationsTouchesFonction[0].equals(fonction))
                                return associationsTouchesFonction[1];
                // la fonction n'est pas trouvée
                throw new IllegalArgumentException("fonction introuvable parmi celles enregistrées");
        }

        /**
         * Récupérer la fonction qui est liée à une touche paramètre.
         * @param touche touche liée à la fonction
         * @return fonction liée à la touche donnée
         * @throws IllegalArgumentException si la touche n'existe pas parmi les labels existants
         */
        public String getFonction(String touche) throws IllegalArgumentException {
            // test System.out.println(touche + " détectée");
            // on cherche parmi les touches définies
            for (String[] associationsTouchesFonction : associationsTouchesFonctions)
                    if (associationsTouchesFonction[1].equals(touche))
                            return associationsTouchesFonction[0];
            // on cherche parmi les touches indéfinies
            for (String labels_touch : LABELS_TOUCHES)
                    if (labels_touch.equals(touche))
                            return "indéfinie";
            // la touche n'est pas trouvée
            throw new IllegalArgumentException("touche \"" + touche + "\" introuvable parmi celles enregistrées");
        }


        /*/**
         * méthode de test
         * @param args non utilisé
         */
         public static void main(String[] args) {
                RaccourciClavier raccourciClavier = new RaccourciClavier();
                // Test getFonction
                System.out.println("Test getFonction");
                try {
                        if((raccourciClavier.getFonction("A")).equals("indéfinie"))
                            System.out.println("A : fonctionne");
                        else
                            System.out.println("A : mauvais label");
                } catch (IllegalArgumentException e) {
                        System.out.println("A : pblm exception");
                }
                try {
                        raccourciClavier.getFonction("TOUCHE");
                        System.out.println("TOUCHE : problème");
                } catch (IllegalArgumentException e) {
                        System.out.println("TOUCHE : Exception bien gérée");
                }
                // Test getTouche
                System.out.println("Test getTouche :");
                try {
                        if(raccourciClavier.getTouche("monter").equals("UP"))
                            System.out.println("monter : fonctionne");
                        else
                            System.out.println("monter : pblm label");
                } catch (IllegalArgumentException e) {
                        System.out.println("monter : pblm exception");
                }
                try {
                        raccourciClavier.getTouche("Z");
                        System.out.println("Z : pblm");
                } catch (IllegalArgumentException e) {
                        System.out.println("Z : Exception bien gérée");
                }
             try {
                 if(raccourciClavier.getFonction("DOWN").equals("descendre"))
                     System.out.println("DOWN : fonctionne");
                 else
                    System.out.println("DOWN : pblm label");
             } catch (IllegalArgumentException e) {
                 System.out.println("Z : pblm exception");
             }
             // définir touche fonction
             System.out.println("Test definirToucheFonction :");
             try {
                 raccourciClavier.definirToucheFonction("monter","A");
                 System.out.println("monter,A : pblm touche et fonction inversés");
             } catch (IllegalArgumentException e) {
                 System.out.println("monter,A : exception bien gérée");
             }
             try {
                 raccourciClavier.definirToucheFonction("A","monter");
                 System.out.println("A,monter : fonctionne");
             } catch (IllegalArgumentException e) {
                 System.out.println("A,monter : pblm exception");
             }
        }
}
