### Credits
Based on a number of resources including 
* [SpringIO Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [Compose and Wordpress example](https://docs.docker.com/compose/wordpress/)
* [Kubernetes Wordpress example](https://github.com/kubernetes/kubernetes/tree/master/examples/mysql-wordpress-pd)
* [OpenShift Images for a Mariadb+Galera Cluster](https://github.com/adfinis-sygroup/openshift-mariadb-galera)
* [MariaDB Galera docs](https://mariadb.com/kb/en/mariadb/getting-started-with-mariadb-galera-cluster/)
* [Galera Cluster Status Monitoring](http://galeracluster.com/documentation-webpages/monitoringthecluster.html)

### Run
#### Setup
* To start a local Kubernetes cluster ```minikube start```.
* Enable heapster for CPU monitoring for autoscaling ```minikube addons enable heapster```
* You can confirm it's running with ```kubectl cluster-info``` and ```minikube service --namespace=kube-system monitoring-grafana --url``` to see the monitoring dashboard.
* To setup ```kubectl``` to use the right cluster run ```kubectl config use-context minikube```
* To setup docker to use the docker containers in the cluster use ```eval $(minikube docker-env)``` 
* To build the application and the docker image ```mvn clean package```

#### Deployment
After each of the following commands run ```kubectl get all,endpoints,pvc,pv``` to confirm the step.

* To deploy the database ```kubectl create -f kubernetes/target/kubernetes/mariadb.yaml```
* To deploy the application ```kubectl create -f kubernetes/target/kubernetes/app.yaml```
* To deploy the application service ```kubectl create -f kubernetes/target/kubernetes/app-service.yaml```

To access the app visit the url specified by ```minikube service spring-boot-app --url```, add ```/messages``` to the end to see the messages.

#### Redeploy
* Build the new version ```mvn clean package```
* Delete the old app ```kubectl delete -f kubernetes/target/kubernetes/app.yaml```
* Deploy the new version ```kubectl create -f kubernetes/target/kubernetes/app.yaml```

### Cleanup
* Delete the app service ```kubectl delete -f kubernetes/target/kubernetes/app-service.yaml```
* Delete the app ```kubectl delete -f kubernetes/target/kubernetes/app.yaml```
* Delete the database nodes ```kubectl delete -f kubernetes/target/kubernetes/mariadb.yaml```

then ```kubectl get all,endpoints,pvc,pv``` to confirm.

Use the Kubernetes dashboard to delete the persistent volumes.

### Known issues
This is a proof of concept, so there are known issues with running this in a production environment.
* The database instances have a long pause on startup, this is to work around an issue where the first node was hanging (socat 100% cpu) during initial replication to the secondary.
This may have involved some DNS resolution issues but is not fully clear what caused this.
* The node status check for the database is done of a simple bash command, when running a mysql client to check for the status issues around socat hitting 100% were also discovered.
* The database can't currently recover from a node failing, this is due to the nodes trying to join the cluster based on their ID. A DNS query of the service to build the ```wsrep_cluster_address``` variable ought to solve this.