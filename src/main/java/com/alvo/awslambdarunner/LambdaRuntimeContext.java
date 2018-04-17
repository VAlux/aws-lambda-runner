package com.alvo.awslambdarunner;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class LambdaRuntimeContext implements Context {

  private String awsRequestId;
  private String logGroupName;
  private String logStreamName;
  private String functionName;
  private String functionVersion;
  private String invokedFunctionArn;
  private int remainingTimeInMillis;
  private int memoryLimitInMB;

  @Override
  public String getAwsRequestId() {
    return this.awsRequestId;
  }

  @Override
  public String getLogGroupName() {
    return logGroupName;
  }

  @Override
  public String getLogStreamName() {
    return logStreamName;
  }

  @Override
  public String getFunctionName() {
    return functionName;
  }

  @Override
  public String getFunctionVersion() {
    return functionVersion;
  }

  @Override
  public String getInvokedFunctionArn() {
    return invokedFunctionArn;
  }

  @Override
  public int getRemainingTimeInMillis() {
    return remainingTimeInMillis;
  }

  @Override
  public int getMemoryLimitInMB() {
    return memoryLimitInMB;
  }

  @Override
  public CognitoIdentity getIdentity() {
    return null;
  }

  @Override
  public ClientContext getClientContext() {
    return null;
  }

  @Override
  public LambdaLogger getLogger() {
    return null;
  }
  
  public static Builder newBuilder() {
    return new LambdaRuntimeContext().new Builder();
  }

  public class Builder {
    public Builder withAwsRequestId(String awsRequestId) {
      LambdaRuntimeContext.this.awsRequestId = awsRequestId;
      return this;
    }

    public Builder withLogGroupName(String logGroupName) {
      LambdaRuntimeContext.this.logGroupName = logGroupName;
      return this;
    }

    
    public Builder withLogStreamName(String logStreamName) {
      LambdaRuntimeContext.this.logStreamName = logStreamName;
      return this;
    }

    
    public Builder withFunctionName(String functionName) {
      LambdaRuntimeContext.this.functionName = functionName;
      return this;
    }

    
    public Builder withFunctionVersion(String functionVersion) {
      LambdaRuntimeContext.this.functionVersion = functionVersion;
      return this;
    }

    
    public Builder withInvokedFunctionArn(String invokedFunctionArn) {
      LambdaRuntimeContext.this.invokedFunctionArn = invokedFunctionArn;
      return this;
    }

    
    public Builder withRemainingTimeInMillis(int remainingTimeInMillis) {
      LambdaRuntimeContext.this.remainingTimeInMillis = remainingTimeInMillis;
      return this;
    }

    
    public Builder withMemoryLimitInMB(int memoryLimitInMB) {
      LambdaRuntimeContext.this.memoryLimitInMB = memoryLimitInMB;
      return this;
    }

    public LambdaRuntimeContext build() {
      return LambdaRuntimeContext.this;
    }
  }
}
