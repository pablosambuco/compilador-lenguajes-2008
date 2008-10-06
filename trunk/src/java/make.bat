@echo off
del compilador\analizadorLexicografico\*.class
del compilador\beans\*.class
del compilador\parser\*.class
del compilador\semantica\*.class
del compilador\util\*.class
javac -d . compilador\parser\Parser.java
pause