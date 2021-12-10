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
 * Cette classe s'occupe de créer  et de configurer le frame contenant tout les composants du fichier pdf et le panel
 * contenant les boutonsansi que l'affichage des pages du pdf.
 * @author Groupe pdf double affichage
 * @version 1.0
 */
public class FenetreApp {

    private final String WINDOW_NAME = "pdfDoubleAffichage"; // chaine de caractère contenant le nom du fichier
    private final String CHEMIN_ICONE_APP = "icon/IconApp.png";// chaine de caractère contenant le chemin de l'icon de l'application

    public final JFrame mainWindow; // création du frame contenant qui va contenir tout les composants de l'application
    private final JPanel background; // création du panel background qui va contenir les boutons et l'affiche des pages du fichier pdf

    // création d'objet des classes que l'on souhaite gérer
    private ButtonPanel button;
    private ContainerPDF container;
    private Clavier clavier;

    /**
     * coonstruit une fenetre ainsi qu'un panel qui va contenir tout les autres composants de l'application
     */
    public FenetreApp() {
        container = new ContainerPDF(this);
        background = new JPanel();
        mainWindow = new JFrame();
        mainWindow.setTitle(WINDOW_NAME);
        configMainWindow();
        configBackground();
        button = new ButtonPanel(this);
        clavier = new Clavier(this);
        this.addListeners(clavier);
    }

    // Getter de la classe ContainerPDF
    public Clavier getClavier() { return clavier;}
    public ButtonPanel getButton() {return button;}
    public ContainerPDF getContainer() {return container;}
    public JFrame getMainWindow() {return mainWindow;}
    public JPanel getBackground() {return background;}

    /**
     * méthode qui configure la fenetre principale, elle défini sa taille de base et la défini son icone
     */
    private void configMainWindow() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1920, 1080);
        mainWindow.setResizable(true);
        mainWindow.setVisible(true);
        ImageIcon iconApp = new ImageIcon(CHEMIN_ICONE_APP);
        mainWindow.setIconImage(iconApp.getImage());
    }


    /**
     * Ajoute les listeners au différents éléments de la page
     * @param clavier la classe regroupant les listeners
     */
    public void addListeners(Clavier clavier) {

        clavier.setFenetreApp(this);
        clavier.setButtonPanel(button);
        clavier.setContainerPDF(container);
        this.background.addKeyListener(clavier);
        this.mainWindow.addKeyListener(clavier);
        container.getScrollPaneContainer().addMouseWheelListener(clavier);
        container.getScrollPaneContainer().getVerticalScrollBar().addMouseMotionListener(clavier);
        container.getScrollPaneContainer().getVerticalScrollBar().addMouseListener(clavier);
        button.getChoixPage().addKeyListener(clavier);
    }

    /**
     * classe que configure le background de l'application
     */
    private void configBackground() {
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
    }

}
