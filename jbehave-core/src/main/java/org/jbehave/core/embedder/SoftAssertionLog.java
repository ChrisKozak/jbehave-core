package org.jbehave.core.embedder;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

public class SoftAssertionLog {

    private final static ThreadLocal<List<Throwable>> softAssertions = new ThreadLocal<List<Throwable>>();

    public static <T> void ensureThat(T actual, Matcher<T> expected){
        ensureThat(null, actual, expected);
    }
    public static <T> void ensureThat(String reason, T actual, Matcher<T> expected){
        try {
            assertThat(reason, actual, expected);
        } catch (AssertionError x) {
            getAssertionLog().add(x);
        }
    }

    public static boolean hasAnyFailures() {
        return ! getAssertionLog().isEmpty();
    }

    private static List<Throwable> getAssertionLog() {
        if (softAssertions.get() == null)
            softAssertions.set(new ArrayList<Throwable>());
        return softAssertions.get();
    }

    public static Throwable getMostRecentFailure() {
        return getAssertionLog().get(getAssertionLog().size()-1);
    }

    public static void reset() {
        getAssertionLog().clear();
    }
}
