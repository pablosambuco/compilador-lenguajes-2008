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
DISPLAY("123456789012345678901234567890123456789012345678901234567890");
UNTIL( !(a != 1) );
END