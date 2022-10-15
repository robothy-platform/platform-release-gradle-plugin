package com.robothy.platform.release.gradle.task;

import java.util.Objects;
import java.util.Optional;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

/**
 * Resolve the project version. If the release option is true, then
 * remove the '-SNAPSHOT' suffix from {@code project.version}.
 */
public class ResolveProjectVersionTask extends DefaultTask {

  @TaskAction
  public void execute() {
    Project project = getProject();
    Object release = project.findProperty("release");
    if (Objects.equals("true", release)) {
      String version = project.getVersion().toString();
      project.setVersion(version.substring(0, version.lastIndexOf('-')));
    }

    project.afterEvaluate(this::configureDependentTask);
  }

  /**
   * Make all other tasks depend on task 'resolveProjectVersion'.
   */
  private void configureDependentTask(Project project) {
    TaskContainer tasks = project.getTasks();
    Optional<ResolveProjectVersionTask> resolveProjectVersionTask = tasks.withType(ResolveProjectVersionTask.class).stream().findFirst();
    resolveProjectVersionTask.ifPresent(projectVersionTask -> tasks.configureEach(task -> task.dependsOn(projectVersionTask)));
  }

}
