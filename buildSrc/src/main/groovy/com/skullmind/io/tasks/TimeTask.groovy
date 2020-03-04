package com.skullmind.io.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

public class TimeTask extends DefaultTask{

    @TaskAction
    void action(){
        createTimeFile(project)
    }

    void createTimeFile(Project project){
        File file = new File("${project.projectDir}${ File.pathSeparator }${System.currentTimeMillis()}.txt")
        if(!file.exists()) file.createNewFile()
    }
}