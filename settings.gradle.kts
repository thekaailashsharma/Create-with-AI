pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url="https://s01.oss.sonatype.org/content/repositories/snapshots/")
//        maven(url="https://packages.jetbrains.team/maven/p/skija/maven")

    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

rootProject.name = "DesktopCompose"
