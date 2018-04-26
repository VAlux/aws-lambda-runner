package com.alvo.awslambdarunner.translator;

import com.alvo.awslambdarunner.ApiGatewayRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ApiGatewayTranslator implements Translator<HttpServletRequest, ApiGatewayRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiGatewayTranslator.class);

  @Override
  public Optional<ApiGatewayRequest> from(HttpServletRequest request) {
    try {
      final ApiGatewayRequest apiGatewayRequest = new ApiGatewayRequest();
      apiGatewayRequest.setBody(extractRequestBody(request));
      apiGatewayRequest.setHeaders(extractHeadersMap(request));
      apiGatewayRequest.setHttpMethod(request.getMethod());
      apiGatewayRequest.setPath(request.getServletPath());
      apiGatewayRequest.setQueryStringParameters(extractQueryStringParameters(request));
      apiGatewayRequest.setPathParameters(extractPathParameters(request));
      return Optional.of(apiGatewayRequest);
    } catch (IOException e) {
      LOGGER.error("error translating HttpServletRequest to ApiGatewayRequest: {}", e.getMessage());
      return Optional.empty();
    }
  }

  private Map<String, String> extractQueryStringParameters(final HttpServletRequest request) {
    final String queryString = request.getQueryString();
    if (queryString != null) {
      return Arrays.stream(queryString.split("&"))
          .map(parameter -> parameter.split("=", 2))
          .collect(Collectors.toMap(splitted -> splitted[0], splitted -> splitted[1]));
    } else {
      return Collections.emptyMap();
    }
  }

  private Map<String, String> extractPathParameters(final HttpServletRequest request) {
    return request.getParameterMap().entrySet().stream()
        .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), flattenEntryValue(entry)))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private String flattenEntryValue(Map.Entry<String, String[]> entry) {
    return Arrays
        .stream(entry.getValue())
        .collect(Collectors.joining(","));
  }

  private String extractRequestBody(final HttpServletRequest request) throws IOException {
    return IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
  }

  private Map<String, String> extractHeadersMap(final HttpServletRequest request) {
    final Map<String, String> headersMap = new HashMap<>();
    final Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      final String headerName = headerNames.nextElement();
      final String headerContent = request.getHeader(headerName);
      headersMap.put(headerName, headerContent);
    }
    return headersMap;
  }
}
