package pruebas;

import java.io.IOException;

import compilador.analizadorLexicografico.Automata;
import compilador.beans.TablaDeSimbolos;
import compilador.util.ArchivoReader;
import compilador.util.TipoToken;

public class Main {
	
	public static void main(String [] args)	{
		
		ArchivoReader archivo = ArchivoReader.getInstance();
		archivo.abrirArhivo(args[0]);
		int tipoToken;
		
		try {
			
			Automata automata = new Automata();
			tipoToken = automata.yylex();
			
			while(tipoToken != TipoToken.INCOMPLETO) {
					
				switch (tipoToken) {
				case TipoToken.ID:
				case TipoToken.CTE_NUM:
				case TipoToken.CTE_STR:
					System.out.println("Tipo:" + tipoToken + " En TS:" + Automata.yylval);						
					break;
				default:
					System.out.println("Tipo:" + tipoToken);
					break;
				}  
				
				automata = new Automata();
				tipoToken = automata.yylex();
			}
			
			System.out.println("\nTabla de Simbolos:");
			System.out.println(TablaDeSimbolos.getInstance().toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			archivo.cerrarArhivo();
		}
	}
}
