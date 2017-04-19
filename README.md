### Credits
Based on a number of resources including 
* [SpringIO Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [Compose and Wordpress example](https://docs.docker.com/compose/wordpress/)
* [Kubernetes Wordpress example](https://github.com/kubernetes/kubernetes/tree/master/examples/mysql-wordpress-pd)

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

* To create the persistent storage ```kubectl create -f target/kubernetes/local-storage.yaml```
* To deploy the database ```kubectl create -f target/kubernetes/mariadb.yaml```
* To deploy the application ```kubectl create -f target/kubernetes/app.yaml```
* To deploy the application service ```kubectl create -f target/kubernetes/app-service.yaml```

To access the app visit the url specified by ```minikube service gs-spring-boot-docker --url```, add ```/messages``` to the end to see the messages.

#### Redeploy
* Build the new version ```mvn clean package```
* Delete the old app ```kubectl delete -f target/kubernetes/app.yaml```
* Deploy the new version ```kubectl create -f target/kubernetes/app.yaml```

### Cleanup
* Delete the app service ```kubectl delete -f target/kubernetes/app-service.yaml```
* Delete the app ```kubectl delete -f target/kubernetes/app.yaml```
* Delete the database nodes ```kubectl delete -f target/kubernetes/mariadb.yaml```

then ```kubectl get all,endpoints,pvc,pv``` to confirm.

To delete the persistent storage ```kubectl delete -f target/kubernetes/local-storage.yaml``` 
