var rsStatus = rs.status();
if(rsStatus.ok==0) {
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
} else if(rsStatus.ok==1) {

    aliveMembers = rsStatus.members.filter(function(statusMember){
        return statusMember.state!==6 && statusMember.state!==8 && statusMember.state!==10
    }).map(function(statusMember) {
       return statusMember.name;
    });

    rsconf = rs.conf();
    rsconf.members = rsconf.members.filter(function(member){
        return aliveMembers.includes(member.host);
    });

    rs.reconfig(rsconf, {force: true});
}

