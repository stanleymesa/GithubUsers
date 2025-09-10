AppConfig.moduleNameSpace("com.stanleymesa.detail_data")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
    "implementation"(projects.features.detail.detailDomain)
    "implementation"(libs.compose.paging)
    "implementation"(libs.room)
    "implementation"(libs.room.ktx)
    "implementation"(libs.retrofit)
    "kapt"(libs.room.compiler)
}