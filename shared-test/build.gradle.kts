AppConfig.moduleNameSpace("com.stanleymesa.shared_test")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
    "implementation"(libs.junit)
    "implementation"(libs.coroutines.test)
}