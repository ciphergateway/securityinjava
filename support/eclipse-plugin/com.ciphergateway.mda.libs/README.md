
remove oradle/db2 jdbc driver, because of can't download them from public repository

------the following line is complete MANIFEST.MF, include oradle/db2 jdbc driver------
Manifest-Version: 6.0.0
Bundle-ManifestVersion: 2
Bundle-Name: com.ciphergateway.mda.libs
Bundle-SymbolicName: com.ciphergateway.mda.libs;singleton:=true
Bundle-Version: 6.0.0.qualifier
Eclipse-BundleShape: dir
Export-Package: org.quickbundle.config,
 org.quickbundle.tools.helper,
 org.quickbundle.mda,
 org.dom4j,
 org.dom4j.io,
 org.apache.xerces.parsers,
 org.jaxen,
 oracle.jdbc,
 oracle.jdbc.driver,
 com.ibm.db2.jcc,
 net.sourceforge.jtds.jdbc,
 com.sybase.jdbc2.jdbc,
 com.informix.jdbc,
 org.postgresql,
 com.mysql.jdbc,
 org.h2
Bundle-Vendor: CipherGateway team
Bundle-ClassPath: .,
 target/classes,
 lib/dom4j-1.6.1.jar,
 lib/xml-apis-1.4.01.jar,
 lib/jaxen-1.1.6.jar,
 lib/jug-2.0.0-lgpl.jar,
 lib/activation-1.1.jar,
 lib/saxon-9.1.0.8.jar,
 lib/jtds-1.3.1.jar,
 lib/postgresql-9.4.1212.jar,
 lib/db2java.jar,
 lib/db2jcc_javax.jar,
 lib/db2jcc_license_cisuz.jar,
 lib/db2jcc_license_cu.jar,
 lib/db2jcc.jar,
 lib/jdbc-4.10.12.jar,
 lib/ojdbc8-12.2.0.1.jar,
 lib/h2-1.4.197.jar,
 lib/mail-1.4.7.jar,
 lib/mysql-connector-java-5.1.47.jar,
 lib/slf4j-api-1.7.25.jar,
 lib/xercesImpl-2.12.0.jar,
 lib/commons-collections-3.2.2.jar
------end MANIFEST.MF, include oradle/db2/informix jdbc driver------