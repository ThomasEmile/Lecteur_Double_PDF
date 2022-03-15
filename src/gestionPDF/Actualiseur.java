/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */
import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Classe d'actualisation (continue) du pdf
 */
public class Actualiseur extends Thread {

    /** lien vers la FenetreApp */
    public FenetreApp fenetreApp;

    /** lien vers l'objet clavierSouris FenetreApp */
    public ClavierSouris clavierSouris;

    /** Constructeur de l'Actualiseur
     * Initialise fenetreApp avec la FenetreApp passée en paramètre
     * @param fenetreApp la FenetreApp à affecté à l'Actualiseur
     */
    public Actualiseur(FenetreApp fenetreApp) {
        this.fenetreApp = fenetreApp;
        this.clavierSouris = this.fenetreApp.clavierSouris;
    }
    /** Constructeur de l'Actualiseur sans paramètre (par défaut) */
    public Actualiseur() {

    }

    /**
     * Méthode de lancement du thread
     */
    @Override
    public void run() {
        /* Boucle infinie */
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*
             * Actualise le document pour éviter les déchets d'affichage
             */
            actualise();

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
        }
    }

    /**
     * Actualise l'ui des composants pdf pour éviter les problème d'affichage
     */
    private void actualise() {
        if (fenetreApp != null && fenetreApp.clavierSouris.getFenetreApp().size() == 2) {
            fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().getContainerDocumentPDF().updateUI();
            fenetreApp.clavierSouris.getFenetreApp().get(1).getContainer().getContainerDocumentPDF().updateUI();
        } else if (fenetreApp != null) {
            fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().getContainerDocumentPDF().updateUI();
        }
    }

    /**
     * transfère le focus à la fenêtre sur laquelle est le curseur
     */
    private void transfereFocus() {
        if (this.fenetreApp != null && fenetreApp.clavierSouris.getFenetreApp().size() == 2) {
            if (fenetreApp.clavierSouris.getFenetreApp().get(0).mainWindow.getMousePosition()  != null
                    && !fenetreApp.clavierSouris.getFenetreApp().get(0).getMenu().choixPage.hasFocus()
                    && !fenetreApp.clavierSouris.getFenetreApp().get(1).getMenu().choixPage.hasFocus()) {
                fenetreApp.clavierSouris.getFenetreApp().get(0).mainWindow.requestFocus();
            } else if (fenetreApp.clavierSouris.getFenetreApp().get(1).mainWindow.getMousePosition()  != null
                    && !fenetreApp.clavierSouris.getFenetreApp().get(0).getMenu().choixPage.hasFocus()
                    && !fenetreApp.clavierSouris.getFenetreApp().get(1).getMenu().choixPage.hasFocus()) {
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
            fenetreApp.clavierSouris.getFenetreApp().get(0).container.redimensionner = false;
            fenetreApp.clavierSouris.getFenetreApp().get(0).container.setPageActuelle(-10);
            fenetreApp.clavierSouris.getFenetreApp().get(0).container.updatePDF();
            fenetreApp.clavierSouris.getFenetreApp().get(0).container.goTo( fenetreApp.clavierSouris.getFenetreApp().get(0).getMenu().c.getValue());
        }
        if (fenetreApp != null && fenetreApp.clavierSouris.getFenetreApp().size() == 2 &&
                fenetreApp.clavierSouris.getFenetreApp().get(1).container.redimensionner) {
            fenetreApp.clavierSouris.getFenetreApp().get(1).container.redimensionner = false;
            fenetreApp.clavierSouris.getFenetreApp().get(1).container.setPageActuelle(-10);
            fenetreApp.clavierSouris.getFenetreApp().get(1).container.goTo( fenetreApp.clavierSouris.getFenetreApp().get(1).getMenu().c.getValue());

        }
    }

    /**
     * Actualise la scrollBar en mode différencié
     */
    private void actualiseScrollBar() {
        if (fenetreApp != null && fenetreApp.container.updateScrollBar) {
            fenetreApp.getContainer().goTo(fenetreApp.getMenu().c.getValue());
            fenetreApp.container.updateScrollBar = false;
        }
    }
}
