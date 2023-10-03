plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.0.0").apply(false)
    id("com.android.library").version("8.0.0").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    id("org.jetbrains.compose").version("1.5.0") apply false
    kotlin("multiplatform").version("1.8.21").apply(false)
    id("dev.icerock.mobile.multiplatform-resources").version("0.22.2").apply(false)
    kotlin("plugin.serialization").version("1.9.0").apply(false)

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
