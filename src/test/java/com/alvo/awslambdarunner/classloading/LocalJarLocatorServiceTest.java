package com.alvo.awslambdarunner.classloading;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LocalJarLocatorServiceTest {

  private static final String SERVICE_NAME = "point-observation";
  private static final String JAR_EXTENSION = ".jar";

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  private JarLocatorService locatorService;

  @Before
  public void setUp() throws IOException {
    folder.newFile(SERVICE_NAME + JAR_EXTENSION);
    locatorService = new LocalJarLocatorService(folder.getRoot().getPath());
  }

  @Test
  public void testLocateJar() throws IOException, URISyntaxException {
    final URI uri = locatorService.locateJar(SERVICE_NAME);
    Assert.assertNotNull(uri);
  }
}