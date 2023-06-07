# RestCipher

## Hi there!

This project saves some users into DB on startup.  It allows us to encrypt the username via Get call and decrypt via Post call. 

### Some information:
- The project runs through SpringBoot. 
- It uses PostgresSQL.
- Logs under INFO are showing in the console. The DEBUG level logs can be found in [log file](logs/out.log). 
- The encrypted and decrypted data can be used only in the scope of one project running.

### Instructions before running: 
1. Make sure that spring.datasource.url is correct in [application.properties](src/main/resources/application.properties).
2. Make sure that the correct credentials for DB are used in [application.properties](src/main/resources/application.properties).
 
