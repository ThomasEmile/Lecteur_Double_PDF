/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Classe qui gère l'affichage du pdf
 */
public class ContainerPDF {

    /** Document pdf du containerPDF */
    public PDDocument document;

    /** True si le pdf est zoomé, false sinon */
    public boolean classique = true;

    /** True si le pdf est zoomé, false sinon */
    public boolean zoomed = false;

    /** True si le pdf est zoomé pleine page, false sinon */
    private boolean pleinePage = false;

    /** True si le pdf doit être redimensionner, false sinon */
    public boolean redimensionner = true;

    /** True si la page affichée doit être actualisée, false sinon */
    public boolean updateScrollBar;

    /** Stock les dimensions de base des pages (non zoomée) */
    private ArrayList<Dimension> dimensionDeBase;

    /** Hauteur total du document affiché (avec les espaces) */
    public int heightTotal = 0;

    /** Nombre de page total du document pdf */
    public int nombrePage;

    /** Page actuellement affichée  */
    public int pageActuelle = 1;

    /** JScrollPane qui contient le containerDocumentPDF */
    public JScrollPane scrollPaneContainer;

    /** JPanel qui contient le documentPDF */
    public JPanel containerDocumentPDF;

    /** JPanel qui contient le pdf */
    public JPanel documentPDF;

    /** champ d'accès à la fenêtre */
    public FenetreApp fenetre;

    /** Liste des espaces */
    private ArrayList<JPanel> espaces;

    /** Liste des pages  */
    private ArrayList<PanelImage> pages;

    /** Le PDF renderer qui permet de faire le rendu d'une page pdf en image */
    public PDFRenderer pdfRenderer;

    /** Dimension d'un espace */
    private Dimension dimensionEspace;

    /** ratio de zoom/dezoom */
    double ratio = 1;




    /**
     * Constructeur de la classe ContainerPDF
     * Initialise les champs de la classe
     * Puis, créer le pdf
     * @param fenetre lien vers la fenêtre
     * @param document lien vers le pdf à affiché
     */
    public ContainerPDF(FenetreApp fenetre, PDDocument document) {
        initComponent(fenetre, document);
        createPDF();
    }

    /**
     * Initialise les champs de la classe
     * @param fenetre lien vers la fenêtre
     * @param document lien vers le pdf à affiché
     */
    private void initComponent(FenetreApp fenetre, PDDocument document) {
        this.document= document;
        dimensionDeBase = new ArrayList<>();
        espaces = new ArrayList<>();
        pages = new ArrayList<>();
        containerDocumentPDF = new JPanel();
        containerDocumentPDF.setBackground(Color.darkGray);
        scrollPaneContainer = new JScrollPane(containerDocumentPDF);
        scrollPaneContainer.setBackground(Color.darkGray);
        scrollPaneContainer.setWheelScrollingEnabled(false);
        documentPDF = new JPanel();
        documentPDF.setBackground(Color.DARK_GRAY);
        this.fenetre = fenetre;
        pdfRenderer = new PDFRenderer(document);
        configContainerPDF();
        configScrollPaneContainer();
        // Nombre de page du document
        nombrePage = document.getNumberOfPages();
        // Actualise le champ graphique du nombre max de page
        fenetre.getMenu().nombreDePage.setText(" | " + nombrePage);
    }

    /**
     * Setter de pageActuelle
     * @param pageActuelle
     */
    public void setPageActuelle(int pageActuelle) {
        this.pageActuelle = pageActuelle;
    }

    /**
     * Getter de nombrePage
     * @return nombrePage
     */
    public int getNombrePage() {
        return nombrePage;
    }

    /**
     * Getter scrollPaneContainer
     * @return scrollPaneContainer
     */
    public JScrollPane getScrollPaneContainer() {
        return scrollPaneContainer;
    }

    /**
     * Getter dimensionEspace
     * @return dimensionEspace
     */
    public Dimension getDimensionEspace() {
        return dimensionEspace;
    }

    /**
     * Getter pages
     * @return pages
     */
    public ArrayList<PanelImage> getPages() {
        return pages;
    }

    /**
     *  Getter containerDocumentPDF
     * @return containerDocumentPDF
     */
    public JPanel getContainerDocumentPDF() {
        return containerDocumentPDF;
    }

    /**
     * Configure le layout du documentPDF
     */
    void configContainerPDF() {
        documentPDF.setLayout(new BoxLayout(documentPDF, BoxLayout.Y_AXIS));
    }

