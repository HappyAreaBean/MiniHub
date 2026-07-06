plugins {
    id("java-library")
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.lombok)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper.api)

    implementation(libs.lamp.common)
    implementation(libs.lamp.bukkit)
    implementation(libs.spec)
    implementation(libs.fastboard)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion(libs.versions.minecraft.get())
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
    }

    processResources {
        val props = mapOf("version" to version, "description" to project.description)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    compileJava {
        options.compilerArgs.addAll(listOf("-parameters"))
        options.encoding = "UTF-8"
    }

    shadowJar {
        var pkg = "net.fantasyrealms.minihub.libs"
        relocate("revxrsal.spec", "${pkg}.spec")
        relocate("revxrsal.commands", "${pkg}.commands")
        relocate("fr.mrmicky.fastboard", "${pkg}.fastboard")
    }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}
