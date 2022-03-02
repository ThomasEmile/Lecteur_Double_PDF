/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu {

    private JPanel containerButton;
    public ContainerPDF containerPDF;
    private JButton pageSuivante;
    private JButton pagePrecedente;
    public JTextField choixPage;
    public JLabel nombreDePage;
    private FenetreApp fenetre;

    // création de la MenuBar afin d'implémenter les fonctionnalités : ouvrir, fermer et réduire un document pdf
    private JMenuBar menuBarMainWindow;
    private JMenu menuMainWindow;
    private JMenuItem ouvrir;
    private JMenuItem quitter;

    // déclaration de deux objets Counter
    public Counter c = new Counter();
    
    
    public Menu(FenetreApp fenetre) {
        c.setValue(1);
        this.fenetre = fenetre;
        this.containerPDF = fenetre.container;
        menuBarMainWindow = new JMenuBar();
        menuMainWindow = new JMenu("Fichier");
        ouvrir = new JMenuItem("Ouvrir");
        quitter = new JMenuItem("Quitter l'application");
        containerButton = new JPanel();
        Dimension dimension = new Dimension(1920, 60);
        containerButton.setMaximumSize(dimension);
        containerButton.setMinimumSize(dimension);
        pageSuivante = new JButton("↓");
        pageSuivante.setFocusable(false);
        pagePrecedente = new JButton("↑");
        pagePrecedente.setFocusable(false);
        choixPage = new JTextField(2);
        choixPage.setText("1");
        nombreDePage = new JLabel("| " + String.valueOf(0));
        addListeners(fenetre.clavierSouris);
    }



    void configButton() {

        pageSuivante.setPreferredSize(new Dimension(50,50));
        pagePrecedente.setPreferredSize(new Dimension(50,50));
        choixPage.setPreferredSize(new Dimension(50,30));

        pageSuivante.setFont(new Font("Serif", Font.BOLD, 20));
        pagePrecedente.setFont(new Font("Serif", Font.BOLD, 20));
        choixPage.setFont(new Font("Arial", Font.PLAIN, 20));
        nombreDePage.setFont(new Font("Arial", Font.PLAIN, 20));

        pagePrecedente.setContentAreaFilled(false);
        pageSuivante.setContentAreaFilled(false);
        containerButton.setBackground(new Color(184, 184, 184));

        // évènement page suivante
        pageSuivante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.clavierSouris.pageSuivante(fenetre);
            }
        });

        // évènement page precedente
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.clavierSouris.pagePrecedente(fenetre);

            }
        });

        // évènement qui ouvre un nouveau document pdf
        ouvrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestionFenetre.nouvelleFenetre();
            }
        });

        // évènement qui quitte l'application
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

    }

    void ajoutComposants() {
        fenetre.getMainWindow().setJMenuBar(menuBarMainWindow);
        menuMainWindow.add(ouvrir);
        menuMainWindow.add(quitter);
        menuBarMainWindow.add(menuMainWindow);
        containerButton.add(pageSuivante);
        containerButton.add(pagePrecedente);
        containerButton.add(choixPage);
        containerButton.add(nombreDePage);
        fenetre.getContainer().getContainerDocumentPDF().add(fenetre.getContainer().documentPDF);
        fenetre.getBackground().add(containerButton);
        fenetre.getBackground().add(fenetre.getContainer().getScrollPaneContainer());
        fenetre.getMainWindow().add(fenetre.getBackground());
        fenetre.getMainWindow().validate();
    }


    public void addListeners(ClavierSouris clavier) {
        clavier.setButtonPanel(this);
        this.nombreDePage.addKeyListener(clavier);
        this.choixPage.addKeyListener(clavier);
        this.pagePrecedente.addKeyListener(clavier);
        this.pageSuivante.addKeyListener(clavier);
    }


    static class Counter {
        private int value = 0;


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
