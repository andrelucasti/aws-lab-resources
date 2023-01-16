package com.andrelucastic;

import com.andrelucastic.clusters.ecs.ClusterStack;
import com.andrelucastic.clusters.ecs.EcsTaskExecutionRole;
import com.andrelucastic.clusters.ecs.EcsTaskRole;
import com.andrelucastic.clusters.ecs.SecurityEcsStack;
import com.andrelucastic.loadbalancer.LoadBalancerStack;
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

        new VpcStack(this, "vpc-stack", props, SANDBOX, vpcName)
                .create();

        new LoadBalancerStack(this, "loadbalancer-stack", props, SANDBOX, vpcName)
                .create();

        new ClusterStack(this, "cluster-stack", props, SANDBOX, vpcName)
                .create();

        new SecurityEcsStack(this, "security-ecs-stack", props, SANDBOX, vpcName)
                .create();

        new EcsTaskExecutionRole(this, "ecs-task-execution-role", props, SANDBOX)
                .create();

        new EcsTaskRole(this, "ecs-task-role", props, SANDBOX)
                .create();
    }
}
