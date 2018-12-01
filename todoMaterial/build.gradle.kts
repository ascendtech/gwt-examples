plugins {
    id("us.ascendtech.gwt.modern")
    id("net.ltgt.apt")
    id("net.ltgt.apt-idea")
}

gwt {
    modules.add("us.ascendtech.ToDo")
    libs.add("vue")
    libs.add("autorest")
    libs.add("elemento-core")
    libs.add("ast-highcharts")
}

dependencies {
    compile(project(":aggrid"))
}


