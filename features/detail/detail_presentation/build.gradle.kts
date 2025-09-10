AppConfig.moduleNameSpace("com.stanleymesa.detail_presentation")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
    "implementation"(projects.features.detail.detailDomain)
    "implementation"(libs.compose.paging)
    "implementation"(libs.coilSvg)
    "implementation"(libs.material)
}