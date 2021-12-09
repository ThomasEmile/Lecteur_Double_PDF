/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

package gestionPDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe s'occupe de créer les deux documents PDF, à savoir sa version de base et sa version zoomé.
 * Les documents du pdf vont être placé dans une arrayList de JLabel qui va ensuite être stocké dans un premier
 * container qui va lui aussi être stocké dans un nouveau container (qui pour le s4 permettra de stocker le deuxième
 * document pdf)
 * @author Groupe pdf double affichage
 * @version 1.0
 */
public class ContainerPDF implements Runnable {

    private boolean zoomed = false;

    private int height = 0; // initialisation d'un entier qui va contenir la hauteur du document de base
    private int width = 0;  // initialisation d'un entier qui va contenir la largeur du document de base
    private int heightZoom = 0;  // initialisation d'un entier qui va contenir la longueur du document zoomé
    private int nombrePage = 0;  // initialisation d'un entier qui va contenir la hauteur du document zoomé

    public JScrollPane scrollPaneContainer; // création d'un scroll pour pouvoir naviguer facilement entre les pages du pdf
    private JPanel containerDocumentPDF; // panel contenant le panel du pdf
    public JPanel documentPDF;  // création du panel contenant l'arrayList du pdf de base
    public JPanel documentPDF2; // création du panel contenant l'arraylist du pdf zoomé
    private ArrayList<JLabel> pagePDF; // création de l'arrayList contenant les pages du document pdf de base
    private ArrayList<JLabel> pagePDF2; // création de l'arrayList contenant les pages du document pdf zoomé

    public FenetreApp fenetre; // implémentation de la classe FenetreApp afin de pouvoir accéder à ses méthodes

    /**
     * constructeur de la classe ContainerPDF
     * @param fenetre
     */
    public ContainerPDF(FenetreApp fenetre) {
        this.fenetre = fenetre;
        //initialisation des différents composants de la classe
        containerDocumentPDF = new JPanel();
        scrollPaneContainer = new JScrollPane(containerDocumentPDF);
        documentPDF = new JPanel();
        documentPDF2 = new JPanel();
        pagePDF = new ArrayList<JLabel>();
        pagePDF2 = new ArrayList<JLabel>();

        // appel des différentes méthodes de la classe
        configContainerPDF();
        configScrollPaneContainer();

        // on cache le pdf zoomé afin qu'à l'ouverture de l'application, l'on ne voit que le pdf au format de base
        getDocumentPDF2().setVisible(false);
    }

    // Getter de la classe ContainerPDF
    public int getHeightZoom() {
        return heightZoom;
    }
    public JPanel getDocumentPDF2() {
        return documentPDF2;
    }
    public int getHeight() {return height;}
    public int getNombrePage() {return nombrePage;}
    public ArrayList<JLabel> getPagePDF() {
        return pagePDF;
    }
    public ArrayList<JLabel> getPagePDF2() {return this.pagePDF2;}
    public JScrollPane getScrollPaneContainer() {return scrollPaneContainer;}
    public JPanel getContainerDocumentPDF() {
        return containerDocumentPDF;
    }
    public JPanel getDocumentPDF() {return documentPDF;}

    public boolean isZoomed() {
        return zoomed;
    }

    public void setZoomed(boolean zoomed) {
        this.zoomed = zoomed;
    }

    /**
     * méthode qui permet d'afficher les pages du pdf les unes en dessous des autres
     */
    void configContainerPDF() {

        documentPDF.setLayout(new BoxLayout(documentPDF, BoxLayout.Y_AXIS));
        documentPDF2.setLayout(new BoxLayout(documentPDF2, BoxLayout.Y_AXIS));
    }

    /**
     * méthode qui configure la barre de scroll de l'application
     */
    void configScrollPaneContainer() {
        scrollPaneContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // réglage du saute de la barre de scroll
        scrollPaneContainer.getVerticalScrollBar().setUnitIncrement(20);
        scrollPaneContainer.getHorizontalScrollBar().setUnitIncrement(20);
    }

