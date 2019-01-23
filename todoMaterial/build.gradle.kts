plugins {
    id("us.ascendtech.gwt.modern")
    id("net.ltgt.apt")
    id("net.ltgt.apt-idea")
}

gwt {
    modules.add("us.ascendtech.ToDo")
    libs.add("autorest")
    libs.add("ast-highcharts")

    includeGwtUser = false
}

dependencies {
    compile(project(":aggrid"))
}


