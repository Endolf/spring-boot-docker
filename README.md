### Credits
Based on a number of resources including 
* [SpringIO Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [Compose and Wordpress](https://docs.docker.com/compose/wordpress/)

### Run
```
mvn clean package
docker-compose -f target/docker-ready/docker-compose.yaml -p gs-spring-boot-docker up -d
```

To access the messages in the database visit [http://localhost:18080/messages](http://localhost:18080/messages)

To clean up afterwards
```
docker-compose -f target/docker-ready/docker-compose.yaml -p gs-spring-boot-docker stop
docker-compose -f target/docker-ready/docker-compose.yaml -p gs-spring-boot-docker rm -f
```
