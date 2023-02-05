package com.mflyyou.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class HelloPlugin implements Plugin<Project> {
    public void apply(Project project) {

        var helloPluginExtension = project.getExtensions().create("hello", HelloPluginExtension.class);
        helloPluginExtension.getMessage().convention("Hello from HelloPlugin");
        var hello = project.task("hello");
        hello.doFirst(str -> {
            System.out.println(helloPluginExtension.getMessage().get() + "---com.mflyyou.plugin.HelloPlugin");
        });
    }
}

interface HelloPluginExtension {
    Property<String> getMessage();
}
