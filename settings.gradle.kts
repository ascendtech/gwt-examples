import java.io.FilenameFilter

val excludes = listOf(".gradle", ".git", ".idea", "build", "buildSrc", "cert", "gradle", "notInMaven")

val dirFilter = FilenameFilter { dir, name -> File(dir, name).isDirectory }

File(rootDir.absolutePath).list(dirFilter).forEach {
    if (!excludes.contains(it)) {
        include(it)
    }
}

rootProject.name = "GWT-Examples"