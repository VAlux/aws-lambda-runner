package com.alvo.awslambdarunner.classloading;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.net.URL;
import java.net.URLClassLoader;

public class AwsLambdaClassloader extends URLClassLoader {
  public AwsLambdaClassloader(URL lambdaJarUrl, ClassLoader parent) {
    super(new URL[]{lambdaJarUrl}, parent);
  }

  public <I, O> Class<? extends RequestHandler<I, O>> loadRequestHandlerClass(String className) throws ClassNotFoundException {
    final Class<?> requestHandlerClass = Class.forName(className, true, this);
    if (requestHandlerClass.isAssignableFrom(RequestHandler.class)) {
      return (Class<? extends RequestHandler<I, O>>) requestHandlerClass;
    } else {
      throw new ClassNotFoundException("Desired class is not found or not assignable from RequestHandler type");
    }
  }
}
