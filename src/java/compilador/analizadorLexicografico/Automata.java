package compilador.analizadorLexicografico;

import java.io.IOException;

import compilador.semantica.CaracterDesconocido;
import compilador.semantica.ContinuarAsignacion;
import compilador.semantica.ContinuarConstante;
import compilador.semantica.ContinuarId;
import compilador.semantica.Error;
import compilador.semantica.FinalizarAsignacion;
import compilador.semantica.FinalizarConstante;
import compilador.semantica.FinalizarId;
import compilador.semantica.IRutinaSemantica;
import compilador.semantica.Ignorar;
import compilador.semantica.IniciarAsignacion;
import compilador.semantica.IniciarConstante;
import compilador.semantica.IniciarId;
import compilador.semantica.OperadorDivision;
import compilador.semantica.OperadorMultiplicacion;
import compilador.semantica.OperadorResta;
import compilador.semantica.OperadorSuma;
import compilador.semantica.ParentesisAbre;
import compilador.semantica.ParentesisCierra;
import compilador.util.ArchivoReader;
import compilador.util.TipoToken;


public class Automata {
	
	public static final int ESTADO_INICIAL = 0;
	public static final int ESTADO_FINAL = 13;
	
    //Tipos de caracter
	public static final int C_LETRA = 0;
    public static final int C_DIGITO = 1;
    public static final int C_MAS = 2;
    public static final int C_MENOS = 3;
    public static final int C_ASTERISCO = 4;
    public static final int C_BARRA = 5;
    public static final int C_PARENT_A = 6;
    public static final int C_PARENT_C = 7;
    public static final int C_PUNTO = 8;
    public static final int C_IGUAL = 9;
    public static final int C_IGNORADO = 10;
    public static final int C_ERROR = 11;
	
    public static final int ERR = -1;
	
	//Matriz de de 14(Cantidad de estados) X 11(Tipos de Caracteres) 
	public static int [][]nuevoEstado = {
    //  let,dig, +,  -,  *,  /,  (,  ),  .,  =,  ign  
		{1,  2,  3,  4,  5,  6,  7,  8,  9,  0,    0,    0}, 
		{1,  1,  13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 2,  13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 11, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 10,  13,  13},
		{13, 13, 13, 13, 13, 13, 13, 13, 13, 13,  13,  13},
		{11, 11, 11, 11, 12, 11, 11, 11, 11, 11,  11,  11},
		{11, 11, 11, 11, 12, 0,  11, 11, 11, 11,  11,  11},
		{ERR,ERR,ERR,ERR,ERR,ERR,ERR,ERR,ERR,ERR, ERR, ERR} // Esta fila no deberia usarse
	};

