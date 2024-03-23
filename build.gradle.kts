import org.unbrokendome.gradle.plugins.testsets.dsl.testSets

plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.ajoberstar.grgit") version "5.0.0-rc.3"
    id("org.flywaydb.flyway") version "10.10.0"
    id("jacoco")
    id("org.unbroken-dome.test-sets") version "4.1.0"
}

group = "com.monadic"
version = getGitVersion()

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.7.3")
        classpath("org.flywaydb:flyway-database-postgresql:10.10.0")
    }
}


flyway {
    url = "jdbc:postgresql://localhost:5432/products"
    user = "products"
    password = "s3cr3t"
    cleanDisabled = false
}



dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("org.testcontainers:postgresql")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

testSets {
    create("integrationTest")
}

tasks.register("sourceSets") {
    group = "help"
    description = "List all source sets"
    doLast{
        for (ss in sourceSets) {
            println("${ss.name}: ${ss.allSource.srcDirs}")
        }
    }
}
fun getGitVersion(): String {
    val tag = grgit.tag.list().firstOrNull() ?: "0.0.1"
    val sha = grgit.head().abbreviatedId ?: "SNAPSHOT"
    return "$tag-$sha"
}
