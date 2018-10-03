import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//versions
val gradleVer="4.10.2"
val axonVer="3.2.2"
val kotlinVer="1.3.0-rc-57"
val kotlinGradleVer="1.3.0-rc-57"
val kotlinCoroutinesVer="0.25.3-eap13"
val junitVer="4.12"
val slf4jVer="1.7.25"
val logbackVer="1.2.3"
val log4jVer="2.11.0"
val elasticsearchVer="6.2.4"
val springBootVer="2.0.2.RELEASE"
val springVer="5.0.6.RELEASE"
val reactorNettyVer="0.7.7.RELEASE"
val seleniumVer="3.12.0"
val httpclientVer="4.5.5"
val tornadoVer="1.7.17"
val jacksonVer="2.9.5"
val mailVer="1.6.1"
val asmVer="6.2"
val ooxmlVer="3.17"
val poiScratchpadVer="3.17"
val jackcessVer="2.1.11"
val googleCloudVer="1.32.0"
val docx4jVer="3.3.7"
val swaggerVer="1.0.36"
val jaxbApiVer="2.3.0"
val xsomVer="20140925"


//TODO, needed for the import above
plugins {
    kotlin("jvm") version "1.3.0-rc-57"
    application
}

val verKotlin = "1.3.0-rc-57"

allprojects {
    group = "ee"
    version = "dev-SNAPSHOT"

    repositories {
        mavenLocal()
        maven { url = uri("http://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("http://dl.bintray.com/kotlin/kotlin-dev") }
        jcenter()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven")
    apply(plugin = "maven-publish")

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin.sourceSets {
        getByName("main").kotlin.srcDirs("src-gen/main/kotlin")
        getByName("test").kotlin.srcDirs("src-gen/test/kotlin")

    }

    tasks.withType<KotlinCompile> {
        //println("Configuring $name in project ${project.name}...")

        sourceCompatibility = JavaVersion.VERSION_1_8.name
        targetCompatibility = JavaVersion.VERSION_1_8.name

        kotlinOptions {
            suppressWarnings = true
            jvmTarget = "1.8"
            apiVersion = "1.2"
            languageVersion = "1.2"
        }
    }

    //val alldeps = tasks.getting(DependencyReportTask::class) {}
}

//projects
project(":ee:ee-common_java") {
}

project(":ee:ee-common") {
    dependencies {
        compile(project (":ee:ee-common_java"))
        compile(kotlin(module = "stdlib", version = verKotlin))
        compile("org.slf4j:slf4j-api:$slf4jVer")
        compile("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVer")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVer")
        testCompile("junit:junit:$junitVer")
        runtime ("ch.qos.logback:logback-classic:$logbackVer")
    }
}


project(":ee:ee-asm") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile("org.ow2.asm:asm:$asmVer")
        compile("org.ow2.asm:asm-tree:$asmVer")
    }
}

/*
project(":ee:ee-axon_example") {
    apply(plugin: "org.springframework.boot")
    apply(plugin: "io.spring.dependency-management")

    dependencies {
        compile("org.springframework.boot:spring-boot-starter-data-jpa")
        compile("org.springframework.boot:spring-boot-starter-jersey")
        compile("org.springframework.boot:spring-boot-starter-web")
        runtime("org.hsqldb:hsqldb")
        compileOnly("org.projectlombok:lombok")
        testCompile("org.springframework.boot:spring-boot-starter-test")
        compile("org.axonframework:axon-spring:$axonVer")
        testCompile("org.axonframework:axon-test:$axonVer")
    }
}
*/

project(":ee:ee-lang_item") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVer") {
            exclude(module ="kotlin-reflect")
        }
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    }
}

project(":ee:ee-design") {
    dependencies {
        compile(project (":ee:ee-lang"))
    }
}

project(":ee:ee-design_gen") {
    dependencies {
        compile(project (":ee:ee-lang"))
    }
}

project(":ee:ee-design_ui") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}

project(":ee:ee-design_ui_des") {
    dependencies {
        compile(project (":ee:ee-design"))
        compile(project (":ee:ee-asm"))
    }
}

project(":ee:ee-design_ui_des_asm") {
    dependencies {
        compile(project (":ee:ee-design"))
        compile(project (":ee:ee-asm"))
    }
}

project(":ee:ee-design_swagger") {
    dependencies {
        compile(project (":ee:ee-design"))
        compile("io.swagger:swagger-parser:$swaggerVer")
    }
}

project(":ee:ee-design_xsd") {
    dependencies {
        compile(project (":ee:ee-design"))
        compile("com.sun.xsom:xsom:$xsomVer")
    }
}

project(":ee:ee-lang") {
    dependencies {
        compile(project (":ee:ee-lang_item"))
    }
}

project(":ee:ee-lang_gen") {
    dependencies {
        compile(project (":ee:ee-lang_item"))
    }
}

