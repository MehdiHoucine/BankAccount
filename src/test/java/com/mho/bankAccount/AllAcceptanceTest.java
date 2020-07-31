package com.mho.bankAccount;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/FeaturesReport"},
        features = {"src/test/resources/feature"})
public class AllAcceptanceTest {
}
