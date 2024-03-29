package com.mflyyou.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

abstract public class LatestArtifactVersion extends DefaultTask {

    @Input
    abstract public Property<String> getCoordinates();

    @Input
    abstract public Property<String> getServerUrl();

    @TaskAction
    public void resolveLatestVersion() {
        System.out.println("LatestArtifactVersion：Retrieving artifact " + getCoordinates().get() + " from " + getServerUrl().get());
    }
}