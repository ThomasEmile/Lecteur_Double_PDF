/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Classe de gestion de tout les événements clavier et souris
 */
public class ClavierSouris implements KeyListener, MouseWheelListener, MouseMotionListener, MouseListener, ComponentListener {

    /** Champ d'accès au containerPDF */
    private ContainerPDF containerPDF;

    /** Champ d'accès au buttonPanel */
    private Menu buttonPanel;

    /** Champ d'accès aux fenetreApp */
    private  ArrayList<FenetreApp> fenetreApp;

    /** Mode unifié */
    private static boolean unified = false;

    /**
     * Constructeur par défaut de la classe ClavierSouris
     */
    public ClavierSouris() {
        fenetreApp = new ArrayList<>();
    }

    /**
     * Getter de fenetreApp
     * @return fenetreApp
     */
    public ArrayList<FenetreApp> getFenetreApp() {
        return fenetreApp;
    }

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
    public void setButtonPanel(Menu buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    /**
     * Initialise fenetreApp[i] avec la FenetreApp passé en paramètre
     * @param fenetreApp le nouveau FenetreApp
     */
    public void setFenetreApp(FenetreApp fenetreApp, int index) {
            this.fenetreApp.add(index, fenetreApp);
    }

    /**
     * Remplace la fenêtre choisi par le nouveau document PDF
     * @param fenetreApp la nouvelle fenetre
     * @param index l'index de la fenetre à remplacer
     */
    public void remplacerFenetre(FenetreApp fenetreApp, int index) {
        Point localisation;
        Dimension taille;
        localisation = this.fenetreApp.get(index).mainWindow.getLocation();
        taille = this.fenetreApp.get(index).mainWindow.getSize();
        this.fenetreApp.get(index).mainWindow.dispose();
        this.fenetreApp.set(index, fenetreApp);
        this.fenetreApp.get(index).mainWindow.setSize(taille);
        this.fenetreApp.get(index).mainWindow.setLocation(localisation);
        this.fenetreApp.get(index).mainWindow.setVisible(true);
        this.fenetreApp.get(index).mainWindow.requestFocus();
    }

    /**
     * Détermine si l'application est en mode unifié ou non
     * @return true si en mode unifié, false sinon
     */
    public boolean isUnified() {
        return unified;
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

        /*
         * Si le focus est sur le choix de page alors on appelle la méthode qui gère
         * les actions du choix de page gestionChoixPage(int)..
         */
        if (fenetreApp.size() >= 1 && fenetreApp.get(0).getMenu().choixPage.hasFocus()) {
            gestionChoixPage(code, fenetreApp.get(0));
        } else if (fenetreApp.size() >= 2 && fenetreApp.get(1).getMenu().choixPage.hasFocus()) {
            gestionChoixPage(code, fenetreApp.get(1));
        /* ..Sinon on détermine l'action à effectuer en fonction de la touche pressée */
        } else {
            switch (code) {
                // Si c'est la touche U..
                case (KeyEvent.VK_U) :
                    // On passe en mode unifié (ou différencié si deja unifié) si il y a 2 fenêtre
                    if (fenetreApp.size() == 2) {
                        setUnified(!unified);
                    }
                    break;
                // Si c'est la touche D..
                case (KeyEvent.VK_D) :
                    // ..Alors on passe à la page suivante de la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                    fenetreApp.get(1).getContainer().pageSuivante();
                    break;
                // Si c'est la touche Q
                case (KeyEvent.VK_Q):
                    // ..Alors on passe à la page précédente de la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                    fenetreApp.get(1).getContainer().pagePrecedente();
                    break;
                // Si c'est la touche Z
                case (KeyEvent.VK_Z) :
                    // ..Alors on monte dans le document de la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                    fenetreApp.get(1).getContainer().monter();
                    break;
                // Si c'est la touche S
                case (KeyEvent.VK_S):
                    // ..Alors on descend dans le document de la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                    fenetreApp.get(1).getContainer().descendre();
                    break;
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
                    // .. Alors on sélectionne le champ de choix de page de la fenetre 1
                    // Et on pré-sélectionne le texte qui y est
                    choixPage(0);
                    break;
                // Si c'est la touche T
                case (KeyEvent.VK_T):
                    // .. Alors on sélectionne le champ de choix de page de la fenetre 2
                    // Et on pré-sélectionne le texte qui y est
                    choixPage(1);
                    break;
                // Si c'est la touche "1"..
                case (KeyEvent.VK_1) :
                    // ..On zoom en 100% ou dézoom la fenetre 1
                    zoomClassique();
                    break;
                // Si c'est la touche 2
                case (KeyEvent.VK_2) :
                    // ..On zoom en pleine largeur ou dézoom la fenetre 1
                    zoomPleineLargeur();
                    break;
                // Si c'est la touche "3"..
                case (KeyEvent.VK_3) :
                    // ..On zoom en pleine page ou dézoom la fenetre 1
                    zoomPleinePage();
                    break;
                // Si c'est la touche "4"..
                case (KeyEvent.VK_4) :
                    // ..On zoom en 100% ou dézoom la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                        fenetreApp.get(1).getContainer().zoomClassique();
                    break;
                // Si c'est la touche "4"..
                case (KeyEvent.VK_5) :
                    // ..On zoom en pleine largeur ou dézoom la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                        fenetreApp.get(1).getContainer().zoomPleineLargeur();
                    break;
                // Si c'est la touche "4"..
                case (KeyEvent.VK_6) :
                    // ..On zoom en pleine page ou dézoom la fenetre 2
                    if (fenetreApp.size() == 2 && !unified)
                        fenetreApp.get(1).getContainer().zoomPleinePage();
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
    public void gestionChoixPage(int code, FenetreApp fenetre) {
        switch (code) {
            /*
             * Si l'utilisateur à pressé "Echap" alors on retourne le focus
             * sur la fenêtre
             */
            case (KeyEvent.VK_ESCAPE):
                fenetre.mainWindow.requestFocus();
                break;
            /*
             * Si l'utilisateur à pressé "Entrée" alors on va à la page indiquée dans le champ
             * de choix de page sous réserve que c'est un entier compris entre 1 et le nombre maximum de page
             */
            case (KeyEvent.VK_ENTER):
                    try {
                        if (fenetreApp.get(0).getMenu().choixPage.hasFocus()) {
                            choixPage(fenetreApp.get(0));
                        } else if (fenetreApp.get(1).getMenu().choixPage.hasFocus()) {
                            choixPage(fenetreApp.get(1));
                        }
                    } catch (NumberFormatException e1) {
                        if (unified && fenetreApp.size() > 1) {
                            JOptionPane.showMessageDialog(null, "Veuillez entre un entier positif compris entre 1 et "
                                    + Math.max(fenetreApp.get(0).getContainer().nombrePage, fenetreApp.get(1).getContainer().nombrePage));
                        } else {
                            JOptionPane.showMessageDialog(null, "Veuillez entre un entier positif compris entre 1 et "
                                    + fenetre.getContainer().nombrePage);
                        }
                    }

                break;
        }

    }

    /**
     * Méthode de saut de page par le champ grapique de choix de page
     * @param fenetre la fenêtre sur laquelle appliquée ce choix de page
     */
    private void choixPage(FenetreApp fenetre) {
        int page;
        try {
            page = Integer.parseInt(fenetre.getMenu().choixPage.getText());

        } catch (NumberFormatException e) {
            page = -1;
        }
        if (!unified && page <= fenetre.getContainer().getNombrePage()
             && page > 0) {
                fenetre.getContainer().goTo(Math.min(fenetre.getContainer().nombrePage, page));
        } else if (unified && page
                <= Math.max(this.fenetreApp.get(0).getContainer().getNombrePage(), this.fenetreApp.get(1).getContainer().getNombrePage())
                && page > 0) {
            if (unified) {
                fenetreApp.get(0).getContainer().goTo(Math.min(fenetreApp.get(0).getContainer().nombrePage, page));
                fenetreApp.get(1).getContainer().goTo(Math.min(fenetreApp.get(1).getContainer().nombrePage, page));
            } else {
                goTo(Integer.parseInt(fenetre.getMenu().choixPage.getText()));
            }
        } else {
            throw new NumberFormatException();
        }
        fenetre.mainWindow.requestFocus();
    }


    /**
     * Renvoie à la page "page"
     * Si unifié, les 2 documents vont affiché le min entre "page" et le nombre max de page
     * @param page la page à affiché
     */
    private void goTo(int page) {
        if (unified) {
            fenetreApp.get(0).getContainer().goTo(page);
            fenetreApp.get(1).getContainer().goTo(page);
        } else {
            fenetreApp.get(0).getContainer().goTo(page);
        }

    }

    /**
     * Sélection du champ graphique de choix de page
     * @param index l'index de la fenêtre où l'on sélectionne (focus) ce champ
     */
    private void choixPage(int index) {
        fenetreApp.get(index).getMenu().choixPage.requestFocus();
        fenetreApp.get(index).getMenu().choixPage.selectAll();
    }

    /**
     * Zoom (dézoom) le document en pleine largeur
     * Si unifié, les 2 documents vont être zoomé (dézoomé)
     */
    public void zoomPleineLargeur() {
        if (unified) {
            fenetreApp.get(0).getContainer().zoomPleineLargeur();
            fenetreApp.get(1).getContainer().zoomPleineLargeur();
        } else {
            fenetreApp.get(0).getContainer().zoomPleineLargeur();
        }
    }

    /**
     * Zoom (dézoom) le document en pleine pleine page
     * Si unifié, les 2 documents vont être zoomé (dézoomé)
     */
    public void zoomPleinePage() {
        if (unified) {
            fenetreApp.get(0).getContainer().zoomPleinePage();
            fenetreApp.get(1).getContainer().zoomPleinePage();
        } else {
            fenetreApp.get(0).getContainer().zoomPleinePage();
        }
    }

    /**
     * revient au format 100%
     * Si unifié, les 2 documents vont être zoomé (dézoomé)
     */
    public void zoomClassique() {
        if (unified) {
            fenetreApp.get(0).getContainer().zoomClassique();
            fenetreApp.get(1).getContainer().zoomClassique();
        } else {
            fenetreApp.get(0).getContainer().zoomClassique();
        }
    }

    /**
     * Renvoie à la page précédente
     * Si unifié, les 2 documents vont affiché la page précédente
     */
    public void pagePrecedente() {
        if (unified) {
            fenetreApp.get(0).getContainer().pagePrecedente();
            fenetreApp.get(1).getContainer().pagePrecedente();
        } else {
            fenetreApp.get(0).getContainer().pagePrecedente();
        }
    }

    /**
     * Renvoie à la page suivante
     * Si unifié, les 2 documents vont affiché la page suivante
     */
    public void pageSuivante() {
        if (unified) {
            fenetreApp.get(0).getContainer().pageSuivante();
            fenetreApp.get(1).getContainer().pageSuivante();
        } else {
            fenetreApp.get(0).getContainer().pageSuivante();
        }
    }

    /**
     * Renvoie à la page précedente des 2 documents si unifié
     * Sinon renvoie à la page précédente de la fenêtre passé en paramètre
     * @param fenetreAppSeule la fenêtre où l'on souhaite afficher la page précédente
     */
    public void pagePrecedente(FenetreApp fenetreAppSeule) {
        if (unified) {
            fenetreApp.get(0).getContainer().pagePrecedente();
            fenetreApp.get(1).getContainer().pagePrecedente();
        } else {
            fenetreAppSeule.getContainer().pagePrecedente();
        }
    }

    /**
     * Renvoie à la page suivante des 2 documents si unifié
     * Sinon renvoie à la page suivante de la fenêtre passé en paramètre
     * @param fenetreAppSeule la fenêtre où l'on souhaite afficher la page suivante
     */
    public void pageSuivante(FenetreApp fenetreAppSeule) {
        if (unified) {
            fenetreApp.get(0).getContainer().pageSuivante();
            fenetreApp.get(1).getContainer().pageSuivante();
        } else {
            fenetreAppSeule.getContainer().pageSuivante();
        }
    }

    /**
     * Scroll vers le bas du document, des 2 si mode unifié
     */
    private void descendre() {
        if (unified) {

            fenetreApp.get(0).getContainer().descendre();
            fenetreApp.get(1).getContainer().descendre();
        } else {
            fenetreApp.get(0).getContainer().descendre();
        }
    }

    /**
     * Scroll vers le haut du document, des 2 si mode unifié
     */
    private void monter() {
        if (unified) {
            fenetreApp.get(0).getContainer().monter();
            fenetreApp.get(1).getContainer().monter();
        } else {
            fenetreApp.get(0).getContainer().monter();
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
            if (unified) {
                fenetreApp.get(0).getContainer().monter();
                fenetreApp.get(1).getContainer().monter();
            } else if (fenetreApp.get(0).mainWindow.isFocused()) {
                fenetreApp.get(0).getContainer().monter();
            } else if (fenetreApp.get(1).mainWindow.isFocused()) {
                fenetreApp.get(1).getContainer().monter();
            }

        } else {
            if (unified) {
                fenetreApp.get(0).getContainer().descendre();
                fenetreApp.get(1).getContainer().descendre();
            } else if (fenetreApp.get(0).mainWindow.isFocused()) {
                fenetreApp.get(0).getContainer().descendre();
            } else if (fenetreApp.get(1).mainWindow.isFocused()) {
                fenetreApp.get(1).getContainer().descendre();
            }


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
        fenetreApp.get(0).getContainer().updatePageCourante();
        if (fenetreApp.size() == 2 && unified) {
            fenetreApp.get(1).getContainer().updatePageCourante();
        }


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
        if (fenetreApp.size()==2) {
            fenetreApp.get(1).getContainer().updatePageCourante();
            fenetreApp.get(1).getMenu().choixPage.setText(String.valueOf(1 + buttonPanel.c.getValue()));
            fenetreApp.get(1).getContainer().updatePDF();
        }

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        getFenetreApp().get(0).getContainer().updatePageCourante();
        if (fenetreApp.size()==2) {
            fenetreApp.get(1).getContainer().updatePageCourante();
            fenetreApp.get(1).getContainer().updatePDF();
        }
        getFenetreApp().get(0).getContainer().updatePDF();

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        getFenetreApp().get(0).getContainer().updatePageCourante();
        if (fenetreApp.size()==2) {
            fenetreApp.get(1).getContainer().updatePageCourante();
            fenetreApp.get(1).getContainer().updatePDF();
        }
        getFenetreApp().get(0).getContainer().updatePDF();
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        containerPDF.updatePageCourante();
        if (fenetreApp.size()==2) {
            fenetreApp.get(1).getContainer().updatePageCourante();
        }
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        containerPDF.updatePageCourante();
        if (fenetreApp.size()==2) {
            fenetreApp.get(1).getContainer().updatePageCourante();
        }
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

    /**
     * setter de unified
     * modifie le zoom de la fenetre 2 en fonction de la fenetre 1
     * @param b la nouvelle valeur de unified
     */
    public void setUnified(boolean b) {

        unified = b;
        if (fenetreApp.size() == 2  && unified) {
            fenetreApp.get(0).getMenu().setModeUnifierIcon();
            fenetreApp.get(1).getMenu().setModeUnifierIcon();
        } else if (fenetreApp.size() == 2) {
            fenetreApp.get(0).getMenu().setModeDifferencierIcon();
            fenetreApp.get(1).getMenu().setModeDifferencierIcon();
        } else {
            fenetreApp.get(0).getMenu().setModeDifferencierIcon();
        }
        if (fenetreApp.size() == 2  && unified) {
            if (fenetreApp.get(0).getContainer().isZoomPleinePage()) {
                fenetreApp.get(1).getContainer().zoomPleinePage();
            } else if (fenetreApp.get(0).getContainer().isZoomPleineLargeur()) {
                fenetreApp.get(1).getContainer().zoomPleineLargeur();
            } else {
                fenetreApp.get(1).getContainer().zoomClassique();
            }
        }
    }
}
