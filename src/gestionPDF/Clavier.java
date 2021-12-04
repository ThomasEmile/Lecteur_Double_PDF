package gestionPDF;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static gestionPDF.createPagePDF.nombrePage;
import static gestionPDF.createPagePDF.tailleEspace;

public class Clavier implements KeyListener {
    private JFrame frame;
    private int gestionTab;
    private JButton pageSuivante;
    private JButton pagePrecedente;
    private JTextField choixPage;
    private JLabel nombreOfPage;
    private JPanel panel;
    private JScrollPane scrollPaneFrame;
    configComposants.Counter c;
    int height;
    int nbScroll;

    public Clavier (JButton pageSuivante, JButton pagePrecedente, JTextField choixPage, JLabel nombreOfPage,
                    JPanel panel1, configComposants.Counter c, int height, JFrame frame, JScrollPane scrollPaneFrame) {
        this.pagePrecedente = pagePrecedente;
        this.pageSuivante = pageSuivante;
        this.gestionTab = 0;
        this.choixPage = choixPage;
        this.nombreOfPage = nombreOfPage;
        this.frame = frame;
        panel = panel1;
        this.c = c;
        this.height = height;
        this.scrollPaneFrame = scrollPaneFrame;
        this.nbScroll = 0;

    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int y;
        int code = e.getKeyCode();
        switch (code) {
            case (KeyEvent.VK_RIGHT):
                panel.requestFocus();
                if (c.getValue() != nombrePage-1) {
                    c.increment();
                     y = (height+tailleEspace) * (c.getValue());
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
                break;
            case (KeyEvent.VK_LEFT):
                panel.requestFocus();
                if (c.getValue() != 0) {
                    c.decrease();
                     y = (height+tailleEspace) * (c.getValue());
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
                break;
            case (KeyEvent.VK_DOWN) :
                if (nbScroll < nombrePage*6) nbScroll++;
                 y = (height+tailleEspace) * (nbScroll) / 6;
                scrollPaneFrame.getVerticalScrollBar().setValue(y);
                if (c.getValue() != nombrePage-1 && nbScroll % 6 == 0) {
                    System.out.println(c.getValue());
                    c.setValue(nbScroll / 6);
                    choixPage.setText(String.valueOf(1 + c.getValue()));
                }
                break;
            case (KeyEvent.VK_UP) :
                if (nbScroll > 0) nbScroll--;
                 y = (height+tailleEspace) * (nbScroll) / 6;
                scrollPaneFrame.getVerticalScrollBar().setValue(y);
                if (c.getValue() != 0 && nbScroll%6 == 0) {  System.out.println(c.getValue());
                    c.setValue(nbScroll/6);

                    choixPage.setText(String.valueOf(1 + c.getValue()));
                }
                break;
            case (KeyEvent.VK_E):

                gestionTab++;
                if (gestionTab == 1) {
                    pageSuivante.requestFocus();
                }
                if (pageSuivante.isFocusOwner()) {
                    pagePrecedente.requestFocus();
                }
                break;
        }
        }


    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