    /**
     * Configure la scroll bar
     */
    void configScrollPaneContainer() {
        scrollPaneContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    /**
     * Création du pdf à l'ouverture de la fenêtre
     */
    public void createPDF() {

        pageActuelle = fenetre.getMenu().c.getValue();
        heightTotal = 0;

        dimensionEspace = new Dimension(10, 100);
        try {
            /* Transforme la page 0 du document en BufferedImage */
            BufferedImage img = pdfRenderer.renderImageWithDPI(0, 100);
            for (int i = 0; i < nombrePage; i++) {


                PanelImage unBlanc = new PanelImage();
                JPanel unEspace = new JPanel();

                dimensionDeBase.add(new Dimension(img.getWidth(), img.getHeight()));
                setDimensions(i, unBlanc, unEspace);
                documentPDF.add(unEspace);
                /* On affiche les 5 images avant la n°c et les 5 après, le reste du documents reste des
                 * pages blanches
                 */
                if (i <= fenetre.getMenu().c.getValue() + 5 && i >= fenetre.getMenu().c.getValue() - 5) {
                    BufferedImage img1 = pdfRenderer.renderImageWithDPI(i, 100);
                    dimensionDeBase.set(i, new Dimension(img1.getWidth(), img1.getHeight()));
                    /* Création du panel contenant l'image i */
                    PanelImage panelImage = new PanelImage(img1, img1.getWidth(), img1.getHeight());
                    /* Ajout du panel contenant l'image de la page i au total des pages */
                    pages.add(i, panelImage);
                    /* Ajout du panel contenant l'image de la page i au JPanel représentant le document pdf en entier */
                    documentPDF.add(panelImage);
                    /* Painte de l'image */
                    panelImage.paint(panelImage.getGraphics());
                } else {
                    /*
                     * Si on ne se trouve pas dans les 5 pages avant ou après la page affichée alors on
                     * laisse des panels blanc aux tailles des pages pdf
                     */
                    unBlanc.setTaille(dimensionDeBase.get(i).width, dimensionDeBase.get(i).height);
                    pages.add(i, unBlanc);
                    documentPDF.add(unBlanc);
                    pages.get(i).paint(unBlanc.getGraphics());
                }
                /* Actualisation de la hauteur totale du documentPDF*/
                heightTotal+= pages.get(i).height + unEspace.getHeight();
                espaces.add(unEspace);
            }
            JPanel unEspace = new JPanel();
            setDimensionEspace(unEspace);
            documentPDF.add(unEspace);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Actualise visuellement le documentPDF */
        documentPDF.updateUI();
    }

    /**
     * Actualisation du pdf
     */
    void updatePDF() {
        if (pageActuelle < fenetre.getMenu().c.getValue() - 3 || pageActuelle > fenetre.getMenu().c.getValue() + 3) {
            pageActuelle = fenetre.getMenu().c.getValue();
            /* Reinitialise la hauteur du document */
            heightTotal = 0;
            /* Recalcule la largeur d'un espace en fonction de la largeur de la fenêtre */
            dimensionEspace = new Dimension(fenetre.mainWindow.getWidth(), 100);
            try {

                for (int i = 0; i < nombrePage; i++) {

                    /* On détermine le ration de zoom (1 si pas zoomé) */
                    defRatio(i);

                    /* Change la taille du panel contenant l'image en multipliant ses dimensions par
                     * le ratio
                     */
                    pages.get(i).dimension(ratio, dimensionDeBase.get(i));
                    /* On affiche les 5 images avant la n°c et les 5 après, le reste du documents reste des
                     * pages blanches
                     */
                    if (i < fenetre.getMenu().c.getValue() + 5 && i > fenetre.getMenu().c.getValue() - 5) {
                        /* Si il y avait déjà une image, on la redimensionne.. */
                        if (pages.get(i).image != null) {
                            pages.get(i).setTaille((int)(dimensionDeBase.get(i).width * ratio), (int)(dimensionDeBase.get(i).height * ratio));
                            pages.get(i).paint(pages.get(i).getGraphics());
                        } else {
                            /* ..Sinon on charge l'image et on l'affiche au bonne dimension */
                            pages.get(i).setImage(pdfRenderer.renderImageWithDPI(i, 100));
                            pages.get(i).setTaille((int)(dimensionDeBase.get(i).width * ratio), (int)(dimensionDeBase.get(i).height * ratio));
                            pages.get(i).paint(pages.get(i).getGraphics());
                        }
                    /*
                    * Si on ne se trouve pas dans les 5 pages avant ou après la page affichée alors on
                    * laisse des panels blanc aux tailles des pages pdf
                    */
                    } else {
                        pages.get(i).setTaille((int) (dimensionDeBase.get(i).width * ratio), (int) (dimensionDeBase.get(i).height * ratio));
                        pages.get(i).paint(pages.get(i).getGraphics());
                    }
                    /* Actualisation de la hauteur totale du documentPDF*/
                    heightTotal += (pages.get(i).height + dimensionEspace.height);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /* Actualise visuellement le documentPDF */
        documentPDF.updateUI();
    }


    /**
     * défini le ration de zoom
     * @param i page sur laquelle on définit le ratio
     */
    private void defRatio(int i) {
        if (zoomed) {
            ratio = (double) (fenetre.mainWindow.getWidth()) / (double) dimensionDeBase.get(i).width;
        } else if (pleinePage) {
            ratio = (double) (fenetre.mainWindow.getHeight() - fenetre.getMenu().getHauteurMenu() * 2 - 40) / (double) dimensionDeBase.get(i).height;
        } else if (classique){
            ratio = 1;
        }

    }

    /**
     * Etablie les dimensions d'une page et d'un espace
     */
    private void setDimensions(int i, JPanel unPage, JPanel unEspace) {
        unEspace.setPreferredSize(dimensionEspace);
        unEspace.setMinimumSize(dimensionEspace);
        unEspace.setMaximumSize(dimensionEspace);
        unEspace.setBackground(Color.DARK_GRAY);
        unPage.setBackground(Color.white);
        unPage.setPreferredSize(dimensionDeBase.get(i));
        unPage.setMinimumSize(dimensionDeBase.get(i));
        unPage.setMaximumSize(dimensionDeBase.get(i));
    }

    /**
     * Etabli les dimensions d'un espace
     * @param unEspace
     */
    private void setDimensionEspace(JPanel unEspace) {
        unEspace.setPreferredSize(dimensionEspace);
        unEspace.setMinimumSize(dimensionEspace);
        unEspace.setMaximumSize(dimensionEspace);
        unEspace.setBackground(Color.DARK_GRAY);
    }

    /**
     * Méthode qui détermine le numéro de la page affichée, l'affiche
     * dans l'indicateur et actualise le compteur de page
     */
    public void updatePageCourante() {

        int pageActuelle = ((scrollPaneContainer.getVerticalScrollBar().getValue() - espaces.get(0).getHeight())
                / (pages.get(0).height + espaces.get(0).getHeight()) + 1);
        System.out.println(pageActuelle);
        if (pageActuelle <= nombrePage) {
            fenetre.getMenu().choixPage.setText("" + pageActuelle);
            fenetre.getMenu().c.setValue(pageActuelle);
        }
    }

    /**
     * Méthode qui renvoie directement à la page demandée
     * @param page le numéro de la page à afficher
     */
    public void goTo(int page) {
            /* Positionne le curseur de la scrollbar au niveau de la page demandée*/
            scrollPaneContainer.getVerticalScrollBar().setValue(
                    pages.get(page - 1).getLocation().y);
            /* Actualise le compteur de page */
            fenetre.getMenu().c.setValue(Math.min(fenetre.getContainer().nombrePage, page));
            fenetre.getMenu().choixPage.setText(String.valueOf(Math.min(fenetre.getContainer().nombrePage, page)));
            /* On met pageActuelle à -10 pour que ce soit différent de la valeur de c et que la méthode updatePDF()
             * affiche les nouvelles pages  */
            setPageActuelle(-10);
            updatePDF();
    }

    /**
     * Méthode de zoom du pdf
     * Change le statut "zoomé" du pdf et change le boolean updateScrollBar pour que le thread
     * qui actualise puisse retourner sur la même page.
     */
    public void zoom() {
        pleinePage = false;
        zoomed = !zoomed;
        setPageActuelle(-10);
        updatePDF();
        updateScrollBar = true;
    }

    /**
     * Méthode de zoom du pdf
     * Change le statut "zoomé" du pdf et change le boolean updateScrollBar pour que le thread
     * qui actualise puisse retourner sur la même page.
     */
    public void zoomPleinePage() {
        zoomed = false;
        pleinePage = !pleinePage;
        setPageActuelle(-10);
        updatePDF();
        updateScrollBar = true;
    }

    /**
     * Méthode de zoom du pdf
     * Change le statut "zoomé" du pdf et change le boolean updateScrollBar pour que le thread
     * qui actualise puisse retourner sur la même page.
     */
    public void zoomClassique() {
        pleinePage = false;
        zoomed = false;
        classique = true;
        setPageActuelle(-10);
        updatePDF();
        updateScrollBar = true;
    }

    /**
     * Méthode qui envoie à la page précédente
     */
    public void pagePrecedente() {
        if (fenetre.getMenu().c.getValue() - 1 > 0) {
            fenetre.getMenu().c.decrease();
            goTo(fenetre.getMenu().c.getValue());
        }
    }

    /**
     * Méthode qui envoie à la page suivante
     */
    public void pageSuivante() {
        if (fenetre.getMenu().c.getValue() + 1 <= nombrePage) {
            fenetre.getMenu().c.increment();
            goTo(fenetre.getMenu().c.getValue());
        }
    }



    /**
     * Descend la scrollbar de 1 unité (100px)
     * Puis actualise l'affiche du pdf et la page courante si besoin
     */
    public void descendre() {

        scrollPaneContainer.getVerticalScrollBar().setValue(
                scrollPaneContainer.getVerticalScrollBar().getValue() + 100);
        updatePDF();
        updatePageCourante();

    }

    /**
     * Monte la scrollbar de 1 unité (100px)
     * Puis actualise l'affiche du pdf et la page courante si besoin
     */
    public void monter() {

        scrollPaneContainer.getVerticalScrollBar().setValue(
                scrollPaneContainer.getVerticalScrollBar().getValue() - 100);
        updatePDF();
        updatePageCourante();
    }
}

