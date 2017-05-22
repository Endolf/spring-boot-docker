FROM openjdk:8-alpine

ENV ARTEMIS_VERSION 2.1.0
ENV ARTEMIS_HOME /opt/artemis

RUN set -x && \
    mkdir -p $ARTEMIS_HOME && \
    apk --update add --virtual build-curl-dependencies curl && \
    curl -s -S https://www.apache.org/dist/activemq/activemq-artemis/$ARTEMIS_VERSION/apache-artemis-$ARTEMIS_VERSION-bin.tar.gz | tar xz -C $ARTEMIS_HOME --strip 1 && \
    addgroup -S activemq && adduser -S -H -G activemq -h $ARTEMIS_HOME activemq && \
    chown -R activemq:activemq $ARTEMIS_HOME && \
    apk del build-curl-dependencies && \
    apk add --no-cache su-exec && \
    rm -rf /var/cache/apk/*

#RUN /opt/activemq/bin/artemis create --autocreate --cluster-password secret --cluster-user cluteruser --clustered --data /data/artemis --name artemiscluster --replicated /opt/artemis/artemiscluster --user user --password secret --host `hostname -i` --require-login

WORKDIR $ARTEMIS_HOME
EXPOSE 1883 5672 8161 61613 61614 61616

CMD ["/bin/sh", "-c", "set -x && \
    $ARTEMIS_HOME/bin/artemis create --autocreate --cluster-password secret --cluster-user cluteruser --clustered --name artemiscluster --replicated $ARTEMIS_HOME/artemiscluster --user user --password secret --host `hostname -i` --require-login && \
    mkdir -p $ARTEMIS_HOME/artemiscluster/data $ARTEMIS_HOME/artemiscluster/log $ARTEMIS_HOME/artemiscluster/lock $ARTEMIS_HOME/artemiscluster/tmp && \
    chown -R activemq:activemq $ARTEMIS_HOME/artemiscluster/data $ARTEMIS_HOME/artemiscluster/log $ARTEMIS_HOME/artemiscluster/lock $ARTEMIS_HOME/artemiscluster/tmp && \
    su-exec activemq $ARTEMIS_HOME/artemiscluster/bin/artemis run"]