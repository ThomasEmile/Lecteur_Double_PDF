package gestionPDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.PDFToImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class ContainerPDF {

    private final String chemin = "Z:/CPO Chap6.pdf";
    public int height = 0;
    public int width = 0;
    public int nombrePage = 0;
    public JScrollPane scrollPaneContainer;
    public JPanel containerDocumentPDF;
    public JPanel documentPDF;
    public ArrayList<JLabel> pagePDF;
    public ArrayList<Image> img;
    public ArrayList<ImageIcon> icon;
    public JFrame fenetre;
    public ArrayList<PanelImage> panelImage;


    public ContainerPDF(JFrame fenetre) {
        panelImage = new ArrayList<>();
        containerDocumentPDF = new JPanel();
        scrollPaneContainer = new JScrollPane(containerDocumentPDF);
        documentPDF = new JPanel();
        pagePDF = new ArrayList<JLabel>();
        img = new ArrayList<>();
        icon = new ArrayList<>();
        this.fenetre = fenetre;
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
    }

    void createPdf() {
        try (PDDocument document = PDDocument.load(new File(chemin))) {
            //nombre de page du document
            nombrePage = document.getNumberOfPages();

            //creation d'un objet PDFRenderer
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            //implémentation des pages du fichier pdf dans un tableau de panel
            for (int page = 0; page < document.getNumberOfPages() ; ++page) {

                BufferedImage img1 = pdfRenderer.renderImageWithDPI(page, 180);


                panelImage.add(new PanelImage(img1,img1.getWidth(), img1.getHeight()));
                panelImage.get(page).setZoom(1);
                panelImage.get(page).updateUI();

                fenetre.repaint();
                //System.out.print(documentPDF.getWidth());
                documentPDF.add(panelImage.get(page));
                documentPDF.setFocusable(false);
                JPanel espace = new JPanel();
                documentPDF.add(espace);
                espace.setFocusable(false);
            }

        } catch (
                IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }

    }

    void createPdfZoom() {
        try (PDDocument document = PDDocument.load(new File(chemin))) {
            //nombre de page du document
            nombrePage = document.getNumberOfPages();

            //creation d'un objet PDFRenderer
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            //implémentation des pages du fichier pdf dans un tableau de panel
            for (int page = 0; page < document.getNumberOfPages() ; ++page) {
                BufferedImage img1 = pdfRenderer.renderImageWithDPI(page, 200);
                panelImage.add(new PanelImage(img1,img1.getWidth(), img1.getHeight()));
                //System.out.print(documentPDF.getWidth());
                documentPDF.add(panelImage.get(page));

                JLabel espace = new JLabel();
                espace.setText("------------------------------------------");
                espace.setVisible(true);
                espace.setBackground(Color.red);
                documentPDF.add(espace);


            }

        } catch (
                IOException e) {
            System.err.println("Exception while trying to create pdf document - " + e);
        }

    }

}
