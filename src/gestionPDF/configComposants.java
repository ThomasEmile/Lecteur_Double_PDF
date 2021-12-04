package gestionPDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gestionPDF.createPagePDF.nombrePage;
import static gestionPDF.createPagePDF.tailleEspace;

/**
 *
 */
public class configComposants {

    /**
     * methode qui configure le JFrame entré en argument
     * @param frame
     */
    public static void configJFrameDouble(JFrame frame, String cheminIconApp) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setBackground(new Color(77,74,73));
        ImageIcon iconApp = new ImageIcon(cheminIconApp);
        frame.setIconImage(iconApp.getImage());
    }

    /**
     * methode qui configure les JPanel entré en argument
     * @param panel1
     * @param background
     * @param button
     */
    public static void configJPanel(JPanel panel1, JPanel background, JPanel button) {

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        button.setBackground(new Color(184, 184, 184));
        button.setBackground(new Color(184, 184, 184));
    }

    /**
     * méthode qui ajoute les différents composants afin de les afficher sur un JFrame
     * @param panel1
     * @param button
     * @param panelCont1
     * @param containerPDF
     * @param background
     * @param frame
     * @param scrollPaneFrame
     */
    public static void ajoutComposantsDouble(JPanel panel1, JPanel button,JPanel panelCont1, JPanel containerPDF,
                                             JPanel background, JFrame frame, JScrollPane scrollPaneFrame) {
        panelCont1.add(panel1);

        containerPDF.add(panelCont1);

        background.add(button);
        background.add(scrollPaneFrame);

        frame.add(background);
        frame.validate();
    }

    /**
     * méthode qui configure le JScrollPane entré en argument
     * @param scrollPaneFrame
     */
    public static void configJScrollPane(JScrollPane scrollPaneFrame) {
        scrollPaneFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneFrame.getVerticalScrollBar().setUnitIncrement(10);
        scrollPaneFrame.getHorizontalScrollBar().setUnitIncrement(10);
    }

    /**
     * méthode qui configure l'action des différents JButton entrés en argument
     * @param pageSuivante
     * @param pagePrecedente
     * @param choixPage
     * @param c
     * @param height
     */
    public static void configJButton(JButton pageSuivante, JButton pagePrecedente, JTextField choixPage, JLabel nombreOfPage,
                                     JScrollPane scrollPaneFrame, Counter c, int height, JPanel countainerPDF)  {

        pageSuivante.setPreferredSize(new Dimension(50,50));
        pagePrecedente.setPreferredSize(new Dimension(50,50));
        choixPage.setPreferredSize(new Dimension(50,30));

        pageSuivante.setFont(new Font("Serif", Font.BOLD, 20));
        pagePrecedente.setFont(new Font("Serif", Font.BOLD, 20));
        choixPage.setFont(new Font("Arial", Font.PLAIN, 20));
        nombreOfPage.setFont(new Font("Arial", Font.PLAIN, 20));

        pagePrecedente.setContentAreaFilled(false);
        pageSuivante.setContentAreaFilled(false);


        choixPage.setBackground(new Color(225, 222, 222));
        choixPage.setText("1");

        // évènement page suivante
        pageSuivante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (c.getValue() != nombrePage-1) {
                    c.increment();
                    int y = (height+tailleEspace) * (c.getValue());
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1 + c.getValue()));
                }
            }
        });

        // évènement page precedente
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (c.getValue() != 0) {
                    c.decrease();
                    int y = (height+tailleEspace) * (c.getValue());
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
            }
        });

        // évènement affiche la page entrée par l'utilisateur
        choixPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(choixPage.getText()) <= nombrePage && Integer.parseInt(choixPage.getText()) > 0) {
                    int y = (height+tailleEspace) * (Integer.parseInt(choixPage.getText())-1);
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    c.setValue(Integer.parseInt(choixPage.getText())-1);
                }
            }
        });



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

