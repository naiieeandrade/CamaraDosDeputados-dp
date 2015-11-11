package edu.unb.fga.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import edu.unb.fga.models.CamaraModel;
import edu.unb.fga.models.CamaraModelDelegate;

public class MainViewController extends JFrame implements CamaraModelDelegate {

	JTabbedPane tabbedPane;
	ArrayList<JTable> tables = new ArrayList<JTable>();
	ArrayList<TableModel> models = new ArrayList<TableModel>();
	TableModel deputadoModel;
	TableModel partidoModel;

	public MainViewController(TableModel deputadoModel, TableModel partidoModel) {
		CamaraModel.getInstance().addListenerForDeputado(this);
		CamaraModel.getInstance().addListenerForPartido(this);

		this.deputadoModel = deputadoModel;
		this.partidoModel = partidoModel;

		tabbedPane = new JTabbedPane();

		tabbedPane.insertTab("Deputados", null, initializePanelWithModel(deputadoModel), null, 0);
		tabbedPane.insertTab("Partidos", null, initializePanelWithModel(partidoModel), null, 1);
		getContentPane().add(tabbedPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 400);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private JPanel initializePanelWithModel(TableModel model) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		JTable table = new JTable(model);
		tables.add(table);
		models.add(model);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);

		return panel;
	}

	@Override
	public void atualizaDados() {
		for (int i = 0; i < tables.size(); i++) {
			JTable table = tables.get(i);
			table.setSize(new Dimension(table.getWidth() + 1, table.getHeight()));
		}
	}
}
