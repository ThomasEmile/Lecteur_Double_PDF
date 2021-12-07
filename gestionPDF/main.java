package gestionPDF;

import javax.swing.*;

import static java.lang.Thread.sleep;

public class main {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");



        FenetreApp fenetre =  new FenetreApp();
        ContainerPDF containerPDF = new ContainerPDF(fenetre);
        fenetre.container.run();


    }
}