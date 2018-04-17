package com.alvo.awslambdarunner;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GenericAwsLambdaRequestHandler {

  private static final int INPUT_TYPE_INDEX = 0;
  private static final int OUTPUT_TYPE_INDEX = 1;

  private final RequestHandler<Object, Object> awsLambdaRequestHandler;
  private final List<Class<?>> typeParameters;

  public GenericAwsLambdaRequestHandler(RequestHandler<Object, Object> awsLambdaRequestHandler,
                                        List<Class<?>> typeParameters) {
    this.awsLambdaRequestHandler = awsLambdaRequestHandler;
    this.typeParameters = typeParameters;
  }

  public RequestHandler<Object, Object> getAwsLambdaRequestHandler() {
    return awsLambdaRequestHandler;
  }

  public List<Class<?>> getTypeParameters() {
    return typeParameters;
  }

  public Class<?> getInputType() {
    return typeParameters.get(INPUT_TYPE_INDEX);
  }

  public Class<?> getOutputType() {
    return typeParameters.get(OUTPUT_TYPE_INDEX);
  }
}
