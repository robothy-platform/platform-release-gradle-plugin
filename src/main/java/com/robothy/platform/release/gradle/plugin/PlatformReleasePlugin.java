package com.robothy.platform.release.gradle.plugin;

import com.robothy.platform.release.gradle.task.Constants;
import com.robothy.platform.release.gradle.task.GetGitWorkingBranch;
import com.robothy.platform.release.gradle.task.NextSnapshotVersionTask;
import com.robothy.platform.release.gradle.task.ReleaseTask;
import com.robothy.platform.release.gradle.task.ReleaseTaskAction;
import com.robothy.platform.release.gradle.task.ReleaseTaskNames;
import com.robothy.platform.release.gradle.task.ReleaseVersionTask;
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

    NextSnapshotVersionTask nextSnapshotVersion = tasks.register(ReleaseTaskNames.NEXT_SNAPSHOT_VERSION.getName(), NextSnapshotVersionTask.class).get();
    nextSnapshotVersion.setGroup(Constants.RELEASE_GROUP_NAME);
    nextSnapshotVersion.setDescription(ReleaseTaskNames.NEXT_SNAPSHOT_VERSION.getDescription());

    ReleaseVersionTask releaseVersion = tasks.register(ReleaseTaskNames.RELEASE_VERSION.getName(), ReleaseVersionTask.class).get();
    releaseVersion.setGroup(Constants.RELEASE_GROUP_NAME);
    releaseVersion.setDescription(ReleaseTaskNames.RELEASE_VERSION.getDescription());

    GetGitWorkingBranch getGitWorkingBranch = tasks.register(ReleaseTaskNames.GET_GIT_WORKING_BRANCH.getName(), GetGitWorkingBranch.class).get();
    getGitWorkingBranch.setGroup(Constants.RELEASE_GROUP_NAME);
    getGitWorkingBranch.setDescription(ReleaseTaskNames.GET_GIT_WORKING_BRANCH.getDescription());

    Exec release = tasks.register(ReleaseTaskNames.RELEASE.getName(), ReleaseTask.class, new ReleaseTaskAction()).get();
    release.setGroup(Constants.RELEASE_GROUP_NAME);
    release.setDescription(ReleaseTaskNames.RELEASE.getDescription());
  }

}