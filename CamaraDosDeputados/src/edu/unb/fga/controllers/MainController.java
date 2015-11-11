package edu.unb.fga.controllers;

import edu.unb.fga.models.DeputadosTableModel;
import edu.unb.fga.models.PartidosTableModel;
import edu.unb.fga.views.MainViewController;

public class MainController {
	public static void main(String[] args) {
		PartidosTableModel partidoModel = new PartidosTableModel();
		DeputadosTableModel deputadoModel = new DeputadosTableModel();
		MainViewController controller = new MainViewController(deputadoModel, partidoModel);
	}
}
