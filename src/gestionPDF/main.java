package gestionPDF;

import javax.swing.*;


public class main {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        FenetreApp fenetre =  new FenetreApp();
        ContainerPDF containerPDF = new ContainerPDF(fenetre);
        fenetre.getContainer().run();
    }
}
