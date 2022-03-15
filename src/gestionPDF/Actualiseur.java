/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

package gestionPDF;
import javax.swing.*;

/**
 * Classe d'actualisation (continue) du pdf
 */
public class Actualiseur extends Thread {

    /** lien vers la FenetreApp */
    public FenetreApp fenetreApp;

    /** Sauvegarde de la position de la scroll bar de la fenêtre 1 */
    private static int scrollF1 = 0;
    /** Sauvegarde de la position de la scroll bar de la fenêtre 1 */
    private static int scrollF2 = 0;

    /** Constructeur de l'Actualiseur
     * Initialise fenetreApp avec la FenetreApp passée en paramètre
     * @param fenetreApp la FenetreApp à affecté à l'Actualiseur
     */
    public Actualiseur(FenetreApp fenetreApp) {
        this.fenetreApp = fenetreApp;
    }
    /** Constructeur de l'Actualiseur sans paramètre (par défaut) */
    public Actualiseur() {

    }


    @Override
    public void run() {
        /** Boucle infinie */
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* Redimensionne le pdf si besoin
             * (fenetreApp.container.redimensionner positionné à true)
             */
            redimensionne();

            /* Actualise la postion de la scrollbar si besoin
            * (fenetreApp.container.updateScrollBar positionné à true)
            */
            actualiseScrollBar();

            /* Si plus aucune fenêtre, on quitte l'application */
            quitterApp();

            /* transfère le focus à la fenêtre sur laquelle est le curseur */
            transfereFocus();

            /* Actualise la scollBar d'une fenêtre ou de l'autre en mode unifié */
            actualiseScrollBarUnified();
        }
    }

    /**
     * Actualise la scollBar d'une fenêtre ou de l'autre en mode unifié
     */
    private void actualiseScrollBarUnified() {
        if (fenetreApp != null && fenetreApp.clavierSouris.getFenetreApp().size() == 2 && fenetreApp.clavierSouris.isUnified()) {
            if (fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue() != scrollF1) {
                scrollF1 = fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
                fenetreApp.clavierSouris.getFenetreApp().get(1).getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(scrollF1);
            } else if (fenetreApp.clavierSouris.getFenetreApp().get(1).getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue() != scrollF2) {
                scrollF2 = fenetreApp.clavierSouris.getFenetreApp().get(1).getContainer().getScrollPaneContainer().getVerticalScrollBar().getValue();
                fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().getScrollPaneContainer().getVerticalScrollBar().setValue(scrollF2);

            }
        }
    }

    /**
     * transfère le focus à la fenêtre sur laquelle est le curseur
     */
    private void transfereFocus() {
        if (this.fenetreApp != null && fenetreApp.clavierSouris.getFenetreApp().size() == 2) {
            if (fenetreApp.clavierSouris.getFenetreApp().get(0).mainWindow.getMousePosition()  != null
                    && !fenetreApp.clavierSouris.getFenetreApp().get(0).getButton().choixPage.hasFocus()
                    && !fenetreApp.clavierSouris.getFenetreApp().get(1).getButton().choixPage.hasFocus()) {
                fenetreApp.clavierSouris.getFenetreApp().get(0).mainWindow.requestFocus();
            } else if (fenetreApp.clavierSouris.getFenetreApp().get(1).mainWindow.getMousePosition()  != null
                    && !fenetreApp.clavierSouris.getFenetreApp().get(0).getButton().choixPage.hasFocus()
                    && !fenetreApp.clavierSouris.getFenetreApp().get(1).getButton().choixPage.hasFocus()) {
                fenetreApp.clavierSouris.getFenetreApp().get(1).mainWindow.requestFocus();
            }
        }
    }

    /**
     * Quitte l'application
     */
    private void quitterApp() {
        if (GestionFenetre.nbFenetre == 0) {
            System.exit(1);
        }
    }

    /**
     * Redimensionne la fenetre
     */
    private void redimensionne() {
        if (fenetreApp != null && fenetreApp.container.redimensionner) {
            fenetreApp.container.redimensionner = false;
            fenetreApp.container.setPageActuelle(-10);
            fenetreApp.container.updatePDF();
            fenetreApp.container.goTo(fenetreApp.getButton().c.getValue());
        }
    }

    /**
     * Actualise la scrollBar en mode différencié
     */
    private void actualiseScrollBar() {
        if (fenetreApp != null && fenetreApp.container.updateScrollBar) {
            fenetreApp.getContainer().goTo(fenetreApp.getButton().c.getValue());
            fenetreApp.container.updateScrollBar = false;
        }
    }
}
