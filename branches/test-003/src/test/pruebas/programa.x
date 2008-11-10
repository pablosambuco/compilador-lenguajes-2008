TYPE frutas AS ["manzana"];
TYPE valores AS [12, 435.6];
DEFVAR
	a, b, c, d, constantes: FLOAT;
	probando, then, else, repeat, mucho: STRING;
	changuito, bolsa: frutas;
	carrito: valores;
	perro, gato: POINTER;
ENDDEF
BEGIN
a=1;
REPEAT
a = a + 1;
DISPLAY("ESTO LOOPEA HERMOSO AUNQUE ESTA CADENA SEA DEMASIADO LARRRGAAA !!!");
UNTIL( !(a != 1) );
END