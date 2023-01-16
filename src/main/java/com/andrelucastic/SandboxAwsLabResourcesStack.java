package com.andrelucastic;

import com.andrelucastic.vpc.VPCStack;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class SandboxAwsLabResourcesStack extends Stack {
    public SandboxAwsLabResourcesStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        new VPCStack(this, "aws-resources-vpc-stack", props, Environment.SANDBOX, "aws-lab-resources")
                .create();

    }
}
