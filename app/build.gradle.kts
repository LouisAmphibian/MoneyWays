plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android") version "2.1.10"
    id("kotlin-kapt")
}

android {
    namespace ="com.example.moneyways"
    compileSdk =35

    defaultConfig {
        applicationId ="com.example.moneyways"
        minSdk =21
        targetSdk =35
        versionCode =1
        versionName ="1.0"

        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled= false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )


        }
    }

    compileOptions {
        sourceCompatibility =JavaVersion.VERSION_17
                targetCompatibility =JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding= true
    }
}

dependencies {
    implementation("androidx.core:core:1.12.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.activity:activity:1.10.1")


    //Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1") {
}

    //Room database dependencies
    implementation("androidx.room:room-runtime:2.7.2")
    kapt("androidx.room:room-compiler:2.7.2")
    implementation("androidx.room:room-ktx:2.7.2")
    implementation ("androidx.constraintlayout:constraintlayout:2.2.1")
}
