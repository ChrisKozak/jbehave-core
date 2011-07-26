package org.jbehave.examples.trader.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

import static org.hamcrest.core.Is.is;
import static org.jbehave.core.embedder.SoftAssertionLog.ensureThat;
import static org.junit.Assert.fail;

public class SandpitSteps extends Steps {

	@Given("I do nothing")
	public void doNothing() {
	}

	@Then("I fail")
	public void doFail() {
		fail("I failed!");
	}

	@Then("I pass")
	public void doPass() {
	}

    @Then("I fail softly")
    public void doFailSoftly(){
        ensureThat("I failed softly", true, is(false));
    }

    @Then("I fail hard")
    public void doFailHard(){
        fail("Runtime Exception");
    }
}