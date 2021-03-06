package compilador.analizadorLexicografico;

import java.io.IOException;
import compilador.beans.TablaDeSimbolos;
import compilador.parser.Parser;
import compilador.parser.ParserVal;
import compilador.semantica.And;
import compilador.semantica.CaracterDesconocido;
import compilador.semantica.Coma;
import compilador.semantica.ContinuarConstante;
import compilador.semantica.ContinuarConstanteString;
import compilador.semantica.ContinuarId;
import compilador.semantica.CorcheteAbre;
import compilador.semantica.CorcheteCierra;
import compilador.semantica.DosPuntos;
import compilador.semantica.Error;
import compilador.semantica.FinalizarConstante;
import compilador.semantica.FinalizarConstanteString;
import compilador.semantica.FinalizarId;
import compilador.semantica.IRutinaSemantica;
import compilador.semantica.Ignorar;
import compilador.semantica.IniciarConstante;
import compilador.semantica.IniciarConstanteString;
import compilador.semantica.IniciarId;
import compilador.semantica.OperadorAsignacion;
import compilador.semantica.OperadorComparacion;
import compilador.semantica.OperadorDistinto;
import compilador.semantica.OperadorDivision;
import compilador.semantica.OperadorMayor;
import compilador.semantica.OperadorMayorIgual;
import compilador.semantica.OperadorMenor;
import compilador.semantica.OperadorMenorIgual;
import compilador.semantica.OperadorMultiplicacion;
import compilador.semantica.OperadorNegacion;
import compilador.semantica.OperadorResta;
import compilador.semantica.OperadorSuma;
import compilador.semantica.Or;
import compilador.semantica.ParentesisAbre;
import compilador.semantica.ParentesisCierra;
import compilador.semantica.PuntoYComa;
import compilador.util.ArchivoReader;

public class Automata {

	private static Automata instancia;
	
	private Automata() {

	}
	
	public static Automata getInstance(){
		if(instancia == null)
			instancia = new Automata();
		return instancia;
		
	}
	
	
	/*En caso de querer imprimir la tira de tokens, usar true*/
	private boolean imprimir = false;
	
    public static final int ERR = -1;
    
    //Tipos de caracter
	public static final int C_LETRA = 0;
    public static final int C_DIGITO = 1;
    public static final int C_PUNTO = 2;
    public static final int C_COMILLAS = 3;
    public static final int C_IGUAL = 4;
    public static final int C_NEGACION = 5;
    public static final int C_MENOR = 6;
    public static final int C_MAYOR = 7;
    public static final int C_AMPERSAND = 8;
    public static final int C_PIPE = 9;
    public static final int C_CORCHETE_ABRE = 10;
    public static final int C_CORCHETE_CIERRA = 11;
    public static final int C_PARENTESIS_ABRE = 12;
    public static final int C_PARENTESIS_CIERRA = 13;
    public static final int C_COMA = 14;
    public static final int C_DOS_PUNTOS = 15;
    public static final int C_PUNTO_Y_COMA = 16;
    public static final int C_MAS = 17;
    public static final int C_MENOS = 18;
    public static final int C_ASTERISCO = 19;
    public static final int C_BARRA = 20;
    public static final int C_NUMERAL = 21;
    public static final int C_PORCENTUAL = 22;
    public static final int C_IGNORADO = 23;
    public static final int C_DESCONOCIDO = 24;
    public static final int C_FIN_DE_ARCHIVO = 25; //no lo leemos pero se lo trata como un caracter ms
		
