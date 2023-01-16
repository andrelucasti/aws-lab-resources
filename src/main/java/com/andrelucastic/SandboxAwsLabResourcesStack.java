package com.andrelucastic;

import com.andrelucastic.clusters.ecs.ClusterStack;
import com.andrelucastic.clusters.ecs.EcsTaskExecutionRole;
import com.andrelucastic.clusters.ecs.EcsTaskRole;
import com.andrelucastic.clusters.ecs.SecurityEcsStack;
import com.andrelucastic.loadbalancer.LoadBalancerStack;
import com.andrelucastic.vpc.VPCStack;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class SandboxAwsLabResourcesStack extends Stack {
    private static final String VPC_NAME = "aws-lab-resources";
    public SandboxAwsLabResourcesStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        new VPCStack(this, "vpc-stack", props, Environment.SANDBOX, VPC_NAME)
                .create();

        new LoadBalancerStack(this, "loadbalancer-stack", props, Environment.SANDBOX, VPC_NAME)
                .create();

        new ClusterStack(this, "cluster-stack", props, Environment.SANDBOX, VPC_NAME)
                .create();

        new SecurityEcsStack(this, "security-ecs-stack", props, Environment.SANDBOX, VPC_NAME)
                .create();

        new EcsTaskExecutionRole(this, "ecs-task-execution-role", props, Environment.SANDBOX)
                .create();

        new EcsTaskRole(this, "ecs-task-role", props, Environment.SANDBOX)
                .create();
    }
}
