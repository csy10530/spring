package com.usc.http;

public class Response {
  private boolean success;
  private int code;
  private String message;


  public Response() {
  }

  public Response(boolean success) {
    this.success = success;
    this.code = success ? 200 : 400;
    this.message = "message";
  }

  public Response(boolean success, String message) {
    this.success = success;
    this.message = message;
    this.code = success ? 200 : 400;
  }

  public Response(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Response{" +
            "success=" + success +
            ", code=" + code +
            ", message='" + message + '\'' +
            '}';
  }
}
