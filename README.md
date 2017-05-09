### Credits
Based on a number of resources including 
* [SpringIO Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [Compose and Wordpress example](https://docs.docker.com/compose/wordpress/)
* [Kubernetes Wordpress example](https://github.com/kubernetes/kubernetes/tree/master/examples/mysql-wordpress-pd)
* [OpenShift Images for a Mariadb+Galera Cluster](https://github.com/adfinis-sygroup/openshift-mariadb-galera)
* [MariaDB Galera docs](https://mariadb.com/kb/en/mariadb/getting-started-with-mariadb-galera-cluster/)
* [Galera Cluster Status Monitoring](http://galeracluster.com/documentation-webpages/monitoringthecluster.html)
* [Running MongoDB as a Microservice with Docker and Kubernetes](https://www.mongodb.com/blog/post/running-mongodb-as-a-microservice-with-docker-and-kubernetes)
* [Enabling Microservices: Containers & Orchestration Explained](https://www.mongodb.com/collateral/microservices-containers-and-orchestration-explained)
* [Mongo internal authentication](https://docs.mongodb.com/v3.0/tutorial/enable-internal-authentication/)
* [RabbitMQ Clustering](https://www.rabbitmq.com/clustering.html)
* [RabbitMQ on Kubernetes](https://wesmorgan.svbtle.com/rabbitmq-cluster-on-kubernetes-with-statefulsets)

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

* To deploy the MariaDB database ```kubectl create -f kubernetes/target/kubernetes/mariadb.yaml```
* To deploy the Mongo database ```kubectl create -f kubernetes/target/kubernetes/mongodb.yaml```
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
* Delete the Mongo database nodes ```kubectl delete -f kubernetes/target/kubernetes/mongodb.yaml```
* Delete the MariaDB database nodes ```kubectl delete -f kubernetes/target/kubernetes/mariadb.yaml```

then ```kubectl get all,endpoints,pvc,pv``` to confirm.

Use the Kubernetes dashboard to delete the persistent volumes or 
* Delete the Mongo persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-mongo-" | xargs kubectl delete pvc```
* Delete the MariaDB persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-mariadb-" | xargs kubectl delete pvc```
* Delete the RabbitMQ persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-rabbitmq-" | xargs kubectl delete pvc```

This is a proof of concept, use it as a starting point and at your own risk.