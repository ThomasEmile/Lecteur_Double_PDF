/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel {

    public int tailleEspace = 22;
    private JPanel containerButton;
    public ContainerPDF containerPDF;
    private JButton pageSuivante;
    private JButton pagePrecedente;
    public JTextField choixPage;
    public JLabel nombreDePage;
    private FenetreApp fenetre;

    // déclaration de deux objets Counter
    public Counter c = new Counter();
    
    
    public ButtonPanel(FenetreApp fenetre) {
        c.setValue(1);
        this.fenetre = fenetre;
        this.containerPDF = fenetre.container;
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
                if (fenetre.getButton().c.getValue() + 1 <= fenetre.container.nombrePage) {
                    fenetre.getButton().c.increment();
                    fenetre.clavierSouris.goTo(fenetre.getButton().c.getValue());
                }
            }
        });

        // évènement page precedente
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fenetre.getButton().c.getValue() - 1 > 0) {
                    fenetre.getButton().c.decrease();
                    fenetre.clavierSouris.goTo(fenetre.getButton().c.getValue());
                }

            }
        });

        // évènement affiche la page entrée par l'utilisateur
        choixPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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
