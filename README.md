Two GWT example projects using GWT-Vue with Webpack and the https://github.com/ascendtech/gwt-gradle plugin.
* todoMaterial / todoRest
* games / gamesRest
 
 The Todo project is documented below but the games project works the same way

# Getting Started

Run the example REST service backend
```bash
cd todoRest && ../gradlew run
```

Run frontend in dev mode (requires 3 different terminals)
```bash

# listens on port 8888 and forwards the module path to gwt super dev mod and the everything else to webpack
cd todoMaterial && ../gradlew gwtDev

# runs webpack on port 8080 and also proxies requests to the rest service
cd todoMaterial && ../gradlew webpack5Dev

# because Vue GWT and SimpleRest use APT generation, run a continous java build to trigger on changes
cd todoMaterial && ../gradlew compileJava --build-cache -t

```
Open browser to http://localhost:8888/

In dev mode a refresh will recompile GWT, CSS, JS, and webpack changes.


## Deploying
```bash
#deploy service with proxy in front (/service/todo to localhost:12111) 
cd todoRest && ../gradlew shadowJar
java -jar build/libs/todoRest-all.jar

#copy to archive in build/webapp to nginx or apache
cd todoMaterial && ../gradlew gwtArchive

```

## Deploying using Docker

```bash
#make sure your user is in the docker group or has permissions to docker service
docker build . -t todomaterial:latest
docker run -p 80:80 todomaterial

cd todoRest && ../gradlew dockerBuild
docker run -p 12111:12111 todoRest
```
