plugins {
    java
    idea
    id("com.github.johnrengelman.shadow") version "4.0.3" apply false
    id("net.ltgt.apt") version "0.19" apply false
    id("net.ltgt.apt-idea") version "0.19" apply false
    id("us.ascendtech.gwt.lib") version "0.4.21" apply false
    id("us.ascendtech.gwt.modern") version "0.4.21" apply false
}

defaultTasks("build")

subprojects {

    apply(plugin = "java")
    apply(plugin = "net.ltgt.apt")
    apply(plugin = "net.ltgt.apt-idea")

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    defaultTasks("build")
    group = "us.ascendtech.examples"


    repositories {
        mavenCentral()
        flatDir {
            dirs(rootProject.file("notInMaven"))
        }
        maven {
            url = uri("https://maven.ascend-tech.us/repo")
        }
    }

    configurations.all {
        // check for updates every build more than 10 minutes apart (for snapshots)
        resolutionStrategy.cacheChangingModulesFor(10, TimeUnit.MINUTES)
    }

    tasks.withType<JavaCompile> {
        options.isDebug = true
        options.debugOptions.debugLevel = "source,lines,vars"
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    }

    sourceSets {
        main {
            java {
                srcDir("src/main/java")
            }
            resources {
                srcDir("src/main/java")
            }
        }
    }

    idea.module {
        resourceDirs = resourceDirs - file("src/main/java")
    }

}
