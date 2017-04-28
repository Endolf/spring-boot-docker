#!/bin/bash

if [ "${1:0:1}" = '-' ]; then
  set -- mysqld "$@"
fi

# if the command passed is 'mysqld' via CMD, then begin processing.
if [ "$1" = 'mysqld' ]; then
  # read DATADIR from the MySQL config
  DATADIR="$("$@" --verbose --help 2>/dev/null | awk '$1 == "datadir" { print $2; exit }')"

  # only check if system tables not created from mysql_install_db and permissions
  # set with initial SQL script before proceeding to build SQL script
  if [ ! -d "$DATADIR/mysql" ]; then
  # fail if user didn't supply a root password
    if [ -z "$MYSQL_ROOT_PASSWORD" -a -z "$MYSQL_ALLOW_EMPTY_PASSWORD" ]; then
      echo >&2 'error: database is uninitialized and MYSQL_ROOT_PASSWORD not set'
      echo >&2 '  Did you forget to add -e MYSQL_ROOT_PASSWORD=... ?'
      exit 1
    fi

    # mysql_install_db installs system tables
    echo 'Running mysql_install_db ...'
    mysql_install_db --datadir="$DATADIR"
    echo 'Finished mysql_install_db'

    # this script will be run once when MySQL first starts to set up
    # prior to creating system tables and will ensure proper user permissions
    tempSqlFile='/tmp/mysql-first-time.sql'
    cat > "$tempSqlFile" <<-EOSQL
DELETE FROM mysql.user ;
CREATE USER 'root'@'%' IDENTIFIED BY '${MYSQL_ROOT_PASSWORD}' ;
GRANT ALL ON *.* TO 'root'@'%' WITH GRANT OPTION ;
EOSQL

    if [ "$MYSQL_DATABASE" ]; then
      echo "CREATE DATABASE IF NOT EXISTS \`$MYSQL_DATABASE\` ;" >> "$tempSqlFile"
    fi

    if [ "$MYSQL_USER" -a "$MYSQL_PASSWORD" ]; then
      echo "CREATE USER '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD' ;" >> "$tempSqlFile"

      if [ "$MYSQL_DATABASE" ]; then
        echo "GRANT ALL ON \`$MYSQL_DATABASE\`.* TO '$MYSQL_USER'@'%' ;" >> "$tempSqlFile"
      fi
    fi

    echo "Galera cluster name:" ${GALERA_CLUSTER}
    if [ -n "$GALERA_CLUSTER" ]; then
        # this is the Single State Transfer user (SST, initial dump or xtrabackup user)
        WSREP_SST_USER=${WSREP_SST_USER:-"sst"}
        if [ -z "$WSREP_SST_PASSWORD" ]; then
            echo >&2 'error: Galera cluster is enabled and WSREP_SST_PASSWORD is not set'
            echo >&2 '  Did you forget to add -e WSREP_SST__PASSWORD=... ?'
            exit 1
        fi
        # add single state transfer (SST) user privileges
        echo "CREATE USER '${WSREP_SST_USER}'@'localhost' IDENTIFIED BY '${WSREP_SST_PASSWORD}';" >> "$tempSqlFile"
        echo "GRANT RELOAD, LOCK TABLES, REPLICATION CLIENT ON *.* TO '${WSREP_SST_USER}'@'localhost';" >> "$tempSqlFile"
    fi

    echo 'FLUSH PRIVILEGES ;' >> "$tempSqlFile"

    cat $tempSqlFile

    # Add the SQL file to mysqld's command line args
    set -- "$@" --init-file="$tempSqlFile"
  fi

  chown -R mysql:mysql "$DATADIR"
fi

if [ -n "$GALERA_CLUSTER" ]; then
  MY_NAME=`hostname -f`
  sed -i -e "s|^wsrep_node_address=.*$|wsrep_node_address=${MY_NAME}|" /etc/mysql/conf.d/galera.cnf
  CLUSTER_NAME=`hostname -d | tr '.' '\n' | head -1`
  sed -i -e "s|^wsrep_cluster_name=.*$|wsrep_cluster_name=${CLUSTER_NAME}|" /etc/mysql/conf.d/galera.cnf
  NODE_BASE_NAME=`hostname | tr '-' '\n' | head -1`
  NODE_ID=`hostname | tr '-' '\n' | tail -1`
  DOMAIN=`hostname -d`
  NODES=`for i in \`seq 0 ${NODE_ID}\`; do echo $NODE_BASE_NAME-$i.$DOMAIN; done | head -n -1 | tr '\n' ',' | cut -c1- | rev | cut -c2- | rev`
  sed -i -e "s|^wsrep_cluster_address=.*$|wsrep_cluster_address=gcomm://${NODES}|" /etc/mysql/conf.d/galera.cnf
  sed -i -e "s|^wsrep_cluster_name=.*$|wsrep_cluster_name=${GALERA_CLUSTER}|" /etc/mysql/conf.d/galera.cnf
  sed -i -e "s|^wsrep_on=.*$|wsrep_on=ON|" /etc/mysql/conf.d/galera.cnf
  sed -i -e "s|^wsrep_sst_auth=sstuser:changethis|wsrep_sst_auth=${WSREP_SST_USER}:${WSREP_SST_PASSWORD}|" /etc/mysql/conf.d/galera.cnf

  cat /etc/mysql/conf.d/galera.cnf
fi

sed -i -e "s|^server\-id=.*$|server-id=${RANDOM}|" /etc/mysql/my.cnf
sed -i -e "s|^#log_bin[= \t].*$|log_bin=${DATADIR}mariadb-bin|" /etc/mysql/my.cnf

exec gosu mysql "$@"