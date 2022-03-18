/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steven LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

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
        /* Boucle infinie */
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /* Update l'affichage pour eviter les problèmes d'affichage*/
            updateUI();
            /* Redimensionne le pdf si besoin
             * (fenetreApp.container.redimensionner positionné à true)
             */
            redimensionne();

            /* Actualise la position de la scrollbar si besoin
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
     * Actualise l'affichage des ou de la fenêtre afin d'éviter les bugs d'affichage
     */
    private void updateUI() {
        if (fenetreApp != null && fenetreApp.clavierSouris.getFenetreApp().size() == 2) {
            fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().documentPDF.updateUI();
            fenetreApp.clavierSouris.getFenetreApp().get(1).getContainer().documentPDF.updateUI();
        } else if (fenetreApp != null) {
            fenetreApp.clavierSouris.getFenetreApp().get(0).getContainer().documentPDF.updateUI();
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
            fenetreApp.container.redimensionner = false;
            fenetreApp.container.setPageActuelle(-10);
            fenetreApp.container.updatePDF();
            fenetreApp.container.goTo(fenetreApp.getMenu().c.getValue());
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