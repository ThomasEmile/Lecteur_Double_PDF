/*-----------------------------------------------------------------------------------------
 *                                                                                        *
 *  IUT de RODEZ département informatique - Projet tutoré PDF double affichage            *
 *  Groupe : Eva SIMON, Thomas EMILE, Steven LAVILLE, Yann MOTTOLA, Pierre LESTRINGUEZ    *
 *                                                                                        *
 * ----------------------------------------------------------------------------------------
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;



public class FileChooser {

	FenetreApp fenetre;

	public FileChooser(FenetreApp fenetre) {
		this.fenetre = fenetre;
	}

	public static String Chooser() {
		UIManager.put("FileChooser.cancelButtonText","Annuler");
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setDialogTitle("Selectionner un pdf");
		chooser.setAcceptAllFileFilterUsed(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF document (*.pdf)", "pdf");
		chooser.addChoosableFileFilter(filter);
		chooser.setApproveButtonText("Ouvrir");

		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath().replace("\\", "/");
		}
		return null;
	}
}



