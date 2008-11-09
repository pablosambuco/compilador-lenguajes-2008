package compilador.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Clase que implementa el patrón Singleton para el manejo de archivos
 */

public class ArchivoWriter {
	
	   private BufferedWriter buffer = null;
	   private String nombre = null;

	   public ArchivoWriter(String nombre) {
		   this.nombre = nombre;
		   try {
			   File file = new File(this.nombre);
			   FileWriter outputWrite = new FileWriter(file);
			   buffer = new BufferedWriter(outputWrite);
		   } catch (IOException e) {
				System.out.println("Error al crear archivo: " + this.nombre);
				System.exit(-1);
		   }
	   }
	   
	   public void write(String contenido) {
        	try {
				buffer.write(contenido);
				buffer.flush();
			} catch (IOException e) {
				System.out.println("Error al escribir en el archivo: " + this.nombre);
				System.exit(-1);
			}
	   }
	   
	   public void cerrarArhivo() {
		   try {
	        	if(buffer != null) {
	        		buffer.close();
	        	}
		   } catch (IOException e) {
				System.out.println("Error al cerrar el archivo: " + this.nombre);
		   }
	   }
}
