var rsStatus = rs.status().ok;
if(rsStatus==0) {
    rs.initiate({_id : myrs, members: [{ _id : 0, host : myip}]});

    startTime = new ISODate().valueOf();
    while(((new ISODate().valueOf() - startTime) < 30000) && !rs.isMaster().ismaster) {
        sleep(100);
    }

    db = db.getSiblingDB(adminDatabase);
    db.createUser({
        user: adminUser,
        pwd: adminPassword,
        roles: [ { role: 'root', db: adminDatabase } ]
    });
}

