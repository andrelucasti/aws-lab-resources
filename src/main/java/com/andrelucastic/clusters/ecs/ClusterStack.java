package com.andrelucastic.clusters.ecs;

import com.andrelucastic.AwsResource;
import com.andrelucastic.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcLookupOptions;
import software.amazon.awscdk.services.ecs.Cluster;
import software.constructs.Construct;

public class ClusterStack extends Stack implements AwsResource {
    private static final String CLUSTER_NAME = "aws-resource-lab";

    private final Environment environment;
    private final String vpcName;

    public ClusterStack(final Construct scope,
                        final String id,
                        final StackProps props,
                        final Environment environment,
                        final String vpcName) {

        super(scope, id, props);
        this.environment = environment;
        this.vpcName = vpcName;
    }

    public void create(){
        IVpc vpc = Vpc.fromLookup(this, "vpc", VpcLookupOptions.builder().vpcName(vpcName).isDefault(false).build());

        Cluster.Builder.create(this, "cluster")
                .vpc(vpc)
                .clusterName(getResourceName(environment, CLUSTER_NAME))
                .build();
    }

}
