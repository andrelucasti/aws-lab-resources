package com.andrelucastic;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class AwsLabResourcesApp {
    public static void main(final String[] args) {
        App app = new App();
        Account account = new Account(app);

        String accountId = account.getAccountId();
        String region = account.getRegion();

        executeStacks(app, accountId, region);

        app.synth();
    }
    private static void executeStacks(App app,
                                      String accountId,
                                      String region) {

        new SandboxAwsLabResourcesStack(app, "sandboxAwsLabResources-stack",
                StackProps.builder().env(getEnv(accountId, region))
                        .build());

        app.synth();
    }

    public static software.amazon.awscdk.Environment getEnv(final String accountId,
                                                            final String region){

        return Environment.builder()
                .account(accountId)
                .region(region)
                .build();

    }
}

