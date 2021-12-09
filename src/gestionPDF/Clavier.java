/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

package gestionPDF;

import java.awt.event.*;

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
        // code de la touche qui a été enfoncée
        int code = e.getKeyCode();
        if (fenetreApp.getButton().getChoixPage().hasFocus()) {
            switch (code) {
                case (KeyEvent.VK_ESCAPE):
                    fenetreApp.mainWindow.requestFocus();
                    break;
                case (KeyEvent.VK_ENTER):
                    fenetreApp.mainWindow.requestFocus();
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
                    zoom();
                    break;
            }
        }
    }

    /**
     * Méthode de zoom quand la touche a est enfoncée.
     * Affiche le bon documentPDF et rend l'autre non visible
     */
    public void zoom() {
        int page = fenetreApp.getButton().getC().getValue();
        if (!containerPDF.isZoomed()) {
            containerPDF.setZoomed(true);
            containerPDF.getDocumentPDF2().setVisible(true);
            containerPDF.getDocumentPDF().setVisible(false);
        } else {
            containerPDF.setZoomed(false);
            containerPDF.getDocumentPDF2().setVisible(false);
            containerPDF.getDocumentPDF().setVisible(true);
        }
        fenetreApp.getButton().getC().setValue(page);
        goToZoom();
    }

    /**
     * Affiche la page du compteur
     */
    public void goTo() {
        int c;
        if (containerPDF.isZoomed()) {
            c = fenetreApp.getButton().getC().getValue();
            containerPDF.getScrollPaneContainer().getVerticalScrollBar().setValue(c
                     * (containerPDF.getPagePDF2().get(c).getHeight() + buttonPanel.getTailleEspace()));
        } else {
            c = fenetreApp.getButton().getC().getValue();
            containerPDF.getScrollPaneContainer().getVerticalScrollBar().setValue(c
                    * (containerPDF.getPagePDF().get(c).getHeight() + buttonPanel.getTailleEspace()));
        }
    }

    /**
     * Affiche la page du compteur
     */
    public void goToZoom() {
        int c;
        if (containerPDF.isZoomed()) {
            c = fenetreApp.getButton().getC().getValue();
            containerPDF.getScrollPaneContainer().getVerticalScrollBar().setValue(c
                    * (containerPDF.getPagePDF2().get(c).getHeight()));
        } else {
            c = fenetreApp.getButton().getC().getValue();
            containerPDF.getScrollPaneContainer().getVerticalScrollBar().setValue(c
                    * (containerPDF.getPagePDF().get(c).getHeight()));
        }
    }
    /**
     * Affiche la page précédente
     */
    private void pagePrecedente() {
        if (buttonPanel.getC().getValue() != 0) {
            buttonPanel.getC().decrease();
            goTo();
            updateCounter();
        }
    }

    /**
     * Affiche la page suivante
     */
    private void pageSuivante() {
        if (buttonPanel.getC().getValue() != fenetreApp.getContainer().getNombrePage() - 1) {
            buttonPanel.getC().increment();
            goTo();
            updateCounter();

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
        if (notches < 0) {
            monter();

        } else {
            descendre();
        }
    }


    /**
     * Scroll dans le document vers le bas
     */
    private void descendre() {
        int y = fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
        fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y+50);
        updateCounter();
    }



    /**
     * Scroll dans le document vers le Haut
     */
    private void monter() {
        int y = fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
        fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(y-50);
        updateCounter();
    }


    /**
     * Actualise la valeur du compteur (page courante)
     * en fonction de si la page est zoomée ou non
     */
    private void updateCounter() {

            if (buttonPanel.getC().getValue() != fenetreApp.getContainer().getNombrePage() - 1) {
                if (!containerPDF.isZoomed()) {
                    buttonPanel.getC().setValue(fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue()
                            / fenetreApp.getContainer().getHeight());
                } else {
                    buttonPanel.getC().setValue(fenetreApp.getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue()
                            / fenetreApp.getContainer().getPagePDF2().get(buttonPanel.getC().getValue
                            ()).getHeight());

                }
                // Affiche la valeur du nouveau counter
                fenetreApp.getButton().getChoixPage().setText(String.valueOf(1 + buttonPanel.getC().getValue()));
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
        updateCounter();
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
        updateCounter();
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
