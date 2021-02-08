// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlinVersion by extra("1.4.21-2")
    val composeVersion by extra("1.0.0-alpha11")
    val composeSnapshotRepo by extra("https://androidx-dev-prod.appspot.com/snapshots/builds/artifacts/repository/")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha05")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven( url = "https://jitpack.io" )
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
