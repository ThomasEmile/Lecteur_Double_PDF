package gestionPDF;

import java.awt.*;
import java.awt.event.*;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class pdfDoubleAffichage extends JPanel {

    static void createPdf(int niveauZoom, JPanel nomPanel, JLabel[] fichierPDF) {

        try (PDDocument document = PDDocument.load(new File("C:/PdfBox/test.pdf"))){

            //creation d'un objet PDFRenderer
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            //implémentation des pages du fichier pdf dans un tableau de label
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                fichierPDF[page] = new JLabel("");
                BufferedImage img = pdfRenderer.renderImageWithDPI(page, niveauZoom);
                ImageIcon icon = new ImageIcon(img);
                fichierPDF[page].setIcon(icon);
                nomPanel.add(fichierPDF[page]);
                System.out.print(img.getHeight());
            }
            document.close();

        } catch (IOException e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }

    public static void configJFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920,1080);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void configJPanel(JPanel panel1, JPanel panel2, JPanel background, JPanel panelTest1, JPanel header) {
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setVisible(false);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        panelTest1.setBackground(Color.getHSBColor(1,1,30));
        header.setBackground(Color.getHSBColor(1,1,30));
    }

    public static void ajoutComposants(JPanel panel1, JPanel panel2, JPanel Header, JPanel panelCont1, JPanel panelCont2
            , JPanel panelTest1, JPanel panelTest2, JPanel background, JFrame frame, JScrollPane scrollPane) {

        panelCont1.add(panel1);
        panelCont2.add(panel2);

        panelTest1.add(Header);
        panelTest2.add(panelCont1);
        panelTest2.add(panelCont2);

        background.add(panelTest1);
        background.add(panelTest2);

        frame.add(scrollPane);
        frame.validate();
    }

    public static void configJScrollPane(JScrollPane scrollPane) {
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }

    public static void main(String[] args) throws Exception {

        int niveauZoom = 90;
        String nomJFrame = "pdfDoubleAffichage";

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        //Création de JFrame
        JFrame frame = new JFrame(nomJFrame);

        // configuration du JFrame
        configJFrame(frame);

        // panel background
        JPanel background = new JPanel();

        // panel test 1
        JPanel panelTest1 = new JPanel();

        // panel test 2
        JPanel panelTest2 = new JPanel();

        // panel contenant le panel pdf 21
        JPanel panelCont1 = new JPanel();

        // panel contenant le panel pdf 2
        JPanel panelCont2 = new JPanel();

        // panel pdf 1
        JPanel panel1 = new JPanel();

        // panel pdf 2
        JPanel panel2 = new JPanel();

        // panel boutons
        JPanel Header = new JPanel();

        JLabel[] pdf = new JLabel[1000];
        JLabel[] pdf2 = new JLabel[1000];

        //création bouton pour passer à la page suivante
        JButton SinglePage = new JButton("Simple");
        Header.add(SinglePage);

        //création bouton pour passer à la page suivante
        JButton DoublePage = new JButton("Double");
        Header.add(DoublePage);

        //création bouton pour passer à la page suivante
        JButton pageSuivante = new JButton("↓");
        Header.add(pageSuivante);

        //création bouton pour passer à la page suivante
        JButton pagePrecedente = new JButton("↑");
        Header.add(pagePrecedente);

        //création textField pour rentrer la page souhaité
        JTextField choixPage = new JTextField(3);
        Header.add(choixPage);

        //création d'un Label pour le nombre de pages restantes
        JLabel NombrePage = new JLabel("6");
        Header.add(NombrePage);
        // configuration du layout des panels
        configJPanel(panel1, panel2, background, panelTest1, Header);

        // création du premier document PDF
        createPdf(niveauZoom, panel1, pdf);

        // création du deuxième document PDF
        createPdf(niveauZoom, panel2, pdf2);

        Counter c = new Counter();

        // évènement afficher deux pdf
        pageSuivante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                c.decrease();
                int y = 1052*c.getValue();
                panel1.setLocation(0,y);
            }
        });

        // évènement afficher deux pdf
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                c.increment();
                int y = 1052*c.getValue();
                panel1.setLocation(0,y);
            }
        });

        // évènement afficher un seul pdf
        SinglePage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                panel2.setVisible(false);
            }
        });

        // évènement afficher deux pdf
        DoublePage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel2.setVisible(true);
            }
        });

        JScrollPane scrollPane = new JScrollPane(background);

        configJScrollPane(scrollPane);

        ajoutComposants(panel1, panel2, Header, panelCont1, panelCont2, panelTest1, panelTest2, background, frame, scrollPane);
    }

    static class Counter {
        private int value=0;

        void increment() {
            value++;
        }

        void decrease() {
            value--;
        }

        void reset() {
            value = 0;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }

}

