/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steven LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe d'un panel comportant une image
 */
public class PanelImage extends JPanel  {

    /** image du panel */
    public Image image;
    /** largeur du panel */
    int width = 0;
    /** hauteur du panel */
    int height = 0;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public PanelImage() {
    }


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     * Avec les dimensions spécifiées en paramètres.
     */
    public PanelImage(BufferedImage img, int width, int height) {
        this.width = width;
        this.height = height;
        // applique l'image img au panel avec les dimensions données en paramètres
        this.image = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

    }

    /**
     * Modifie la taille de l'image
     * @param width la largeur
     * @param height la hauteur
     */
    public void setTaille(int width, int height) {
        this.width = width;
        this.height = height;
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width,height);
    }

    /**
     * Surcharger le dessin du composant
     * @param g canvas
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {
            g.drawImage(image, 0, 0, width, height, this);
        } else {
            g.drawRect(0,0, width, height);
        }

    }

    /**
     * Applique la BufferedImage img1 au panel
     * @param img1
     */
    public void setImage(BufferedImage img1) {
        image = img1;
    }

    /**
     * Redimensionne l'image en fonction du ratio
     * @param ratio le ration a appliqué sur l'image
     * @param image l'image sur laquelle appliquer le ratio
     */
    public void dimension(double ratio, Image image) {
        Dimension d = new Dimension((int)(image.getWidth(null) * ratio) , (int)(image.getHeight(null) * ratio));
        setMaximumSize(d);
        setMinimumSize(d);
        setPreferredSize(d);
    }
}

