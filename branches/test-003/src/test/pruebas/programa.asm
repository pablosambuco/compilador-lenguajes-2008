.MODEL LARGE
.386
.STACK 200h

.DATA

MAXTEXTSIZE	 equ 	 30
CARRIAGE_RETURN	 equ 	 13
LINE_FEED	 equ 	 10
SALTO_LINEA db	 CARRIAGE_RETURN, LINE_FEED, '$' 
_@cadena1	 db 	 "manzana", '$', 23 dup (?) ;Constante String
__12	 dd 	 12.0 ;Constante Real
_@cadena5	 db 	 "manzalna", '$', 22 dup (?) ;Constante String
__a	 dd 	 ? ;Variable Real
__b	 dd 	 ? ;Variable Real
__c	 dd 	 ? ;Variable Real
__d	 dd 	 ? ;Variable Real
__constantes	 dd 	 ? ;Variable Real
__mucho	 dd 	 ? ;Variable Real
__probando	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
__then	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
__else	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
__repeat	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
__changuito	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
__bolsa	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
__carrito	 dd 	 ? ;Variable Real
__perro	 dw 	 ? ;Variable Pointer (16 bits)
__gato	 dw 	 ? ;Variable Pointer (16 bits)
_@cadena21	 db 	 "123...", '$', 24 dup (?) ;Constante String
__4	 dd 	 4.0 ;Constante Real
__2	 dd 	 2.0 ;Constante Real
__3	 dd 	 3.0 ;Constante Real
_@cadena25	 db 	 "A > B VERDADERO", '$', 15 dup (?) ;Constante String
_@cadena26	 db 	 "C = D VERDADERO", '$', 15 dup (?) ;Constante String
_@cadena27	 db 	 "C = D FALSO", '$', 19 dup (?) ;Constante String
_@cadena28	 db 	 "A > B FALSO", '$', 19 dup (?) ;Constante String
_@cadena29	 db 	 "A != D VERDADERO", '$', 14 dup (?) ;Constante String
_@cadena30	 db 	 "A != D FALSO", '$', 18 dup (?) ;Constante String
__1	 dd 	 1.0 ;Constante Real
__0	 dd 	 0.0 ;Constante Real


.CODE
	JMP	 INICIO

include lib.asm ;Funciones de biblioteca

INICIO:
	 mov	 AX,@DATA	 ;Comienzo de la zona de Código
	 mov	 DS,AX
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _@cadena21 
	 mov di,OFFSET __probando 
	 call COPIAR 
	 mov 	 eax,__4
	 mov 	 __a, eax 
	 mov 	 eax,__2
	 mov 	 __b, eax 
	 mov 	 eax,__3
	 mov 	 __c, eax 
	 mov 	 eax,__4
	 mov 	 __d, eax 
etiqueta_20: ;REPEAT
	 fld 	 __b
	 fld 	 __a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jbe 	 etiqueta_54
etiqueta_27: ;THEN
	 mov 	 DX, OFFSET _@cadena25 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 __d
	 fld 	 __c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_43
etiqueta_37: ;THEN
	 mov 	 DX, OFFSET _@cadena26 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
 	 jmp 	 etiqueta_51
etiqueta_43: ;ELSE
	 mov 	 DX, OFFSET _@cadena27 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 eax, __d
	 mov 	 __c, eax 
etiqueta_51: ;ENDIF
 	 jmp 	 etiqueta_79
etiqueta_54: ;ELSE
	 mov 	 DX, OFFSET _@cadena28 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 __d
	 fld 	 __a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_74
etiqueta_64: ;THEN
	 mov 	 DX, OFFSET _@cadena29 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 eax, __a
	 mov 	 __d, eax 
 	 jmp 	 etiqueta_78
etiqueta_74: ;ELSE
	 mov 	 DX, OFFSET _@cadena30 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_78: ;ENDIF
etiqueta_79: ;ENDIF
	 fld 	 __a
	 fld 	 __1
	 fsubp 
	 fstp 	 __a
	 fld 	 __0
	 fld 	 __a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 ja 	 etiqueta_20
	 mov	 ax,4C00h	 ;Termina la ejecución
	 int	 21h
END
