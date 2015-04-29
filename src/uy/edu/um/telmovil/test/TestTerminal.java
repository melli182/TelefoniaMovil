package uy.edu.um.telmovil.test;

import java.io.IOException;

import uy.edu.um.telmovil.terminal.Terminal;


public class TestTerminal {



	public static void main(String[] args) throws IOException {
		Terminal terminal = new Terminal("min2","msn2");
		terminal.register(2182);
	}

}
