@echo off
type %1.x 1>NUL 2>NUL
IF ERRORLEVEL 1 GOTO ARCH_NO_ENCONTRADO
cd ..\src\java 
java compilador.parser.Parser ..\..\tools\%1
cd ..\..\tools
IF ERRORLEVEL 1 GOTO ERROR_AL_COMPILAR

tasm /la /zi %1.asm

IF ERRORLEVEL 1 GOTO ERROR_AL_ENSAMBLAR

tlink /v %1

IF ERRORLEVEL 1 GOTO ERROR_AL_LINKEDITAR

GOTO COMPILA_OK

:ARCH_NO_ENCONTRADO
echo No se pudo encontrar el archivo %1.x
exit /b 1

:ERROR_AL_COMPILAR
echo Error al compilar
exit /b 1

:ERROR_AL_ENSAMBLAR
echo Error al compilar
exit /b 1

:ERROR_AL_LINKEDITAR
echo Error al compilar
exit /b 1

:COMPILA_OK
echo Compilacion exitosa. 
echo Ejecutar el programa con %1.exe
exit /b 0