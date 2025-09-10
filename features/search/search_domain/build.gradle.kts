AppConfig.moduleNameSpace("com.stanleymesa.search_domain")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
    "implementation"(libs.compose.paging)
}