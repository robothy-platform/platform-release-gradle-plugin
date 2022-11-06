package com.robothy.platform.release.gradle.task;

import static org.junit.jupiter.api.Assertions.*;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReleaseVersionTaskTest {

  private final ReleaseVersionTask task = ProjectBuilder.builder().build()
      .getTasks().create("releaseVersion", ReleaseVersionTask.class);

  @CsvSource({
      "1.0-SNAPSHOT, 1.0",
      "1.0, 1.0",
      "1-SNAPSHOT, 1",
      "1.0.0, 1.0.0",
      "1.0.0-SNAPSHOT, 1.0.0"
  })
  @ParameterizedTest
  void update(String input, String expect) {
    assertEquals(expect, task.update(input));
  }
}