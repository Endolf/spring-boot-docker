#!/bin/bash

set -x

while true;
do
    timeout 1 bash -c 'cat < /dev/null > /dev/tcp/localhost/27017'
    exitCode=$?

    if [ $exitCode = 0 ]; then
        otherNodeCount=`getent hosts \`hostname -d\` | wc -l`
        myip=`hostname -i`

        if [ $otherNodeCount = 0 ]; then
            mongo -u ${MONGO_ADMIN_USERNAME} -p ${MONGO_ADMIN_PASSWORD} --authenticationDatabase ${MONGO_ADMIN_DATABASE} localhost --eval "var myip=\"${myip}\", myrs=\"${MONGO_RS}\", adminDatabase=\"${MONGO_ADMIN_DATABASE}\", adminUser=\"${MONGO_ADMIN_USERNAME}\", adminPassword=\"${MONGO_ADMIN_PASSWORD}\"" /data/create-rs.js
        else
            mongo -u ${MONGO_ADMIN_USERNAME} -p ${MONGO_ADMIN_PASSWORD} --authenticationDatabase ${MONGO_ADMIN_DATABASE} `hostname -d`/?replicaSet=${MONGO_RS} --eval "rs.add(\"`hostname -i`\")"
        fi
        break
    fi
    sleep 1s
done

trap : TERM INT;
sleep infinity & wait