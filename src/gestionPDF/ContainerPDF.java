package gestionPDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ContainerPDF implements Runnable {

    private int height = 0;
    private int width = 0;
    private int heightZoom = 0;
    private int nombrePage = 0;
    public JScrollPane scrollPaneContainer;
    private JPanel containerDocumentPDF;
    public JPanel documentPDF;
    public JPanel documentPDF2;
    private ArrayList<JLabel> pagePDF;
    private ArrayList<JLabel> pagePDF2;
    public FenetreApp fenetre;

    public ContainerPDF(FenetreApp fenetre) {
        this.fenetre = fenetre;
        containerDocumentPDF = new JPanel();
        scrollPaneContainer = new JScrollPane(containerDocumentPDF);
        documentPDF = new JPanel();
        documentPDF2 = new JPanel();
        pagePDF = new ArrayList<JLabel>();
        pagePDF2 = new ArrayList<JLabel>();
        configContainerPDF();
        configScrollPaneContainer();
        getDocumentPDF2().setVisible(false);
    }

    public int getHeightZoom() {
        return heightZoom;
    }

    public JPanel getDocumentPDF2() {
        return documentPDF2;
    }

    public int getHeight() {

        return height;
    }

    public int getNombrePage() {

        return nombrePage;
    }

    public ArrayList<JLabel> getPagePDF() {
        return pagePDF;
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
        documentPDF2.setLayout(new BoxLayout(documentPDF2, BoxLayout.Y_AXIS));
    }

    void configScrollPaneContainer() {
        scrollPaneContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneContainer.getVerticalScrollBar().setUnitIncrement(20);
        scrollPaneContainer.getHorizontalScrollBar().setUnitIncrement(20);
    }

    void createPdf() {
        try (PDDocument document = PDDocument.load(new File(FileChooser.Chooser()))) {
            //nombre de page du document
            nombrePage = document.getNumberOfPages();

            //creation d'un objet PDFRenderer
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            //implémentation des pages du fichier pdf dans un tableau de label
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                System.out.println(page);
                JTextArea espace = new JTextArea();
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

                fenetre.getBackground().updateUI();
                createPageZoom(page, document);

            }
        } catch (
                IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }

    void createPageZoom(int page, PDDocument document) throws IOException {

        //nombre de page du document
        nombrePage = document.getNumberOfPages();

        //creation d'un objet PDFRenderer
        PDFRenderer pdfRenderer2 = new PDFRenderer(document);
        //implémentation des pages du fichier pdf dans un tableau de label

        System.out.println(page);
        JTextArea espace2 = new JTextArea();
        JLabel containerPagePDF2 = new JLabel("");
        pagePDF2.add(containerPagePDF2);
        containerPagePDF2.setFocusable(false);


        BufferedImage img2 = pdfRenderer2.renderImageWithDPI(page, 150);
        ImageIcon icon2 = new ImageIcon(img2);
        width = 1870;
        heightZoom = (icon2.getIconHeight() * width) / icon2.getIconWidth();
        ;

        // transform it
        Image image = icon2.getImage();
        // scale it the smooth way
        Image newimg = image.getScaledInstance(width, heightZoom, Image.SCALE_DEFAULT);
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