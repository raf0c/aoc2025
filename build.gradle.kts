plugins {
    kotlin("jvm") version "2.2.20"
}

group = "dev.gaor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}