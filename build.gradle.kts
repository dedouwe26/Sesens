repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

plugins {
    java
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}