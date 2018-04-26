package com.alvo.awslambdarunner.controller;

import com.alvo.awslambdarunner.ApiGatewayRequest;
import com.alvo.awslambdarunner.LambdaRuntimeContext;
import com.alvo.awslambdarunner.handler.AwsLambdaRequestHandler;
import com.alvo.awslambdarunner.handler.GenericAwsLambdaRequestHandler;
import com.alvo.awslambdarunner.translator.ApiGatewayTranslator;
import com.alvo.awslambdarunner.translator.Translator;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Controller
public class ApiRequestHandlerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestHandlerController.class);
  private static final String AWS_REQUEST_ID = "lambda-runner-request-id";

  private final AwsLambdaRequestHandler<Object, Object> requestHandler;
  private final Translator<HttpServletRequest, ApiGatewayRequest> translator;

  @Value("${aws.lambda.function.name}")
  private String functionName;

  @Autowired
  public ApiRequestHandlerController(GenericAwsLambdaRequestHandler requestHandler, ApiGatewayTranslator translator) {
    this.requestHandler = requestHandler;
    this.translator = translator;
  }

  @RequestMapping
  @ResponseBody
  public ResponseEntity<?> handleRequest(HttpServletRequest request) {
    final String queryString = request.getQueryString();
    LOGGER.info("Received request {}", queryString);

    final LambdaRuntimeContext ctx = LambdaRuntimeContext.newBuilder()
        .withFunctionName(functionName)
        .withAwsRequestId(AWS_REQUEST_ID)
        .build();

    final Object output = translator.from(request)
        .map(this::marshalToInputType)
        .map(input -> requestHandler.handleRequest(input, ctx))
        .orElseThrow(() -> new IllegalStateException("Lambda request handler returned empty or error response"));

    try {
      Method httpResponseBodyGetter = output.getClass().getMethod("getBody");
      return ResponseEntity.ok(Jackson.fromJsonString((String) httpResponseBodyGetter.invoke(output), JsonNode.class));
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      LOGGER.error("Error getting http lambda response body: {}", e.getMessage());
    }

    return ResponseEntity.ok(output);
  }

  private Object marshalToInputType(ApiGatewayRequest apiGatewayRequest) {
    final String requestAsJson = Jackson.toJsonString(apiGatewayRequest);
    return Jackson.fromJsonString(requestAsJson, requestHandler.getInputType());
  }
}
