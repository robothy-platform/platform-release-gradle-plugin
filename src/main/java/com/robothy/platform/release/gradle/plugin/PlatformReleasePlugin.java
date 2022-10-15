package com.robothy.platform.release.gradle.plugin;

import com.robothy.platform.release.gradle.task.Constants;
import com.robothy.platform.release.gradle.task.DefaultNextVersionResolver;
import com.robothy.platform.release.gradle.task.GetGitWorkingBranch;
import com.robothy.platform.release.gradle.task.NextVersion;
import com.robothy.platform.release.gradle.task.ReleaseTaskAction;
import com.robothy.platform.release.gradle.task.ResolveProjectVersionTask;
import com.robothy.platform.release.gradle.task.TaskNames;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.TaskContainer;

/**
 * Release workflow.
 *
 */
public class PlatformReleasePlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    TaskContainer tasks = project.getTasks();
    tasks.register(TaskNames.RESOLVE_PROJECT_VERSION.getName(), ResolveProjectVersionTask.class)
        .get().setGroup(Constants.RELEASE_GROUP_NAME);

    if (project.getRootProject() == project) {
      tasks.register(TaskNames.NEXT_VERSION.getName(), NextVersion.class, new DefaultNextVersionResolver())
          .get().setGroup(Constants.RELEASE_GROUP_NAME);
      tasks.register(TaskNames.GET_GIT_WORKING_BRANCH.getName(), GetGitWorkingBranch.class)
          .get().setGroup(Constants.RELEASE_GROUP_NAME);
      tasks.register(TaskNames.RELEASE.getName(), Exec.class, new ReleaseTaskAction())
          .get().setGroup(Constants.RELEASE_GROUP_NAME);
    }
  }

}