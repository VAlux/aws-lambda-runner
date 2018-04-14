package com.alvo.awslambdarunner.classloading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;
import java.util.jar.JarEntry;

public class JarClassloader extends URLClassLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(JarClassloader.class);

  private static final int CLASS_EXT_LENGTH = ".class".length();

  private final URL jarUrl;

  public JarClassloader(URL jarUrl) {
    super(new URL[]{jarUrl});
    this.jarUrl = jarUrl;
  }

  public Class<?> loadJarClass(String className) throws ClassNotFoundException, IOException {
    URL url = new URL("jar", "", jarUrl + "!/");
    JarURLConnection connection = (JarURLConnection) url.openConnection();
    return connection.getJarFile().stream()
        .filter(jarEntry -> !jarEntry.isDirectory() && jarEntry.getName().contains(className))
        .findAny()
        .flatMap(this::loadClassForJarEntry)
        .orElseThrow(() ->
            new ClassNotFoundException("Desired class is not found or can't be loaded"));
  }

  private Optional<Class<?>> loadClassForJarEntry(JarEntry jarEntry) {
    try {
      final String entryName = jarEntry.getName();
      final String normalizedClassName = entryName
          .substring(0, entryName.length() - CLASS_EXT_LENGTH)
          .replace("/", ".");

      LOGGER.info("Loading class from jar for name: {}", normalizedClassName);
      return Optional.ofNullable(loadClass(normalizedClassName));
    } catch (ClassNotFoundException e) {
      LOGGER.error("Can't load class for jar entry: {}", jarEntry);
      return Optional.empty();
    }
  }
}
