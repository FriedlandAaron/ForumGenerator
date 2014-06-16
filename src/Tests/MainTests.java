package Tests;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestSuite;


public class MainTests {

	@SuppressWarnings("serial")
	private static final ArrayList<Test> Test_list = new ArrayList<Test>(){{
		add(Tests.Acceptance_testing.AllTests.suite());
		add(Tests.UnitTest.AllTests.suite());
		add(Tests.integration_test.network.AllTests.suite());
		add(Tests.integration_test.server.AllTests.suite());
		//add(Tests.Database.AllTests.suite());
	}};
    public static Test suite(){
    	TestSuite suite =  new TestSuite("MainTests_ForumManagement"); 
        for(Test test : Test_list)
        	suite.addTest(test);
        return suite;
    }    
}

