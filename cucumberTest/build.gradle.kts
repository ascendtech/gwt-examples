plugins {
    `java-library`
}

dependencies {
    testImplementation("io.cucumber:cucumber-java:6.10.2")
    testImplementation("org.seleniumhq.selenium:selenium-java:3.141.59")
}

configurations {
    create("cucumberRuntime") {
        extendsFrom(configurations["testImplementation"])
    }
}

task("cucumberTodo") {
    dependsOn("assemble", "testClasses")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = configurations.getByName("cucumberRuntime") + sourceSets.main.get().output + sourceSets.test.get().output
            args = listOf("--plugin", "pretty", "--glue", "todo", "src/test/resources/todo")
        }
    }
}