@echo off
yacc.exe -J -Jpackage=compilador.parser input.y
move *.java ..\src\java\compilador\parser
