
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.openapi.generator")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "net.chigita.openapigenexample"
        minSdk = 28
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("com.squareup.moshi:moshi:1.12.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.moshi:moshi-adapters:1.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("org.apache.oltu.oauth2:org.apache.oltu.oauth2.client:1.0.1") {
        exclude("org.apache.oltu.oauth2", "org.apache.oltu.oauth2.common")
    }
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}

// API 名
val apiName = "pet"
// ビルド先ディレクトリ
val buildApiDir = "$buildDir/openApiGenerator/$apiName"
// 自動生成先のパッケージ名
val basePackage = "net.chigita.openapigenexample.gen"

fun String.packageToDir() = replace('.', '/')

task<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generate") {
    doFirst {
        delete(file(buildApiDir))
    }

    generatorName.set("kotlin")
    library.set("jvm-retrofit2")
    inputSpec.set("$rootDir/spec/spec.yml")
    outputDir.set(buildApiDir)
    packageName.set(basePackage)
    apiPackage.set("$basePackage.$apiName.api")
    modelPackage.set("$basePackage.$apiName.model")
    configOptions.set(mapOf(
        "dateLibrary" to "java8"
    ))
    additionalProperties.set(mapOf(
        "useCoroutines" to "true"
    ))
    generateApiTests.set(false)
}

task<Copy>("copy") {
    val dirFrom = "$buildApiDir/src/main/kotlin/${basePackage.packageToDir()}/"
    val dirInto = "$projectDir/src/main/java/${basePackage.packageToDir()}/"

    doFirst {
        delete(file(dirInto))
    }

    dependsOn("generate")
    from(dirFrom)
    into(dirInto)
}

task("buildApi") {
    dependsOn("generate", "copy")
}
