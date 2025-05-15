plugins {
    micronaut_rest_convention
}

application {
    mainClass.set("us.ascendtech.rest.GamesApplication")
}

tasks.withType<io.micronaut.gradle.docker.MicronautDockerfile> {
    exposedPorts.add(12111)
}

dependencies {
    implementation("com.google.guava:guava:32.0.1-android")
    implementation("com.google.code.gson:gson:2.8.9")
}
