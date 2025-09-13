AppConfig.moduleNameSpace("com.stanleymesa.setting_presentation")
apply {
    from("$rootDir/buildSrc/android-common.gradle")
}

dependencies {
    "implementation"(projects.core)
}