project(":ee:ee-task") {
    dependencies {
        compile(project (":ee:ee-lang"))
    }
}

project(":ee:ee-task_des") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}

project(":ee:ee-system_des") {
    dependencies {
        compile(project (":ee:ee-task_des"))
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    }
}

project(":ee:ee-system") {
    dependencies {
        compile(project (":ee:ee-task"))
    }
}

project(":ee:ee-design_task") {
    dependencies {
        compile(project (":ee:ee-design"))
        compile(project (":ee:ee-system"))
    }
}

project(":ee:ee-lang_fx") {
    apply(plugin = "application")
    application {
        mainClassName = "ee.lang.fx.ModelApp"
    }


    dependencies {
        compile(project (":ee:ee-design_task"))
        compile("no.tornado:tornadofx:$tornadoVer") {
            exclude(module = "kotlin-stdlib-jre8")
            exclude(module = "kotlin-reflect")
        }
    }

    /*
    jar {
        manifest {
            attributes(
                "Class-Path": configurations. compile(. collect { it.getName() }.join(" "),)
            "Main-Class": "ee.lang.fx.ModelApp"
            )
        }
        from configurations . compile(. collect { entry -> zipTree(entry) })
    }
    */
}

project(":ee-slides:ee-slides_des") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}

project(":ee-slides:ee-slides") {
    dependencies {
        compile(project (":ee:ee-common"))
    }
}

project(":ee-slides:ee-slides_json") {
    dependencies {
        compile(project (":ee-slides:ee-slides"))
        compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVer") {
            exclude(module = "kotlin-reflect")
        }
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    }
}

project(":ee-slides:ee-slides_fx") {
    apply(plugin = "application")
    application {
        mainClassName = "ee.slides.fx.SlidesApp"
    }

    dependencies {
        compile(project (":ee-slides:ee-slides_json"))
        compile("no.tornado:tornadofx:$tornadoVer") {
            exclude(module = "kotlin-stdlib-jre8")
            exclude(module = "kotlin-reflect")
        }
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    }

    /*
    jar {
        manifest {
            attributes(
                "Class-Path": configurations. compile(. collect { it.getName() }.join(" "),)
            "Main-Class": "ee.slides.fx.SlidesApp"
            )
        }
        from configurations . compile(. collect { entry -> zipTree(entry) })
    }
    */
}

project(":ee-timeline:ee-timeline_des") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}

project(":ee-timeline:ee-timeline") {
    dependencies {
        compile(project (":ee:ee-common"))
    }
}

project(":ee-timeline:ee-timeline_json") {
    dependencies {
        compile(project (":ee-timeline:ee-timeline"))
        compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVer") {
            exclude(module = "kotlin-reflect")
        }
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    }
}

project(":ee-timeline:ee-timeline_excel") {
    dependencies {
        compile(project (":ee-timeline:ee-timeline"))
        compile(project (":ee-timeline:ee-timeline_json"))
        compile(project (":ee-office:ee-excel"))
    }
}

project(":ee-elastic:ee-elastic") {
    apply(plugin = "application")
    application {
        mainClassName = "ee.es.ExportMainKt"
    }

    dependencies {
        compile(project (":ee:ee-common"))
        compile("org.elasticsearch.client:transport:$elasticsearchVer")
        compile("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVer") {
            exclude(module = "kotlin-reflect")
        }
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
    }
}

project(":ee-office:ee-excel") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee:ee-design"))

        compile("org.apache.poi:poi-ooxml:$ooxmlVer")
    }
}

project(":ee-office:ee-msaccess") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile("com.healthmarketscience.jackcess:jackcess:$jackcessVer")

        testCompile(project (":ee:ee-design"))
    }
}

project(":ee-office:ee-powerpoint") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee:ee-design"))
        compile(project (":ee-slides:ee-slides_json"))
        compile(project (":ee-office:ee-translate"))

        compile("org.apache.poi:poi-ooxml:$ooxmlVer")
    }
}

project(":ee-office:ee-word") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee:ee-design"))

        compile("org.apache.poi:poi-ooxml:$ooxmlVer")
        compile("org.apache.poi:poi-scratchpad:$poiScratchpadVer")
    }
}

project(":ee-office:ee-docx4j") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee-slides:ee-slides_json"))
        compile(project (":ee-office:ee-translate"))

        compile("org.docx4j:docx4j:$docx4jVer")
    }
}

project(":ee-email:ee-email") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile("javax.mail:javax.mail-api:$mailVer")
    }
}

project(":ee-email:ee-email_des") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}

project(":ee-email:ee-email_excel") {
    dependencies {
        compile(project (":ee-email:ee-email"))
        compile(project (":ee-office:ee-excel"))
    }
}

