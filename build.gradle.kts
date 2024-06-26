plugins {
    kotlin("jvm") version "1.9.23"
    war
    id("org.jetbrains.kotlin.plugin.jpa") version "2.0.0-RC3"
    kotlin("plugin.noarg") version "1.9.24"
}

group = "com.chskela"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/jakarta.servlet/jakarta.servlet-api
    compileOnly(libs.jakarta.servlet.api)
    // https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api
    implementation(libs.jstl.api)
    // https://mvnrepository.com/artifact/org.glassfish.web/jakarta.servlet.jsp.jstl
    implementation(libs.jsp.jstl)
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation(libs.hibernate.core)
    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation(libs.postgresql)
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j-impl
    implementation(libs.slf4j.log4j12)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}