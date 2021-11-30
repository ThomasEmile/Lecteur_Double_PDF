package gestionPDF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class configComposants {

    /**
     * methode qui configure le JFrame entré en argument
     * @param frame
     */
    public static void configJFrameDouble(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     * methode qui configure les JPanel entré en argument
     * @param panel1
     * @param background
     * @param buttonForAllPage
     * @param header
     */
    public static void configJPanel(JPanel panel1, JPanel background, JPanel buttonForAllPage, JPanel header) {

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        buttonForAllPage.setBackground(Color.getHSBColor(1, 1, 30));
        header.setBackground(Color.getHSBColor(1, 1, 30));
    }

    /**
     * méthode qui ajoute les différents composants afin de les afficher sur un JFrame
     * @param panel1
     * @param Header
     * @param panelCont1
     * @param buttonForPage
     * @param containerPDF
     * @param background
     * @param frame
     * @param scrollPaneFrame
     */
    public static void ajoutComposantsDouble(JPanel panel1, JPanel Header,JPanel panelCont1, JPanel buttonForPage,
                                             JPanel containerPDF, JPanel background, JFrame frame, JScrollPane scrollPaneFrame) {
        panelCont1.add(panel1);

        buttonForPage.add(Header);
        containerPDF.add(panelCont1);

        background.add(buttonForPage);
        background.add(containerPDF);

        frame.add(scrollPaneFrame);
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
     * @param fenetreDouble
     * @param DoublePage
     * @param choixPage
     * @param headerPDF2
     * @param panel1
     * @param c
     * @param height
     */
    public static void configJButton(JButton pageSuivante, JButton pagePrecedente, JTextField choixPage, JPanel panel1,
                                     Counter c, int height)  {

        // évènement page suivante
        pageSuivante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.decrease();
                int y = (height+17) * c.getValue();
                panel1.setLocation(0, y);
            }
        });

        // évènement page precedente
        pagePrecedente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.increment();
                int y = (height+17) * c.getValue();
                panel1.setLocation(0, y);
            }
        });

        // évènement affiche la page entrée par l'utilisateur
        choixPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int y = (height+17) * -(Integer.parseInt(choixPage.getText())-1);
                panel1.setLocation(0, y);
                c.setValue(Integer.parseInt(choixPage.getText())-1);
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

