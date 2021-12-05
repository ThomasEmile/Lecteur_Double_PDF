package gestionPDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel {

    public int tailleEspace = 22;
    private JPanel containerButton;
    private JButton pageSuivante;
    private JButton pagePrecedente;
    public JTextField choixPage;
    public JLabel nombreDePage;
    private FenetreApp fenetre;

    // déclaration de deux objets Counter
    Counter c = new Counter();
    
    
    public ButtonPanel(FenetreApp fenetre) {
        this.fenetre = fenetre;
        containerButton = new JPanel();
        pageSuivante = new JButton("↓");
        pagePrecedente = new JButton("↑");
        choixPage = new JTextField(2);
        c.setValue(0);
        choixPage.setText("1");
        nombreDePage = new JLabel("| " + String.valueOf(fenetre.getContainer().getNombrePage()));
        configButton();
        ajoutComposants();
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
                if (c.getValue() != fenetre.getContainer().getNombrePage()-1) {
                    c.increment();
                    int y = (fenetre.getContainer().height+tailleEspace) * (c.getValue());
                    fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
            }
        });

        // évènement page precedente
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (c.getValue() != 0) {
                    c.decrease();
                    int y = (fenetre.getContainer().getHeight()+tailleEspace) * (c.getValue());
                    fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
            }
        });

        // évènement affiche la page entrée par l'utilisateur
        choixPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(choixPage.getText()) <= fenetre.getContainer().getNombrePage() && Integer.parseInt(choixPage.getText()) > 0) {
                    int y = (fenetre.getContainer().height+tailleEspace) * (Integer.parseInt(choixPage.getText())-1);
                    fenetre.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
                    c.setValue(Integer.parseInt(choixPage.getText())-1);
                }
            }
        });
    }

    void ajoutComposants() {
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

    public void addListeners(Clavier clavier) {
        clavier.setButtonPanel(this);
        this.fenetre.mainWindow.addKeyListener(clavier);
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