	//Matriz de 14(Cantidad de estados) X 11(Tipos de Caracteres) 
	public static IRutinaSemantica [][]rutinaSemantica = {
		//letra						digito						+							-							*								/							(							)							.							=							ign							desconocido
/* 0 */	{new IniciarId(),  			new IniciarConstante(),		new OperadorSuma(),  		new OperadorResta(), 		new OperadorMultiplicacion(),	new Ignorar(), 				new ParentesisAbre(),  		new ParentesisCierra(),  	new IniciarAsignacion(), 	new Error(),	 			new Ignorar(),				new CaracterDesconocido()}, 
/* 1 */	{new ContinuarId(), 		new ContinuarId(),  		new FinalizarId(), 			new FinalizarId(), 			new FinalizarId(), 				new FinalizarId(), 			new FinalizarId(), 			new FinalizarId(), 			new FinalizarId(),			new FinalizarId(),			new FinalizarId(),			new FinalizarId()},
/* 2 */	{new FinalizarConstante(), 	new ContinuarConstante(), 	new FinalizarConstante(), 	new FinalizarConstante(),	new FinalizarConstante(), 		new FinalizarConstante(), 	new FinalizarConstante(), 	new FinalizarConstante(), 	new FinalizarConstante(), 	new FinalizarConstante(),	new FinalizarConstante(),	new FinalizarConstante()},
/* 3 */	{new Ignorar(), 			new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),  			new Ignorar(),				new Ignorar()},
/* 4 */	{new Ignorar(), 			new Ignorar(), 				new Ignorar(),				new Ignorar(), 				new Ignorar(), 					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),				new Ignorar(),				new Ignorar()},
/* 5 */	{new Ignorar(), 			new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),				new Ignorar(),				new Ignorar()},
/* 6 */	{new OperadorDivision(), 	new OperadorDivision(), 	new OperadorDivision(),		new OperadorDivision(), 	new Ignorar(), 					new OperadorDivision(),		new OperadorDivision(), 	new OperadorDivision(), 	new OperadorDivision(), 	new OperadorDivision(),		new OperadorDivision(),		new OperadorDivision()},
/* 7 */	{new Ignorar(), 			new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),				new Ignorar(),				new Ignorar()},
/* 8 */	{new Ignorar(), 			new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),				new Ignorar(),				new Ignorar()},
/* 9 */	{new Error(),				new Error(),				new Error(),				new Error(),				new Error(),					new Error(),				new Error(),				new Error(),				new Error(),				new ContinuarAsignacion(),	new Error(),				new Error()},
/* 10 */{new FinalizarAsignacion(), new FinalizarAsignacion(), 	new FinalizarAsignacion(), 	new FinalizarAsignacion(),	new FinalizarAsignacion(),		new FinalizarAsignacion(), 	new FinalizarAsignacion(), 	new FinalizarAsignacion(),	new FinalizarAsignacion(), 	new FinalizarAsignacion(),  new FinalizarAsignacion(),	new FinalizarAsignacion()},
/* 11 */{new Ignorar(), 			new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),  			new Ignorar(),				new Ignorar()},
/* 12 */{new Ignorar(), 			new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),					new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(), 				new Ignorar(),  			new Ignorar(),				new Ignorar()},
/* 13 */{new Error(),				new Error(),				new Error(),				new Error(),				new Error(),					new Error(),				new Error(),				new Error(),				new Error(),				new Error(), 				new Error(),				new Error()}, // Esta fila no deberia usarse
		};
	
	
	
	private StringBuffer token = new StringBuffer();
	
	public int yylex () throws IOException {

			ArchivoReader archivo = ArchivoReader.getInstance();
		
			char caracter;
			int estado = ESTADO_INICIAL; // Estado dentro del AF (inicializado en con el primer estado) 
		    int tipoCaracter;  			 // Tipo del caracter leido
		    int tipoToken;               // Tipo de Token
		    
		    do {
				
		    	caracter = archivo.getChar(); //levantamos el caracter
				tipoCaracter = clasificarCaracter(caracter); //lo clasificamos

				IRutinaSemantica rutina = rutinaSemantica[estado][tipoCaracter];
				tipoToken = rutina.execute(caracter, token);
				// Se pasa al proximo estado
				estado = nuevoEstado[estado][tipoCaracter];

		    } while ((estado != ESTADO_FINAL) && (tipoToken != TipoToken.ERROR_LEXICO) && (!archivo.esFinDeArchivo()));

		    if((estado == ESTADO_FINAL)) {
		    	archivo.unGet();
		    }
		    
			if(archivo.esFinDeArchivo()) {
				IRutinaSemantica rutina = rutinaSemantica[estado][C_IGNORADO];
				tipoToken = rutina.execute(caracter, token);
			}
		    
		    System.out.print(token);
	    
		    return tipoToken;
		}
	
	int clasificarCaracter(char c) {

		/* Caracteres numericos */    
		if( c >= '0' && c <= '9' )
			return C_DIGITO;
		
		/* Caracteres alfabeticos */    
		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') )
			return C_LETRA;

		/* Caracteres especiales */
	    switch( c ) {
	        case '+':   return C_MAS;
	        case '-':   return C_MENOS;
	        case '*':   return C_ASTERISCO;
	        case '/':   return C_BARRA;
	        case '(':   return C_PARENT_A;
	        case ')':   return C_PARENT_C;
	        case '.':   return C_PUNTO;
	        case '=':   return C_IGUAL;
	        
	        case ' ':
	        case '\r':
	        case '\n':
	        case '\t':
	                    return C_IGNORADO;
	    }

	    return C_ERROR;
	}

}
