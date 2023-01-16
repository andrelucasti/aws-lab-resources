package com.andrelucastic;

public enum TargetType {
    IP;

    public String value(){
        return this.name().toLowerCase();
    }

}
