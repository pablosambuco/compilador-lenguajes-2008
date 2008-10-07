#!/bin/bash
rm compilador/analizadorLexicografico/*.class
rm compilador/beans/*.class
rm compilador/parser/*.class
rm compilador/semantica/*.class
rm compilador/util/*.class@echo off
rm compilador/analizadorLexicografico/*.class
rm compilador/beans/*.class
rm compilador/parser/*.class
rm compilador/semantica/*.class
rm compilador/util/*.class
javac -d . compilador/parser/Parser.java