	public static int [][]nuevoEstado = {
		//letra		digito	.		"		=		!		<		>		&		|		[		]		(		)		,		:		;		+		-		*		/		#		%		ign.	desc.	EOF /* El contenido de esta ltima columna es simblico porque una vez que se ley fin de archivo se ejecuta la rutina de cierre necesaria y no se vuelve a pasar al estado siguiente (que tampoco interesa) */
/* 0 */	{1,			2,		3,		5,		7,		9,		11,		13,		15,  	17, 	19,		20,		21,		22,		23,		24,		25,		26,		27,		28,		29,		30,		0,		0,		0,		0}, 
/* 1 */	{1,			1,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 2 */	{36,		2,		4,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 3 */	{36,		4,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 4 */	{36,		4,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 5 */	{5,			5,		5,		6, 		5,		5,		5,		5,  	5, 		5,	 	5,		5,		5,		5,		5,		5,		5,		5,		5,		5,		5,		5,		5,		5,		5,		5},
/* 6 */	{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 7 */	{36,		36,		36,		36, 	8,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 8 */	{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 9 */	{36,		36,		36,		36, 	10,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 10 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 11 */{36,		36,		36,		36, 	12,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 12 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 13 */{36,		36,		36,		36, 	14,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 14 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 15 */{36,		36,		36,		36, 	36,		36,		36,		36,  	16, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 16 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 17 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	18,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 18 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 19 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 20 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 21 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 22 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 23 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 24 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 25 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 26 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 27 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 28 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 29 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36},
/* 30 */{36,		36,		36,		36, 	36,		36,		36,		36,  	36, 	36,	 	36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		36,		31,		36,		36,		36}, /* Si vino un # y luego otra cosa distinta de % se necesita informar error lxico y hacer unGet() (por eso llevamos al estado final) del ltimo caracter ingresado */
/* 31 */{31,		31,		31,		31, 	31,		31,		31,		31,  	31, 	31,	 	31,		31,		31,		31,		31,		31,		31,		31,		31,		31,		31,		32,		35,		31,		31,		31},
/* 32 */{31,		31,		31,		31, 	31,		31,		31,		31,  	31, 	31,	 	31,		31,		31,		31,		31,		31,		31,		31,		31,		31,		31,		32,		33,		31,		31,		31},
/* 33 */{33,		33,		33,		33, 	33,		33,		33,		33,  	33, 	33,	 	33,		33,		33,		33,		33,		33,		33,		33,		33,		33,		33,		33,		34,		33,		33,		33},
/* 34 */{33,		33,		33,		33, 	33,		33,		33,		33,  	33, 	33,	 	33,		33,		33,		33,		33,		33,		33,		33,		33,		33,		33,		31,		34,		33,		33,		33},
/* 35 */{31,		31,		31,		31, 	31,		31,		31,		31,  	31, 	31,	 	31,		31,		31,		31,		31,		31,		31,		31,		31,		31,		31,		0,		35,		31,		31,		31},
/* 36 */{ERR,		ERR,	ERR,	ERR, 	ERR,	ERR,	ERR,	ERR,  	ERR, 	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR,	ERR}, /* A esta Fila nunca se debra acceder */
};

	private static IniciarId iniciarId = new IniciarId();
	private static ContinuarId continuarId = new ContinuarId();
	private static FinalizarId finalizarId = new FinalizarId(); 
	
	private static IniciarConstante iniciarConstante = new IniciarConstante();
	private static ContinuarConstante continuarConstante = new ContinuarConstante();
	private static FinalizarConstante finalizarConstante = new FinalizarConstante();
	
	private static IniciarConstanteString iniciarConstanteString = new IniciarConstanteString();
	private static ContinuarConstanteString continuarConstanteString = new ContinuarConstanteString();
	private static FinalizarConstanteString finalizarConstanteString = new FinalizarConstanteString();
	
	private static Coma coma = new Coma();
	private static DosPuntos dosPuntos = new DosPuntos();
	private static PuntoYComa puntoYComa = new PuntoYComa();
	
	private static OperadorSuma operadorSuma = new OperadorSuma();
	private static OperadorResta operadorResta = new OperadorResta();
	private static OperadorMultiplicacion operadorMultiplicacion = new OperadorMultiplicacion();
	private static OperadorDivision operadorDivision = new OperadorDivision();
	private static OperadorAsignacion operadorAsignacion = new OperadorAsignacion();
	private static OperadorComparacion operadorComparacion = new OperadorComparacion();
	private static OperadorDistinto operadorDistinto = new OperadorDistinto();
	private static OperadorMayor operadorMayor = new OperadorMayor();
	private static OperadorMayorIgual operadorMayorIgual = new OperadorMayorIgual();
	private static OperadorMenor operadorMenor = new OperadorMenor();
	private static OperadorMenorIgual operadorMenorIgual = new OperadorMenorIgual();
	private static OperadorNegacion operadorNegacion = new OperadorNegacion();
	
	private static And and = new And();
	private static Or or = new Or();
	
	private static ParentesisAbre parentesisAbre = new ParentesisAbre();
	private static ParentesisCierra parentesisCierra = new ParentesisCierra();
	private static CorcheteAbre corcheteAbre = new CorcheteAbre();
	private static CorcheteCierra corcheteCierra = new CorcheteCierra();
	
	private static Ignorar ignorar = new Ignorar();
	private static Error error = new Error();
	private static CaracterDesconocido caracterDesconocido = new CaracterDesconocido();
	
	public static IRutinaSemantica [][]rutinaSemantica = {
		//letra						digito						.							"							=							!							<							>							&							|							[							]							(							)							,							:							;							+							-							*							/							#							%							ignorado					desconocido					fin de archivo
/* 0 */	{iniciarId,  				iniciarConstante,			iniciarConstante,  			iniciarConstanteString, 	ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				corcheteAbre,				corcheteCierra,				parentesisAbre,				parentesisCierra,			coma,		 				dosPuntos,					puntoYComa,					operadorSuma,				operadorResta,				operadorMultiplicacion,		operadorDivision,			ignorar,					error,						ignorar,					caracterDesconocido,		ignorar}, 
/* 1 */	{continuarId,  				continuarId,				finalizarId,  				finalizarId, 				finalizarId,				finalizarId, 				finalizarId,		  		finalizarId,  				finalizarId, 				finalizarId,	 			finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId,				finalizarId},
/* 2 */	{finalizarConstante,  		continuarConstante,			continuarConstante,  		finalizarConstante, 		finalizarConstante,			finalizarConstante, 		finalizarConstante,			finalizarConstante,  		finalizarConstante, 		finalizarConstante,	 		finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante},
/* 3 */	{error,  					continuarConstante,			error,  					error, 						error,						error, 						error,		  				error,  					error, 						error,	 					error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error},
/* 4 */	{finalizarConstante,  		continuarConstante,			finalizarConstante,  		finalizarConstante, 		finalizarConstante,			finalizarConstante, 		finalizarConstante,			finalizarConstante,  		finalizarConstante, 		finalizarConstante,	 		finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante,			finalizarConstante},
/* 5 */	{continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	finalizarConstanteString,	continuarConstanteString,  	continuarConstanteString,	continuarConstanteString,	continuarConstanteString, 	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,  	continuarConstanteString, 	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	continuarConstanteString,	error},
/* 6 */	{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 7 */	{operadorAsignacion,  		operadorAsignacion,			operadorAsignacion,  		operadorAsignacion, 		operadorComparacion,		operadorAsignacion, 		operadorAsignacion,		  	operadorAsignacion,  		operadorAsignacion, 		operadorAsignacion,	 		operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion,			operadorAsignacion},
/* 8 */	{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 9 */	{operadorNegacion,  		operadorNegacion,			operadorNegacion,  			operadorNegacion, 			operadorDistinto,			operadorNegacion, 			operadorNegacion,		  	operadorNegacion,  			operadorNegacion, 			operadorNegacion,	 		operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion,			operadorNegacion},
/* 10 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 11 */{operadorMenor,  			operadorMenor,				operadorMenor,  			operadorMenor, 				operadorMenorIgual,			operadorMenor, 				operadorMenor,		  		operadorMenor,  			operadorMenor, 				operadorMenor,	 			operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,			operadorMenor,					operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor,				operadorMenor},
/* 12 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 13 */{operadorMayor,  			operadorMayor,				operadorMayor,  			operadorMayor, 				operadorMayorIgual,			operadorMayor, 				operadorMayor,		  		operadorMayor,  			operadorMayor, 				operadorMayor,	 			operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,			operadorMayor,					operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor,				operadorMayor},
/* 14 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar}, 
/* 15 */{error,  					error,						error,  					error, 						error,						error, 						error,		  				error,  					and, 						error,	 					error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error},
/* 16 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 17 */{error,  					error,						error,  					error, 						error,						error, 						error,		  				error,  					error, 						or,	 						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error},
/* 18 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 19 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 20 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 21 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 22 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 23 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 24 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 25 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 26 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 27 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 28 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 29 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar},
/* 30 */{error,  					error,						error,  					error, 						error,						error, 						error,		  				error,  					error, 						error,	 					error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						ignorar,					error,						error,						error},
/* 31 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					error},
/* 32 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					error},
/* 33 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					error},
/* 34 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					error},
/* 35 */{ignorar,  					ignorar,					ignorar,  					ignorar, 					ignorar,					ignorar, 					ignorar,		  			ignorar,  					ignorar, 					ignorar,	 				ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					ignorar,					error},
/* 36 */{error,  					error,						error,  					error, 						error,						error, 						error,		  				error,  					error, 						error,	 					error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error,						error},
	};
	
	private StringBuffer token = new StringBuffer();
	
	private int clasificarCaracter(char c) {

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
	        case '(':   return C_PARENTESIS_ABRE;
	        case ')':   return C_PARENTESIS_CIERRA;
	        case '.':   return C_PUNTO;
	        case '=':   return C_IGUAL;
	        case '"':   return C_COMILLAS;
	        case '!':   return C_NEGACION;
	        case '<':   return C_MENOR;
	        case '>':   return C_MAYOR;
	        case '&':   return C_AMPERSAND;
	        case '|':   return C_PIPE;
	        case '[':   return C_CORCHETE_ABRE;
	        case ']':   return C_CORCHETE_CIERRA;
	        case ',':   return C_COMA;
	        case ':':   return C_DOS_PUNTOS;
	        case ';':   return C_PUNTO_Y_COMA;
	        case '#':   return C_NUMERAL;
	        case '%':   return C_PORCENTUAL;
        
	        /* Caracteres especiales ignorados*/
	        case ' ':
	        case '\r':
	        case '\n':
	        case '\t':
	                    return C_IGNORADO;
	    }

	    return C_DESCONOCIDO;
	}
	
	public int getToken (ParserVal yylval) {

		token.delete(0, token.length()); //Reseteo el buffer
		ArchivoReader archivo = ArchivoReader.getInstance();
		
		if(archivo.esFinDeArchivo()) {
			return Parser.INCOMPLETO;
		}		
				
		char caracter;
		int estado = Parser.ESTADO_INICIAL; 			// Estado dentro del AF (inicializado en con el primer estado) 
		int tipoCaracter;  			 			// Tipo del caracter leido
		int tipoToken = Parser.INCOMPLETO;  	// Tipo de Token es Incompleto por default, para que yyparse() no pida mas tokens si ya no se estan devolviendo a pesar de que no se haya llegado a fin de archivo (esto se da en los casos en que el archivo termina con caracteres ignorados o con comentarios)
		int tipoTokenAux;            
		try {		
			do {

				caracter = archivo.getChar(); //levantamos el caracter
				tipoCaracter = clasificarCaracter(caracter); //lo clasificamos
			
				IRutinaSemantica rutina = rutinaSemantica[estado][tipoCaracter];
				tipoTokenAux = rutina.execute(caracter, token, yylval);
			
				/*
				 * Algunos casos devuelven INCOMPLETO porque se llama a Ignorar antes de pasar
				 * al ltimo estado, cuando en verdad el Tipo de Token correcto ya lo tenemos guardado de
				 *  una llamada a rutina anterior. En esos casos, no guardamos el valor tipoToken sino que
				 *  lo ignoramos. 
				 */ 
				if(tipoTokenAux != Parser.INCOMPLETO) {
					tipoToken = tipoTokenAux;
				}
			
				// Se pasa al proximo estado
				estado = nuevoEstado[estado][tipoCaracter];
			} while ((estado != Parser.ESTADO_FINAL) && (tipoTokenAux != Parser.ERROR_LEXICO) && (!archivo.esFinDeArchivo()));
		
			if((estado == Parser.ESTADO_FINAL)) {
				archivo.unGet();
			}
			
			if(archivo.esFinDeArchivo()) {
				IRutinaSemantica rutina = rutinaSemantica[estado][C_FIN_DE_ARCHIVO];
				tipoToken = rutina.execute(caracter, token, yylval);
			}
		
			if(imprimir) {
				System.out.print("Tipo:" + tipoToken + " ");
				System.out.print(token);
				if(tipoToken == Parser.ID || tipoToken == Parser.CTE_NUM || tipoToken == Parser.CTE_STR)
				{	String posicion = "(Pos " + yylval.ival + ")"; 
					System.out.print("                   ".substring(token.length()));
				 	System.out.print(posicion);
			   		System.out.print("          ".substring(posicion.length()));
			   		System.out.print(TablaDeSimbolos.getInstance().getPos(yylval.ival)); 
				}
				System.out.print("\n");
			}			
			return tipoToken;
		} catch (IOException e) {
			return Parser.INCOMPLETO; 
		}
	}
}
