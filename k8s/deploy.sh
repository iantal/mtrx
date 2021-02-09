#!/bin/bash

kubectl apply -f mtrx-deployment-local.yml
kubectl apply -f mtrx-service.yml
kubectl apply -f mtrx-configmap.yml