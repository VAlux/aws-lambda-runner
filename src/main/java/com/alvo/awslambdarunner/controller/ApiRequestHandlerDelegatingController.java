package com.alvo.awslambdarunner.controller;

import com.alvo.awslambdarunner.ApiGatewayRequest;
import com.alvo.awslambdarunner.translator.ApiGatewayTranslator;
import com.alvo.awslambdarunner.GenericAwsLambdaRequestHandler;
import com.alvo.awslambdarunner.LambdaRuntimeContext;
import com.amazonaws.util.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApiRequestHandlerDelegatingController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestHandlerDelegatingController.class);

  private final GenericAwsLambdaRequestHandler requestHandler;
  private final ApiGatewayTranslator translator;

  @Autowired
  public ApiRequestHandlerDelegatingController(GenericAwsLambdaRequestHandler requestHandler,
                                               ApiGatewayTranslator translator) {
    this.requestHandler = requestHandler;
    this.translator = translator;
  }

  @RequestMapping
  @ResponseBody
  public ResponseEntity<?> handleRequest(HttpServletRequest request) {
    final String queryString = request.getQueryString();
    LOGGER.info("Received request {}", queryString);

    final LambdaRuntimeContext ctx = LambdaRuntimeContext.newBuilder()
        .withFunctionName("net_meteogroup_mg-posi_api")
        .withAwsRequestId("lambda-runner-request")
        .build();

    final Object output = translator.from(request)
        .map(this::marshalToInputType)
        .map(input -> requestHandler.getAwsLambdaRequestHandler().handleRequest(input, ctx))
        .orElseThrow(() -> new IllegalStateException("Lambda request handler returned empty or error response"));

    return ResponseEntity.ok(output);
  }

  private Object marshalToInputType(ApiGatewayRequest apiGatewayRequest) {
    final String requestAsJson = Jackson.toJsonString(apiGatewayRequest);
    return Jackson.fromJsonString(requestAsJson, requestHandler.getInputType());
  }
}
