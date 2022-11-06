package com.robothy.platform.release.gradle.task;

import com.robothy.platform.release.gradle.utils.PropertyFileUtils;
import java.io.File;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * An updater that update the {@code version} property in the "gradle.properties" file.
 */
public abstract class AbstractVersionUpdater extends DefaultTask {

  /**
   * Suffix of a snapshot version.
   */
  protected static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";

  /**
   * Update version.
   *
   * @param oldVersion the current value of {@code version} in "gradle.properties".
   * @return a new value of the {@code version} property.
   */
  public abstract String update(String oldVersion);

  /**
   * Execute update.
   */
  @TaskAction
  public void exec() {
    File file = new File(getProject().getProjectDir(), "gradle.properties");
    PropertyFileUtils.upsertProperty(file, "version", this::update);
  }

}
