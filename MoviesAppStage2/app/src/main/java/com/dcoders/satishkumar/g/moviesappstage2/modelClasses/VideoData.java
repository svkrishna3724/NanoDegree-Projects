package com.dcoders.satishkumar.g.moviesappstage2.modelClasses;

import java.io.Serializable;

public class VideoData implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String name;

    public VideoData(String name, String key) {
        this.name = name;
        this.key = key;
    }

    private String key;
}
