# README #

This application is built with Spring boot. 
The application runs at port 8081.

### Requirement

1. Java 1.8+ JRE
2. Java 1.8+ JDK for development
3. MariaDb/Mysql database
4. gradle
5. AWS S3 credential

### How do I get set up? ###

1. Download the source code from the git hub
2. Create a database(MariaDb/Mysql) for the application.
2. And set up System environment variables for connecting to database and AWS

```
DEMO_DB_SOURCE=jdbc:mariadb://localhost:3306/dog
DEMO_DB_USER=dummy
AWS_ACCESS_KEY_ID=[AWS_ACCESS_KEY]
AWS_SECRET_ACCESS_KEY=[AWS_SECERT_KEY]
S3_REGION=[S3_REGION]
S3_BUCKET=[S3_BUCKET]
UPLOAD_QUOTA_FLAG=false
UPLOAD_QUOTA_TIME=0
```

UPLOAD_QUOTA_FLAG restricts the S3 uploading.

3. go to project root and run the following command to generate the artifact.
`./gradlew clean build`

You can skip testing by running 
`./gradlew clean build -x test`

4. You can run the application by the following command if you set the sysytem variables.
`java -jar /project/root/build/libs/assessment-0.0.0.jar`

Otherwise, you add `-DDEMO_DB_SOURCE=` and `-DDEMO_DB_USER=` in the command.

Alternative, you can run it via your IDE.
The main class is 
`com.dog.demo.DemoApplication`

### Deployment

You can run the application as service.
In linux 
```[Unit]
   Description=dog
   
   [Service]
   User=[user]
   Group=[group]
   EnvironmentFile=[FILE_PATH]
   ExecStart=/usr/bin/java -jar -Xms64M -Xmx256M /path/demo-0.0.0.jar
   ExecReload=/bin/kill -s HUP $MAINPID
   ExecStop=/bin/kill -S TERM $MAINPID
   #Restart=on-failure
   PrivateTmp=true
   [Install]
   WantedBy=multi-user.target

```


### Feature

Swagger documentation 

### Demo
Demo API : `http://188.166.243.95:8081`

Swagger documentation 
`http://188.166.243.95:8081/swagger-ui.html`



