/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */


import javax.swing.*;
import java.awt.event.*;

/**
 * Méthode de gestion de tout les événements clabier et souris
 */
public class ClavierSouris implements KeyListener, MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener {

    /** Champ d'accès au containerPDF */
    private ContainerPDF containerPDF;
    /** Champ d'accès au buttonPanel */
    private ButtonPanel buttonPanel;
    /** Champ d'accès au fenetreApp */
    private FenetreApp fenetreApp;

    /**
     * Initialise containerPDF avec le ContainerPDF passé en paramètre
     * @param containerPDF le nouveau ContainerPDF
     */
    public void setContainerPDF(ContainerPDF containerPDF) {
        this.containerPDF = containerPDF;

    }

    /**
     * Initialise buttonPanel avec le ButtonPanel passé en paramètre
     * @param buttonPanel le nouveau ButtonPanel
     */
    public void setButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    /**
     * Initialise fenetreApp avec la FenetreApp passé en paramètre
     * @param fenetreApp le nouveau FenetreApp
     */
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
        // Inutilisée
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
        // Détermine le code de la touche qui à été pressée
        int code = e.getKeyCode();

        /* Si le focus est sur le choix de page alors on appelle la méthode qui gère
         * les actions du choix de page gestionChoixPage(int)..
         */
        if (buttonPanel.choixPage.hasFocus()) {
            gestionChoixPage(code);
        /* ..Sinon on détermine l'action à effectuer en fonction de la touche pressée */
        } else {
            switch (code) {
                // Si c'est la flèche de droite..
                case (KeyEvent.VK_RIGHT):
                    // ..Alors on passe à la page suivante
                    pageSuivante();
                    break;
                // Si c'est la flèche de gauche..
                case (KeyEvent.VK_LEFT):
                    // ..Alors on passe à la page précédente
                    pagePrecedente();
                    break;
                // Si c'est la flèche du bas..
                case (KeyEvent.VK_DOWN):
                    // ..Alors on scroll vers le bas
                    descendre();
                    break;
                // Si c'est la flèche du haut..
                case (KeyEvent.VK_UP):
                    // ..Alors on scroll vers le haut
                    monter();
                    break;
                // Si c'est la touche "R"..
                case (KeyEvent.VK_R):
                    // ..Alors on sélectionne le champ de choix de page
                    // Et on pré-sélectionne le texte qui y est
                    buttonPanel.choixPage.requestFocus();
                    buttonPanel.choixPage.selectAll();
                    break;
                // Si c'est la touche "A"..
                case (KeyEvent.VK_A) :
                    // ..On zoom ou dézoom
                    zoom();
                    break;
            }
        }
    }

    /**
     * Si le champ de numéro de page est selectionné alors
     * si l'utilisateur presse "Echap" alors on transmet le focus à la fenetre
     * si l'utilisateur presse "Entrée", on affiche la page entrée (si il s'agit d'un entier compris
     * entre 1 et le nombre de page
     */
    private void gestionChoixPage(int code) {
        switch (code) {
            /*
             * Si l'utilisateur à pressé "Echap" alors on retourne le focus
             * sur la fenêtre
             */
            case (KeyEvent.VK_ESCAPE):
                fenetreApp.mainWindow.requestFocus();
                break;
            /*
             * Si l'utilisateur à pressé "Entrée" alors on va à la page indiquée dans le champ
             * de choix de page sous réserve que c'est un entier compris entre 1 et le nombre maximum de page
             */
            case (KeyEvent.VK_ENTER):
                if (buttonPanel.choixPage.hasFocus()) {
                    fenetreApp.mainWindow.requestFocus();
                    try {
                        if (Integer.parseInt(fenetreApp.getButton().choixPage.getText()) <= fenetreApp.getContainer().getNombrePage()
                                && Integer.parseInt(fenetreApp.getButton().choixPage.getText()) > 0) {
                            goTo(Integer.parseInt(fenetreApp.getButton().choixPage.getText()));
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null,"Veuillez entre un entier positif compris entre 1 et "
                        + containerPDF.nombrePage);
                    }
                }
                break;
        }
    }


    /**
     * Méthode qui renvoie directement à la page demandée
     * @param page le numéro de la page à afficher
     */
    public void goTo(int page) {
            /* Positionne le curseur de la scrollbar au niveau de la page demandée*/
            containerPDF.scrollPaneContainer.getVerticalScrollBar().setValue(
                    (int)((double)(page-1)/(double)containerPDF.getPages().size()* containerPDF.heightTotal)
            + containerPDF.getDimensionEspace().height);
            /* Actualise le compteur de page */
            fenetreApp.getButton().c.setValue(page);
            fenetreApp.getButton().choixPage.setText(String.valueOf(fenetreApp.getButton().c.getValue()));
            /* On met pageActuelle à -10 pour que ce soit différent de la valeur de c et que la méthode updatePDF()
             * affiche les nouvelles pages  */
            containerPDF.setPageActuelle(-10);
            containerPDF.updatePDF();

    }


    /**
     * Méthode de zoom du pdf
     * Change le statut "zoomé" du pdf et change le boolean updateScrollBar pour que le thread
     * qui actualise puisse retourner sur la même page.
     */
    public void zoom() {
        containerPDF.zoomed = !containerPDF.zoomed;
        containerPDF.setPageActuelle(-10);
        containerPDF.updatePDF();
        containerPDF.updateScrollBar = true;


    }

    /**
     * Méthode qui envoie à la page précédente
     */
    public void pagePrecedente() {
        if (fenetreApp.getButton().c.getValue() - 1 > 0) {
            fenetreApp.getButton().c.decrease();
            goTo(fenetreApp.getButton().c.getValue());
        }
    }

    /**
     * Méthode qui envoie à la page suivante
     */
    public void pageSuivante() {
        if (fenetreApp.getButton().c.getValue() + 1 <= containerPDF.nombrePage) {
            fenetreApp.getButton().c.increment();
            goTo(fenetreApp.getButton().c.getValue());
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
        /* Détermine la valeur du scroll */
        double notches = e.getPreciseWheelRotation();
        if (notches < 0) {
            monter();
        } else {
            descendre();
        }
    }


    /**
     * Descend la scrollbar de 1 unité (100px)
     * Puis actualise l'affiche du pdf et la page courante si besoin
     */
    private void descendre() {

        containerPDF.scrollPaneContainer.getVerticalScrollBar().setValue(
                containerPDF.scrollPaneContainer.getVerticalScrollBar().getValue() + 100);
        containerPDF.updatePDF();
        containerPDF.updatePageCourante();

    }

    /**
     * Monte la scrollbar de 1 unité (100px)
     * Puis actualise l'affiche du pdf et la page courante si besoin
     */
    private void monter() {

        containerPDF.scrollPaneContainer.getVerticalScrollBar().setValue(
                containerPDF.scrollPaneContainer.getVerticalScrollBar().getValue() - 100);
        containerPDF.updatePDF();
        containerPDF.updatePageCourante();
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

        containerPDF.updatePageCourante();
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

        // non utilisée
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        containerPDF.updatePageCourante();
        buttonPanel.choixPage.setText(String.valueOf(1 + buttonPanel.c.getValue()));
        containerPDF.updatePDF();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

        containerPDF.updatePageCourante();
        containerPDF.updatePDF();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        containerPDF.updatePageCourante();
        containerPDF.updatePDF();
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        containerPDF.updatePageCourante();
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        containerPDF.updatePageCourante();
    }

    /**
     * Invoked when the component's size changes.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent e) {
        containerPDF.redimensionner = true;
    }

    /**
     * Invoked when the component's position changes.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentMoved(ComponentEvent e) {

    }

    /**
     * Invoked when the component has been made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentShown(ComponentEvent e) {

    }

    /**
     * Invoked when the component has been made invisible.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
