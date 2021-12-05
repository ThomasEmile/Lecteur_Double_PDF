package gestionPDF;

import javax.swing.*;

public class main {

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        Clavier clavier = new Clavier();
        new FenetreApp(clavier);
    }
}
