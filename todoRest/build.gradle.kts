plugins {
    micronaut_rest_convention
}

application {
    mainClass.set("us.ascendtech.rest.ToDoApplication")
}

tasks.withType<io.micronaut.gradle.docker.MicronautDockerfile>() {
    exposedPorts.add(12111)
}

dependencies {
    implementation("com.google.guava:guava:27.1-jre")
}
