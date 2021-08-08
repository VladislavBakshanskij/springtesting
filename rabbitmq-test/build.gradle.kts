import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("nu.studer.jooq") version "6.0"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "com.vladislavbakshanskij"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.liquibase:liquibase-core")
    implementation("org.apache.commons:commons-lang3")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.testcontainers:testcontainers:1.16.0")
    testImplementation("org.testcontainers:junit-jupiter:1.16.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

jooq {
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/postgres"
                    user = "postgres"
                    password = "pass"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                        isPojos = false
                        isDaos = false
                        isImmutablePojos = false
                        isRecords = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.vladislavbakshanskij.rabbitmqtest.jooq"
                        directory = "src/main/generated/"  // default (can be omitted)
                    }
                    strategy.apply {
                        name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    }
                }
            }
        }
    }

    dependencies {
        jooqGenerator("org.postgresql:postgresql:42.2.23.jre7")
    }
}

//buildscript {
//    repositories {
//        mavenLocal()
//        mavenCentral()
//    }
//
//    dependencies {
//        classpath("org.jooq:jooq-codegen:3.15.1")
//        classpath("org.postgresql:postgresql:42.2.23.jre7")
//    }
//}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


//// todo create task for liquibase
//abstract class RunJooqCodeGenerationTask : DefaultTask() {
//    @get:Input
//    abstract val host: Property<String>
//    @get:Input
//    abstract val port: Property<Int>
//    @get:Input
//    abstract val db: Property<String>
//    @get:Input
//    abstract val username: Property<String>
//    @get:Input
//    abstract val password: Property<String>
//
//    @TaskAction
//    fun generate() {
//        org.jooq.codegen.GenerationTool.generate(org.jooq.meta.jaxb.Configuration()
//            .withJdbc(org.jooq.meta.jaxb.Jdbc()
//                .withDriver("org.postgresql.Driver")
//                .withUrl("jdbc:postgresql://${host.get()}:${port.get()}/${db.get()}")
//                .withUser(username.get())
//                .withPassword(password.get()))
//            .withGenerator(org.jooq.meta.jaxb.Generator()
//                .withDatabase(org.jooq.meta.jaxb.Database()
//                    .withName("org.jooq.meta.postgres.PostgresDatabase")
//                    .withInputSchema("public"))
//                .withGenerate(org.jooq.meta.jaxb.Generate()
//                    .withPojos(true)
//                    .withDaos(true))
//                .withTarget(org.jooq.meta.jaxb.Target()
//                    .withPackageName("com.vladislavbakshanskij.rabbitmqtest.jooq")
//                    .withDirectory("src/main/kotlin"))))
//    }
//}
//
//tasks.register<RunJooqCodeGenerationTask>("jooqGenerate") {
//    host.set("localhost")
//    port.set(5432)
//    db.set("postgres")
//    username.set("postgres")
//    password.set("pass")
//}
