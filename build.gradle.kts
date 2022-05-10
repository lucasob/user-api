import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.3.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	id("application")
}

group = "com.lucasob"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springdoc:springdoc-openapi-ui:1.5.2")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.3.9")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.5.2")
	implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin:2.2.8.RELEASE")
	implementation("junit:junit:4.12")
	testImplementation("io.kotest:kotest-assertions-jvm:4.0.7")
	testImplementation("org.assertj", "assertj-core", "3.11.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

application {
	mainClassName = "com.lucasob.userapi.UserApiApplicationKt"
}
