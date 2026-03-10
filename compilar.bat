@echo off

echo =================================
echo Generando Analizador Lexico
echo =================================

java -jar lib\jflex-1.9.1.jar src\analizadores\Lexico.jflex

echo.
echo =================================
echo Generando Analizador Sintactico
echo =================================

java -jar lib\java-cup-11b.jar -parser Sintactico -symbols sym -destdir src\analizadores src\analizadores\Sintactico.cup

echo.
echo =================================
echo Analizadores generados
echo =================================

pause
