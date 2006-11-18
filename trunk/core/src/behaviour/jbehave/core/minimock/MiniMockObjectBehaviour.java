/*
 * Created on 17-Dec-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.minimock;

import jbehave.core.Ensure;
import jbehave.core.exception.VerificationException;
import jbehave.core.mock.Mock;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MiniMockObjectBehaviour extends UsingMiniMock {
    public interface Foo {
        void doSomething();
        void doSomething(String arg);
        void doSomethingElse();
    }
    
    public void shouldCreateObjectThatCanBeCastToTheCorrectType() throws Exception {
        // given...
        Mock mock = MiniMockObject.mock(Foo.class, "foo");

        // verify...
        Ensure.that(mock instanceof Foo);
    }
    
    public void shouldCreateObjectWithInterfaceFromSystemClassLoader() throws Exception {
        // given
        Mock mock = MiniMockObject.mock(Comparable.class, "comparable");
        
        // verify
        Ensure.that(mock instanceof Comparable);
    }
    
    public void shouldSucceedWhenMethodCalledWithExpectedArgument() {
        Mock mock = MiniMockObject.mock(Foo.class, "foo");
        
        mock.expects("doSomething").with(eq("A"));
        
        ((Foo)mock).doSomething("A");
        
        try {
            mock.verify();
        } catch (VerificationException ve) {
            Ensure.that(false);
        }
    }
    
    public void shouldFailOnVerifyWhenMethodCalledWithExpectedThenUnexpectedArgument() {
        Mock mock = MiniMockObject.mock(Foo.class, "foo");
        
        mock.expects("doSomething").with(eq("A"));
        
        ((Foo)mock).doSomething("A");
        
        boolean skippedThis = true;
        
        try {
            ((Foo)mock).doSomething("B");
            skippedThis = false;
        } catch (VerificationException ve) {
            Ensure.that(ve.getMessage().equals("Unexpected arguments for foo.doSomething"));
        }
        Ensure.that(skippedThis);
    }
    
    public void shouldSucceedOnVerifyWhenMethodCalledWithExpectedArgumentThenOtherMethodCalled() {
        Mock mock = MiniMockObject.mock(Foo.class, "foo");
        mock.expects("doSomething");
        
        ((Foo)mock).doSomething();
        ((Foo)mock).doSomethingElse();
        
        mock.verify();
    }
    
}