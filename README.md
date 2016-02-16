# FileParser
A project that can parse file's data to POJO and display in console. Application also can load POJO object's data in to MySql database.

This Application tested on Windows 7 and java SDK 1.7

Application based on maven and EClipse MARS.1 [version 4.5.1]

Application has a class called RunMe to start the process.

Application will generate a log file in C:/Waynaut/log/FileParse.log incase of trace exceptions.

Also a configuration file called Config.xml will generate in C:/Waynaut/Config.xml and default values are

1. LoadInDatabase = Inactive 

2. ShowProgress = Inactive

3. DbUrl = jdbc:mysql://localhost/dnname

4. UserID = root

5. UserPass = root

6. FolderPathOfFiles = C:/GTFS

Options can be used like below:

1. LoadInDatabase = active -> All necessary table will create in database and POJO object's data will insert in database. 

2. ShowProgress = active -> Application will write progress percentage in console of database inserted data.

3. DbUrl = jdbc:mysql://IP:PORT/dbname -> example jdbc:mysql://192.168.2.1:3306/dbname (for remote database).

4. UserID = Database user id

5. UserPass = Databse user password

6. FolderPathOfFiles = File's folder path that need to read and load in POJO objects.

