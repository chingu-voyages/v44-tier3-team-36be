# v44-tier3-team-36be

Application Details:

This project is a spring boot application with spring boot web dependency to run on tomcat web server 

Other dependencies: Spring Data JPA and MySQL Driver for data persistence.

We are utilizing Java version 17 and Maven build tools.

The main-app is used for dealing with user, subscription and station information while the notifications-app is used for managing and sending notifications to our end users as needed.
 
The main-app is set to run on port 8080 while the notifications-app is set to run on port 8090

When running locally, can stop and start both applications at the same time using docker-compose.

The command 'docker-compose up --build' will start them and docker-compose down will stop running them.

To run tests for each application, simply right click on the application name and select `run all tests`.

### Contributors

<br/>

<a href="https://github.com/chingu-voyages/v44-tier3-team-36be/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=chingu-voyages/v44-tier3-team-36be" />
</a>
