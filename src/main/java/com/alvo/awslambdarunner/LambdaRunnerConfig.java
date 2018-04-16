package com.alvo.awslambdarunner;

import com.alvo.awslambdarunner.classloading.JarClassloader;
import com.alvo.awslambdarunner.classloading.LocalJarLocatorService;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Configuration
public class LambdaRunnerConfig {

  @Value("${aws.lambda.service.name}")
  private String awsLambdaServiceName;

  @Value("${aws.lambda.request.handler.classname}")
  private String requestHandlerClassname;

  @Bean
  public JarClassloader jarClassloader(LocalJarLocatorService locator)
      throws IOException, URISyntaxException {

    final URL lambdaJarUrl = locator.locateJar(awsLambdaServiceName).toURL();
    return new JarClassloader(lambdaJarUrl);
  }

  @Bean
  public GenericAwsLambdaRequestHandler loadRequestHandler(JarClassloader lambdaClassloader)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

    final Class<?> requestHandlerClass = lambdaClassloader.loadJarClass(requestHandlerClassname);
    final List<Class<?>> typeParameters = lambdaClassloader.getGenericTypeArgumentClasses(requestHandlerClass);
    final RequestHandler requestHandler = (RequestHandler) requestHandlerClass.newInstance();

    return new GenericAwsLambdaRequestHandler(requestHandler, typeParameters);
  }
}
