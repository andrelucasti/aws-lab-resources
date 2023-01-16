package com.andrelucastic.clusters.ecs;

import com.andrelucastic.AwsResource;
import com.andrelucastic.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.CfnSecurityGroup;
import software.amazon.awscdk.services.ec2.CfnSecurityGroupIngress;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcLookupOptions;
import software.constructs.Construct;

public class SecurityEcsStack extends Stack implements AwsResource {

    private final Environment environment;
    private final String vpcName;

    public SecurityEcsStack(final Construct scope,
                            final String id,
                            final StackProps props,
                            final Environment environment,
                            final String vpcName) {
        super(scope, id, props);

        this.environment = environment;
        this.vpcName = vpcName;
    }

    public void create(){
        IVpc vpc = Vpc.fromLookup(this, "aws-resources-vpc-stack", VpcLookupOptions.builder().vpcName(vpcName).isDefault(false).build());

        CfnSecurityGroup ecsSecurityGroup = CfnSecurityGroup.Builder.create(this, "ecsSecurityGroup")
                .groupName("ecsSecurityGroup")
                .vpcId(vpc.getVpcId())
                .groupDescription("SecurityGroup for the ECS containers")
                .build();

        CfnSecurityGroupIngress.Builder.create(this, "ecsIngressFromSelf")
                .sourceSecurityGroupId(ecsSecurityGroup.getAttrGroupId())
                .groupId(ecsSecurityGroup.getAttrGroupId())
                .ipProtocol("-1")
                .build();


        ISecurityGroup loadbalancerSecGroup = SecurityGroup.fromLookupByName(this, "loadbalancerSecGroup", (getResourceName(environment, "loadbalancerSecGroup")), vpc);
        CfnSecurityGroupIngress.Builder.create(this, "ecsIngressFromLoadbalancer")
                .ipProtocol("-1")
                .sourceSecurityGroupId(loadbalancerSecGroup.getSecurityGroupId())
                .groupId(ecsSecurityGroup.getAttrGroupId())
                .build();

    }
}