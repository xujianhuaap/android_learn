package com.skullmind.io.plugins

import com.skullmind.io.tasks.FirstTask
import org.apache.tools.ant.types.resources.First
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project


public class DemoPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.beforeEvaluate {
            println("before evaluate")
        }
        project.task("printTime"){
            println("task [printTime] ${System.currentTimeMillis()}")
            createTimeFile(project)
        }

        project.getTasks().create("firstTask", FirstTask.class,new Action<FirstTask>() {
            @Override
            void execute(FirstTask t) {
                t.action()
            }
        })
    }

    void createTimeFile(Project project){
        File file = new File("${project.projectDir}${ File.pathSeparator }${System.currentTimeMillis()}.txt")
        if(!file.exists()) file.createNewFile()
    }
}
