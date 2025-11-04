plugins {
    application
    checkstyle
    jacoco
    id("org.sonarqube") version "7.0.1.6134"
}

checkstyle {
    toolVersion = "10.12.4"
    configFile = file("checkstyle.xml")
    isIgnoreFailures = false
    maxWarnings = 0
}

jacoco {
    toolVersion = "0.8.14" // Укажите версию JaCoCo
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

sonar {
    properties {
        property("sonar.projectKey", "SaintCap_java-project-71")
        property("sonar.organization", "saintcap")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.java.checkstyle.reportPaths", "build/reports/checkstyle/main.xml")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.java.binaries", "build/classes")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor("info.picocli:picocli-codegen:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.1")
    implementation("commons-io:commons-io:2.14.0")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testCompileOnly("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")
    implementation(libs.guava)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass.set("hexlet.code.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks {
    checkstyleMain {
        dependsOn(compileJava)
    }

    checkstyleTest {
        dependsOn(compileTestJava)
    }
}
