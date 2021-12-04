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
    configComposants.Counter c;
    int height;

    public Clavier (JButton pageSuivante, JButton pagePrecedente, JTextField choixPage, JLabel nombreOfPage,
                    JPanel panel1, configComposants.Counter c, int height, JFrame frame) {
        this.pagePrecedente = pagePrecedente;
        this.pageSuivante = pageSuivante;
        this.gestionTab = 0;
        this.choixPage = choixPage;
        this.nombreOfPage = nombreOfPage;
        this.frame = frame;
        panel = panel1;
        this.c = c;
        this.height = height;

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
        int code = e.getKeyCode();
        switch (code) {
            case (KeyEvent.VK_RIGHT):
                panel.requestFocus();
                if (c.getValue() != nombrePage - 1) {
                    c.increment();
                    int y = (height + tailleEspace) * -(c.getValue());
                    panel.setLocation(0, y);
                    choixPage.setText(String.valueOf(1 + c.getValue()));
                }
                break;
            case (KeyEvent.VK_LEFT):
                // todo essayer de garder le focus sur la frame pour pouvoir scroll et changer de page avec les fleches
                panel.requestFocus();
                if (c.getValue() != 0) {
                    c.decrease();
                    int y = (height + tailleEspace) * -(c.getValue());
                    panel.setLocation(0, y);
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
