package gestionPDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel {

    private int tailleEspace = 22;
    private JPanel containerButton;

    private JMenuBar menuBarMainWindow;
    private JMenu menuMainWindow;
    private JMenuItem ouvrir;
    private JMenuItem fermer;
    private JMenuItem reduire;
    private boolean verifZoom = false;
    private JButton zoom;
    private JButton dezoom;
    private JButton pageSuivante;
    private JButton pagePrecedente;
    public JTextField choixPage;
    private JLabel nombreDePage;
    private FenetreApp fenetre;


    public ButtonPanel(FenetreApp fenetre) {
        this.fenetre = fenetre;
        containerButton = new JPanel();
        menuBarMainWindow = new JMenuBar();
        menuMainWindow = new JMenu("Fichier");
        ouvrir = new JMenuItem("ouvrir");
        fermer = new JMenuItem("fermer");
        reduire = new JMenuItem("reduire");
        pageSuivante = new JButton("↓");
        pagePrecedente = new JButton("↑");
        zoom = new JButton("+");
        dezoom = new JButton("-");
        choixPage = new JTextField(2);
        nombreDePage = new JLabel("| " + String.valueOf(fenetre.getContainer().getNombrePage()));
        configButton();
        ajoutComposants();
    }

    // déclaration de deux objets Counter
    Counter c = new Counter();

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
                //TODO
            }
        });

        // évènement qui zoom un pdf
        zoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verifZoom = true;
                fenetre.getContainer().getDocumentPDF2().setVisible(true);
                fenetre.getContainer().getDocumentPDF().setVisible(false);
            }
        });

        // évènement qui dezoom un pdf
        dezoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verifZoom = false;
                fenetre.getContainer().getDocumentPDF2().setVisible(false);
                fenetre.getContainer().getDocumentPDF().setVisible(true);
            }
        });
    }

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
