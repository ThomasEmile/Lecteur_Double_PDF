/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

/**
 * Classe d'actualisation (continue) du pdf
 */
public class Actualiseur extends Thread {

    /** lien vers la FenetreApp */
    public FenetreApp fenetreApp;

    /** Constructeur de l'Actualiseur */
    public Actualiseur(FenetreApp fenetreApp) {
        this.fenetreApp = fenetreApp;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /* Redimensionne le pdf si besoin
             * (fenetreApp.container.redimensionner positionné à true)
             */
            if (fenetreApp.container.redimensionner) {
                fenetreApp.container.redimensionner = false;
                fenetreApp.container.setPageActuelle(-10);
                fenetreApp.container.updatePDF();
            }

            /* Actualise la postion de la scrollbar si besoin
            * (fenetreApp.container.updateScrollBar positionné à true)
            */
            if (fenetreApp.container.updateScrollBar) {
                fenetreApp.clavierSouris.goTo(fenetreApp.getButton().c.getValue());
                fenetreApp.container.updateScrollBar = false;
            }

        }
    }
}
