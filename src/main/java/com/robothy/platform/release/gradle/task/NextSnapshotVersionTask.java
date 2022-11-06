package com.robothy.platform.release.gradle.task;


/**
 * Increment the last version number and add a suffix "-SNAPSHOT".
 *
 * <pre>
 *   1.0.1-SNAPSHOT -> 1.0.2-SNAPSHOT
 *   1.0.1 -> 1.0.2-SNAPSHOT
 *   1.0 -> 1.1-SNAPSHOT
 *   1.0-SNAPSHOT -> 1.1-SNAPSHOT
 * </pre>
 *
 */
public class NextSnapshotVersionTask extends AbstractVersionUpdater {

  @Override
  public String update(String oldVersion) {
    String version = oldVersion;
    if (oldVersion.endsWith(SNAPSHOT_SUFFIX)) {
      version = oldVersion.substring(0, oldVersion.length() - SNAPSHOT_SUFFIX.length());
    }

    String[] slices = version.split("\\.");
    slices[slices.length - 1] = String.valueOf(Long.parseLong(slices[slices.length - 1]) + 1);

    return String.join(".", slices) + SNAPSHOT_SUFFIX;
  }

}
