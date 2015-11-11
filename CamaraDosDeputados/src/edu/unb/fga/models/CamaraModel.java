package edu.unb.fga.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import edu.unb.fga.dadosabertos.Camara;
import edu.unb.fga.dadosabertos.Deputado;
import edu.unb.fga.dadosabertos.Partido;

public class CamaraModel {

	private static CamaraModel instance;

	public static CamaraModel getInstance() {
		if (instance == null) {
			instance = new CamaraModel();
		}
		return instance;
	}

	private Camara camara;

	private List<Partido> partidos;
	private List<Integer> partidoCount;

	private ArrayList<CamaraModelDelegate> deputadoListeners = new ArrayList<CamaraModelDelegate>();
	private ArrayList<CamaraModelDelegate> partidoListeners = new ArrayList<CamaraModelDelegate>();

	private CamaraModel() {
		partidos = new ArrayList<Partido>();
		partidoCount = new ArrayList<Integer>();
		camara = new Camara();
		carregaCamara();
	}

	public void carregaCamara() {
		if (camara != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						camara.obterDados();
						for (CamaraModelDelegate listener : deputadoListeners) {
							listener.atualizaDados();
						}
						for (Deputado deputado : getDeputados()) {
							deputado.obterDetalhes();
							Partido partido = deputado.getDetalhes().getPartido();
							if (!partidos.contains(partido)) {
								partidos.add(partido);
								partidoCount.add(new Integer(0));
								for (CamaraModelDelegate listener : partidoListeners) {
									listener.atualizaDados();
								}
							}
							int index = partidos.indexOf(partido);
							partidoCount.set(index, partidoCount.get(index) + 1);
						}
						System.out.println(partidos);
					} catch (IOException | JAXBException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public Integer getCountForIndex(int index) {
		return partidoCount.get(index);
	}

	public void addListenerForDeputado(CamaraModelDelegate listener) {
		if (!deputadoListeners.contains(listener)) {
			deputadoListeners.add(listener);
		}
	}

	public void addListenerForPartido(CamaraModelDelegate listener) {
		if (!partidoListeners.contains(listener)) {
			partidoListeners.add(listener);
		}
	}

	public List<Deputado> getDeputados() {
		return camara.getDeputados();
	}

	public List<Partido> getPartidos() {
		return partidos;
	}
}
