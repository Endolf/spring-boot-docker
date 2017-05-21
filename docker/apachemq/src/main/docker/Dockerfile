FROM openjdk:8-alpine

ENV ARTEMIS_VERSION 2.1.0
ENV ACTIVEMQ_HOME /opt/activemq

RUN set -x && \
    mkdir -p $ACTIVEMQ_HOME && \
    apk --update add --virtual build-curl-dependencies curl && \
    curl -s -S https://www.apache.org/dist/activemq/activemq-artemis/$ARTEMIS_VERSION/apache-artemis-$ARTEMIS_VERSION-bin.tar.gz | tar xz -C $ACTIVEMQ_HOME --strip 1 && \
    addgroup -S activemq && adduser -S -H -G activemq -h $ACTIVEMQ_HOME activemq && \
    chown -R activemq:activemq $ACTIVEMQ_HOME && \
    apk del build-curl-dependencies && \
    rm -rf /var/cache/apk/*

RUN /opt/activemq/bin/artemis create --autocreate --cluster-password
 secret --cluster-user cluteruser --clustered --data /data/artemis --name artemi
scluster --replicated /opt/artemis/artemiscluster --user user --password secret
--host `hostname -i` --require-login

USER activemq

VOLUME /opt/

WORKDIR $ACTIVEMQ_HOME
EXPOSE 1883 5672 8161 61613 61614 61616

CMD ["/bin/sh", "-c", "trap : TERM INT; tail -f /dev/null"]