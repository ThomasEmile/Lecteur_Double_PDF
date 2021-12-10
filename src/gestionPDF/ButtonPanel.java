/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

/*
 *  gestion des buttons que l'on va implémenter dans l'application
 */
package gestionPDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cette classe s'occupe de gérer la configuration des buttons de l'application. L'application contient également
 * la méthode ajoutComposants qui permet comme son nom l'indique, d'implémenter les composants de manière correcte
 * dans une fenêtre de type JFrame, fenètre que l'on va ensuite afficher à l'écran.
 * @author Groupe Pdf Double affichage
 * @version 1.0
 */
public class ButtonPanel {

    private int tailleEspace = 22; // entier qui représente la taille d'un espace entre chaque page
    private JPanel containerButton; // panel qui contiendra les buttons de l'application

    // création de la MenuBar afin d'implémenter les fonctionnalités : ouvrir, fermer et réduire un document pdf
    private JMenuBar menuBarMainWindow;
    private JMenu menuMainWindow;
    private JMenuItem ouvrir;
    private JMenuItem fermer;
    private JMenuItem reduire;

    private boolean verifZoom = false; //boolean qui vérifi si oui ou non le button zoom a été appuyé

    private Counter c; // création d'un counter permettant de récupérer le numéro de page courant du pdf

    private JButton zoom; // button qui permet d'afficher le panel contenant le fichier pdf zoomé
    private JButton dezoom; // button qui permet d'afficher le panel contenant le fichier pdf zoomé
    private JButton pageSuivante; // button qui permet de passer à la page suivante
    private JButton pagePrecedente;// button qui permet de passer à la page precedente
    private JTextField choixPage; // champ de texte qui d'afficher la page du pdf rentrée en argument
    private JLabel nombreDePage;  // label qui affiche le nombre de page du document pdf

    private FenetreApp fenetre;  // implémentation de la classe FenetreApp afin de pouvoir accéder à ses méthodes

    /**
     * constructeur de la classe ButtonPanel
     * @param fenetre
     */
    public ButtonPanel(FenetreApp fenetre) {
        this.fenetre = fenetre;

        //initialisation des différents composants de la classe
        containerButton = new JPanel();
        menuBarMainWindow = new JMenuBar();
        menuMainWindow = new JMenu("Fichier");
        ouvrir = new JMenuItem("Ouvrir");
        fermer = new JMenuItem("Fermer");
        reduire = new JMenuItem("Reduire");
        pageSuivante = new JButton("↓");
        pageSuivante.setFocusable(false);
        pagePrecedente = new JButton("↑");
        pagePrecedente.setFocusable(false);
        zoom = new JButton("+");
        zoom.setFocusable(false);
        dezoom = new JButton("-");
        dezoom.setFocusable(false);
        choixPage = new JTextField(2);
        nombreDePage = new JLabel("| " + String.valueOf(1));
        nombreDePage.setFocusable(false);
        c = new Counter();

        //appel des méthodes de la classe
        configButton();
        ajoutComposants();
    }

    // Getter de la classe ButtonPanel
    public int getTailleEspace() {return tailleEspace;}
    public JTextField getChoixPage() {return choixPage;}
    public Counter getC() {return c;}

    public JPanel getContainerButton() {
        return containerButton;
    }

    public void setNombreDePage(int nbDePage) {
        this.nombreDePage.setText("|" + String.valueOf(nbDePage));
    }

    public void setChoixPage(int i) {
        this.choixPage.setText(String.valueOf(i));
    }

