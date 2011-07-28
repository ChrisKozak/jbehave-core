package org.jbehave.core.embedder;

import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.OutcomesTable;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.AbstractStepResult;

public class SoftFailure extends AbstractStepResult{
    public SoftFailure(String step, UUIDExceptionWrapper throwable) {
        super(step, new UUIDExceptionWrapper(throwable));
    }

    public void describeTo(StoryReporter reporter) {
        if (throwable.getCause() instanceof OutcomesTable.OutcomesFailed) {
            reporter.failedOutcomes(parametrisedStep(), ((OutcomesTable.OutcomesFailed) throwable.getCause()).outcomesTable());
        } else {
            reporter.failed(parametrisedStep(), throwable);
        }
    }

}
