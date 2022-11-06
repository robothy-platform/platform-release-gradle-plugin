package com.robothy.platform.release.gradle.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NextSnapshotVersionTaskTest {

  private final NextSnapshotVersionTask nextSnapshotVersion = ProjectBuilder.builder()
      .build().getTasks().create("nextSnapshotVersion", NextSnapshotVersionTask.class);

  @ParameterizedTest
  @CsvSource({
      "1.0, 1.1-SNAPSHOT",
      "1.0-SNAPSHOT, 1.1-SNAPSHOT",
      "1.0, 1.1-SNAPSHOT",
      "1, 2-SNAPSHOT",
      "1.0.0, 1.0.1-SNAPSHOT"
  })
  void update(String input, String expect) {
    assertEquals(expect, nextSnapshotVersion.update(input));
  }
}