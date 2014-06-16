package Tests.Acceptance_testing;
import java.util.ArrayList;

import junit.framework.*;


public class AllTests {
	@SuppressWarnings("serial")
	private static final ArrayList<Class<?>> class_list = new ArrayList<Class<?>>(){{
		add(TestGeneral.class);
		add(TestSuperAdmin.class);
		add(TestAdmin.class);
		add(TestMember.class);
		add(TestGuest.class);
		add(databaseTesting.class);
		add(clientTesting.class);
		add(serverTesting.class);
	}};
    public static Test suite(){
        TestSuite suite= new TestSuite("Acceptance_testing");
        for(Class<?> c : class_list)
        	suite.addTest(new TestSuite(c));
        return suite;
    }
    public static ArrayList<Class<?>> get_class_list(){
    	return AllTests.class_list;
    }
}
