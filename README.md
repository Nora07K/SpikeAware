"# SpikeAware"


Instructions:
Download MySQL Installer 8.0.46 for the devices Operating System. I used the mysql-installer-community-8.xxxxx download.
Once you run it:
In 'Choosing a Setup Type' Choose Full to get the Server and the Client installed
Set the path to chosen location
Execute the products installation. It should look like this:
In product Configuration under Accounts and Roles create a MySQL Root Password. NOTE THIS DOWN AS ITS THE DATABASE PASSWORD YOU NEED TO USE EVERYWHERE!!
Under Account and Roles create an account with the BD Admin role, note down the password.
From here for setup everything should be left as the defaults. 


Once this is finished you can import the .sql file into the handed in folder that has my Java program in it by:
Open MySQL Workbench
Either scan for or select the Local Instance Server you just created
To connect to the MySQL Server it'll ask for a password. This is the Database Password.
On the top of the screen hover over 'Server' and click on 'Data Import'
Select 'Import from self-contained file'
Navigate to and select the file in MySQL
Click on 'Start Import'
If you navigate back to the other tab, 'Query ??' you can run MYSQL commands from here but this is unnecessary for the project to function.


To connect the Java files to the MySQL database you'll need to download the MySQL Connector/Java from: https://dev.mysql.com/downloads/connector/j/
Select the appropriate OS from the list. For window devices download the Platform Independent OS one.
From the zip file please export the mysql-connector-j-xxx.jar file into the folder submitted
In the Java IDE where you opened this project, go to Project Structure, Modules, Dependencies and click '+', select JARs or Directories and select the MySQL connector-j.jar file you just downloaded. Click Apply.

To be able to register a user account and to log in the java program needs to be ran in Windows Powershell or an OS equivalent. The command you need to run the program is gotten from running the program in the Java IDE at the very top of the Command prompt. 

It will start with: C:\Users\ME\.jdks\openjdk-25.0.2\bin\java.exe. To get this project working you will need to modify this line for your own server connection. 

The -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 section will need to be modified to add apostrophes from the -D part of each command to the = section; it should look like this: -D'file.encoding'=UTF-8 -D'sun.stdout.encoding'=UTF-8 -D'sun.stderr.encoding'=UTF-8

Your classpath should have quotes around the path location like this: -classpath "C:\Users\ME\Desktop\School Coursework\SoftwarePrinciples\out\production\SoftwarePrinciples;C:\Users\Me\Desktop\School Coursework\SoftwarePrinciples\mysql-connector-j-9.6.0.jar"

After the classpath and before the 'Main' you need to configure these 3 parameters:  -Ddb_host=***** -Ddb_user=***** -Ddb_password=*****
   The default value of -Ddb_host unless you modified the server setup by changing the port number from 3306 is: jdbc:mysql://localhost:3306/2503079'sMYSQLdatabse
   The -Ddb_user= root; unless you modified the setup in whichcase change this accordingly.
   The -Ddb_password= MySQL Database Password; this depends on what you used in the server configuration. Check in the 1st section of the Instructions.

If everything was done appropriately it should look something like this:
C:\Users\ME\.jdks\openjdk-25.0.2\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.3\lib\idea_rt.jar=54955" -D'file.encoding'=UTF-8 -D'sun.stdout.encoding'=UTF-8 -D'sun.stderr.encoding'=UTF-8 -classpath "C:\Users\ME\Desktop\School Coursework\SoftwarePrinciples\out\production\SoftwarePrinciples;C:\Users\ME\Desktop\School Coursework\SoftwarePrinciples\mysql-connector-j-9.6.0.jar" -Ddb_host=***** -Ddb_user=***** -Ddb_password=***** Main


For testing:

Username: Hawke
Password: Hawke
Access Level: Super Admin

Username: Fenris
Password: Fenris
Access Level: Admin

Username: Talis
Password: Talis
Access Level: User

