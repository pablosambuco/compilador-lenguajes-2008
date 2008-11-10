@echo off
cd ..\BYACC-J
yacc.exe -J -Jpackage=compilador.parser input.y
move *.java ..\src\java\compilador\parser
cd ..\src\java
del compilador\analizadorLexicografico\*.class 2>NUL
del compilador\beans\*.class 2>NUL
del compilador\parser\*.class 2>NUL
del compilador\semantica\*.class 2>NUL
del compilador\util\*.class 2>NUL
del compilador\sintaxis\*.class 2>NUL
javac -d . compilador\parser\Parser.java
cd ..\..\tools