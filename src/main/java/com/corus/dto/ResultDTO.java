/**
 * Created by dmitrigu on 22/09/2019.
 */
package com.corus.dto;

import com.corus.enums.ErrorCode;
import org.springframework.util.CollectionUtils;

import java.util.Collection;


public class ResultDTO {
    private ErrorCode status;
    private String message;
    private Collection<WeatherDTO> data;


    public ErrorCode getStatus() {
        return status;
    }

    public void setStatus(ErrorCode status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<WeatherDTO> getData() {
        return data;
    }

    public void setData(Collection<WeatherDTO> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        String res = "Status=" + status +
                ", message='" + message + '\'';
        if (!CollectionUtils.isEmpty(data)) {
            for (WeatherDTO we : data) {
                res += " " + we.toString();
            }
        }
        if (res.length() >= 1000) {
            res = res.substring(0, 1000) + "....";
        }
        return res;
    }
}