package com.robothy.platform.release.gradle.task;

/**
 * Task descriptions.
 */
public enum ReleaseTaskNames {

  /**
   * release task.
   */
  RELEASE("release", "Release current project. This task only available on the root project. Set '-PRELEASE=ture' if you want to publish a formal version."),

  /**
   * Release version task.
   */
  RELEASE_VERSION("releaseVersion", "Set the version property in gradle.properties to a release version. " +
      "i.e remove the -SNAPSHOT suffix."),

  /**
   * nextVersion task.
   */
  NEXT_SNAPSHOT_VERSION("nextSnapshotVersion", "Update the value of 'version' in ${projectDir}/gradle.properties to next snapshot version."),

  /**
   * getGitWorkingBranch task.
   */
  GET_GIT_WORKING_BRANCH("getGitWorkingBranch", "Get the git working branch of current branch."),

  ;

  ReleaseTaskNames(String name, String description) {
    this.name = name;
    this.description = description;
  }

  private final String name;

  private final String description;

  /**
   * Get the task name.
   * @return task name.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the task description.
   * @return task description.
   */
  public String getDescription() {
    return description;
  }
}
