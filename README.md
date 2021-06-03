# autenticacao-ms

# create postgres and publish, user postgres and password postgres, run in backgroud
sudo docker run --rm -d -P -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD="postgres" --name pg postgres:alpine

# create database  
CREATE DATABASE dbauthenticate

