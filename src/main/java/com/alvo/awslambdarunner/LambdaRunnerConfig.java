package com.alvo.awslambdarunner;

import com.alvo.awslambdarunner.classloading.AwsLambdaClassloader;
import com.alvo.awslambdarunner.classloading.LocalJarLocatorService;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
public class LambdaRunnerConfig {

  @Value("${aws.lambda.service.name}")
  private String awsLambdaServiceName;

  @Value("${aws.lambda.request.handler.classname}")
  private String requestHandlerClassname;

  @Bean
  public AwsLambdaClassloader awsLambdaClassloader(LocalJarLocatorService locator)
      throws IOException, URISyntaxException {

    final URL lambdaJarUrl = locator.locateJar(awsLambdaServiceName).toURL();
    return new AwsLambdaClassloader(lambdaJarUrl, this.getClass().getClassLoader());
  }

  @Bean
  public RequestHandler loadRequestHandler(AwsLambdaClassloader lambdaClassloader)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    return lambdaClassloader.loadRequestHandlerClass(requestHandlerClassname).newInstance();
  }
}
