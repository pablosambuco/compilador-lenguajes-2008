package pruebas;

import java.io.IOException;

import compilador.analizadorLexicografico.Automata;
import compilador.util.ArchivoReader;

public class Main {
	
	public static void main(String [] args)	{
		
		ArchivoReader archivo = ArchivoReader.getInstance();
		archivo.abrirArhivo("src//test/pruebas//programa.txt");
		int tipoToken;
		
		try {
			while(!archivo.esFinDeArchivo()) {
				Automata automata = new Automata();
				tipoToken = automata.yylex();
				//System.out.println(tipoToken);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			archivo.cerrarArhivo();
		}
	}

}
