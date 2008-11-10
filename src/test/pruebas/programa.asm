.MODEL LARGE
.386
.STACK 200h

.DATA

MAXTEXTSIZE	 equ 	 30
CARRIAGE_RETURN	 equ 	 13
LINE_FEED	 equ 	 10
SALTO_LINEA db	 CARRIAGE_RETURN, LINE_FEED, '$' 
@cadena1	 db 	 "manzana", '$', 23 dup (?) ;Constante String
@real3	 dd 	 12.0 ;Constante Real
@cadena5	 db 	 "manzalna", '$', 22 dup (?) ;Constante String
_a	 dd 	 ? ;Variable Real
_b	 dd 	 ? ;Variable Real
_c	 dd 	 ? ;Variable Real
_d	 dd 	 ? ;Variable Real
_constantes	 dd 	 ? ;Variable Real
_probando	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_then	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_else	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_repeat	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_mucho	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_changuito	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_bolsa	 db 	 MAXTEXTSIZE dup (?),'$' ;Variable String
_carrito	 dd 	 ? ;Variable Real
_perro	 dw 	 ? ;Variable Pointer (16 bits)
_gato	 dw 	 ? ;Variable Pointer (16 bits)
@cadena21	 db 	 "oraleeee", '$', 22 dup (?) ;Constante String
@cadena22	 db 	 "que traes?", '$', 20 dup (?) ;Constante String
@cadena23	 db 	 "123...", '$', 24 dup (?) ;Constante String
@real24	 dd 	 4.0 ;Constante Real
@real25	 dd 	 2.0 ;Constante Real
@real26	 dd 	 3.0 ;Constante Real
@cadena27	 db 	 "A > B VERDADERO", '$', 15 dup (?) ;Constante String
@cadena28	 db 	 "C = D VERDADERO", '$', 15 dup (?) ;Constante String
@cadena29	 db 	 "C = D FALSO", '$', 19 dup (?) ;Constante String
@cadena30	 db 	 "A > B FALSO", '$', 19 dup (?) ;Constante String
@cadena31	 db 	 "A != D VERDADERO", '$', 14 dup (?) ;Constante String
@cadena32	 db 	 "A != D FALSO", '$', 18 dup (?) ;Constante String
@real33	 dd 	 1.0 ;Constante Real
@real34	 dd 	 0.0 ;Constante Real
@cadena35	 db 	 "MIRA NO ANDA", '$', 18 dup (?) ;Constante String
@cadena36	 db 	 "MIRA@NO@ANDA", '$', 18 dup (?) ;Constante String
@cadena37	 db 	 "MIRANOANDA", '$', 20 dup (?) ;Constante String
@cadena38	 db 	 "MIRA$NO$ANDA", '$', 18 dup (?) ;Constante String
@cadena39	 db 	 "MIRA_NO_ANDA", '$', 18 dup (?) ;Constante String
@cadena40	 db 	 "MIRA.NO.ANDA", '$', 18 dup (?) ;Constante String
@cadena41	 db 	 "holaaaaa!!!!", '$', 18 dup (?) ;Constante String
@real42	 dd 	 8.1 ;Constante Real
@cadena43	 db 	 "lalal", '$', 25 dup (?) ;Constante String
@real44	 dd 	 8.0 ;Constante Real
@cadena45	 db 	 "FUNCIONA", '$', 22 dup (?) ;Constante String
@cadena46	 db 	 "pepe", '$', 26 dup (?) ;Constante String
@real47	 dd 	 8.2 ;Constante Real
@real48	 dd 	 8.4 ;Constante Real
@real49	 dd 	 2.9 ;Constante Real
@real50	 dd 	 2.7 ;Constante Real
@real51	 dd 	 2.5 ;Constante Real
@real52	 dd 	 7.1 ;Constante Real
@cadena53	 db 	 "mandioca", '$', 22 dup (?) ;Constante String
@cadena54	 db 	 "_mandioca", '$', 21 dup (?) ;Constante String
@cadena55	 db 	 "_repeat", '$', 23 dup (?) ;Constante String
@real56	 dd 	 5.0 ;Constante Real
@real57	 dd 	 3.9 ;Constante Real
@real58	 dd 	 234.0 ;Constante Real
@real59	 dd 	 443.1 ;Constante Real
@real60	 dd 	 22.0 ;Constante Real
@real61	 dd 	 768.0 ;Constante Real
@real62	 dd 	 43.0 ;Constante Real
@real63	 dd 	 23.0 ;Constante Real
@real64	 dd 	 85.0 ;Constante Real


