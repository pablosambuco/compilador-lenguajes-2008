TYPE frutas1 AS ["manzana"];
TYPE valores1 AS [12,13,14];
DEFVAR
	a, b, c, d, constantes, mucho: FLOAT;
	probando, then, else, repeat: STRING;
	changuito, bolsa: frutas1;
	carrito: valores1;
	perro, gato: POINTER;
ENDDEF
BEGIN

	  probando = "123...";
    
    a = 1; 
    a = 1 + 2 + a; 
    b = a - 2;
    c = b * 2;
    d = b / 4 + 3;

    REPEAT
		IF (a>b) #%Si no estan inicializadas, no compila%#
	       DISPLAY("A > B VERDADERO");
	       IF(c==d)
	          DISPLAY("C = D VERDADERO");
	       ELSE
	          DISPLAY("C = D FALSO");
	          c = d;
	       ENDIF
	    ELSE
	       DISPLAY("A > B FALSO");
	       IF(a!=d)
	          DISPLAY("A != D VERDADERO");
	          d = a;
	       ELSE
	          DISPLAY("A != D FALSO");
	       ENDIF       
	    ENDIF
	    a = a - 1;
	UNTIL(a > 0);    
 
    #%
    DISPLAY("MIRA NO ANDA");
    DISPLAY("MIRA@NO@ANDA");
    DISPLAY("MIRANOANDA");
    DISPLAY("MIRA$NO$ANDA");
    DISPLAY("MIRA_NO_ANDA");
    DISPLAY("MIRA.NO.ANDA");
    %#
    #%
	probando = "manzana";
	perro = probando;
	
	carrito = 12;
	b = carrito * 3;
	
	probando = "holaaaaa!!!!";
	changuito = "manzana";
	bolsa = "manzana";
	
	changuito = bolsa;
	
	
	b = 8.1;
	repeat = "lalal";
	IF(carrito + 8 < b)
		DISPLAY("FUNCIONA");
	ENDIF
	
	changuito = "manzana";
	bolsa = changuito;
	probando = "pepe";
	then = probando;
	b = 8.2; 
	a = b;
	a = 8.4 / 2.9 - 2.7 * 2.5 + 7.1 + b;
	DISPLAY("mandioca");
	repeat = "_mandioca";
	repeat = "_repeat";
	changuito = "manzana";
    %#
	#%
	#%Comiezo de IFs simples%#
	
	IF(a == b)	
		probando = then; 
		DISPLAY("FUNCIONA");
		changuito = "manzana";
	ENDIF
	
	IF(!(a == b))	
		probando = then;
		DISPLAY("FUNCIONA");  
	ENDIF
	
	IF(a == b && c == d)	
		probando = then; 
		DISPLAY("FUNCIONA"); 		
	ENDIF
	
	IF(a == b || c ==d)	
		probando = then; 
		DISPLAY("FUNCIONA"); 		
	ENDIF
	
	
	#%Comiezo de IFs compuestos%#
	
	IF(a == b)	
		probando = then;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;		
	ELSE
		probando = else;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;			 
	ENDIF
	
	IF(!(a == b))	
		probando = mucho;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9 + AVG([234,443.1,22,768]);		
	ELSE
		probando = else;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9 + AVG([234,443.1,22,768]);				 
	ENDIF
	
	IF(a == b && c == d)	
		probando = mucho;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;		
	ELSE
		probando = else;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;				 
	ENDIF
	
	IF(a == b || c ==d)	
		probando = mucho;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;		
	ELSE
		probando = else;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;				 
	ENDIF
	
	#%Comiezo de REPEAT-UNTIL%#
	
	REPEAT
		probando = repeat;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;	
	UNTIL(a == b);
	
	REPEAT
		probando = repeat;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;
	UNTIL(!(a == b));
	
	REPEAT
		probando = repeat;
		DISPLAY("FUNCIONA"); 		
		constantes = 5 + 8 + 3.9;
	UNTIL(a == b && c == d);
	
	REPEAT
		probando = repeat;
		DISPLAY("FUNCIONA");
		constantes = 5 + 8 + 3.9;		
	UNTIL(a == b || c == d); 

	#%ANIDAMIENTO%#
	
	REPEAT
		probando = repeat;
		DISPLAY("FUNCIONA");
		
		IF(a == b && c ==d)	
			probando = mucho;
			DISPLAY("FUNCIONA");
			
			REPEAT
				probando = repeat;
				DISPLAY("FUNCIONA");
				
				IF(a == b && c ==d)	
					probando = mucho;
					DISPLAY("FUNCIONA"); 		
					constantes = 5 + 8 + 3.9;		
				ELSE
					probando = else;
					DISPLAY("FUNCIONA"); 		
					constantes = 5 + 8 + 3.9;				 
				ENDIF
				
				constantes = 5 + 8 + 3.9;		
			UNTIL(a == b || c == d);	
				 		
			constantes = 5 + 8 + 3.9;		
		ELSE
			probando = else;
			DISPLAY("FUNCIONA");
			IF(a != b)
				DISPLAY("FUNCIONA");	
			ENDIF
			constantes = 5 + 8 + 3.9;				 
		ENDIF
		
		constantes = 5 + 8 + 3.9;		
	UNTIL(a == b || c == d);
		
	IF(a == b)
		IF(c == d)		
			probando = then; 
		ENDIF
	ENDIF
	
	probando = 3 + AVG([234,443.1,22,768]) / AVG([43, 23, 85]);%#
END