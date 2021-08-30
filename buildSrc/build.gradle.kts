plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("io.micronaut.gradle:micronaut-gradle-plugin:2.0.3")
    implementation("com.bmuschko:gradle-docker-plugin:7.1.0")
    implementation("gradle.plugin.com.github.jengelman.gradle.plugins:shadow:7.0.0")
}