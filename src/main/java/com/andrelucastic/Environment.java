package com.andrelucastic;

public enum Environment {
    SANDBOX,
    PRODUCTION
    ;

   public String value(){
       return this.name().toLowerCase();
   }
}
