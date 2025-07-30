plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")

}

    android {
        namespace = "com.dedany.registrodelibros.data"
        compileSdk = 35

        defaultConfig {
            minSdk = 27
            targetSdk = 35
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = "11"
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

    //Dagger Hilt
    implementation(libs.hilt.android.v250)
    ksp(libs.hilt.compiler.v250)
}





