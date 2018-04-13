package com.alvo.awslambdarunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestHandlerInvokeService<T> {

  private final RequestHandler lambdaRequestHandler;

  @Autowired
  public RequestHandlerInvokeService(RequestHandler lambdaRequestHandler) {
    this.lambdaRequestHandler = lambdaRequestHandler;
  }

  public T invoke(Object requestPayload, Context context) {
    return (T) lambdaRequestHandler.handleRequest(requestPayload, context);
  }
}
