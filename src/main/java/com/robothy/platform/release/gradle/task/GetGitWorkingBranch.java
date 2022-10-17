package com.robothy.platform.release.gradle.task;

import java.io.ByteArrayOutputStream;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.AbstractExecTask;
import org.gradle.api.tasks.LocalState;
import org.slf4j.Logger;

/**
 * An action to get current branch.
 */
public class GetGitWorkingBranch extends AbstractExecTask<GetGitWorkingBranch> {

  private static final Logger log = Logging.getLogger(GetGitWorkingBranch.class);

  private String workingBranch;

  /**
   * Get git working branch of current source code repository.
   * The task must execute before get the working branch.
   *
   * @return Git working branch.
   */
  @LocalState
  public String getWorkingBranch() {
    return this.workingBranch;
  }

  /**
   * Construct a task.
   */
  public GetGitWorkingBranch() {
    super(GetGitWorkingBranch.class);
    setGroup(Constants.RELEASE_GROUP_NAME);
    setDescription(RelaseTaskNames.GET_GIT_WORKING_BRANCH.getDescription());
    ByteArrayOutputStream stdout = new ByteArrayOutputStream();
    ByteArrayOutputStream stderr = new ByteArrayOutputStream();

    setWorkingDir(getProject().getProjectDir());

    String os = System.getProperty("os.name");
    if (os.toLowerCase().contains("windows")) {
      commandLine("cmd", "/c", "git branch | findstr *");
    } else {
      commandLine("sh", "-c", "git branch | grep \\*");
    }
    setStandardOutput(stdout);
    setErrorOutput(stderr);

    doLast(task -> {
      if(stderr.size() == 0) {
        String branchLine = stdout.toString();
        this.workingBranch = branchLine.substring(branchLine.indexOf('*') + 2).trim();
        log.info("Got working branch '{}'", workingBranch);
      } else {
        throw new IllegalStateException(stderr.toString());
      }
    });

  }

}
