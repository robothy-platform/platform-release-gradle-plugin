package com.robothy.platform.release.gradle.task;

/**
 * Task descriptions.
 */
public enum TaskNames {

  /**
   * release task.
   */
  RELEASE("release", "Release current project. This task only available on the root project. Set '-PRELEASE=ture' if you want to publish a formal version."),

  /**
   * nextVersion task.
   */
  NEXT_VERSION("nextVersion", "Update the value of 'version' in ${projectDir}/gradle.properties to next value."),

  /**
   * getGitWorkingBranch task.
   */
  GET_GIT_WORKING_BRANCH("getGitWorkingBranch", "Get the git working branch of current branch."),

  /**
   * Load library versions from lib-versions.properties.
   */
  INJECT_LIBRARY_VERSIONS("injectLibraryVersions", "Inject library versions that declared in 'lib-versions.properties'. " +
      " The entry follows '${group}/${name}=${version}' format."),


  RESOLVE_PROJECT_VERSION("resolveProjectVersion", "Resolve the project version according the release. " +
      "If '-Prelease=true', then make the project version formal. i.e. remove the '-SNAPSHOT' suffix.")

  ;



  TaskNames(String name, String description) {
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
