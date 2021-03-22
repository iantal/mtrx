#!/bin/bash

kubectl apply -f mtrx-deployment.yml
kubectl apply -f mtrx-service.yml
kubectl apply -f mtrx-configmap.yml