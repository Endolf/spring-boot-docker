FROM ubuntu:16.04
VOLUME /tmp

RUN \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6 && \
    echo "deb [ arch=amd64,arm64 ] http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.4 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.4.list && \
    apt-get update && \
    apt-get install -y mongodb-org-shell dnsutils && \
    rm -rf /var/lib/apt/lists/*

ADD classes/scripts/docker-entrypoint.sh /usr/local/bin/
ADD classes/scripts/create-rs.js /data/
RUN chmod 755 /usr/local/bin/docker-entrypoint.sh

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]