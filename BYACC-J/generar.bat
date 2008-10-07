@echo off
yacc.exe -J -Jpackage=compilador.parser input.y
pause
move *.java ..\src\java\compilador\parser
