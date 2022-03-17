import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe de gestion des fenêtres
 */
public class GestionFenetre {

    /** Nombre de fenêtres actives */
    public static int nbFenetre = 0;

    /** Liste des fenêtre actives */
    public static ArrayList<FenetreApp> fenetres;

    /** Lien vers le gestionnaire d'action clavier/souris */
    public static ClavierSouris clavierSouris = new ClavierSouris();

    /**
     *  Constructeur par défaut de la classe
     *  Initialise fenetres
     */
    GestionFenetre () {
        fenetres = new ArrayList<>();
    }

    /**
     * Création d'une nouvelle fenêtre affichant un document pdf
     */
    public static void nouvelleFenetre() {
        try {
            // Choix du document à afficher
            String path = FileChooser.Chooser();
            if (path != null) {
                PDDocument document = PDDocument.load(new File(path));
                if (nbFenetre != 2) {
                    // création de la fenêtre
                    FenetreApp fenetre = new FenetreApp(document, clavierSouris);
                    fenetres.add(fenetre);
                    nbFenetre++;
                    if (nbFenetre == 1) {
                        fenetre.mainWindow.setTitle(fenetre.getWINDOW_NAME() + " - fenêtre 1");
                        clavierSouris.setFenetreApp(fenetre, 0);
                    } else if (nbFenetre == 2) {
                        fenetre.mainWindow.setTitle(fenetre.getWINDOW_NAME() + " - fenêtre 2");
                        clavierSouris.setFenetreApp(fenetre, 1);
                    }
                    fenetre.mainWindow.setVisible(true);
                    fenetre.mainWindow.requestFocus();
                    // Création du thread actualiseur
                    Actualiseur actualiseur = new Actualiseur(fenetre);
                    // Lancement du thread actualisuer
                    actualiseur.start();
                } else if (nbFenetre >= 2) {
                    Object[] choix = {"Fenetre 1", "Fenetre 2"};
                    int choixFais = JOptionPane.showOptionDialog(null,
                            "Dans quelle fenêtre voulez vous ouvrir le document ?",
                            "Remplacer un pdf",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, choix, choix[0]);
                    FenetreApp fenetre = new FenetreApp(document, clavierSouris);
                    fenetres.set(choixFais, fenetre);

                    clavierSouris.remplacerFenetre(fenetre, choixFais);

                    // Création du thread actualiseur
                    Actualiseur actualiseur = new Actualiseur(fenetre);
                    // Lancement du thread actualisuer
                    actualiseur.start();
                }
            } else if (nbFenetre == 0) {
                System.exit(0);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ouverture du document.");
            nouvelleFenetre();
        }
    }

    /**
     * Méthode qui gère le remplacement d'un pdf
     * @param index
     */
    public static void remplacer(int index ) {
        // Choix du document à afficher
        String path = FileChooser.Chooser();
        try {
            if (path != null) {
                PDDocument document = PDDocument.load(new File(path));
                // création de la fenêtre
                FenetreApp fenetre = new FenetreApp(document, clavierSouris);
                fenetre.mainWindow.setTitle(fenetre.getWINDOW_NAME() + " - fenêtre " + (index + 1));
                fenetres.set(index, fenetre);
                clavierSouris.remplacerFenetre(fenetre, index);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ouverture du document.");
            remplacer(index);
        }

    }
}