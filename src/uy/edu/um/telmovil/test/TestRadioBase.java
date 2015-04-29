package uy.edu.um.telmovil.test;

import java.io.IOException;

import uy.edu.um.telmovil.radiobase.BaseStation;
import uy.edu.um.telmovil.terminal.Terminal;


public class TestRadioBase {



	public static void main(String[] args) throws IOException {
		BaseStation baseStation = new BaseStation(10);
		baseStation.setRadioBaseName("Radio Base 1");
		baseStation.setConexionSocket(2182);
		baseStation.startListening();
	}

}