    /**
     * configuration des différents buttons de la classe
     */
    void configButton() {

        pageSuivante.setPreferredSize(new Dimension(50,40));
        pagePrecedente.setPreferredSize(new Dimension(50,40));
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
                if(verifZoom) {
                    if (c.getValue() != fenetre.getContainer().getNombrePage()-1) {
                        c.increment();
                        int y = (fenetre.getContainer().getHeightZoom() +tailleEspace) * (c.getValue());
                        fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                        choixPage.setText(String.valueOf(1+c.getValue()));
                    }
                } else {
                    if (c.getValue() != fenetre.getContainer().getNombrePage()-1) {
                        c.increment();
                        int y = (fenetre.getContainer().getHeight() +tailleEspace) * (c.getValue());
                        fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                        choixPage.setText(String.valueOf(1+c.getValue()));
                    }
                }

            }
        });

        // évènement page precedente
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (verifZoom) {
                    if (c.getValue() != 0) {
                        c.decrease();
                        int y = (fenetre.getContainer().getHeightZoom()+tailleEspace) * (c.getValue());
                        fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                        choixPage.setText(String.valueOf(1+c.getValue()));
                    }
                } else {
                    if (c.getValue() != 0) {
                        c.decrease();
                        int y = (fenetre.getContainer().getHeight()+tailleEspace) * (c.getValue());
                        fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                        choixPage.setText(String.valueOf(1+c.getValue()));
                    }
                }
            }
        });

        // évènement affiche la page entrée par l'utilisateur
        choixPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (verifZoom) {
                    if (Integer.parseInt(choixPage.getText()) <= fenetre.getContainer().getNombrePage() && Integer.parseInt(choixPage.getText()) > 0) {
                        int y = (fenetre.getContainer().getHeightZoom() +tailleEspace) * (Integer.parseInt(choixPage.getText())-1);
                        fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                        c.setValue(Integer.parseInt(choixPage.getText())-1);
                    }
                } else {
                    if (Integer.parseInt(choixPage.getText()) <= fenetre.getContainer().getNombrePage() && Integer.parseInt(choixPage.getText()) > 0) {
                        int y = (fenetre.getContainer().getHeight() +tailleEspace) * (Integer.parseInt(choixPage.getText())-1);
                        fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                        c.setValue(Integer.parseInt(choixPage.getText())-1);
                    }
                }
            }
        });

        // évènement qui ferme le frame courant
        fermer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.getMainWindow().dispose();
            }
        });

        // évènement qui réduit le frame courant
        reduire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.getMainWindow().setExtendedState(JFrame.ICONIFIED);
            }
        });

        // évènement qui ouvre un nouveau document pdf
        ouvrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.getContainer().createPdf();
            }
        });

        // évènement qui zoom un pdf
        zoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.getContainer().setZoomed(true);
                fenetre.getContainer().getDocumentPDF2().setVisible(true);
                fenetre.getContainer().getDocumentPDF().setVisible(false);
            }
        });

        // évènement qui dezoom un pdf
        dezoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.getContainer().setZoomed(false);
                fenetre.getContainer().getDocumentPDF2().setVisible(false);
                fenetre.getContainer().getDocumentPDF().setVisible(true);
            }
        });
    }

    /**
     * ajout des composants de l'application aux JFrame mainWindow
     */
    void ajoutComposants() {
        fenetre.getMainWindow().setJMenuBar(menuBarMainWindow);
        menuMainWindow.add(ouvrir);
        menuMainWindow.add(fermer);
        menuMainWindow.add(reduire);
        menuBarMainWindow.add(menuMainWindow);
        containerButton.add(zoom);
        containerButton.add(dezoom);
        containerButton.add(pageSuivante);
        containerButton.add(pagePrecedente);
        containerButton.add(choixPage);
        containerButton.add(nombreDePage);
        fenetre.getContainer().getContainerDocumentPDF().add(fenetre.getContainer().getDocumentPDF());
        fenetre.getContainer().getContainerDocumentPDF().add(fenetre.getContainer().getDocumentPDF2());
        fenetre.getBackground().add(containerButton);
        fenetre.getBackground().add(fenetre.getContainer().getScrollPaneContainer());
        fenetre.getMainWindow().add(fenetre.getBackground());
        fenetre.getMainWindow().validate();
    }

    /**
     * classe permettant, par le biais d'un compteur, de récupérer de le numéro de la page courante du fichier pdf
     */
    static class Counter {

        private int value = 0;


        void increment() {

            value++;
        }

        void decrease() {

            value--;
        }


        public int getValue() {

            return value;
        }

        public void setValue(int value) {

            this.value = value;
        }

    }


}
