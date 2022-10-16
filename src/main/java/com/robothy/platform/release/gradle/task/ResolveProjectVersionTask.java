package com.robothy.platform.release.gradle.task;

import java.util.Objects;
import java.util.Optional;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;
import org.slf4j.Logger;

/**
 * Resolve the project version. If the release option is true, then
 * remove the '-SNAPSHOT' suffix from {@code project.version}.
 */
public class ResolveProjectVersionTask extends DefaultTask {

  private static final Logger log = Logging.getLogger(ResolveProjectVersionTask.class);

  public ResolveProjectVersionTask() {
    Project project = getProject();
    project.afterEvaluate(this::configureDependentTask);
  }

  @TaskAction
  public void execute() {
    Project project = getProject();
    Object release = project.findProperty("release");
    if (Objects.equals("true", release)) {
      String version = project.getVersion().toString();
      project.setVersion(version.substring(0, version.lastIndexOf('-')));
      log.debug("Resolved project version is '{}'", project.getVersion());
    }
  }

  /**
   * Make all other tasks depend on task 'resolveProjectVersion'.
   */
  private void configureDependentTask(Project project) {
    TaskContainer tasks = project.getTasks();
    Optional<ResolveProjectVersionTask> resolveProjectVersionTask = tasks.withType(ResolveProjectVersionTask.class).stream().findFirst();
    resolveProjectVersionTask.ifPresent(projectVersionTask -> tasks.configureEach(task -> {
      if (!(task instanceof ResolveProjectVersionTask)) {
        task.dependsOn(projectVersionTask);
      }
    }));
  }

}
