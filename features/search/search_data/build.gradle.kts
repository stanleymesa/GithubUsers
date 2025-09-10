AppConfig.moduleNameSpace("com.stanleymesa.search_data")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
    "implementation"(projects.features.search.searchDomain)
    "implementation"(libs.paging.runtime)
    "implementation"(libs.compose.paging)
    "implementation"(libs.room)
    "implementation"(libs.room.ktx)
    "implementation"(libs.retrofit)
    "kapt"(libs.room.compiler)
}