package com.alvo.awslambdarunner;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GenericAwsLambdaRequestHandler {

  private final RequestHandler awsLambdaRequestHandler;
  private final List<Class<?>> typeParameters;

  public GenericAwsLambdaRequestHandler(RequestHandler awsLambdaRequestHandler, List<Class<?>> typeParameters) {
    this.awsLambdaRequestHandler = awsLambdaRequestHandler;
    this.typeParameters = typeParameters;
  }

  public RequestHandler getAwsLambdaRequestHandler() {
    return awsLambdaRequestHandler;
  }

  public List<Class<?>> getTypeParameters() {
    return typeParameters;
  }
}
