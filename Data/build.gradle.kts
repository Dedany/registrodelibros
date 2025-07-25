plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)

}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation(project(":domain"))
    implementation(libs.gson)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.room.runtime.jvm)

    //retrofit
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
}





