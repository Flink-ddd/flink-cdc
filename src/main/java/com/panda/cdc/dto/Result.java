package com.panda.cdc.dto;

/**
 * @Author muxh
 **/
public class Result<T> {
    private String resp_code;
    private String resp_msg;
    private T data;

    private boolean isSuccess;

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String msg) {
        this.resp_msg = resp_msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public static Result buildFailure(String errStatus, String errMessage) {
        Result response = new Result();
        response.setSuccess(false);
        response.setResp_code(errStatus);
        response.setResp_msg(errMessage);
        return response;
    }

    public static <T> Result<T> of(T data, String message) {
        Result<T> logisticsResponse = new Result();
        logisticsResponse.setSuccess(true);
        logisticsResponse.setResp_code("0");
        logisticsResponse.setResp_msg("ok");
        logisticsResponse.setData(data);
        return logisticsResponse;
    }

    public static Result buildSuccess(String message) {
        Result response = new Result();
        response.setSuccess(true);
        response.setResp_code("0");
        response.setResp_msg("ok");
        return response;
    }

    public boolean checkSuccess() {
        return Integer.valueOf(200).equals(resp_code);
    }

}
