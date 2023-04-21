pluginManagement {
    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }
}

include ("ee:ee-common_java", "ee:ee-common", "ee:ee-lang_item", "ee:ee-lang_gen", "ee:ee-lang",
"ee:ee-design_gen", "ee:ee-design", "ee:ee-design_swagger", "ee:ee-design_xsd", "ee:ee-design_json")
//include "ee:ee-axon_example"
include ("ee-slides:ee-slides_des", "ee-slides:ee-slides", "ee-slides:ee-slides_json", "ee-slides:ee-slides_fx")
include ("ee-timeline:ee-timeline_des", "ee-timeline:ee-timeline", "ee-timeline:ee-timeline_json", "ee-timeline:ee-timeline_excel")
include ("ee-elastic:ee-elastic")
include ("ee-office:ee-excel", "ee-office:ee-powerpoint", "ee-office:ee-msaccess", "ee-office:ee-word", "ee-office:ee-translate", "ee-office:ee-translate_fx", "ee-office:ee-docx4j")
include ("ee-email:ee-email", "ee-email:ee-email_des", "ee-email:ee-email_excel")
include ("ee-grab:ee-grab", "ee-grab:ee-fiveh", "ee-grab:ee-rulit", "ee-grab:ee-moodle", "ee-grab:ee-moodle_fx", "ee-grab:ee-unisa", "ee-grab:ee-brill", "ee-grab:ee-sharepoint")
include ("ee-schkola:ee-schkola", "ee-schkola:ee-schkola_des")

include ("auth:auth-des")
include ("seminar:seminar-des")
include("preachit:preachit-des")