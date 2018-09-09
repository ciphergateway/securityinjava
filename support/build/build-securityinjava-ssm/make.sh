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
mv build-securityinjava-ssm-6.0.0-bin eclipse
cd eclipse
mv build-securityinjava-ssm-6.0.0 plugins
cd plugins

echo extract plugin jars...
extractJar org.quickbundle.mda.gc-6.0.0
extractJar org.quickbundle.mda.gp-6.0.0
extractJar org.quickbundle.mda.libs-6.0.0
extractJar org.quickbundle.mda.mvm-6.0.0

echo copy securityinjava-ssm...
cd ../../../../../../securityinjava-ssm
mvn clean eclipse:clean eclipse:eclipse
#mvn clean
cd ..
echo extract securityinjava-ssm.jar...
jar cfM support/build/build-securityinjava-ssm/target/securityinjava-ssm.jar securityinjava-ssm
pwd
cd support/build/build-securityinjava-ssm/target/eclipse/plugins/org.quickbundle.mda.gp-6.0.0/t/j1
jar xfM ../../../../../securityinjava-ssm.jar
