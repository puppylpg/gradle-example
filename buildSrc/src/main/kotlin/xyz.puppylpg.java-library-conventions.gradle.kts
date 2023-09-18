/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("xyz.puppylpg.java-common-conventions")

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "xyz.puppylpg.gradle"
            artifactId = "lib-tutorial"
            version = "1.0"

            from(components["java"])
        }
    }
}