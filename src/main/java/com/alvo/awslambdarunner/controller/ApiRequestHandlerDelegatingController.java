package com.alvo.awslambdarunner.controller;

import com.alvo.awslambdarunner.GenericAwsLambdaRequestHandler;
import com.alvo.awslambdarunner.HttpRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

@Controller
public class ApiRequestHandlerDelegatingController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestHandlerDelegatingController.class);

  private final GenericAwsLambdaRequestHandler requestHandler;

  @Autowired
  public ApiRequestHandlerDelegatingController(GenericAwsLambdaRequestHandler requestHandler) {
    this.requestHandler = requestHandler;
  }

  @RequestMapping
  public @ResponseBody Object handleRequest(HttpServletRequest request) throws IOException {
    final String queryString = request.getQueryString();
    final String requestContent = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
    LOGGER.info("Received request with query: {} and body: {}", requestContent, queryString);
    final HttpRequest httpRequest = new HttpRequest();
    httpRequest.setBody(requestContent);
    httpRequest.setIsTrusted(true);
    httpRequest.setPath(request.getPathTranslated());

//    return requestHandler.getAwsLambdaRequestHandler().handleRequest(request, null);
    return null;
  }
}
