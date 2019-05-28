package buildsrc.dependencies

/*
Single file for all the shared dependencies
 */
object Libs {
    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.0.2"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val ktxCore = "androidx.core:core-ktx:1.0.2"
        const val ktxActivity = "androidx.activity:activity-ktx:1.0.0-alpha06"
        const val ktxFragment = "androidx.fragment:fragment-ktx:1.1.0-alpha06"

        object Lifecycle {
            private const val version = "2.0.0-rc01"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        }

        object Room {
            private const val version = "2.1.0-alpha07"
            const val runtime = "androidx.room:room-runtime:$version"
            const val rxjava2 = "androidx.room:room-rxjava2:$version"
            const val roomTesting = "androidx.room:room-testing:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            object GUI {
                // GUI for Room. Use emulator's browser and open link http://xxx.xxx.x.xxx:8080
                // found in Logcat
                const val debug = "com.amitshekhar.android:debug-db:1.0.4"
            }
        }
    }

    object Google {
        const val gson = "com.google.code.gson:gson:2.8.5"
        const val material = "com.google.android.material:material:1.0.0"

        object Dagger {
            private const val version = "2.22.1"
            const val androidSupport = "com.google.dagger:dagger-android-support:$version"
            const val base = "com.google.dagger:dagger:$version"
            const val compiler = "com.google.dagger:dagger-compiler:$version"
            const val processor = "com.google.dagger:dagger-android-processor:$version"
        }

        object Firebase {
            const val core = "com.google.firebase:firebase-core:16.0.8"
        }
    }

    object Kotlin {
        private const val version = "1.3.31"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object Retrofit {
        private const val version = "2.4.0"
        const val base = "com.squareup.retrofit2:retrofit:$version"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.13.1"
        const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        const val scalarsConverter = "com.squareup.retrofit2:converter-scalars:$version"
    }

    object Rx2 {
        const val appCompatBinding = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.2.0"
        const val binding = "com.jakewharton.rxbinding2:rxbinding:2.2.0"
        const val googleMapsBinding = "org.aaronhe.rxgooglemapsbinding:rxgooglemapsbinding:0.1.3"
        const val location = "pl.charmas.android:android-reactive-location2:2.1@aar"
        const val relay = "com.jakewharton.rxrelay2:rxrelay:2.0.0"
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.6"
    }

    object Square {
        const val assistedInjectAnnotations = "com.squareup.inject:assisted-inject-annotations-dagger2:0.4.0"
        const val assistedInjectProcessor = "com.squareup.inject:assisted-inject-processor-dagger2:0.4.0"
    }

    object Glide {
        private const val version = "4.9.0"
        const val base =  "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }
}