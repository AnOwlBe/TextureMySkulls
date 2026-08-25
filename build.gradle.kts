plugins {
    id("java-library")
    alias(libs.plugins.run.paper)
    id("com.gradleup.shadow") version "9.3.1"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
    implementation("org.bstats:bstats-bukkit:3.2.1")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks {
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version, "description" to project.description)
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}

tasks.shadowJar {
    configurations = project.configurations.runtimeClasspath.map { setOf(it) }

    dependencies {
        exclude { it.moduleGroup != "org.bstats" }
    }

    relocate("org.bstats", project.group.toString())
}
