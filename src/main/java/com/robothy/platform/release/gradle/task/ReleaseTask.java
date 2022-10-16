package com.robothy.platform.release.gradle.task;

import java.util.Set;
import org.gradle.api.Project;
import org.gradle.api.tasks.Exec;

/**
 * Release task.
 */
public class ReleaseTask extends Exec {

  /**
   * Set dependencies of the ReleaseTask.
   */
  public ReleaseTask() {
    Project project = getProject();
    project.afterEvaluate(this::configureTaskDependencies);
    project.getSubprojects().forEach(subproject -> subproject.afterEvaluate(this::configureTaskDependencies));
  }

  protected void configureTaskDependencies(Project project) {
    Set<String> dependencies = Set.of(TaskNames.NEXT_VERSION.getName(), TaskNames.GET_GIT_WORKING_BRANCH.getName(),
        "publish", "publishPlugins");
    project.getTasks().stream().filter(it -> dependencies.contains(it.getName()))
        .forEach(this::dependsOn);
  }

}