.CODE
	JMP	 INICIO

include lib.asm ;Funciones de biblioteca

INICIO:
	 mov	 AX,@DATA	 ;Comienzo de la zona de Código
	 mov	 DS,AX
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena21 
	 mov di,OFFSET _else 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena22 
	 mov di,OFFSET _mucho 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena23 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 eax, @real24
	 mov 	 _a, eax 
	 mov 	 eax, @real25
	 mov 	 _b, eax 
	 mov 	 eax, @real26
	 mov 	 _c, eax 
	 mov 	 eax, @real24
	 mov 	 _d, eax 
etiqueta_28: ;REPEAT
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jbe 	 etiqueta_62
etiqueta_35: ;THEN
	 mov 	 DX, OFFSET @cadena27 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_51
etiqueta_45: ;THEN
	 mov 	 DX, OFFSET @cadena28 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
 	 jmp 	 etiqueta_59
etiqueta_51: ;ELSE
	 mov 	 DX, OFFSET @cadena29 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 eax, _d
	 mov 	 _c, eax 
etiqueta_59: ;ENDIF
 	 jmp 	 etiqueta_87
etiqueta_62: ;ELSE
	 mov 	 DX, OFFSET @cadena30 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 _d
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_82
etiqueta_72: ;THEN
	 mov 	 DX, OFFSET @cadena31 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 eax, _a
	 mov 	 _d, eax 
 	 jmp 	 etiqueta_86
etiqueta_82: ;ELSE
	 mov 	 DX, OFFSET @cadena32 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_86: ;ENDIF
etiqueta_87: ;ENDIF
	 fld 	 _a
	 fld 	 @real33
	 fsubp 
	 fstp 	 _a
	 fld 	 @real34
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 ja 	 etiqueta_28
etiqueta_100: ;END REPEAT-UNTIL
	 mov 	 DX, OFFSET @cadena35 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 DX, OFFSET @cadena36 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 DX, OFFSET @cadena37 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 DX, OFFSET @cadena38 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 DX, OFFSET @cadena39 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov 	 DX, OFFSET @cadena40 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena1 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 ax, OFFSET _probando
	 mov 	 _perro, ax 
	 mov 	 eax, @real3
	 mov 	 _carrito, eax 
	 fld 	 _carrito
	 fld 	 @real26
	 fmulp 
	 fstp 	 _b
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena41 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena1 
	 mov di,OFFSET _changuito 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena1 
	 mov di,OFFSET _bolsa 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _bolsa 
	 mov di,OFFSET _changuito 
	 call COPIAR 
	 mov 	 eax, @real42
	 mov 	 _b, eax 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena43 
	 mov di,OFFSET _repeat 
	 call COPIAR 
	 fld 	 _carrito
	 fld 	 @real44
	 faddp 
	 fld 	 _b
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jae 	 etiqueta_173
etiqueta_169: ;THEN
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_173: ;ENDIF
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena1 
	 mov di,OFFSET _changuito 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _changuito 
	 mov di,OFFSET _bolsa 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena46 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _probando 
	 mov di,OFFSET _then 
	 call COPIAR 
	 mov 	 eax, @real47
	 mov 	 _b, eax 
	 mov 	 eax, _b
	 mov 	 _a, eax 
	 fld 	 @real48
	 fld 	 @real49
	 fdivp 
	 fld 	 @real50
	 fld 	 @real51
	 fmulp 
	 fsubp 
	 fld 	 @real52
	 faddp 
	 fld 	 _b
	 faddp 
	 fstp 	 _a
	 mov 	 DX, OFFSET @cadena53 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena54 
	 mov di,OFFSET _repeat 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena55 
	 mov di,OFFSET _repeat 
	 call COPIAR 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena1 
	 mov di,OFFSET _changuito 
	 call COPIAR 
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_245
etiqueta_233: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _then 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET @cadena1 
	 mov di,OFFSET _changuito 
	 call COPIAR 
etiqueta_245: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_260
etiqueta_252: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _then 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_260: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_280
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_280
etiqueta_272: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _then 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_280: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_292
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_300
etiqueta_292: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _then 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_300: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_325
etiqueta_307: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _then 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
 	 jmp 	 etiqueta_341
