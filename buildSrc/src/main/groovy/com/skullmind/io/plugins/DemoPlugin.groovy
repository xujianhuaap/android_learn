package com.skullmind.io.plugins

import com.skullmind.io.tasks.FirstTask
import com.skullmind.io.tasks.TimeTask
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

public class DemoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.beforeEvaluate {
            println("before evaluate")
        }
        project.tasks.create("timeTask",TimeTask.class,new Action<TimeTask>() {
            @Override
            void execute(TimeTask t) {
//                t.project = project
            }
        })
        project.tasks.create("firstTask", FirstTask.class)
    }
}
