package com.andrelucastic;

public interface AwsResource {

    default String getResourceName(final Environment environment, final String resourceName){
        return environment.value().concat("-").concat(resourceName);
    }
}
