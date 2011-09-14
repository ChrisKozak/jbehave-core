package org.jbehave.core.embedder;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.jbehave.core.embedder.SoftAssertionLog.ensureThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.containsString;

public class SoftAssertionLogBehaviour {

    @Before
    public void reset() {
        SoftAssertionLog.reset();
    }

    @Test
    public void shouldAccumulateErrorsInTheAssertionLog() {
        assertThat(SoftAssertionLog.hasAnyFailures(), is(false));
        SoftAssertionLog.ensureThat(true, is(false));
        assertThat(SoftAssertionLog.hasAnyFailures(), is(true));
    }

    @Test
    public void shouldAccumulateErrorsWithExplanationsInTheAssertionLog() {
        assertThat(SoftAssertionLog.hasAnyFailures(), is(false));
        SoftAssertionLog.ensureThat("Failure Explanation", true, is(false));
        assertThat(SoftAssertionLog.getMostRecentFailure().getMessage(), containsString("Failure Explanation"));
    }

    @Test
    public void shouldResetTheAssertionLog() {
        SoftAssertionLog.ensureThat(true, is(false));
        assertThat(SoftAssertionLog.hasAnyFailures(), is(true));
        SoftAssertionLog.reset();
        assertThat(SoftAssertionLog.hasAnyFailures(), is(false));
    }

    @Test
    public void shouldRetrieveMostRecentFailure() {
        SoftAssertionLog.ensureThat("Failure Explanation1", true, is(false));
        SoftAssertionLog.ensureThat("Failure Explanation2", true, is(false));

        assertThat(SoftAssertionLog.getMostRecentFailure().getMessage(), containsString("Failure Explanation2"));
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenRetrievingFailureFromEmptyLog() {
        try {
            SoftAssertionLog.getMostRecentFailure();
            fail("retrieving a failure from an empty assertion log should throw an error");
        } catch (RuntimeException x) {
            assertThat(x.getMessage(), containsString("Soft assertion log is empty!"));
        }
    }

    @Test
    public void shouldNotPukeInaMultiThreadedSituation() throws InterruptedException, ExecutionException {
        List<Future<List<Throwable>>> futures = Executors.newFixedThreadPool(2).invokeAll(asList(new TestThread("Thread 1"), new TestThread("Thread 2")));

        List<Throwable> assertionLog1 = futures.get(0).get();
        List<Throwable> assertionLog2 = futures.get(1).get();

        assertThat(assertionLog1.size(), is(1));
        assertThat(assertionLog2.size(), is(1));
        assertThat(assertionLog2.get(0).getMessage(), is(not(assertionLog1.get(0).getMessage())));

        SoftAssertionLog.reset();

        assertThat(assertionLog1.size(), is(1));
        assertThat(assertionLog2.size(), is(1));
        assertThat(assertionLog2.get(0).getMessage(), is(not(assertionLog1.get(0).getMessage())));
    }

    private class TestThread implements Callable<List<Throwable>> {

        private String threadName;

        public TestThread(String threadName) {
            this.threadName = threadName;
        }

        public List<Throwable> call() {
            ensureThat(threadName, true, is(false));
            return SoftAssertionLog.softAssertions.get();
        }
    }
}
