@ECHO OFF

echo "%revision%="%revision%

echo [1/4]rename plugin directory...
cd target 
move com.ciphergateway.build-eclipse-plugin-%revision%-bin eclipse
cd eclipse
move com.ciphergateway.build-eclipse-plugin-%revision% plugins
cd plugins

echo [2/4]extract plugin jars...
call :extractJar com.ciphergateway.mda.gc-%revision%
call :extractJar com.ciphergateway.mda.gp-%revision%
call :extractJar com.ciphergateway.mda.libs-%revision%
call :extractJar com.ciphergateway.mda.mvm-%revision%

echo [3/4]copy securityinjava-ssm...
cd ..\..\..\..\..\..\securityinjava-ssm
rem call mvn clean eclipse:clean eclipse:eclipse
cd ..

echo [4/4]extract securityinjava-ssm.jar...
call jar cfM support\build-all\com.ciphergateway.build-eclipse-plugin\target\securityinjava-ssm.jar securityinjava-ssm
cd support\build-all\com.ciphergateway.build-eclipse-plugin\target\eclipse\plugins\com.ciphergateway.mda.gp-%revision%\t\j1
call jar xfM ..\..\..\..\..\securityinjava-ssm.jar

goto :EOF  


:extractJar
setlocal
echo extracting %1
mkdir %1
cd %1
call jar xf ../%1.jar
cd ..
del %1.jar
endlocal&goto :EOF

:packageJar
setlocal
echo packeging %1
cd %1
call jar cf %1.jar *
move %1.jar ..
cd ..
rd /s/q %1
endlocal&goto :EOF

