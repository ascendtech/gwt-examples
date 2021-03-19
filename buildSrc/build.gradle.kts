plugins {
    `kotlin-dsl`
}

repositories {
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("io.micronaut.gradle:micronaut-gradle-plugin:1.4.2")
    implementation("com.bmuschko:gradle-docker-plugin:6.7.0")
    implementation("com.github.jengelman.gradle.plugins:shadow:6.1.0")
}