etiqueta_325: ;ELSE
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _else 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
etiqueta_341: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_376
etiqueta_348: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _mucho 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fld 	 @real58
	 fld 	 @real59
	 faddp 
	 fld 	 @real60
	 faddp 
	 fld 	 @real61
	 faddp 
	 fld 	 @real24
	 fdivp 
	 faddp 
	 fstp 	 _constantes
 	 jmp 	 etiqueta_402
etiqueta_376: ;ELSE
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _else 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fld 	 @real58
	 fld 	 @real59
	 faddp 
	 fld 	 @real60
	 faddp 
	 fld 	 @real61
	 faddp 
	 fld 	 @real24
	 fdivp 
	 faddp 
	 fstp 	 _constantes
etiqueta_402: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_432
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_432
etiqueta_414: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _mucho 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
 	 jmp 	 etiqueta_448
etiqueta_432: ;ELSE
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _else 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
etiqueta_448: ;ENDIF
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_460
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_478
etiqueta_460: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _mucho 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
 	 jmp 	 etiqueta_494
etiqueta_478: ;ELSE
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _else 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
etiqueta_494: ;ENDIF
etiqueta_495: ;REPEAT
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _repeat 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_495
etiqueta_517: ;END REPEAT-UNTIL
etiqueta_518: ;REPEAT
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _repeat 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_518
etiqueta_540: ;END REPEAT-UNTIL
etiqueta_541: ;REPEAT
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _repeat 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_568
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_541
etiqueta_568: ;END REPEAT-UNTIL
etiqueta_569: ;REPEAT
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _repeat 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_569
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_569
etiqueta_596: ;END REPEAT-UNTIL
etiqueta_597: ;REPEAT
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _repeat 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_708
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_708
etiqueta_616: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _mucho 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_624: ;REPEAT
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _repeat 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_661
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_661
etiqueta_643: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _mucho 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
 	 jmp 	 etiqueta_677
etiqueta_661: ;ELSE
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _else 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
etiqueta_677: ;ENDIF
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_624
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_624
etiqueta_697: ;END REPEAT-UNTIL
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
 	 jmp 	 etiqueta_735
etiqueta_708: ;ELSE
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _else 
	 mov di,OFFSET _probando 
	 call COPIAR 
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_726
etiqueta_722: ;THEN
	 mov 	 DX, OFFSET @cadena45 
	 mov 	 AH, 9 ;Impresion por pantalla 
	 int 	 21h 
	 mov 	 DX, OFFSET SALTO_LINEA 
	 mov 	 AH, 9 ;Salto de Linea
	 int 	 21h 
etiqueta_726: ;ENDIF
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
etiqueta_735: ;ENDIF
	 fld 	 @real56
	 fld 	 @real44
	 faddp 
	 fld 	 @real57
	 faddp 
	 fstp 	 _constantes
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_597
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 je 	 etiqueta_597
etiqueta_755: ;END REPEAT-UNTIL
	 fld 	 _b
	 fld 	 _a
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_775
etiqueta_762: ;THEN
	 fld 	 _d
	 fld 	 _c
	 fcompp ;Comparacion en FPU 
	 fstsw 	 AX ;Guardamos el estado de la comparacion en AX 
	 fwait 
	 sahf ;Cargamos el CPU para hacer el salto 
	 jne 	 etiqueta_774
etiqueta_769: ;THEN
	 mov ax,@DATA 
	 mov ds,ax 
	 mov es,ax 
	 mov si,OFFSET _then 
	 mov di,OFFSET _probando 
	 call COPIAR 
etiqueta_774: ;ENDIF
etiqueta_775: ;ENDIF
	 fld 	 @real58
	 fld 	 @real59
	 faddp 
	 fld 	 @real60
	 faddp 
	 fld 	 @real61
	 faddp 
	 fld 	 @real24
	 fdivp 
	 fld 	 @real62
	 fld 	 @real63
	 faddp 
	 fld 	 @real64
	 faddp 
	 fld 	 @real26
	 fdivp 
	 fdivp 
	 fld 	 @real26
	 faddp 
	 fstp 	 _constantes
	 mov	 ax,4C00h	 ;Termina la ejecución
	 int	 21h
END
