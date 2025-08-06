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
   

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)



    //Dagger Hilt
    implementation(libs.hilt.android.v250)
    ksp(libs.hilt.compiler.v250)
}





