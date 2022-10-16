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
    ResolveProjectVersionTask resolveProjectVersionTask = tasks.register(TaskNames.RESOLVE_PROJECT_VERSION.getName(), ResolveProjectVersionTask.class).get();
    resolveProjectVersionTask.setGroup(Constants.RELEASE_GROUP_NAME);
    resolveProjectVersionTask.setDescription(TaskNames.RESOLVE_PROJECT_VERSION.getDescription());

    if (project.getRootProject() == project) {
      NextVersion nextVersion = tasks.register(TaskNames.NEXT_VERSION.getName(), NextVersion.class, new DefaultNextVersionResolver()).get();
      nextVersion.setGroup(Constants.RELEASE_GROUP_NAME);
      nextVersion.setDescription(TaskNames.NEXT_VERSION.getDescription());

      GetGitWorkingBranch getGitWorkingBranch = tasks.register(TaskNames.GET_GIT_WORKING_BRANCH.getName(), GetGitWorkingBranch.class).get();
      getGitWorkingBranch.setGroup(Constants.RELEASE_GROUP_NAME);
      getGitWorkingBranch.setDescription(TaskNames.GET_GIT_WORKING_BRANCH.getDescription());

      Exec release = tasks.register(TaskNames.RELEASE.getName(), Exec.class, new ReleaseTaskAction()).get();
      release.setGroup(Constants.RELEASE_GROUP_NAME);
      release.setDescription(TaskNames.RELEASE.getDescription());
    }
  }

}