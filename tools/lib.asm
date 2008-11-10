;************************************************************
; devuelve en BX la cantidad de caracteres que tiene un string
; DS:SI apunta al string.
;
PROC STRLEN
mov bx,0
STRL01:
cmp BYTE PTR [SI+BX],'$'
je STREND
inc BX
JMP STRL01
STREND:
ret
ENDP

;*********************************************************************8
; copia DS:SI a ES:DI; busca la cantidad de caracteres
;
PROC COPIAR
call STRLEN ; busco la cantidad de caracteres
cmp bx,MAXTEXTSIZE
JBE COPIARSIZEOK
mov bx,MAXTEXTSIZE
COPIARSIZEOK:
mov cx,bx ; la copia se hace de 'CX' caracteres
cld ; cld es para que la copia se realice "hacia adelante"
rep movsb ; copia la cadea
mov al,'$' ; caracter terminador
mov BYTE PTR [DI],al ; el registro DI quedo apuntando al final
ret
ENDP


