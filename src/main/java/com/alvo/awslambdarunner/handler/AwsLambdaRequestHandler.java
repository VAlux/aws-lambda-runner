package com.alvo.awslambdarunner.handler;

import com.amazonaws.services.lambda.runtime.Context;

public interface AwsLambdaRequestHandler<I, O> {
  O handleRequest(I input, Context context);

  Class<? extends I> getInputType();

  Class<? extends O> getOutputType();
}