project(":ee-office:ee-word") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee:ee-design"))

        compile("org.apache.poi:poi-ooxml:$ooxmlVer")
    }
}

project(":ee-office:ee-translate") {
    dependencies {
        compile(project (":ee-office:ee-excel"))
        compile("com.google.cloud:google-cloud-translate:$googleCloudVer")
    }
}

project(":ee-office:ee-translate_fx") {
    dependencies {
        compile(project (":ee-office:ee-translate"))
        compile(project (":ee-office:ee-docx4j"))
        compile("no.tornado:tornadofx:$tornadoVer") {
            exclude(module = "kotlin-stdlib-jre8")
            exclude(module = "kotlin-reflect")
        }
    }
}

project(":ee-grab:ee-grab") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile("org.seleniumhq.selenium:selenium-api:$seleniumVer")
        compile("org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVer")
        compile("org.seleniumhq.selenium:selenium-chrome-driver:$seleniumVer")
        compile("org.seleniumhq.selenium:selenium-support:$seleniumVer")
        compile("org.apache.httpcomponents:httpclient:$httpclientVer")
    }

}
project(":ee-grab:ee-fiveh") {
    dependencies {
        compile(project (":ee-grab:ee-grab"))
        compile(project (":ee-email:ee-email"))
    }
}
project(":ee-grab:ee-rulit") {
    dependencies {
        compile(project (":ee-grab:ee-grab"))
    }
}
project(":ee-grab:ee-moodle") {
    dependencies {
        compile(project (":ee-grab:ee-grab"))
    }
}

project(":ee-grab:ee-moodle_fx") {
    apply(plugin = "application")
    application {
        mainClassName = "ee.moodle.fx.MoodleApp"
    }

    dependencies {
        compile(project (":ee-grab:ee-moodle"))
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
        compile("no.tornado:tornadofx:$tornadoVer") {
            exclude(module = "kotlin-stdlib-jre8")
            exclude(module = "kotlin-reflect")
        }
    }

    /*
    jar {
        manifest {
            attributes(
                "Class-Path": configurations. compile(. collect { it.getName() }.join(" "),)
            "Main-Class": "ee.moodle.fx.MoodleApp"
            )
        }
        from configurations . compile(. collect { entry -> zipTree(entry) })
    }

    task copyDrivers (type: Copy) {
    from "$buildDir/../../ee-grab/drivers"
    into "$buildDir/drivers"
    }
    */

    /*
    distributions {
        main {
            contents {
                from(copyDrivers) {
                    into "drivers"
                }
            }
        }
    }
    */
}

project(":ee-grab:ee-unisa") {
    dependencies {
        compile(project (":ee-grab:ee-grab"))
    }
}

project(":ee-grab:ee-brill") {
    dependencies {
        compile(project (":ee-grab:ee-grab"))
    }
}

project(":ee-schkola:ee-schkola") {
    dependencies {
        compile(project (":ee:ee-common"))

        testCompile(project (":ee-grab:ee-fiveh"))
        testCompile(project (":ee-office:ee-msaccess"))
        testCompile(project (":ee-email:ee-email_excel"))
    }
}
project(":ee-schkola:ee-schkola_fx") {
    apply(plugin = "application")
    application {
        mainClassName = "ee.schkola.fx.app.MyApp"
    }


    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee:ee-design"))
        compile(project (":ee-schkola:ee-schkola"))
        compile("no.tornado:tornadofx:$tornadoVer") {
            exclude(module = "kotlin-stdlib-jre8")
            exclude(module = "kotlin-reflect")
        }
    }

    /*
    jar {
        manifest {
            attributes(
                "Class-Path": configurations. compile(. collect { it.getName() }.join(" "),)
            "Main-Class": "ee.schkola.fx.app.MyApp"
            )
        }
        from configurations . compile(. collect { entry -> zipTree(entry) })
    }
    */
}
project(":ee-schkola:ee-schkola_des") {
    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee:ee-design"))
        compile(project (":ee:ee-system"))
    }
}
project(":ee-schkola:ee-schkola_des_fx") {
    dependencies {
        compile(project (":ee-schkola:ee-schkola_des"))
        compile(project (":ee:ee-lang_fx"))
    }
}
project(":ee-schkola:ee-schkola_groovy") {
    apply(plugin = "groovy")

    dependencies {
        compile(project (":ee:ee-common"))
        compile(project (":ee-schkola:ee-schkola"))
        compile(project (":ee-office:ee-msaccess"))
        compile("org.codehaus.groovy:groovy-all:2.4.7")

        testCompile(project (":ee:ee-design"))
    }
}

project(":rfc1006:rfc1006") {
    dependencies {
    }
}

project(":rfc1006:rfc1006_des") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}

project(":eye:eye") {
    dependencies {
    }
}

project(":eye:eye_des") {
    dependencies {
        compile(project (":ee:ee-design"))
    }
}