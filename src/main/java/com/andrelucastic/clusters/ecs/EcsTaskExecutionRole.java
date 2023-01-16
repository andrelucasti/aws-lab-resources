package com.andrelucastic.clusters.ecs;

import com.andrelucastic.AwsResource;
import com.andrelucastic.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyDocument;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.constructs.Construct;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EcsTaskExecutionRole extends Stack implements AwsResource {
    
    private final Environment environment;
    public EcsTaskExecutionRole(final Construct scope,
                                final String id,
                                final StackProps props,
                                final Environment environment) {
        super(scope, id, props);
        this.environment = environment;
    }

    public Role create(){
        return Role.Builder.create(this, "ecsTaskExecutionRole")
                .roleName(getResourceName(environment, "aws-resouce-ecsTaskExecutionRole"))
                .assumedBy(ServicePrincipal.Builder.create("ecs-tasks.amazonaws.com").build())
                .path("/")
                .inlinePolicies(Map.of(
                        getResourceName(environment, "ecsTaskExecutionRolePolicy"),
                        PolicyDocument.Builder.create()
                                .statements(Collections.singletonList(PolicyStatement.Builder.create()
                                        .effect(Effect.ALLOW)
                                        .resources(Collections.singletonList("*"))
                                        .actions(List.of(
                                                "ecr:GetAuthorizationToken",
                                                "ecr:BatchCheckLayerAvailability",
                                                "ecr:GetDownloadUrlForLayer",
                                                "ecr:BatchGetImage",
                                                "logs:CreateLogStream",
                                                "logs:PutLogEvents"))
                                        .build()))
                                .build()))
                .build();
    }
}
