package com.alvo.awslambdarunner;

import java.util.Map;

public class HttpRequest {
  private boolean isTrusted;
  private String path;
  private Map<String, String> queryStringParameters;
  private Map<String, String> headers;
  private String body;

  public HttpRequest() {
    //Default constructor
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Map<String, String> getQueryStringParameters() {
    return queryStringParameters;
  }

  public void setQueryStringParameters(Map<String, String> queryStringParameters) {
    this.queryStringParameters = queryStringParameters;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public boolean isTrusted() {
    return isTrusted;
  }

  public void setIsTrusted(boolean trusted) {
    isTrusted = trusted;
  }

  @Override
  public String toString() {
    return "HttpRequest{" +
        "isTrusted=" + isTrusted +
        ", path='" + path + '\'' +
        ", queryStringParameters=" + queryStringParameters +
        ", headers=" + headers +
        ", body='" + body + '\'' +
        '}';
  }
}
