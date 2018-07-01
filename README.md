A GWT example project using GWT-Vue with Webpack and the https://github.com/ascendtech/gwt-gradle plugin.



# Getting Started

Run REST service
```bash
cd rest
../gradlew run
```


Run frontend in dev mode
```bash
cd todoMaterial
../gradlew gwtDev

#new terminal
cd todoMaterial
../gradlew gwtDev

#new terminal
cd todoMaterial
../gradlew -t build

#open browser to http://localhost:8888/
```

In dev mode a refresh will recompile GWT, CSS, JS, and webpack changes.

Compile deploy
```bash
cd rest
../gradlew shadowJar
#deploy service with proxy in front (/todo to localhost:12111) (run service using java -jar)

cd todoMaterial
../gradlew gwtArchive
#copy to archive in build/webapp to nginx or apache
```



