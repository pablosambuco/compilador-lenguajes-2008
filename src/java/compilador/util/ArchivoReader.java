package compilador.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Clase que implementa el patrón Singleton para el manejo de archivos
 */

public class ArchivoReader {
	
	   private static ArchivoReader instance = null;
	   
	   private BufferedReader buffer = null;

	   protected ArchivoReader() {
		   //evita que sea instanciado por constructor
	   }
	   
	   public static ArchivoReader getInstance() {
	      if(instance == null) {
	         instance = new ArchivoReader();
	      }
	      return instance;
	   }
	   
	   public void abrirArhivo(String nombreArchivo) {
		   try {
	        	File file = new File(nombreArchivo);
	        	FileReader inputRead = new FileReader(file);
	        	buffer = new BufferedReader(inputRead);

		   } catch (FileNotFoundException e) {
				System.out.println("Archivo no encontrado");
		   }
	   }
	   
	   public void cerrarArhivo() {
		   try {
	        	if(buffer != null) {
	        		buffer.close();
	        	}
		   } catch (IOException e) {
				System.out.println("Error al cerrar el archivo");
		   }
	   }
	   
	   public boolean esFinDeArchivo() {
		   try {
			   return !buffer.ready();
		   } catch (IOException e) {
			   System.out.println("Error al consultar el fin de archivo");
			   return true;
		   }
	   }
	   
	   public char getChar() throws IOException {
		   
		   buffer.mark(1); //marcamos el buffer en la posicion actual para poder volver caso de que se haga un unGet() 
		   return (char) buffer.read();
			
	   }
	   
	   public void unGet() throws IOException {

		   //vuelve el buffer a la marca seteada justo antes de leer el caracter actual de manera que vuelva a ser tomado
		   buffer.reset();
	   }

}
