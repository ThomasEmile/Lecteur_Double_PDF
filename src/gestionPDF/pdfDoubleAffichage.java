package gestionPDF;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static gestionPDF.configComposants.*;
import static gestionPDF.createPagePDF.*;

public class pdfDoubleAffichage extends JPanel {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        String nomJFrame = "gestionPDF.pdfDoubleAffichage";
        String cheminPDF = "C:/PdfBox/test.pdf";
        String cheminIconApp = "icon/IconApp.png";

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        //Création de JFrame.
        JFrame frame = new JFrame(nomJFrame);

        //frame.setFocusable(false);
        // configuration du JFrame
        configJFrameDouble(frame, cheminIconApp);

        // panel background
        JPanel background = new JPanel();
        background.setFocusable(false);
        background.setFocusable(false);

        //panel contenant le pdf
        JPanel containerPDF = new JPanel();
        containerPDF.setFocusable(false);
        containerPDF.setFocusable(false);

        // panel contenant le panel pdf
        JPanel panelCont1 = new JPanel();
        panelCont1.setFocusable(false);
        panelCont1.setFocusable(false);

        // panel pdf 1
        JPanel panel1 = new JPanel();
        panel1.setFocusable(false);
        panel1.setLayout(new GridLayout(0, 1));

        // panel boutons
        JPanel button = new JPanel();

        // ArrayList contenant les pages du fichier PDF
        ArrayList<JLabel> pdf = new ArrayList<JLabel>();

        //création bouton pour passer à la page suivante
        JButton pageSuivante = new JButton("↓");
        button.add(pageSuivante);

        //création bouton pour passer à la page précédente
        JButton pagePrecedente = new JButton("↑");
        button.add(pagePrecedente);

        //création textField pour rentrer la page souhaitée
        JTextField choixPage = new JTextField(2);
        button.add(choixPage);

        //déclaration d'un objet JScrollPane afin de pouvoir scroll dans la fenêtre
        JScrollPane scrollPaneFrame = new JScrollPane(containerPDF);

        //configuration du scrollPane de la fenêtre
        configJScrollPane(scrollPaneFrame);

        // configuration du layout des panels
        configJPanel(panel1, background, button);

        // création du premier document PDF
        createPdf(panel1, pdf, cheminPDF);

        //création d'un Label pour le nombre de pages restantes
        JLabel nombreOfPage = new JLabel("| " + String.valueOf(nombrePage));
        button.add(nombreOfPage);

        ajoutComposantsDouble(panel1, button, panelCont1, containerPDF, background, frame, scrollPaneFrame);

        // déclaration de deux objets Counter
        Counter c = new Counter();
        NbScroll nbScroll = new NbScroll();
        Tab tab = new Tab();


        frame.addKeyListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                            c, height,frame,scrollPaneFrame, nbScroll, tab));
        frame.addMouseWheelListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                     c, height,frame,scrollPaneFrame, nbScroll, tab));
        pagePrecedente.addKeyListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                        c, height,frame,scrollPaneFrame, nbScroll, tab));

        pageSuivante.addKeyListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                    c, height,frame,scrollPaneFrame,nbScroll, tab));

        choixPage.addKeyListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                   c, height,frame,scrollPaneFrame, nbScroll, tab));

        scrollPaneFrame.addMouseWheelListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                               c, height,frame,scrollPaneFrame, nbScroll, tab));
        panel1.addMouseWheelListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                         c, height,frame,scrollPaneFrame, nbScroll, tab));
        background.addMouseWheelListener(new Clavier(pageSuivante, pagePrecedente, choixPage, nombreOfPage, panel1,
                                            c, height,frame,scrollPaneFrame, nbScroll, tab));


        configJButton(pageSuivante, pagePrecedente, choixPage, nombreOfPage, scrollPaneFrame, c, height, containerPDF);


    }

}
