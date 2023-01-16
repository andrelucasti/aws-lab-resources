package com.andrelucastic.clusters.ecs;

import com.andrelucastic.AwsResource;
import com.andrelucastic.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.constructs.Construct;

public class EcsTaskRole extends Stack implements AwsResource {

    private final Environment environment;
    public EcsTaskRole(final Construct scope,
                       final String id,
                       final StackProps props,
                       final Environment environment) {
        super(scope, id, props);
        this.environment = environment;
    }

    public Role create(){
        return Role.Builder.create(this, "ecsTaskRole")
                .roleName(getResourceName(environment, "aws-resource-ecsTaskRole"))
                .assumedBy(ServicePrincipal.Builder.create("ecs-tasks.amazonaws.com").build())
                .path("/")
                .build();
    }
}
