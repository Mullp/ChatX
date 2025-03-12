import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
    id("com.gradleup.shadow") version "9.0.0-beta10"
}

group = "me.mullp"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "extendedclip"
        url = uri("https://repo.extendedclip.com/releases/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("org.bstats:bstats-bukkit:3.1.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release = 21
}

tasks.shadowJar {
    dependencies {
        relocate("org.bstats", "me.mullp.chatx.libs")
    }

    archiveBaseName.set("ChatX")
    archiveVersion.set(project.version.toString())
    archiveClassifier.set("")

    minimize()
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}

paper {
    name = "ChatX"
    author = "Mullp"
    version = project.version.toString()
    description = "The Chat plugin for managing your chat."
    apiVersion = "1.21"
    main = "me.mullp.chatx.ChatX"
    bootstrapper = "me.mullp.chatx.ChatXBootstrap"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    serverDependencies {
        register("PlaceholderAPI") {
            required = false
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
        }
    }
}

tasks.runServer {
    minecraftVersion("1.21.4")

    downloadPlugins {
        hangar("PlaceholderAPI", "2.11.6")
        url("https://download.luckperms.net/1573/bukkit/loader/LuckPerms-Bukkit-5.4.156.jar")
    }
}
