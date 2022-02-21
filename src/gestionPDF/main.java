/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe d'entrée de l'application, contenant le main() de celle-ci
 */
public class main {

    /**
     * Méthode main de l'application.
     * Créer la fenêtre à partir du document choisi
     */
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        nouvelleFenetre();
    }

    /**
     * Création d'une nouvelle fenêtre affichant un document pdf
     */
    private static void nouvelleFenetre() {
        try {
            // Choix du document à afficher
            PDDocument document =  PDDocument.load(new File(FileChooser.Chooser()));
            // création de la fenêtre
            FenetreApp fenetre = new FenetreApp(document);
            // Création du thread actualiseur
            Actualiseur actualiseur = new Actualiseur(fenetre);
            // Lancement du thread actualisuer
            actualiseur.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Erreur lors de l'ouverture du document.");
            nouvelleFenetre();
        }
    }
}
