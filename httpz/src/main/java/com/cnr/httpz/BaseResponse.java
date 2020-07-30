package com.cnr.httpz;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    public StatusUnit status;

    public StatusUnit getStatus() {
        return status;
    }

    public void setStatus(StatusUnit status) {
        this.status = status;
    }

}