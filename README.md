# sql-config-utils

###### This tool can be used to perform RDBMS CRUD operations. This tool can be very useful if you want to automate the db operations via script or via continuous integrations tools such as Jenkins,Bamboo etc. I have used this tool to configure database after build deployment via Jenkins.

e.g. Update/Alter some table in MySQL database once build is deployed based on need.

###### It takes an XML file as input configuration and performs the operation based on configurations mapped in xml file. This tool is very useful for CI when you want to perform some operations on database tables after build deployment.

###### Sample ANT script (available as part of this project) can be used to trigger the SQLConfigProcessor. This tool supports MySQL and MSSQL configuration as of now. 


Ant command (Refer sample-build.xml to read more about parameters):


e.g. ant local-environment configure-db-via-app -DuserName=root -Dpassword=root -DdbServerHost=localhost -DdbServerPort=3306 -DdbVendor=mysql -DdbName=test -DappVersion=v1.0.1