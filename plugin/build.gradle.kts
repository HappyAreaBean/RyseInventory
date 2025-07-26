group = "io.github.rysefoxx.inventory.plugin"
description = "RyseInventory"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven("https://repo.codemc.io/repository/maven-snapshots/")

    maven("https://mvn-repo.arim.space/lesser-gpl3/") // MorePaperLib
}

dependencies {
    implementation(project(":v1_21"))
    implementation(project(":v1_20"))
    implementation(project(":v1_19"))
    implementation(project(":v1_18"))
    implementation(project(":v1_17"))
    implementation(project(":v1_16"))
    implementation(project(":api"))
    implementation("net.wesjd:anvilgui:1.9.3-SNAPSHOT")
    implementation("space.arim.morepaperlib:morepaperlib:0.4.4")
    implementation("com.github.cryptomorin:XSeries:11.2.0") { isTransitive = false }

    compileOnly("net.kyori:adventure-platform-bukkit:4.3.2")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.apache.commons:commons-lang3:3.12.0")
    compileOnly("org.jetbrains:annotations:24.1.0")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    annotationProcessor("org.projectlombok:lombok:1.18.32")
    compileOnly("org.projectlombok:lombok:1.18.32")
}

publishing {
    publications {
        create<MavenPublication>("shadow") {
            groupId = "io.github.rysefoxx.inventory"
            artifactId = "RyseInventory-Plugin"
            version = "${project.version}"

            pom {
                name = "RyseInventory"
                packaging = "jar"
                description = "Inventory System"
                url = "https://github.com/Rysefoxx/RyseInventory"
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
                username = project.property("NEXUS_USERNAME").toString()
                password = project.property("NEXUS_PASSWORD").toString()
            }
        }
    }
}

tasks {
    shadowJar {
        //minimize()

        exclude("com/cryptomorin/xseries/abstractions/*")
        exclude("com/cryptomorin/xseries/messages/*")
        exclude("com/cryptomorin/xseries/particles/*")
        exclude("com/cryptomorin/xseries/profiles/*")
        exclude("com/cryptomorin/xseries/XBiome*")
        exclude("com/cryptomorin/xseries/XBlock*")
        exclude("com/cryptomorin/xseries/XEntity*")
        exclude("com/cryptomorin/xseries/XMaterial*")
        exclude("com/cryptomorin/xseries/XItemStack*")
        exclude("com/cryptomorin/xseries/XSound*")
        exclude("com/cryptomorin/xseries/XTag*")
        exclude("com/cryptomorin/xseries/XPotion*")
        exclude("com/cryptomorin/xseries/XWorldBorder*")
        exclude("com/cryptomorin/xseries/NoteBlockMusic*")
        exclude("com/cryptomorin/xseries/SkullCacheListener*")

        archiveClassifier.set("")
        relocate("net.wesjd.anvilgui", "io.github.rysefoxx.inventory.anvilgui")
        relocate("space.arim.morepaperlib", "io.github.rysefoxx.inventory.morepaperlib")
        relocate("com.cryptomorin.xseries", "io.github.rysefoxx.inventory.xseries")
        exclude("io/github/rysefoxx/inventory/plugin/ItemBuilder.class")
    }

    build {
        dependsOn(shadowJar)
    }
}