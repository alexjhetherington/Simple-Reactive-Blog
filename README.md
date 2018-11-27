# Springboot Webflux Blog

##About

This is a very simple blog for practicing reactive Java. It was created for a backend position coding test. There is a *very* simple front end.

Important technologies are *Spring Boot, Reactor, Thymeleaf*. Data is MongoDB.

Users can create, edit, view and delete blog posts. Any user can view any blogpost, but only authorised users can perform other actions. Users can register accounts.

##Running

Requires a MongoDB connection. Adjust the below in application.properties

`spring.data.mongodb.uri=mongodb://127.0.0.1:27017/SimpleBlogTest`

To create an executable jar using maven: 

`mvn clean package`

The application can be run using:

`java -jar target/simple-blog-0.0.1-SNAPSHOT.jar`