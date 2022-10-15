package com.robothy.platform.release.gradle.task;

/**
 * Resolve next version.
 */
public interface NextVersionResolver {

  /**
   * Calculate the next version.
   *
   * @param task a next version task instance, which provides the project context.
   *
   * @return calculated next version.
   */
  Object nextVersion(NextVersion task);

}
