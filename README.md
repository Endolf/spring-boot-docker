### Credits
Based on a number of resources including 
* [SpringIO Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
* [Compose and Wordpress example](https://docs.docker.com/compose/wordpress/)
* [Kubernetes Wordpress example](https://github.com/kubernetes/kubernetes/tree/master/examples/mysql-wordpress-pd)

### Run
To start a local Kubernetes cluster
```minikube start```. You can confirm it's running with ```kubectl cluster-info```.

To setup ```kubectl``` to use the right cluster run ```kubectl config use-context minikube```

To setup docker to use the docker containers in the cluster use ```eval $(minikube docker-env)``` 

To build the application and the docker image ```mvn clean package```

To deploy the database ```kubectl create -f target/kubernetes/mariadb.yaml```, to confirm deployment ```kubectl get deployment,pod,svc,endpoints,pvc```

To deploy the application ```kubectl create -f target/kubernetes/app.yaml```, then ```kubectl get deployment,pod,svc,endpoints,pvc``` to confirm the deployment.

To access the app visit the url specified by ```minikube service gs-spring-boot-docker --url```, add ```/messages``` to the end to see the messages.

### Cleanup
```
kubectl delete -f target/kubernetes/app.yaml
kubectl delete -f target/kubernetes/mariadb.yaml
```
then ```kubectl get deployment,pod,svc,endpoints,pvc``` to confirm.