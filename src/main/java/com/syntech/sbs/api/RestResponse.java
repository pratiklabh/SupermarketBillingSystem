package com.syntech.sbs.api;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestResponse {

    private String success;
    private String code;
    private String message;
    private Object result;

    public RestResponse() {
    }

    public RestResponse(String success, String code, String message, Object result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
     public static Response responseBuilder(String success, String code, String message, JsonValue result) {
        JsonObject json = Json.createObjectBuilder()
                .add("success", success)
                .add("code", code)
                .add("message", message)
                .add("result", result == null ? JsonValue.EMPTY_JSON_OBJECT : result)
                .build();

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(json)
                .build();
    }
}