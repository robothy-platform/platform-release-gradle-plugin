package com.robothy.platform.release.gradle.task;

import java.time.ZonedDateTime;
import java.util.Objects;
import org.gradle.api.Project;

/**
 * Resolver version following format '{Major}.{Minor}-SNAPSHOT' or '{Major}.{Minor}.{Patch}-SNAPSHOT';
 */
public class DefaultNextVersionResolver implements NextVersionResolver{
  @Override
  public Object nextVersion(NextVersion task) {
    String currentVersion = task.getProject().getVersion().toString();
    int year = ZonedDateTime.now().getYear();
    if (Objects.equals(currentVersion, Project.DEFAULT_VERSION)) {
      return year + ".1-SNAPSHOT";
    }

    String[] slices = currentVersion.split("\\.|\\-");
    try {
      int lastNumberIdx = currentVersion.endsWith("-SNAPSHOT") ? slices.length - 2 : slices.length - 1;
      long version = Long.parseLong(slices[lastNumberIdx]) + 1;
      return currentVersion.substring(0, currentVersion.lastIndexOf('.') + 1) + version + "-SNAPSHOT";
    } catch (Exception e) {
      throw new IllegalArgumentException("An valid version number should follow the format '{Major}.{Minor}-SNAPSHOT' or '{Major}.{Minor}.{Patch}-SNAPSHOT'.");
    }
  }
}
