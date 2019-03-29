plugins {
    id("us.ascendtech.gwt.modern")
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


