plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "16"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.ajalt.clikt:clikt:3.3.0")
    implementation("com.soywiz.korlibs.korio:korio-jvm:2.4.8")
    implementation("com.github.ajalt:mordant:1.2.1")
}

tasks{
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "aoc.CliKt"))
        }
    }
}