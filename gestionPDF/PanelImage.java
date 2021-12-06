package gestionPDF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class PanelImage extends JPanel  {
    public Image image;
    public double zoom;
    int width = 0;
    int height = 0;
    /**
     * Constructeur
     * @param img image Ã  afficher
     */
    public PanelImage(BufferedImage img) {
        this.zoom = 2;
        this.image = img.getScaledInstance(getPreferredSize().width, getPreferredSize().height, 16);


    }

    public PanelImage(BufferedImage img, int width, int height) {
        this.zoom = 2;
        this.width = width;
        this.height = height;
        this.image = img.getScaledInstance(getPreferredSize().width, getPreferredSize().height, 16);

    }

    public double getZoom() {
        return zoom;
    }
    public void setZoom(double zoom) {
        if(zoom > 0)
            this.zoom = zoom;
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension((int)(width*zoom),(int)(height*zoom));
    }

    /**
     * Surcharger le dessin du composant
     * @param g canvas
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, (int)(width*zoom), (int)(height*zoom),this);

    }

}

