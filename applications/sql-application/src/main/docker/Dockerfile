FROM openjdk:9-slim
VOLUME /tmp
ADD @project.artifactId@-@project.version@.@project.packaging@ /@project.artifactId@/@project.artifactId@-@project.version@.@project.packaging@
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /@project.artifactId@/@project.artifactId@-@project.version@.jar" ]