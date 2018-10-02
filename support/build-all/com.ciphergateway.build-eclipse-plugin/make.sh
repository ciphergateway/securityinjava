#!/bin/bash

function extractJar()
{
  echo extracting $1
  mkdir $1
  cd $1
  jar xf ../$1.jar
  cd ..
  rm -f $1.jar
}

echo "${revision}="${revision}

echo [1/4]rename plugin directory...
cd target
mv com.ciphergateway.build-eclipse-plugin-${revision}-bin eclipse
cd eclipse
mv com.ciphergateway.build-eclipse-plugin-${revision} plugins
cd plugins

echo [2/4]extract plugin jars...
extractJar com.ciphergateway.mda.gc-${revision}
extractJar com.ciphergateway.mda.gp-${revision}
extractJar com.ciphergateway.mda.libs-${revision}
extractJar com.ciphergateway.mda.mvm-${revision}

echo [3/4]copy securityinjava-ssm...
cd ../../../../../../securityinjava-ssm
mvn clean eclipse:clean eclipse:eclipse
cd ..

echo [4/4]extract securityinjava-ssm.jar...
jar cfM support/build-all/com.ciphergateway.build-eclipse-plugin/target/securityinjava-ssm.jar securityinjava-ssm
cd support/build-all/com.ciphergateway.build-eclipse-plugin/target/eclipse/plugins/com.ciphergateway.mda.gp-${revision}/t/j1
jar xfM ../../../../../securityinjava-ssm.jar
