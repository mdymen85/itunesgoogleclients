kubectl delete deployment kubernetes-webclient-deployment
kubectl delete service my-service
kubectl delete configmap webclient-configmap

kubectl apply -f configmap.yaml
kubectl apply -f deployment.yaml
kubectl expose deployment kubernetes-webclient-deployment --type=LoadBalancer --name=my-service

