package pruebas;

import java.io.IOException;

import compilador.analizadorLexicografico.Automata;
import compilador.util.ArchivoReader;
import compilador.util.TipoToken;

public class Main {
	
	public static void main(String [] args)	{
		
		ArchivoReader archivo = ArchivoReader.getInstance();
		archivo.abrirArhivo("src//test/pruebas//programa.txt");
		int tipoToken;
		
		try {
			while(!archivo.esFinDeArchivo()) {
				Automata automata = new Automata();
				tipoToken = automata.yylex();
				
				/* En el caso de que lo último que haya en el archivo sea un comentario o
				 * algun caracter ignorado (ENTER, ESPACIO, etc), la funcion yylex retorna
				 * TipoToken.INCOMPLETO; esto indica que el archivo termina con algo que NO
				 * es un token, por eso no se ignora.
				 */
				if(tipoToken != TipoToken.INCOMPLETO) {
					System.out.println("Tipo:" + tipoToken + " En TS:" + Automata.yylval);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			archivo.cerrarArhivo();
		}
	}

}