    /**
     * méthode qui va permettre la création d'un fichier pdf. Elle utilise tout d'abord un objet PDDocument qui récupère,
     * un document pdf à l'aide de son chemin, choisi par l'utilisateur à l'aide d'un file chooser. le document pdf est
     * ensuite rendu à l'aide de la classe PDFRenderer. A l'aide de ce rendu nous allons pouvoir créer un objet  Buffer
     * Image qui va créer une image représentant une page du pdf choisi. cette image va ensuite être converti en ImageIcon
     * afin de pouvoir la redimentionner. Après le resize de l'image, cette dernière va une nouvelle fois être converti
     * puis stocké dans un Label qui va ensuite être stocké dans un panel
     */
    void createPdf() {
        try (PDDocument document = PDDocument.load(new File(FileChooser.Chooser()))) {
            //nombre de page du document
            nombrePage = document.getNumberOfPages();
            // page courante affichée =  première page
            fenetre.getButton().setChoixPage(1);
            // Affiche le nombre de page du document
            fenetre.getButton().setNombreDePage(nombrePage);

            //creation d'un objet PDFRenderer
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            //implémentation des pages du fichier pdf dans un tableau de label
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                JTextArea espace = new JTextArea();
                JLabel containerPagePDF = new JLabel("");
                pagePDF.add(containerPagePDF);
                containerPagePDF.setFocusable(false);
                espace.setFocusable(false);


                BufferedImage img = pdfRenderer.renderImageWithDPI(page, 80);
                ImageIcon icon = new ImageIcon(img);
                height = 1000;
                width = (icon.getIconWidth() * height) / icon.getIconHeight();

                // transform it
                Image image = icon.getImage();
                // scale it the smooth way
                Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                // transform it back
                icon = new ImageIcon(newimg);
                pagePDF.get(page).setIcon(icon);
                documentPDF.setFocusable(false);
                espace.setBackground(new Color(239, 237, 237));
                documentPDF.add(pagePDF.get(page));
                if (page != document.getNumberOfPages() - 1) {
                    documentPDF.add(espace);
                }

                fenetre.getBackground().updateUI();
                createPageZoom(page, document);

            }
        } catch (
                IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }

    /**
     * méthode qui va permettre la création du zoom du fichier pdf. Elle utilise tout d'abord un objet PDDocument qui
     * récupère,un document pdf à l'aide de son chemin, choisi par l'utilisateur à l'aide d'un file chooser. le document pdf est
     * ensuite rendu à l'aide de la classe PDFRenderer. A l'aide de ce rendu nous allons pouvoir créer un objet  Buffer
     * Image qui va créer une image représentant une page du pdf choisi. cette image va ensuite être converti en ImageIcon
     * afin de pouvoir la redimentionner. Après le resize de l'image, cette dernière va une nouvelle fois être converti
     * puis stocké dans un Label qui va ensuite être stocké dans un panel
     */
    void createPageZoom(int page, PDDocument document) throws IOException {

        //nombre de page du document
        nombrePage = document.getNumberOfPages();

        //creation d'un objet PDFRenderer
        PDFRenderer pdfRenderer2 = new PDFRenderer(document);
        //implémentation des pages du fichier pdf dans un tableau de label

        JTextArea espace2 = new JTextArea();
        JLabel containerPagePDF2 = new JLabel("");
        pagePDF2.add(containerPagePDF2);
        containerPagePDF2.setFocusable(false);
        espace2.setFocusable(false);

        BufferedImage img2 = pdfRenderer2.renderImageWithDPI(page, 120);
        ImageIcon icon2 = new ImageIcon(img2);
        width = 1870;
        heightZoom = (icon2.getIconHeight() * width) / icon2.getIconWidth();
        ;

        // transform it
        Image image = icon2.getImage();
        // scale it the smooth way
        Image newimg = image.getScaledInstance(width, heightZoom, Image.SCALE_SMOOTH);
        // transform it back
        icon2 = new ImageIcon(newimg);
        pagePDF2.get(page).setIcon(icon2);
        documentPDF2.setFocusable(false);
        espace2.setBackground(new Color(239, 237, 237));
        documentPDF2.add(pagePDF2.get(page));
        if (page != document.getNumberOfPages() - 1) {
            documentPDF2.add(espace2);
        }
        fenetre.getBackground().updateUI();
    }



    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        createPdf();
    }

}