plugins {
    kotlin("jvm") version "1.9.23"
    war
}

group = "com.chskela"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api
    compileOnly(libs.jakarta.servlet.api)
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation(libs.hibernate.core)
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-platform
    implementation(libs.hibernate.platform)
    // https://mvnrepository.com/artifact/jakarta.transaction/jakarta.transaction-api
    implementation(libs.jakarta.transaction.api)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}