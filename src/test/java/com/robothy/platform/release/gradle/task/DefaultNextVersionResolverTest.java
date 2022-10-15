package com.robothy.platform.release.gradle.task;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.gradle.api.Project;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class DefaultNextVersionResolverTest {

  DefaultNextVersionResolver defaultNextVersionResolver = new DefaultNextVersionResolver();

  @ParameterizedTest
  @CsvSource(value = {
      "1.0-SNAPSHOT, 1.1-SNAPSHOT",
      "1.1.2-SNAPSHOT, 1.1.3-SNAPSHOT"
  }, delimiter = ',')
  public void testNextVersion(String input, String expect) {
    Project project = Mockito.mock(Project.class);
    NextVersion nextVersionTask = Mockito.mock(NextVersion.class);
    when(project.getVersion()).thenReturn(input);
    when(nextVersionTask.getProject()).thenReturn(project);
    assertEquals(expect, defaultNextVersionResolver.nextVersion(nextVersionTask));
  }

}