/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steven LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Méthode de gestion de tous les événements clavier et souris
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

    /** ClavierSouris doit connaitre les raccourcis clavier */
    private RaccourciClavier raccourciClavier;

    /**
     * Constructeur par défaut de la classe ClavierSouris
     */
    public ClavierSouris() {
        // crée une nouvelle configuration clavier (celle par défaut)
        raccourciClavier = new RaccourciClavier();
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
    public void setFenetreApp(FenetreApp fenetreApp) {
        if (this.fenetreApp == null) {
            this.fenetreApp = new ArrayList<>();
            this.fenetreApp.add(0, fenetreApp);
        } else if (this.fenetreApp.size() == 0) {
            this.fenetreApp.add(0, fenetreApp);
        } else if (this.fenetreApp.size() == 1) {
            //get local graphics environment
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //get maximum window bounds
            Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();
            int largeurEcran = maximumWindowBounds.width;
            int hauteurEcran = maximumWindowBounds.height;
            this.fenetreApp.add(1, fenetreApp);
            this.fenetreApp.get(0).getMainWindow().setLocation(0, 0);
            this.fenetreApp.get(0).getMainWindow().setSize(new Dimension(largeurEcran / 2, hauteurEcran));
            this.fenetreApp.get(0).mainWindow.requestFocus();
            this.fenetreApp.get(1).getMainWindow().setLocation(largeurEcran / 2, 0);
            this.fenetreApp.get(1).getMainWindow().setSize(new Dimension(largeurEcran / 2, hauteurEcran));
            this.fenetreApp.get(1).getMainWindow().requestFocus();
        }
    }

    /**
     * Remplace la fenêtre choisie par le nouveau document PDF
     * @param fenetreApp la nouvelle fenetre
     * @param index l'index de la fenetre à remplacer
     */
    public void remplacerFenetre(FenetreApp fenetreApp, int index) {
        // On enregistre l'environnement graphique
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // pour obtenir la taille totale de l'écran
        Rectangle maximumWindowBounds = graphicsEnvironment.getMaximumWindowBounds();

        int largeurEcran = maximumWindowBounds.width;
        int hauteurEcran = maximumWindowBounds.height;

        this.fenetreApp.get(index).mainWindow.dispose();
        this.fenetreApp.set(index, fenetreApp);
        this.fenetreApp.get(index).mainWindow.setVisible(true);
        this.fenetreApp.get(index).mainWindow.requestFocus();
        this.fenetreApp.get(0).mainWindow.setLocation(0, 0);
        this.fenetreApp.get(0).mainWindow.setSize(largeurEcran / 2, hauteurEcran);
        this.fenetreApp.get(1).mainWindow.setLocation(largeurEcran / 2, 0);
        this.fenetreApp.get(1).mainWindow.setSize(largeurEcran / 2, hauteurEcran);
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
        // Détermine le code de la touche qui a été pressée
        int code = e.getKeyCode();

        /*
         * Si le focus est sur le choix de page alors on appelle la méthode qui gère
         * les actions du choix de page gestionChoixPage(int)..
         */
        if (fenetreApp.size() >= 1 && fenetreApp.get(0).getButton().choixPage.hasFocus()) {
            gestionChoixPage(code, fenetreApp.get(0));
        } else if (fenetreApp.size() >= 2 && fenetreApp.get(1).getButton().choixPage.hasFocus()) {
            gestionChoixPage(code, fenetreApp.get(1));
        /* ..Sinon on détermine l'action à effectuer en fonction de la touche pressée */
        } else {
            // touche sélectionnée
            switch (code) {
                // exécution de la fonction liée à la touche
                case (KeyEvent.VK_A) -> fonctionRaccourci(raccourciClavier.getFonction("A"));
                case (KeyEvent.VK_Z) -> fonctionRaccourci(raccourciClavier.getFonction("Z"));
                case (KeyEvent.VK_E) -> fonctionRaccourci(raccourciClavier.getFonction("E"));
                case (KeyEvent.VK_R) -> fonctionRaccourci(raccourciClavier.getFonction("R"));
                case (KeyEvent.VK_T) -> fonctionRaccourci(raccourciClavier.getFonction("T"));
                case (KeyEvent.VK_Y) -> fonctionRaccourci(raccourciClavier.getFonction("Y"));
                case (KeyEvent.VK_U) -> fonctionRaccourci(raccourciClavier.getFonction("U"));
                case (KeyEvent.VK_I) -> fonctionRaccourci(raccourciClavier.getFonction("I"));
                case (KeyEvent.VK_O) -> fonctionRaccourci(raccourciClavier.getFonction("O"));
                case (KeyEvent.VK_P) -> fonctionRaccourci(raccourciClavier.getFonction("P"));
                case (KeyEvent.VK_Q) -> fonctionRaccourci(raccourciClavier.getFonction("Q"));
                case (KeyEvent.VK_S) -> fonctionRaccourci(raccourciClavier.getFonction("S"));
                case (KeyEvent.VK_D) -> fonctionRaccourci(raccourciClavier.getFonction("D"));
                case (KeyEvent.VK_F) -> fonctionRaccourci(raccourciClavier.getFonction("F"));
                case (KeyEvent.VK_G) -> fonctionRaccourci(raccourciClavier.getFonction("G"));
                case (KeyEvent.VK_H) -> fonctionRaccourci(raccourciClavier.getFonction("H"));
                case (KeyEvent.VK_J) -> fonctionRaccourci(raccourciClavier.getFonction("J"));
                case (KeyEvent.VK_K) -> fonctionRaccourci(raccourciClavier.getFonction("K"));
                case (KeyEvent.VK_L) -> fonctionRaccourci(raccourciClavier.getFonction("L"));
                case (KeyEvent.VK_M) -> fonctionRaccourci(raccourciClavier.getFonction("M"));
                case (KeyEvent.VK_W) -> fonctionRaccourci(raccourciClavier.getFonction("W"));
                case (KeyEvent.VK_X) -> fonctionRaccourci(raccourciClavier.getFonction("X"));
                case (KeyEvent.VK_C) -> fonctionRaccourci(raccourciClavier.getFonction("C"));
                case (KeyEvent.VK_V) -> fonctionRaccourci(raccourciClavier.getFonction("V"));
                case (KeyEvent.VK_B) -> fonctionRaccourci(raccourciClavier.getFonction("B"));
                case (KeyEvent.VK_N) -> fonctionRaccourci(raccourciClavier.getFonction("N"));
                case (KeyEvent.VK_0) -> fonctionRaccourci(raccourciClavier.getFonction("N0"));
                case (KeyEvent.VK_1) -> fonctionRaccourci(raccourciClavier.getFonction("N1"));
                case (KeyEvent.VK_2) -> fonctionRaccourci(raccourciClavier.getFonction("N2"));
                case (KeyEvent.VK_3) -> fonctionRaccourci(raccourciClavier.getFonction("N3"));
                case (KeyEvent.VK_4) -> fonctionRaccourci(raccourciClavier.getFonction("N4"));
                case (KeyEvent.VK_5) -> fonctionRaccourci(raccourciClavier.getFonction("N5"));
                case (KeyEvent.VK_6) -> fonctionRaccourci(raccourciClavier.getFonction("N6"));
                case (KeyEvent.VK_7) -> fonctionRaccourci(raccourciClavier.getFonction("N7"));
                case (KeyEvent.VK_8) -> fonctionRaccourci(raccourciClavier.getFonction("N8"));
                case (KeyEvent.VK_9) -> fonctionRaccourci(raccourciClavier.getFonction("N9"));
                case (KeyEvent.VK_UP) -> fonctionRaccourci(raccourciClavier.getFonction("UP"));
                case (KeyEvent.VK_DOWN) -> fonctionRaccourci(raccourciClavier.getFonction("DOWN"));
                case (KeyEvent.VK_RIGHT) -> fonctionRaccourci(raccourciClavier.getFonction("RIGHT"));
                case (KeyEvent.VK_LEFT) -> fonctionRaccourci(raccourciClavier.getFonction("LEFT"));
                case (KeyEvent.VK_ENTER) -> fonctionRaccourci(raccourciClavier.getFonction("ENTREE"));
                case (KeyEvent.VK_ESCAPE) -> fonctionRaccourci(raccourciClavier.getFonction("ECHAP"));
                // touche non associée
                default -> {
                }
            }
        }
    }

    /**
     * Méthode qui permet de lancer une fonction attribué
     * à une touche définie dans la classe RaccourciClavier
     * @param fonctionALancer label exact parmi ceux définis de la fonction à lancer
     */
    public void fonctionRaccourci(String fonctionALancer) {
        switch (fonctionALancer) {
            // 0 Passe en mode unifié (ou différencié si deja unifié) s'il y a 2 fenêtre
            case "changementModeAffich":
                if (fenetreApp.size() == 2) {
                    unified = !unified;
                }
                break;
            // 1 Passe à la page suivante de la fenêtre 2
            case "pageSuivFenetre2":
                fenetreApp.get(1).getContainer().pageSuivante();
                break;
            // 2 Passe à la page précédente de la fenêtre 2
            case "pagePrecFenetre2":
                fenetreApp.get(1).getContainer().pagePrecedente();
                break;
            // 3 Monte dans le document de la fenêtre 2
            case "monteDocFenetre2":
                fenetreApp.get(1).getContainer().monter();
                break;
            // 4 descend dans le document de la fenêtre 2
            case "descendDocFenetre2":
                fenetreApp.get(1).getContainer().descendre();
                break;
            // 5 passe à la page suivante
            case "pageSuivante":
                pageSuivante();
                break;
            // 6 passe à la page précédente
            case "pagePrecedente":
                pagePrecedente();
                break;
            // 7 scroll vers le bas
            case "descendre":
                descendre();
                break;
            // 8 scroll vers le haut
            case "monter":
                monter();
                break;
            /*
             * 9 sélectionne le champ de choix de page de la fenêtre 1
             * présélectionne le texte qui y est
             */
            case "choixFenetrePremiere":
                choixPage(0);
                break;
            /*
             * 10 sélectionne le champ de choix de page de la fenêtre 2
             * présélectionne le texte qui y est
             */
            case "choixFenetreDeuxieme" :
                choixPage(1);
                break;
            // 11 zoom ou dézoome la fenêtre 1
            case "inverseZoomFenetrePremiere" :
                zoom();
                break;
            // 12 zoom ou dézoome la fenêtre 2
            case "inverseZoomFenetreDeuxieme" :
                fenetreApp.get(1).getContainer().zoom();
                break;
            // fonction indéfinie
            default:
                break;
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
             * Si l'utilisateur a pressé "Echap" alors on retourne le focus
             * sur la fenêtre
             */
            case (KeyEvent.VK_ESCAPE):
                fenetre.mainWindow.requestFocus();
                break;
            /*
             * Si l'utilisateur a pressé "Entrée" alors on va à la page indiquée dans le champ
             * de choix de page sous réserve que c'est un entier compris entre 1 et le nombre maximum de page
             */
            case (KeyEvent.VK_ENTER):
                if (fenetre.getButton().choixPage.hasFocus()) {
                    fenetre.mainWindow.requestFocus();
                    try {
                        if (!unified && Integer.parseInt(fenetre.getButton().choixPage.getText()) <= fenetre.getContainer().getNombrePage()) {
                                fenetre.getContainer().goTo(Integer.parseInt(fenetre.getButton().choixPage.getText()));
                        } else if (unified && Integer.parseInt(fenetre.getButton().choixPage.getText())
                                <= Math.max(this.fenetreApp.get(0).getContainer().getNombrePage(), this.fenetreApp.get(1).getContainer().getNombrePage())) {
                                goTo(Integer.parseInt(fenetre.getButton().choixPage.getText()));
                        } else {
                            throw new NumberFormatException();
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
                }
                break;
        }

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
        fenetreApp.get(index).getButton().choixPage.requestFocus();
        fenetreApp.get(index).getButton().choixPage.selectAll();

    }

    /**
     * Zoom (dézoom) le document
     * Si unifié, les 2 documents vont être zoomé (dézoomé)
     */
    private void zoom() {
        if (unified) {
            fenetreApp.get(0).getContainer().zoom();
            fenetreApp.get(1).getContainer().zoom();
        } else {
            fenetreApp.get(0).getContainer().zoom();
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
        if (fenetreApp.size() == 2) {
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
            fenetreApp.get(1).getButton().choixPage.setText(String.valueOf(1 + buttonPanel.c.getValue()));
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
}
