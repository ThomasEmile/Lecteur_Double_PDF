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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
    private Menu button;
    /** Champ du conteneur du pdf */
    public ContainerPDF container;
    /** Champ du listener clavier & souris */
    public ClavierSouris clavierSouris;

    /**
     * Construit une fenetre ainsi qu'un panel qui va contenir tout les autres composants de l'application
     */
    public FenetreApp(PDDocument document, ClavierSouris clavierSouris) {

        this.clavierSouris = clavierSouris;
        configMainWindow();
        configBackground();
        button = new Menu(this);
        container = new ContainerPDF(this, document);
        this.addListeners();
        button.configButton();
        button.ajoutComposants();

        mainWindow.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (clavierSouris.getFenetreApp().size() - 1 == 0) {
                    System.exit(0);
                } else if (clavierSouris.getFenetreApp().size() > 1 && clavierSouris.getFenetreApp().get(0).mainWindow.equals(mainWindow)) {
                    clavierSouris.getFenetreApp().set(0, clavierSouris.getFenetreApp().get(1));
                    clavierSouris.getFenetreApp().remove(1);
                    clavierSouris.getFenetreApp().get(0).mainWindow.setSize(new Dimension(1920, 1080));
                    clavierSouris.getFenetreApp().get(0).mainWindow.setLocation(0, 0);
                } else if (clavierSouris.getFenetreApp().size() > 1 && clavierSouris.getFenetreApp().get(1).mainWindow.equals(mainWindow)) {
                    clavierSouris.getFenetreApp().remove(1);
                    clavierSouris.getFenetreApp().get(0).mainWindow.setSize(new Dimension(1920, 1080));
                    clavierSouris.getFenetreApp().get(0).mainWindow.setLocation(0, 0);
                }
                clavierSouris.getFenetreApp().get(0).mainWindow.setTitle(clavierSouris.getFenetreApp().get(0).getWINDOW_NAME() + " - fenêtre 1");
                GestionFenetre.nbFenetre--;

            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });


    }

    public String getWINDOW_NAME() {
        return WINDOW_NAME;
    }

    /**
     * Getter de button
     * @return button
     */
    public Menu getButton() {
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
        mainWindow.setVisible(false);
        mainWindow.setTitle(WINDOW_NAME);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainWindow.setSize(1920, 1080);
        mainWindow.setResizable(true);

        mainWindow.setBackground(new Color(77, 74, 73));
        ImageIcon iconApp = new ImageIcon(CHEMIN_ICONE_APP);
        mainWindow.setIconImage(iconApp.getImage());
    }

    /**
     * Ajout du listener aux éléments de la fenêtre*/
    public void addListeners() {
        mainWindow.addMouseMotionListener(clavierSouris);
        clavierSouris.setFenetreApp(this);
        clavierSouris.setButtonPanel(button);
        clavierSouris.setContainerPDF(container);
        this.background.addKeyListener(clavierSouris);
        this.mainWindow.addKeyListener(clavierSouris);
        mainWindow.addComponentListener(clavierSouris);
        container.scrollPaneContainer.addMouseWheelListener(clavierSouris);
        container.scrollPaneContainer.getVerticalScrollBar().addMouseMotionListener(clavierSouris);
        container.scrollPaneContainer.getVerticalScrollBar().addMouseListener(clavierSouris);
    }

    /**
     * Configure le fond de la fenêtre
     */
    private void configBackground() {
        background = new JPanel();
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));

    }
}
