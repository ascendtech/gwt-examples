import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("io.micronaut.application")
    id("com.github.johnrengelman.shadow")
}

val micronautVersion: String by project
val log4jVersion: String by project

micronaut {
    version(micronautVersion)
    runtime("netty")

}

application {
    mainClass.set("us.ascendtech.rest.ToDoApplication")
}

tasks.getByName<Zip>("shadowDistZip").archiveClassifier.set("shadow")
tasks.getByName<Tar>("shadowDistTar").archiveClassifier.set("shadow")

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    isZip64 = true
}


tasks.withType<io.micronaut.gradle.docker.MicronautDockerfile>() {
    exposedPorts.add(12111)
}


dependencies {

    implementation("com.google.guava:guava:27.1-jre")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-jul:$log4jVersion")

    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

}
