import com.google.common.base.Charsets
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

val keyProperties = Properties()
val propertiesFile = File(rootDir, "app.properties")
if (propertiesFile.isFile) {
    InputStreamReader(
        FileInputStream(propertiesFile),
        Charsets.UTF_8
    ).use { reader ->
        keyProperties.load(reader)
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = keyProperties["app.id"].toString()
    compileSdk = keyProperties["sdk.compile"].toString().toInt()

    defaultConfig {
        applicationId = keyProperties["app.id"].toString()
        minSdk = keyProperties["sdk.min"].toString().toInt()
        targetSdk = keyProperties["sdk.target"].toString().toInt()
        versionCode = keyProperties["app.version.code"].toString().toInt()
        versionName = keyProperties["app.version.name"].toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val timestamp = SimpleDateFormat("dd_MMM_yyyy").format(Date())
        setProperty(
            "archivesBaseName",
            "github_users_${timestamp}_v_${
                keyProperties["app.version.name"].toString().replace(".", "_")
            }"
        )
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
//            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        dataBinding = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.core.splashcreen)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.navigation)
    implementation(libs.compose.material.icon)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.paging)
    implementation(libs.timber)
    implementation(libs.appcompat)
    implementation(libs.fragment)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.datastore)
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    kapt(libs.room.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    implementation(libs.work.runtime)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(projects.core)
    implementation(projects.features.search.searchData)
    implementation(projects.features.search.searchDomain)
    implementation(projects.features.search.searchPresentation)
    implementation(projects.features.detail.detailData)
    implementation(projects.features.detail.detailDomain)
    implementation(projects.features.detail.detailPresentation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
}