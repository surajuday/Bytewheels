* UI best viewed on Google Chrome *

Tools used : 
*******************************************
java version : jdk1.8
tomcat version : 9.0.6
Mysql community server
Angular 4



Deployment Notes:
********************************************
1. There is an already packaged .WAR file, built using Maven, in the src/target path of the source code.
2. Deploy the war directly onto your application server (used Tomcat 9 for Testing & Development).
3. For the table structure, There are two .sql files that contain all the DDL and DML queries. Please run the same.
4. The default path for the properties file is set as "${user.home}/ByteWheels/properties.txt" . Please place the properties.txt file in the same location on the server you would like to deploy the application.
5. Change the jdbc. properties according to the Database you are connecting to.
6. Change the mail. properties based on your smtp configurations.
7. The UI components(distribution) have been placed already under the "/WebContent" folder of the project. For the source code, please refer to the folder named angular.



Challenges faced:
**********************************************
1. CORS : I faced issues when i was trying to connect from my angular app to my Rest backend. Upon adding CORS headers to the response object, i was able to resolve the issue.
2. Integration (UI + backend) : When integrating the UI distributed code with my Backend so that i can run as a single WAR, i was not able to render my UI content upon deploying the WAR. This was resolved after changing the base-href of the UI code.
3. Designing the UI : Since i am new to angular, i had lot of challenges while designing the UI. 