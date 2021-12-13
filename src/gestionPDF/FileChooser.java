package gestionPDF;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steavn LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

public class FileChooser {

	FenetreApp fenetre;

	public FileChooser(FenetreApp fenetre) {
		this.fenetre = fenetre;
	}

	public static String Chooser() {

		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setDialogTitle("Selectionner un pdf");

		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF file", "pdf");
		chooser.addChoosableFileFilter(filter);

		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath().replace("\\", "/");
		}
		return null;
	}
}


