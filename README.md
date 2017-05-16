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
* [Cassandra cluster made easy](http://node.mu/2015/09/18/multi-node-cassandra-cluster-made-easy-with-kubernetes/)

### Run
#### Setup
* To start a local Kubernetes cluster ```minikube start```.
* Enable heapster for CPU monitoring for autoscaling ```minikube addons enable heapster```
* Enable the ingress plugin for HTTP routing ```minikube addons enable ingress```
* You can confirm it's running with ```kubectl cluster-info``` and ```minikube service --namespace=kube-system monitoring-grafana --url``` to see the monitoring dashboard.
* To setup ```kubectl``` to use the right cluster run ```kubectl config use-context minikube```
* To setup docker to use the docker containers in the cluster use ```eval $(minikube docker-env)``` 
* To build the application and the docker image ```mvn clean package```

#### Deployment
After each of the following commands run ```kubectl get all,endpoints,pvc,pv``` to confirm the step.

* To deploy the MariaDB database ```kubectl apply -f kubernetes/target/kubernetes/mariadb.yaml```
* To deploy the Mongo database ```kubectl apply -f kubernetes/target/kubernetes/mongodb.yaml```
* To deploy the Cassandra database ```kubectl apply -f kubernetes/target/kubernetes/cassandra.yaml```

* To deploy RabbitMQ ```kubectl apply -f kubernetes/target/kubernetes/rabbitmq.yaml```

* To deploy the SQL application ```kubectl apply -f kubernetes/target/kubernetes/sql-application.yaml```
* To deploy the Mongo application ```kubectl apply -f kubernetes/target/kubernetes/mongo-application.yaml```
* To deploy the ingress service ```kubectl apply -f kubernetes/target/kubernetes/ingress-service.yaml```

* To deploy the front end ```kubectl apply -f kubernetes/target/kubernetes/front-end.yaml```

To access the app visit the url specified by ```minikube ip``` to the end to see the messages.

To run the development docker container for the live reloading of the angular front end ```docker run -it -v `pwd`/applications/front-end:/development computerbooth/front-end-dev```

### Cleanup
* To undeploy the front end ```kubectl delete -f kubernetes/target/kubernetes/front-end.yaml```

* To undeploy the ingress service ```kubectl delete -f kubernetes/target/kubernetes/ingress-service.yaml```
* To undeploy the SQL application ```kubectl delete -f kubernetes/target/kubernetes/sql-application.yaml```
* To undeploy the Mongo application ```kubectl delete -f kubernetes/target/kubernetes/mongo-application.yaml```

* To undeploy RabbitMQ ```kubectl delete -f kubernetes/target/kubernetes/rabbitmq.yaml```

* To undeploy the MariaDB database ```kubectl delete -f kubernetes/target/kubernetes/mariadb.yaml```
* To undeploy the Mongo database ```kubectl delete -f kubernetes/target/kubernetes/mongodb.yaml```
* To undeploy the Cassandra database ```kubectl delete -f kubernetes/target/kubernetes/cassandra.yaml```

then ```kubectl get all,endpoints,pvc,pv``` to confirm.

Use the Kubernetes dashboard to delete the persistent volumes or 
* Delete the Mongo persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-mongodb-" | xargs kubectl delete pvc```
* Delete the Cassandra persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-cassandra-" | xargs kubectl delete pvc```
* Delete the MariaDB persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-mariadb-" | xargs kubectl delete pvc```
* Delete the RabbitMQ persistent claims ```kubectl get pvc | cut -d " " -f1 | grep -v -e "^NAME$" | grep -e "-rabbitmq-" | xargs kubectl delete pvc```

This is a proof of concept, use it as a starting point and at your own risk.