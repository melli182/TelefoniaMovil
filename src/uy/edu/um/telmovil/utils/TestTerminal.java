package uy.edu.um.telmovil.utils;

import java.io.IOException;

import uy.edu.um.telmovil.terminal.Terminal;


public class TestTerminal {



	public static void main(String[] args) throws IOException {
		Terminal terminal = new Terminal("min","msn");
		terminal.register(2182);
	}

}
