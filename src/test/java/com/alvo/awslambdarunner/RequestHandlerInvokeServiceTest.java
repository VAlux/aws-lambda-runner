package com.alvo.awslambdarunner;

import com.alvo.awslambdarunner.classloading.JarClassloader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class RequestHandlerInvokeServiceTest {

  private RequestHandlerInvokeService invokeService;
  private String spec = "C:\\Users\\Oleksandr_Voievodin1\\.m2\\repository\\com\\meteogroup\\point-observation-api\\1.0-SNAPSHOT\\point-observation-api-1.0-SNAPSHOT.jar";

  @Before
  public void setUp()
      throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

    try (JarClassloader classloader = new JarClassloader(new URL(spec))) {
//      RequestHandler handler = classloader.loadJarClass("ApiRequestHandler").newInstance();
    }
  }

  @Test
  public void testLoadRequestHandler() {
    final Object invoke = invokeService.invoke(null, null);
  }
}