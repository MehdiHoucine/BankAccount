package com.mho.bankAccount.acceptance.configuration;

import io.cucumber.java8.En;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
        RepositoriesConfiguration.class,
})
public class ContextConfigurationTesting implements En {
}
