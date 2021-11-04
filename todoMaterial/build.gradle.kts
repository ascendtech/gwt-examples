plugins {
    id("us.ascendtech.gwt.modern")
}


tasks.register<us.ascendtech.js.npm.NpmTask>("npmAudit") {
    dependsOn("npmInstallDep", "npmInstall")
    baseCmd.set("npm")
    baseArgs.addAll("audit")
}

tasks.register<us.ascendtech.js.npm.NpmTask>("npmAuditFix") {
    dependsOn("npmInstallDep", "npmInstall")
    baseCmd.set("npm")
    baseArgs.addAll("audit", "fix")
}

gwt {
    modules.add("us.ascendtech.ToDo")
    libs.add("vue")
    libs.add("simplerest")
    libs.add("elemento-core")
    libs.add("ast-highcharts")
    libs.add("ast-aggrid")
    libs.add("ast-momentjs")
    libs.add("ast-wordcloud")
}

dependencies {
}

