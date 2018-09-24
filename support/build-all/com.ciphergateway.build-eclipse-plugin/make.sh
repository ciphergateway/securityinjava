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

cd target 

echo rename directory...
mv com.ciphergateway.build-eclipse-plugin-6.0.0-bin eclipse
cd eclipse
mv com.ciphergateway.build-eclipse-plugin-6.0.0 plugins
cd plugins

echo extract plugin jars...
extractJar com.ciphergateway.mda.gc-6.0.0
extractJar com.ciphergateway.mda.gp-6.0.0
extractJar com.ciphergateway.mda.libs-6.0.0
extractJar com.ciphergateway.mda.mvm-6.0.0

echo copy securityinjava-ssm...
cd ../../../../../../securityinjava-ssm
mvn clean eclipse:clean eclipse:eclipse
#mvn clean
cd ..
echo extract securityinjava-ssm.jar...
jar cfM support/build/com.ciphergateway.build-eclipse-plugin/target/securityinjava-ssm.jar securityinjava-ssm
pwd
cd support/build/com.ciphergateway.build-eclipse-plugin/target/eclipse/plugins/com.ciphergateway.mda.gp-6.0.0/t/j1
jar xfM ../../../../../securityinjava-ssm.jar
