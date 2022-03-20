kubectl delete deployment kubernetes-webclient-deployment
kubectl delete service my-service
kubectl apply -f deployment.yaml
kubectl expose deployment kubernetes-webclient-deployment --type=NodePort --name=my-service
