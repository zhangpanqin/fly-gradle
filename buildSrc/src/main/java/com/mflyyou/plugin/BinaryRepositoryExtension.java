package com.mflyyou.plugin;

import org.gradle.api.provider.Property;

public abstract class BinaryRepositoryExtension {

    public abstract Property<String> getCoordinates();

    public abstract Property<String> getServerUrl();
}