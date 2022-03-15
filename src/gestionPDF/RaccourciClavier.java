import gestionPDF.Clavier;

import java.util.ArrayList;

/**
 * Classe qui gère l'attribution des touches pour chacune des fonctions
 */
public class RaccourciClavier {

        /**
         * Liste des associations touches-fonctions constantes par défaut.
         */
        public final String[][] ASSOCIATION_TOUCHE_FONCTION_DEFAULT = {
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
                {"inverseZoomFenetrePremiere","1"},
                // 12 zoom ou dézoome la fenêtre 2
                {"inverseZoomFenetreDeuxieme","2"}
        };

        /** Liste des touches paramétrables (43 touches au total). */
        public final String[] LABELS_TOUCHES = {
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
        }

        /**
         * Constructeur avec fichier de sauvegarde des touches :
         * Permet de créer une instance avec un fichier contenant les sauvegardes des touches déjà configurées
         * @param nomFich fichier de sauvegarde des touches
         */
        public RaccourciClavier(String nomFich) throws IllegalArgumentException {
                // NON FAIT récupérer l'objet associationsTouchesFonctions écrit dans le fichier
        }

        /**
         * permet de définir la fonction reliée à la touche que l'on souhaite parmi celles disponibles.
         * @param lblTouche label touche
         * @param lblFonction label fonction
         */
        public void definirToucheFonction(String lblTouche, String lblFonction) {
                // on teste si la touche et la fonction existent
                getTouche(lblTouche);
                getFonction(lblFonction);
                // on parcours les associations
                for(int i = 0; i < associationsTouchesFonctions.length;i++)
                        // on trouve la fonction
                        if(associationsTouchesFonctions[i][0].equals(lblFonction))
                                // on associe la touche
                                associationsTouchesFonctions[i][1] = lblTouche;
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
                // on cherche parmi les touches définies
                for (String[] associationsTouchesFonction : associationsTouchesFonctions)
                        if (associationsTouchesFonction[1].equals(touche))
                                return associationsTouchesFonction[1];
                // on cherche parmi les touches indéfinies
                for (String labels_touch : LABELS_TOUCHES)
                        if (labels_touch.equals(touche))
                                return "indéfinie";
                // la touche n'est pas trouvée
                throw new IllegalArgumentException("touche introuvable parmi celles enregistrées");
        }


        /*/**
         * méthode de test
         * @param args non utilisé
         */
        /* public static void main(String[] args) {
                RaccourciClavier raccourciClavier = new RaccourciClavier();
                // Test getFonction
                System.out.println("Test getFonction");
                try {
                        System.out.println(raccourciClavier.getFonction("A"));
                        System.out.println("A : fonctionne");
                } catch (IllegalArgumentException e) {
                        System.out.println("A : NOK");
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
                        System.out.println(raccourciClavier.getTouche("monter"));
                        System.out.println("monter : fonctionne");
                } catch (IllegalArgumentException e) {
                        System.out.println("monter : pblm");
                }
                try {
                        raccourciClavier.getTouche("Z");
                        System.out.println("Z : pblm");
                } catch (IllegalArgumentException e) {
                        System.out.println("Z : Exception bien gérée");
                }
        } */
}
