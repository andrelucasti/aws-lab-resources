package com.andrelucastic;

import com.andrelucastic.clusters.ecs.ClusterStack;
import com.andrelucastic.clusters.ecs.EcsTaskExecutionRole;
import com.andrelucastic.clusters.ecs.EcsTaskRole;
import com.andrelucastic.clusters.ecs.SecurityEcsStack;
import com.andrelucastic.vpc.VpcStack;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class SandboxAwsLabResourcesStack extends Stack implements AwsResource {
    private static final String VPC_NAME = "aws-resources-lab";
    private static final Environment SANDBOX = Environment.SANDBOX;
    public SandboxAwsLabResourcesStack(final Construct scope, final String id, final StackProps props) {

        super(scope, id, props);

        var vpcName = getResourceName(SANDBOX, VPC_NAME);

        new VpcStack(this, "1-vpc-stack", props, SANDBOX, vpcName)
                .create();

        new ClusterStack(this, "3-cluster-stack", props, SANDBOX, vpcName)
                .create();

        new SecurityEcsStack(this, "4-security-ecs-stack", props, SANDBOX, vpcName)
                .create();

        new EcsTaskExecutionRole(this, "5-ecs-task-execution-role", props, SANDBOX)
                .create();

        new EcsTaskRole(this, "6-ecs-task-role", props, SANDBOX)
                .create();
    }
}
