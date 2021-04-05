plugins {
    `java-library`
}

dependencies {
    testImplementation("io.cucumber:cucumber-java:6.8.1")
    testImplementation("org.seleniumhq.selenium:selenium-java:3.14.0")
}

configurations {
    create("cucumberRuntime") {
        extendsFrom(configurations["testImplementation"])
    }
}

task("cucumber") {
    dependsOn("assemble", "testClasses")
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath = configurations.getByName("cucumberRuntime") + sourceSets.main.get().output + sourceSets.test.get().output
            args = listOf("--plugin", "pretty", "--glue", "src/test/resources")
        }
    }
}