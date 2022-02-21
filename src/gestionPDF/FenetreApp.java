/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;

/**
 * Classe globale de l'application qui définit la fenêtre et tout ses composants
 */
public class FenetreApp {

    /** chaine de caractère contenant le nom du fichier */
    private final String WINDOW_NAME = "pdfDoubleAffichage";
    /** chaine de caractère contenant le chemin de l'icon de l'application */
    private final String CHEMIN_ICONE_APP = "icon/IconApp.png";

    /** Champ de la fenetre JFrame */
    public JFrame mainWindow;
    /** Champ du fond de la fenetre mainWindow */
    private JPanel background;

    /** Champ menu */
    private ButtonPanel button;
    /** Champ du conteneur du pdf */
    public ContainerPDF container;
    /** Champ du listener clavier & souris */
    public ClavierSouris clavierSouris;

    /**
     * Construit une fenetre ainsi qu'un panel qui va contenir tout les autres composants de l'application
     */
    public FenetreApp(PDDocument document) {
        clavierSouris = new ClavierSouris();
        configMainWindow();
        configBackground();
        button = new ButtonPanel(this);
        container = new ContainerPDF(this, document);
        this.addListeners(clavierSouris);
        button.configButton();
        button.ajoutComposants();

    }

    /**
     * Getter de button
     * @return button
     */
    public ButtonPanel getButton() {
        return button;
    }

    /**
     * Getter de container
     * @return container
     */
    public ContainerPDF getContainer() {
        return container;
    }

    /**
     * Getter de mainWindow
     * @return mainWindow
     */
    public JFrame getMainWindow() {
        return mainWindow;
    }

    /**
     * Getter de background
     * @return background
     */
    public JPanel getBackground() {
        return background;
    }

    /**
     * Configure la JFrame.
     * Défini sa taille de base et défini son icone
     */
    private void configMainWindow() {
        mainWindow = new JFrame();
        mainWindow.setTitle(WINDOW_NAME);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1920, 1080);
        mainWindow.setResizable(true);
        mainWindow.setVisible(true);
        mainWindow.setBackground(new Color(77, 74, 73));
        ImageIcon iconApp = new ImageIcon(CHEMIN_ICONE_APP);
        mainWindow.setIconImage(iconApp.getImage());
    }

    /**
     * Ajout du listener aux éléments de la fenêtre
     * @param clavier le listener clavier/souris
     */
    public void addListeners(ClavierSouris clavier) {
        mainWindow.addMouseMotionListener(clavierSouris);
        clavier.setFenetreApp(this);
        clavier.setButtonPanel(button);
        clavier.setContainerPDF(container);
        this.background.addKeyListener(clavier);
        this.mainWindow.addKeyListener(clavier);
        mainWindow.addComponentListener(clavier);
        container.scrollPaneContainer.addMouseWheelListener(clavier);
        container.scrollPaneContainer.getVerticalScrollBar().addMouseMotionListener(clavier);
        container.scrollPaneContainer.getVerticalScrollBar().addMouseListener(clavier);
    }

    /**
     * Configure le fond de la fenêtre
     */
    private void configBackground() {
        background = new JPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

    }

}
