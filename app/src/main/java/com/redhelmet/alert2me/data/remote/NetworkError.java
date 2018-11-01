package com.redhelmet.alert2me.data.remote;

import com.google.gson.annotations.SerializedName;

public class NetworkError {
    public boolean success;
    public String name;
    public String created;
    @SerializedName("errorMessage")
    public String errorMessage;
}
