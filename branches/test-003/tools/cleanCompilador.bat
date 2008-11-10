@echo off
cd ..\src\java
del compilador\analizadorLexicografico\*.class 2>NUL
del compilador\beans\*.class 2>NUL
del compilador\parser\*.class 2>NUL
del compilador\semantica\*.class 2>NUL
del compilador\util\*.class 2>NUL
del compilador\sintaxis\*.class 2>NUL
cd ..\..\tools
