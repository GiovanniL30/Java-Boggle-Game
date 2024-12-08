# CORBA Java Project

This project demonstrates a distributed application built using Java CORBA. Ensure Java 8 is installed and properly configured before proceeding. Follow the steps below to set up and run the ORB, server, admin interface, and client application.

---

## Prerequisites
1. **Java 8**: Ensure Java 8 is installed and configured.
    - Check Java version:
      ```
      java -version
      ```
    - If not installed, download and install Java 8 from the Oracle Java 8 website.

2. **Environment Configuration**:
    - Set `JAVA_HOME` to your Java 8 installation path.
    - Add the `bin` directory to your system's PATH.

   Example for Unix-based systems:
   ```
   set JAVA_HOME=C:\path\to\java8 set PATH=%JAVA_HOME%\bin;%PATH%
   ```
---
## Steps to Run the Project

### 1. Start the ORB Daemon
Start the ORB daemon (ORBD) to initialize the Name Service:
   ```
   start orbd -ORBInitialPort 1099 -ORBInitialHost localhost
   ```
This command starts the ORB daemon on port `1099` and binds it to `localhost`.

### 2. Run the Server
1. Navigate to the `src/Server_Java` directory:
   ```
    cd src/Server_Java
   ```
2. Start the server by running:
   ```
    java Server
   ```
Note: You can connect to the server by entering the IP address of the machine hosting the server.

### 3. Run the Admin Application (Optional)
1. Navigate to the `src/Server_Java` directory:
   ```
   cd src/Server_Java
   ```
2. Start the Admin application:
   ```
    java AdminMainApplication
   ```
   
### 4.  Run the Game Application
1. Navigate to the `src/Client_Java` directory:
   ```
   cd src/Client_Java
   ```
2. Start the Client application:
   ```
   java MainApplication
   ```

---
## Additional Notes
- Ensure the **IP address** of the server is correctly configured in the client application when joining the game.
- Use `localhost` for testing on a single machine.
- For debugging, check the console logs for any connection or initialization errors.
---