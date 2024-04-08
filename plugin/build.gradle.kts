group = "io.github.rysefoxx.inventory.plugin"
description = "RyseInventory"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(project(":v1_19"))
    implementation(project(":v1_18"))
    implementation(project(":v1_17"))
    implementation(project(":v1_16"))
    implementation(project(":api"))
    implementation("net.wesjd:anvilgui:1.9.2-SNAPSHOT")

    compileOnly("net.kyori:adventure-platform-bukkit:4.3.1")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.apache.commons:commons-lang3:3.12.0")
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    compileOnly("org.projectlombok:lombok:1.18.24")
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            groupId = "io.github.rysefoxx.inventory"
            artifactId = "RyseInventory-Plugin"
            version = "${project.version}"

            pom {
                name.set("RyseInventory")
                description.set("Inventory System")
                url.set("https://github.com/Rysefoxx/RyseInventory")
                packaging = "jar"
            }
            project.extensions.configure<com.github.jengelman.gradle.plugins.shadow.ShadowExtension>() {
                component(this@create)
            }
        }
    }
    repositories {
        maven {
            name = "frsRepository"
            url = uri("https://repo.fantasyrealms.net/other-libraries")
            credentials {
                username = findProperty("frsRepositoryUsername").toString()
                password = findProperty("frsRepositoryPassword").toString()
            }
        }
        maven {
            name = "frsRepositorySnapShots"
            url = uri("https://repo.fantasyrealms.net/other-snapshots")
            credentials {
                username = findProperty("frsRepositoryUsername").toString()
                password = findProperty("frsRepositoryPassword").toString()
            }
        }
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("net.wesjd.anvilgui", "io.github.rysefoxx.inventory.anvilgui")
        exclude("io/github/rysefoxx/inventory/plugin/ItemBuilder.class")
    }

    build {
        dependsOn(shadowJar)
    }
}
