#!/usr/bin/env bash

set -exo pipefail

trap : TERM INT

while true
do
  sleep 10s
  HOSTS=`dig +noall +answer SRV \`hostname -d\` | cut -d " " -f7 | rev | cut -c2- | rev | grep -v \`hostname -f\``
done