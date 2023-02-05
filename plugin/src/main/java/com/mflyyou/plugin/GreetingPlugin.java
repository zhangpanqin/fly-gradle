package com.mflyyou.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.internal.CollectionCallbackActionDecorator;
import org.gradle.api.plugins.ApplicationPluginConvention;
import org.gradle.api.plugins.JavaApplication;
import org.gradle.api.plugins.internal.DefaultApplicationPluginConvention;
import org.gradle.api.plugins.internal.DefaultJavaApplication;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.internal.reflect.Instantiator;

import javax.inject.Inject;

public class GreetingPlugin implements Plugin<Project> {
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();

        GreetingTask greeting = tasks.create("greeting", GreetingTask.class, greetingTask -> {
            System.out.println("GreetingTask 配置阶段执行 %s".formatted(greetingTask.getMessage().getOrElse("没有赋值")));
        });

        greeting.doFirst((greetingTask) -> {
            System.out.println("GreetingTask 执行阶段执行 doFirst %s".formatted(greeting.getMessage().getOrElse("没有赋值")));
        });

        greeting.doLast((greetingTask) -> {
            System.out.println("GreetingTask 执行阶段执行 doLast %s".formatted(greeting.getMessage().getOrElse("没有赋值")));
        });
    }
}
