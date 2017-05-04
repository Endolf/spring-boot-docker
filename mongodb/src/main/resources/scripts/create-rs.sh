#!/usr/bin/env bash

set -ex

if [ "$MONGO_RS" ]; then

    RS_STATUS=`mongo admin -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --authenticationDatabase admin --quiet -eval "db.runCommand( { replSetGetStatus : 1 } ).ok"`
    OTHER_HOSTS=`getent hosts \`hostname -d\` | cut -d " " -f1 | ( grep -v \`hostname -i\` | true )`

    if [ "$RS_STATUS" = 0 ] && [ -z "$OTHER_HOSTS" ]; then
      mongo admin -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --authenticationDatabase admin --quiet -eval "rs.initiate({_id : \"${MONGO_RS}\",members: [ { _id : 0, host : \"`hostname -f`:27017\"}]})"
    else
      echo 'RS already exists'
    fi
else
    echo 'No RS set'
fi