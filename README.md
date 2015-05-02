# knbit-events-bc #

System supporting management of BIT Scientific Group - events organization and management subsystem.

### Run the application ###

Backend
```
#!python

cd backend/
./gradlew :events-bc-app:bootRun
```

Frontend
```
#!python

https://bitbucket.org/aghbit/knbit-system-front-end
```


### Sending Docker image to Docker Hub repository ###


```
#!python
cd backend/
./gradlew :events-bc-app:build :events-bc-app:buildDocker
```