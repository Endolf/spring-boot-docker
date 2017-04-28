FROM mariadb:10.1
VOLUME /tmp
ADD classes/mysql-config/* /etc/mysql/conf.d/
ADD classes/scripts/docker-entrypoint.sh /usr/local/bin/
RUN chmod 755 /usr/local/bin/docker-entrypoint.sh
