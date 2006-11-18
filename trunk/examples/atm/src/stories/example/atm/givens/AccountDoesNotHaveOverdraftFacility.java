/*
 * Created on 27-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package example.atm.givens;


import jbehave.core.mock.Mock;
import jbehave.core.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.World;


import example.domain.Account;


/** set overdraft limit = 0 */
public class AccountDoesNotHaveOverdraftFacility extends GivenUsingMiniMock {

    public void setUp(World world) {
        Mock account = (Mock) world.get("account", mock(Account.class));
        account.stubs("getOverdraftLimit").withNoArguments().will(returnValue(0));
    }

    public String getDescription() {
        return "set overdraft limit = 0";
    }
}