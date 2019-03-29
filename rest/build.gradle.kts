import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    id("com.github.johnrengelman.shadow")
}

val micronautVersion: String by project

tasks.getByName<Zip>("shadowDistZip").classifier = "shadow"
tasks.getByName<Tar>("shadowDistTar").classifier = "shadow"

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    isZip64 = true
}


dependencies {

    compile("org.slf4j:slf4j-log4j12:1.7.25")


    compile("io.reactivex.rxjava2:rxjava:2.2.0")

    annotationProcessor("io.micronaut:micronaut-inject-java:$micronautVersion")
    compile("io.micronaut:micronaut-http-client:$micronautVersion")
    compile("io.micronaut:micronaut-http-server-netty:$micronautVersion")
    compile("io.micronaut:micronaut-inject:$micronautVersion")
    compile("io.micronaut:micronaut-runtime:$micronautVersion")

    compileOnly("io.micronaut:micronaut-inject-java:$micronautVersion")
    testCompile("junit:junit:4.12")

    compile("javax.annotation:javax.annotation-api:1.3.2")
    annotationProcessor("javax.annotation:javax.annotation-api:1.3.2")

    compile("com.google.guava:guava:26.0-jre")

}

application {
    mainClassName = "us.ascendtech.rest.ToDoApplication"
}

