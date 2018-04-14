package com.alvo.awslambdarunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestHandlerInvokeService {

  private final RequestHandler<Object, Object> lambdaRequestHandler;

  @Autowired
  public RequestHandlerInvokeService(RequestHandler<Object, Object> lambdaRequestHandler) {
    this.lambdaRequestHandler = lambdaRequestHandler;
  }

  public Object invoke(Object requestPayload, Context context) {
    return lambdaRequestHandler.handleRequest(requestPayload, context);
  }
}
