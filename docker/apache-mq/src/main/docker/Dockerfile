FROM openjdk:8-alpine

ENV ACTIVEMQ_VERSION 5.14.5
ENV ACTIVEMQ_HOME /opt/activemq

RUN set -x && \
    mkdir -p $ACTIVEMQ_HOME && \
    apk --update add --virtual build-curl-dependencies curl && \
    curl -s -S https://archive.apache.org/dist/activemq/$ACTIVEMQ_VERSION/apache-activemq-$ACTIVEMQ_VERSION-bin.tar.gz | tar xz -C $ACTIVEMQ_HOME --strip 1 && \
    addgroup -S activemq && adduser -S -H -G activemq -h $ACTIVEMQ_HOME activemq && \
    chown -R activemq:activemq $ACTIVEMQ_HOME && \
    apk del build-curl-dependencies && \
    rm -rf /var/cache/apk/*

USER activemq

WORKDIR $ACTIVEMQ_HOME
EXPOSE 1883 5672 8161 61613 61614 61616

CMD ["/bin/sh", "-c", "bin/activemq console"]