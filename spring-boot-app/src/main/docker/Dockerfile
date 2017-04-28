FROM openjdk:8
VOLUME /tmp
ADD @project.artifactId@/@project.artifactId@-@project.version@.jar /@project.artifactId@/@project.artifactId@-@project.version@.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /@project.artifactId@/@project.artifactId@-@project.version@.jar" ]