package gestionPDF;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class FenetreApp {

    private final String WINDOW_NAME = "pdfDoubleAffichage";
    private final String CHEMIN_ICONE_APP = "icon/IconApp.png";

    public final JFrame mainWindow;
    private final JPanel background;
    private ButtonPanel button;
    private ContainerPDF container;

    public Clavier clavier = new Clavier();

    /**
     * coonstruit une fenetre ainsi qu'un panel qui va contenir tout les autres composants de l'application
     */
    public FenetreApp() {
        background = new JPanel();
        mainWindow = new JFrame();
        container = new ContainerPDF(new JFrame());
        mainWindow.setTitle(WINDOW_NAME);
        configMainWindow();
        configBackground();
        button = new ButtonPanel(this);
        this.addListeners(clavier);
        container = new ContainerPDF(this.getMainWindow());
    }

    public ButtonPanel getButton() {
        return button;
    }

    public ContainerPDF getContainer() {
        return container;
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public JPanel getBackground() {
        return background;
    }

    /**
     *
     */
    private void configMainWindow() {
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1920, 1080);
        mainWindow.setResizable(true);
        mainWindow.setVisible(true);
        mainWindow.setBackground(new Color(77, 74, 73));
        ImageIcon iconApp = new ImageIcon(CHEMIN_ICONE_APP);
        mainWindow.setIconImage(iconApp.getImage());
    }

    public void addListeners(Clavier clavier) {

        clavier.setFenetreApp(this);
        clavier.setButtonPanel(button);
        clavier.setContainerPDF(container);
        this.background.addKeyListener(clavier);
        this.mainWindow.addKeyListener(clavier);
        container.scrollPaneContainer.addMouseWheelListener(clavier);
        container.scrollPaneContainer.getVerticalScrollBar().addMouseMotionListener(clavier);
        container.scrollPaneContainer.getVerticalScrollBar().addMouseListener(clavier);
    }

    private void configBackground() {
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
    }

}
