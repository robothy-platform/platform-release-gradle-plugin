package com.robothy.platform.release.gradle.task;

import java.util.ArrayList;
import java.util.List;
import org.gradle.api.Action;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Exec;
import org.slf4j.Logger;

/**
 * Register release task.
 */
public class ReleaseTaskAction implements Action<Exec> {

  private static final Logger log = Logging.getLogger(ReleaseTaskAction.class);

  @Override
  public void execute(Exec exec) {
    exec.setWorkingDir(exec.getProject().getProjectDir());

    exec.doFirst(task -> {
      String version = exec.getProject().getVersion().toString();
      List<String> cmd = new ArrayList<>();
      cmd.add("git config user.name 'CI Pipeline'");
      cmd.add("git config user.email 'bot@robothy.com'");
      tag(cmd, version);

      GetGitWorkingBranch gitWorkingBranchTask =
          (GetGitWorkingBranch) task.getProject().getTasks().findByName(RelaseTaskNames.GET_GIT_WORKING_BRANCH.getName());
      commitNextVersion(cmd, version, gitWorkingBranchTask.getWorkingBranch());
      exec.commandLine("sh", "-c", String.join(" && ", cmd));
    });
  }

  private void tag(List<String> cmd, String version) {
    cmd.add("git tag " + version);
    cmd.add("git push origin " + version);
  }

  private void commitNextVersion(List<String> cmd, String version, String workingBranch) {
    cmd.add("git add ./gradle.properties");
    cmd.add("git commit -m '" + version + " released.'");
    cmd.add("git push origin " + workingBranch);
  }

}
