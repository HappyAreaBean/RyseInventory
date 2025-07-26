plugins {
    java
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

group = "io.github.rysefoxx"
description = "RyseInventory"

allprojects {
    apply {
        plugin("java")
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
    }
    version = "1.6.15"
}

subprojects {
    apply {
        plugin("java-library")
        plugin("com.github.johnrengelman.shadow")
        plugin("maven-publish")
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }

    java {
        withSourcesJar()
        withJavadocJar()
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        mergeServiceFiles()
    }
}
