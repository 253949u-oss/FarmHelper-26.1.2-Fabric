@file:Suppress("UnstableApiUsage")

plugins {
    idea
    java
    id("fabric-loom") version "1.20"
    id("dev.architectury.architectury-pack200") version "0.1.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.kyori.blossom") version "1.3.2"
}

val baseGroup: String by project
val mcVersion: String by project
val version: String by project
val loaderVersion: String by project
val fabricApiVersion: String by project
val modid: String by project
val modName: String by project

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

blossom {
    replaceToken("%%VERSION%%", version)
}

loom {
    silentMojangMappingsLicense()
    mixin {
        defaultRefmapName.set("mixins.$modid.refmap.json")
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
    maven("https://repo.polyfrost.cc/releases")
    maven("https://repo.essential.gg/repository/maven-public")
    maven("https://jitpack.io")
    maven("https://maven.fabricmc.net")
}

val shadowImpl: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    
    // Mixin
    compileOnly("org.spongepowered:mixin:0.12.5")  // Updated for newer Fabric
    annotationProcessor("org.spongepowered:mixin:0.12.5")
    
    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    
    // Your dependencies
    shadowImpl("org.java-websocket:Java-WebSocket:1.5.7")
    implementation("net.dv8tion:JDA:5.1.0")
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(Jar::class) {
    archiveBaseName.set(modName)
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("mcversion", mcVersion)
    inputs.property("modid", modid)
    inputs.property("modName", modName)
    filesMatching(listOf("fabric.mod.json", "mixins.$modid.json")) {
        expand(inputs.properties)
    }
}

// Shadow JAR relocation (prevents conflicts)
tasks.shadowJar {
    configurations = listOf(shadowImpl)
    archiveClassifier.set("shadow")
    relocate("org.java_websocket", "${baseGroup}.shadow.websocket")
}

// Make sure the shadow JAR is included in the final mod JAR
tasks.remapJar {
    dependsOn(tasks.shadowJar)
    inputFile.set(tasks.shadowJar.flatMap { it.archiveFile })
}
