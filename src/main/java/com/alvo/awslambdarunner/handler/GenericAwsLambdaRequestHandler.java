package com.alvo.awslambdarunner.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GenericAwsLambdaRequestHandler implements AwsLambdaRequestHandler<Object, Object> {

  private static final int INPUT_TYPE_INDEX = 0;
  private static final int OUTPUT_TYPE_INDEX = 1;

  private final RequestHandler awsLambdaRequestHandler;
  private final List<Class<?>> typeParameters;

  public GenericAwsLambdaRequestHandler(RequestHandler awsLambdaRequestHandler,
                                        List<Class<?>> typeParameters) {
    this.awsLambdaRequestHandler = awsLambdaRequestHandler;
    this.typeParameters = typeParameters;
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

  @Override
  public Object handleRequest(Object input, Context context) {
    return awsLambdaRequestHandler.<Object, Object>handleRequest(input, context);
  }
}
