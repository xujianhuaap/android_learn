package com.skullmind.io.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project


public class DemoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.beforeEvaluate {
            println("before evaluate")
        }
        project.task("printTime",{
            println(System.currentTimeMillis())
        })
        System.out.println("")
    }
}
