package com.corus.dto;

import java.io.Serializable;
/**
 * Created by dmitrigu on 22/09/2019.
 */
public class UserDTO implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
