package com.alvo.awslambdarunner.classloading;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class LocalJarLocatorService implements JarLocatorService {

  private static final String FILE_PROTOCOL_PREFIX = "file:///";
  private static final String JAR_EXTENSION = ".jar";

  private final String lookupBasePath;

  public LocalJarLocatorService(@Value("${local.jar.lookup.directory}") String lookupBasePath) {
    this.lookupBasePath = lookupBasePath;
  }

  @Override
  public URI locateJar(@Value("${aws.lambda.service.name}") String jarName) throws URISyntaxException, IOException {
    final URI uri = new URI(normalizeLookupPath(lookupBasePath));
    try (Stream<Path> walk = Files.walk(FileUtils.getFile(uri.getPath()).toPath())) {
      return walk
          .filter(path -> validateFile(path, jarName))
          .findAny()
          .map(Path::toUri)
          .orElseThrow(() ->
              new FileNotFoundException(String.format("No files contains %s in %s", jarName, lookupBasePath)));
    }
  }

  private boolean validateFile(Path path, String jarName) {
    final String filename = path.getFileName().toString();
    return filename.contains(jarName) && filename.contains(JAR_EXTENSION);
  }

  private String normalizeLookupPath(String lookupBasePath) {
    final String normalizedPath = lookupBasePath.replace("\\", "/");
    if (normalizedPath.contains(FILE_PROTOCOL_PREFIX)) {
      return FILE_PROTOCOL_PREFIX + normalizedPath;
    }
    return normalizedPath;
  }
}
