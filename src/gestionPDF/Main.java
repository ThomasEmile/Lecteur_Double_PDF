/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */
package gestionPDF;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe d'entrée de l'application, contenant le main() de celle-ci
 */

public class Main {

    /**
     * Méthode main de l'application.
     * Créer la fenêtre à partir du document choisi
     */
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        GestionFenetre gestionFenetre = new GestionFenetre();

        GestionFenetre.nouvelleFenetre();
        Actualiseur actualiseur = new Actualiseur();
        actualiseur.start();
    }
}
