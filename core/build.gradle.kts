AppConfig.moduleNameSpace("com.stanleymesa.core")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(libs.activity)
    "implementation"(libs.fragment)
    "implementation"(libs.material)
    "implementation"(libs.appcompat)
    "implementation"(libs.compose.paging)
    "implementation"(libs.coil)
    "implementation"(libs.gson)
    "implementation"(libs.room)
    "implementation"(libs.room.ktx)
    "implementation"(libs.room.paging)
    "kapt"(libs.room.compiler)
    "implementation"(libs.okhttp)
    "implementation"(libs.logging.interceptor)
    "implementation"(libs.retrofit)
    "implementation"(libs.retrofit.converter.gson)
    "implementation"(libs.core.splashcreen)
    "implementation"(libs.work.runtime)
    "implementation"(libs.hilt.work)
    "implementation"(platform(libs.firebase.bom))
    "implementation"(libs.firebase.firestore)
    "implementation"(libs.lottie)

    "debugImplementation"(libs.chucker.debug)
    "releaseImplementation"(libs.chucker.release)
}