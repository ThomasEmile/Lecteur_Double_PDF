package gestionPDF;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static gestionPDF.createPagePDF.nombrePage;
import static gestionPDF.createPagePDF.tailleEspace;

public class Souris implements MouseWheelListener {

    private configComposants.Counter c;
    private JTextField choixPage;
    private JScrollPane scrollPaneFrame;
    int height;
    int nbScroll;

    public Souris(JTextField choixPage, configComposants.Counter c, int height, JScrollPane scrollPaneFrame) {
        this.choixPage = choixPage;
        this.c = c;
        this.height = height;
        this.scrollPaneFrame = scrollPaneFrame;
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();

        if (notches < 0) {
            if (nbScroll > 0) nbScroll--;
            int y = (height+tailleEspace) * (nbScroll) / 6;
            scrollPaneFrame.getVerticalScrollBar().setValue(y);
            if (c.getValue() != 0 && nbScroll%6 == 0) {  System.out.println(c.getValue());
                c.setValue(nbScroll/6);

                choixPage.setText(String.valueOf(1 + c.getValue()));
            }

        } else {
            if (nbScroll < nombrePage*6) nbScroll++;
            int y = (height+tailleEspace) * (nbScroll) / 6;
            scrollPaneFrame.getVerticalScrollBar().setValue(y);
            if (c.getValue() != nombrePage-1 && nbScroll % 6 == 0) { System.out.println(c.getValue());
                c.setValue(nbScroll/6);
;                choixPage.setText(String.valueOf(1 + c.getValue()));
            }

        }
    }
}
