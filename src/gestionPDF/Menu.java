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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu {


    ImageIcon iconMode;
    ImageIcon iconZoom;
    ImageIcon iconBlank = new ImageIcon();


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
    private JMenu ouvrir;
    private JMenuItem ouvertureMemeFenetre;
    private JMenuItem ouvertureNouvelleFenetre;
    private JMenuItem quitter;

    private JMenu menuMode;
    private JMenuItem modeUnifier;
    private JMenuItem modeDifferencier;

    private JMenu menuZoom;
    private JMenuItem pleinePage;
    private JMenuItem pleineLargeur;
    private JMenuItem affichageClassique;

    private JMenu menuAide;
    private JMenuItem aideRaccourci;

    // déclaration de deux objets Counter
    public Counter c = new Counter();
    
    
    public Menu(FenetreApp fenetre) {
        c.setValue(1);
        this.fenetre = fenetre;
        this.containerPDF = fenetre.container;
        menuBarMainWindow = new JMenuBar();
        configMenu();
        setIcon();
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

    void setIcon(){

        int largeur = 15;
        int hauteur = 15;

        iconMode = new ImageIcon("icon/iconModeUnifieDifferencie.png");
        iconZoom = new ImageIcon("icon/iconModeZoom.png");

        Image imgMode = iconMode.getImage();
        Image imgZoom = iconZoom.getImage();
        imgMode	= imgMode.getScaledInstance(largeur,hauteur,Image.SCALE_DEFAULT);
        imgZoom	= imgZoom.getScaledInstance(largeur,hauteur,Image.SCALE_DEFAULT);

        iconZoom.setImage(imgZoom);
        iconMode.setImage(imgMode);

        affichageClassique.setIcon( iconZoom );
        setModeUnifier();


    }
    void configMenu(){



        menuMainWindow = new JMenu("Fichier");
        ouvrir = new JMenu("Ouvrir");
        ouvertureMemeFenetre = new JMenuItem("Sur cette fenetre");
        ouvertureNouvelleFenetre = new JMenuItem("Nouvelle fenetre");
        quitter = new JMenuItem("Quitter l'application");
        menuMode = new JMenu("Mode");
        modeUnifier = new JMenuItem("Mode Unifié");
        modeDifferencier = new JMenuItem("Mode Différencié");
        menuZoom = new JMenu("Zoom");
        affichageClassique = new JMenuItem("Affichage Classique");
        pleineLargeur = new JMenuItem("Pleine Largeur");
        pleinePage = new JMenuItem("Pleine Page");
        menuAide = new JMenu("Aide");
        aideRaccourci = new JMenuItem("Raccourci");

        menuMainWindow.setFont(new Font("SansSerif", Font.PLAIN, 16));
        menuZoom.setFont(new Font("SansSerif", Font.PLAIN, 16));
        menuMode.setFont(new Font("SansSerif", Font.PLAIN, 16));
        menuAide.setFont(new Font("SansSerif", Font.PLAIN, 16));

        ouvrir .setFont(new Font("SansSerif", Font.PLAIN, 12));
        ouvertureMemeFenetre.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ouvertureNouvelleFenetre.setFont(new Font("SansSerif", Font.PLAIN, 12));
        quitter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        modeUnifier.setFont(new Font("SansSerif", Font.PLAIN, 12));
        modeDifferencier.setFont(new Font("SansSerif", Font.PLAIN, 12));
        affichageClassique.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pleineLargeur.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pleinePage.setFont(new Font("SansSerif", Font.PLAIN, 12));
        aideRaccourci.setFont(new Font("SansSerif", Font.PLAIN, 12));



        // évènement mode Unifié
        modeUnifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.clavierSouris.setUnified(true);
            }
        });

        // évènement mode Différencié
        modeDifferencier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.clavierSouris.setUnified(false);
            }
        });

        // évènement pleine Page
        pleinePage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                affichageClassique.setIcon( iconBlank );
                pleineLargeur.setIcon(iconBlank);
                pleinePage.setIcon(iconZoom);
                //TODO mettre la méthode de pleine page
            }
        });

        // évènement pleine Largeur
        pleineLargeur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                affichageClassique.setIcon( iconBlank );
                pleineLargeur.setIcon(iconZoom);
                pleinePage.setIcon(iconBlank);
                //TODO mettre la méthode de pleine largeur pour le moment cela zoom ou dezoom
                fenetre.clavierSouris.zoom();
            }
        });

        // évènement page classique
        affichageClassique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                affichageClassique.setIcon( iconZoom );
                pleineLargeur.setIcon(iconBlank);
                pleinePage.setIcon(iconBlank);
                //TODO mettre la méthode de page classique pour le moment cela zoom ou dezoom
                fenetre.clavierSouris.zoom();
            }
        });

        // évènement qui ouvre un nouveau document pdf
        ouvertureMemeFenetre.addActionListener(new ActionListener() {
            // TODO mettre l'option permettant d'ouvrir sur la meme fenetre pour le moment cela créer une nouvelle fenetre
            public void actionPerformed(ActionEvent e) {
                GestionFenetre.nouvelleFenetre();
            }
        });

        // évènement qui ouvre un nouveau document pdf sur une nouvelle fenetre
        ouvertureNouvelleFenetre.addActionListener(new ActionListener() {
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

    void setModeUnifier(){
        modeUnifier.setIcon( iconMode );
        modeDifferencier.setIcon(iconBlank);

    }

    void setModeDifferencier(){
        modeUnifier.setIcon( iconBlank );
        modeDifferencier.setIcon(iconMode);

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



    }

    void ajoutComposants() {
        fenetre.getMainWindow().setJMenuBar(menuBarMainWindow);
        menuMainWindow.add(ouvrir);
        ouvrir.add(ouvertureMemeFenetre);
        ouvrir.add(ouvertureNouvelleFenetre);
        //menuMainWindow.add(fermerFenetre);
        menuMainWindow.add(quitter);
        menuBarMainWindow.add(menuMainWindow);
        menuMode.add(modeUnifier);
        menuMode.add(modeDifferencier);
        menuBarMainWindow.add(menuMode);
        menuZoom.add(affichageClassique);
        menuZoom.add(pleineLargeur);
        menuZoom.add(pleinePage);
        menuBarMainWindow.add(menuZoom);
        menuAide.add(aideRaccourci);
        menuBarMainWindow.add(menuAide);
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
