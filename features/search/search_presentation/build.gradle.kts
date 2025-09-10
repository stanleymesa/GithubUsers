AppConfig.moduleNameSpace("com.stanleymesa.search_presentation")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
    "implementation"(projects.features.search.searchDomain)
    "implementation"(libs.compose.paging)
    "implementation"(libs.coilSvg)
    "implementation"(libs.material)
}