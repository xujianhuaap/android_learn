package com.skullmind.io.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class FirstTask extends DefaultTask{
    @TaskAction
    void action(){
        println("=== my first task ===")
    }
}