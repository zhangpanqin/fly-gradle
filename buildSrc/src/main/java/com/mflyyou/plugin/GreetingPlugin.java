package com.mflyyou.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class GreetingPlugin implements Plugin<Project> {
    public void apply(Project project) {

        var greeting = project.getExtensions().create("greeting", GreetingPluginExtension.class);
        greeting.getMessage().convention("Hello from GreetingPlugin");
        var hello = project.task("hello");
        hello.doFirst(str -> {
            System.out.println(greeting.getMessage().get()+"com.mflyyou.plugin.GreetingPlugin");
        });
    }
}

interface GreetingPluginExtension {
    Property<String> getMessage();
}
