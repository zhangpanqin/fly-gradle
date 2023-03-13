package com.mflyyou.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.ApplicationPluginConvention;
import org.gradle.api.provider.Property;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

import static org.gradle.api.reflect.TypeOf.typeOf;

public class Greeting implements HasPublicType {
    private final Project project;
    private String message;
    private String recipient;

    @Override
    public TypeOf<?> getPublicType() {
        return typeOf(Greeting.class);
    }


    public Greeting(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }
}
