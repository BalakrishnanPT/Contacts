// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:3.5.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}