package com.andrelucastic.vpc;

import com.andrelucastic.Environment;
import org.jetbrains.annotations.Nullable;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

import java.util.List;

public class VPCStack extends Stack {
    private final Environment environment;
    private final String vpcName;

    public VPCStack(final Construct scope,
                    final String id,
                    final StackProps props,
                    final Environment environment,
                    final String vpcName) {

        super(scope, id, props);
        this.environment = environment;
        this.vpcName = vpcName;
    }

    public void create(){
        SubnetConfiguration publicSubnet = SubnetConfiguration.builder()
                .subnetType(SubnetType.PUBLIC)
                .name(environment.value().concat("-").concat("publicSubnet"))
                .build();

        SubnetConfiguration isolateSubnet = SubnetConfiguration.builder()
                .subnetType(SubnetType.PRIVATE_ISOLATED)
                .name(environment.value().concat("-").concat("isolateSubnet"))
                .build();

        Vpc.Builder.create(this, "vpc")
                .vpcName(vpcName)
                .natGateways(0)
                .maxAzs(2)
                .subnetConfiguration(List.of(publicSubnet, isolateSubnet))
                .build();
    }
}
