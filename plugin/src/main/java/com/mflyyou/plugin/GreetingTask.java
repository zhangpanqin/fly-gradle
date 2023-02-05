package com.mflyyou.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class GreetingTask extends DefaultTask {
    @Input
    public abstract Property<String> getMessage();

    @TaskAction
    public void run() {
        System.out.println("GreetingTask action 打印 message: %s".formatted(getMessage().getOrElse("GreetingTask 默认值")));
    }
}
