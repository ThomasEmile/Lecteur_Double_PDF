package gestionPDF;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static gestionPDF.createPagePDF.nombrePage;
import static gestionPDF.createPagePDF.tailleEspace;

public class Clavier implements KeyListener, MouseWheelListener {
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
    configComposants.NbScroll nbScroll;
    configComposants.Tab tab;

    public Clavier (JButton pageSuivante, JButton pagePrecedente, JTextField choixPage, JLabel nombreOfPage,
                    JPanel panel1, configComposants.Counter c, int height, JFrame frame, JScrollPane scrollPaneFrame,
                    configComposants.NbScroll nbScroll, configComposants.Tab tab) {
        this.pagePrecedente = pagePrecedente;
        this.pageSuivante = pageSuivante;
        this.gestionTab = 0;
        this.choixPage = choixPage;
        this.nombreOfPage = nombreOfPage;
        this.frame = frame;
        this.panel = panel1;
        this.c = c;
        this.height = height;
        this.scrollPaneFrame = scrollPaneFrame;
        this.nbScroll = nbScroll;
        this.tab = tab;

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
                    nbScroll.setValue(c.getValue()*10);
                    y = (height+tailleEspace) * (c.getValue());
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
                break;
            case (KeyEvent.VK_LEFT):
                panel.requestFocus();
                if (c.getValue() != 0) {
                    c.decrease();
                    nbScroll.setValue(c.getValue()*10);
                     y = (height+tailleEspace) * (c.getValue());
                    scrollPaneFrame.getVerticalScrollBar().setValue(y);
                    choixPage.setText(String.valueOf(1+c.getValue()));
                }
                break;
            case (KeyEvent.VK_DOWN) :
                descendre();
                break;
            case (KeyEvent.VK_UP) :
                monter();
                break;
            case (KeyEvent.VK_A):
                if (tab.getValue() > 3) {
                    tab.decrease();
                } else {
                    tab.increment();
                }
                switch (tab.getValue()) {
                    case 1 : pageSuivante.requestFocus();
                    break;
                    case 2 : pagePrecedente.requestFocus();
                    break;
                    case 3 : choixPage.requestFocus();
                    break;
                }
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

    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            System.out.println(notches);
            monter();
        } else {
            System.out.println(notches);
            descendre();
        }
    }

    private void descendre() {
        int y = scrollPaneFrame.getVerticalScrollBar().getValue();
        scrollPaneFrame.getVerticalScrollBar().setValue(y+50);

        if (c.getValue() != nombrePage-1) {
            c.setValue(scrollPaneFrame.getVerticalScrollBar().getValue()/height);

            choixPage.setText(String.valueOf(1 + c.getValue()));
        }
    }

    private void monter() {


        int y = scrollPaneFrame.getVerticalScrollBar().getValue();
        scrollPaneFrame.getVerticalScrollBar().setValue(y-50);
        if (c.getValue() != 0) {
            c.setValue(scrollPaneFrame.getVerticalScrollBar().getValue()/height);

            choixPage.setText(String.valueOf(1 + c.getValue()));
        }
    }
}
