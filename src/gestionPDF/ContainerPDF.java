package src;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class ContainerPDF {

    public String chemin = FileChooser.Chooser();  //= "C:/Users/Eva/scolaire/info2/projetTuteure/Projet_Tutores_-_Exigences_projets_V3.pdf";
    public int height = 0;
    public int width = 0;
    public int nombrePage = 0;
    public JScrollPane scrollPaneContainer;
    public JPanel containerDocumentPDF;
    public JPanel documentPDF;
    public ArrayList<JLabel> pagePDF;



    public ContainerPDF() {
        containerDocumentPDF = new JPanel();
        scrollPaneContainer = new JScrollPane(containerDocumentPDF);
        documentPDF = new JPanel();
        pagePDF = new ArrayList<JLabel>();
        configContainerPDF();
        getDocumentPDF();
        configContainerPDF();
        configScrollPaneContainer();
        createPdf();
        
    }

    public int getHeight() {
        return height;
    }

    public int getNombrePage() {
        return nombrePage;
    }

    public JScrollPane getScrollPaneContainer() {
        return scrollPaneContainer;
    }

    public JPanel getContainerDocumentPDF() {
        return containerDocumentPDF;
    }

    public JPanel getDocumentPDF() {
        return documentPDF;
    }

    void configContainerPDF() {
        documentPDF.setLayout(new BoxLayout(documentPDF, BoxLayout.Y_AXIS));
    }

    void configScrollPaneContainer() {
        scrollPaneContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneContainer.getVerticalScrollBar().setUnitIncrement(10);
        scrollPaneContainer.getHorizontalScrollBar().setUnitIncrement(10);
    }

    void createPdf() {

        try (PDDocument document = PDDocument.load(new File(chemin))) {
            //nombre de page du document
            nombrePage = document.getNumberOfPages();

            //creation d'un objet PDFRenderer
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            //implémentation des pages du fichier pdf dans un tableau de label
            for (int page = 0; page < document.getNumberOfPages(); ++page) {

                JTextArea espace = new JTextArea();
                espace.setFocusable(false);
                JLabel containerPagePDF = new JLabel("");
                pagePDF.add(containerPagePDF);
                containerPagePDF.setFocusable(false);


                BufferedImage img = pdfRenderer.renderImageWithDPI(page, 100);
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
            }

        } catch (
                IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }

    }

    public void addListeners(Clavier clavier) {
        clavier.setContainerPDF(this);
        this.scrollPaneContainer.addKeyListener(clavier);
        this.documentPDF.addKeyListener(clavier);
        this.scrollPaneContainer.addKeyListener(clavier);
    }
}
