package gestionPDF;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Clavier implements KeyListener, MouseWheelListener, MouseMotionListener, MouseListener {

    private ContainerPDF containerPDF;
    private ButtonPanel buttonPanel;
    private FenetreApp fenetreApp;
    private int y;
    public Clavier(FenetreApp fenetreAPP) {
        y=0;
    }

    public void setContainerPDF(ContainerPDF containerPDF) {
        this.containerPDF = containerPDF;

    }

    public void setButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public void setFenetreApp(FenetreApp fenetreApp) {
        this.fenetreApp = fenetreApp;
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
        if (fenetreApp.getButton().getChoixPage().hasFocus()) {
            switch (code) {
                case (KeyEvent.VK_ESCAPE):
                    fenetreApp.mainWindow.requestFocus();
                    break;
                case (KeyEvent.VK_ENTER):
                    if (fenetreApp.getButton().getChoixPage().hasFocus()) {
                        fenetreApp.mainWindow.requestFocus();
                    }
                    break;
            }
        } else {
            switch (code) {
                case (KeyEvent.VK_RIGHT):
                    fenetreApp.getContainer().getDocumentPDF().requestFocus();
                    pageSuivante();
                    break;
                case (KeyEvent.VK_LEFT):
                    fenetreApp.getContainer().getDocumentPDF().requestFocus();
                    pagePrecedente();
                    break;
                case (KeyEvent.VK_DOWN):
                    descendre();
                    break;
                case (KeyEvent.VK_UP):
                    monter();
                    break;
                case (KeyEvent.VK_R):
                    fenetreApp.getButton().getChoixPage().requestFocus();
                    fenetreApp.getButton().getChoixPage().selectAll();
                    break;
                case (KeyEvent.VK_A) :
                    ArrayList<JLabel> pdf = new ArrayList<JLabel>();

            }
        }
    }

    private void pagePrecedente() {
        System.out.println(buttonPanel.c.getValue());
        if (buttonPanel.c.getValue() != 0) {
            buttonPanel.c.decrease();
            y = (fenetreApp.getContainer().getHeight() + fenetreApp.getButton().getTailleEspace()) * (buttonPanel.c.getValue());
            fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
            fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.c.getValue()));
        }
    }

    private void pageSuivante() {
        System.out.println(buttonPanel.c.getValue());
        if (buttonPanel.c.getValue() != fenetreApp.getContainer().getNombrePage() - 1) {
            buttonPanel.c.increment();
            y = (fenetreApp.getContainer().getHeight() + fenetreApp.getButton().getTailleEspace()) * (buttonPanel.c.getValue());
            fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y);
            fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.c.getValue()));
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

    /**
     * Invoked when the mouse wheel is rotated.
     *
     * @param e the event to be processed
     * @see MouseWheelEvent
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double notches = e.getPreciseWheelRotation();
        System.out.println(notches);
        if (notches < 0) {
            monter();
        } else {
            descendre();
        }
    }



    private void descendre() {
        int y = fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
        fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y+50);

        if ( buttonPanel.c.getValue() != fenetreApp.getContainer().getNombrePage()-1) {
            buttonPanel.c.setValue( fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue()/fenetreApp.getContainer().getHeight());

            fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.c.getValue()));
        }
    }

    private void monter() {
        int y = fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
        fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y-50);
        if ( buttonPanel.c.getValue() != 0) {
            buttonPanel.c.setValue( fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue()/fenetreApp.getContainer().getHeight());

            fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.c.getValue()));
        }
    }




    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        y = fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
        System.out.println(y);
        buttonPanel.c.setValue(fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue()/fenetreApp.getContainer().getHeight());
        fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.c.getValue()));
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        y = fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
        System.out.println(y);
        buttonPanel.c.setValue(fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue()/fenetreApp.getContainer().getHeight());
        fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.c.getValue()));
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
