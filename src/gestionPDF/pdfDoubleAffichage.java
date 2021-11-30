package gestionPDF;

import java.awt.*;
import javax.swing.*;

import static gestionPDF.configComposants.*;
import static gestionPDF.createPagePDF.*;

public class pdfDoubleAffichage extends JPanel {

    public static void main(String[] args) throws Exception {

        String nomJFrame = "pdfDoubleAffichage";
        String chemin = "C:/Cours/Réseau/1ère année/cours_p1.pdf";

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        //Création de JFrame
        JFrame frame = new JFrame(nomJFrame);

        // configuration du JFrame
        configJFrameDouble(frame);

        // panel background
        JPanel background = new JPanel();

        // panel rassemblant les boutons pour gérer l'affichage du
        JPanel buttonForAllPage = new JPanel();

        //panel contenant le pdf
        JPanel containerPDF = new JPanel();

        // panel contenant le panel pdf
        JPanel panelCont1 = new JPanel();

        // panel pdf 1
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1));

        // panel boutons
        JPanel Header = new JPanel();

        JLabel[] pdf = new JLabel[100];

        //création bouton pour passer à la page suivante
        JButton pageSuivante = new JButton("↓");
        Header.add(pageSuivante);

        //création bouton pour passer à la page précédente
        JButton pagePrecedente = new JButton("↑");
        Header.add(pagePrecedente);

        //création textField pour rentrer la page souhaitée
        JTextField choixPage = new JTextField(2);
        Header.add(choixPage);

        //déclaration d'un objet JScrollPane afin de pouvoir scroll dans la fenêtre
        JScrollPane scrollPaneFrame = new JScrollPane(background);

        //configuration du scrollPane de la fenêtre
        configJScrollPane(scrollPaneFrame);

        // configuration du layout des panels
        configJPanel(panel1, background, buttonForAllPage, Header);

        // création du premier document PDF
        createPdf(panel1, pdf, chemin);

        ajoutComposantsDouble(panel1, Header, panelCont1, buttonForAllPage, containerPDF, background, frame, scrollPaneFrame);

        // déclaration de deux objets Counter
        Counter c = new Counter();

        configJButton(pageSuivante, pagePrecedente, choixPage, panel1, c, height);

        //création d'un Label pour le nombre de pages restantes
        JLabel NombrePage = new JLabel(String.valueOf(nombrePage));
        Header.add(NombrePage);


    }
}
