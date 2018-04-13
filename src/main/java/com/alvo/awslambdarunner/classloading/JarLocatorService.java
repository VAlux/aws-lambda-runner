package com.alvo.awslambdarunner.classloading;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public interface JarLocatorService {
  URI locateJar(String name) throws URISyntaxException, IOException;
}
