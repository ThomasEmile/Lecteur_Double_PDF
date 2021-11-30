package gestionPDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class createPagePDF {
    
    public static int height = 0;
    public static int width = 0;

    public static int nombrePage = 0;


    /**
     * méthode qui récupère les pages d'un fichier pdf entré en argument à l'aide d'un chemin vers celui-ci, les
     * implémente dans un tableau de Label qui est lui-même ajouté à un panel entré en argument
     * @param nomPanel
     * @param fichierPDF
     * @param chemin
     */
    static void createPdf(JPanel nomPanel, JLabel[] fichierPDF, String chemin) {

        try(PDDocument document = PDDocument.load(new File(chemin))) {

                //nombre de page du document
                nombrePage = document.getNumberOfPages();

                //creation d'un objet PDFRenderer
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                //implémentation des pages du fichier pdf dans un tableau de label
                for (int page = 0; page < document.getNumberOfPages(); ++page) {
                    JTextArea espace = new JTextArea();
                    espace.setEditable(false);

                    fichierPDF[page] = new JLabel("");
                    BufferedImage img = pdfRenderer.renderImageWithDPI(page, 100);
                    ImageIcon icon = new ImageIcon(img);

                    height = (icon.getIconHeight() * 1200) / icon.getIconWidth();
                    width = icon.getIconWidth();

                    // transform it
                    Image image = icon.getImage();
                    // scale it the smooth way
                    Image newimg = image.getScaledInstance(1200, height,
                            Image.SCALE_SMOOTH);
                    // transform it back
                    icon = new ImageIcon(newimg);
                    fichierPDF[page].setIcon(icon);
                    nomPanel.add(fichierPDF[page]);
                    nomPanel.add(espace);
                }
        } catch (IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }

    }
}
