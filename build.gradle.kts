plugins {
    java
    idea
    id("net.ltgt.apt") version "0.19"
    id("net.ltgt.apt-idea") version "0.19"
    id("com.github.johnrengelman.shadow") version "4.0.3" apply false
    id("us.ascendtech.gwt.lib") apply false version "0.3.7"
    id("us.ascendtech.gwt.modern") apply false version "0.3.7"
}

defaultTasks("build")


subprojects {

    apply(plugin = "java")
    apply(plugin = "idea")

    sourceSets {
        main {
            resources {
                srcDir("src/main/java")
            }
            java {
                srcDir("src/main/java")
            }
        }
    }


    defaultTasks("build")

    version = "1.0"
    group = "us.ascendtech"

    repositories {
        mavenCentral()
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


}
