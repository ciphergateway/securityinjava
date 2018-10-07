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
echo "${basedir}="${basedir}

echo [1/4]rename plugin directory...
cd ${basedir}/target
mv com.ciphergateway.build-eclipse-plugin-${revision}-bin eclipse
cd eclipse
mv com.ciphergateway.build-eclipse-plugin-${revision} plugins
cd plugins

echo [2/4]extract plugin jars...
extractJar com.ciphergateway.mda.gc-${revision}
extractJar com.ciphergateway.mda.gp-${revision}
extractJar com.ciphergateway.mda.libs-${revision}
extractJar com.ciphergateway.mda.mvm-${revision}
# back to root dir
cd ../../../../../../

echo [3/4]copy securityinjava-ssm...
cd securityinjava-ssm
mvn clean eclipse:clean eclipse:eclipse
# back to root dir
cd ..
jar cfM support/build-all/com.ciphergateway.build-eclipse-plugin/target/securityinjava-ssm.jar securityinjava-ssm
cd support/build-all/com.ciphergateway.build-eclipse-plugin/target/eclipse/plugins/com.ciphergateway.mda.gp-${revision}/t/j1
jar xfM ../../../../../securityinjava-ssm.jar
# back to root dir
cd ../../../../../../../../../


echo [4/4]copy securityinjava-vertx...
cd securityinjava-vertx
mvn clean eclipse:clean eclipse:eclipse
# back to root dir
cd ..
jar cfM support/build-all/com.ciphergateway.build-eclipse-plugin/target/securityinjava-vertx.jar securityinjava-vertx
cd support/build-all/com.ciphergateway.build-eclipse-plugin/target/eclipse/plugins/com.ciphergateway.mda.gp-${revision}/t/j2
jar xfM ../../../../../securityinjava-vertx.jar
# back to root dir
cd ../../../../../../../../../