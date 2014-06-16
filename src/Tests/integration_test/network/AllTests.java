package Tests.integration_test.network;
import java.util.ArrayList;

import junit.framework.*;


public class AllTests {
   	@SuppressWarnings("serial")
   	private static final ArrayList<Class<?>> class_list = new ArrayList<Class<?>>(){{
   		add(integration_test_1.class);
   		add(integration_test_2.class);
   		add(integration_test_3.class);
   		add(integration_test_4.class);
   		add(integration_test_5.class);
   		add(integration_test_6.class);
   		add(integration_test_7.class);
   		add(integration_test_8.class);
   		add(integration_test_9.class);
   	}};
    public static Test suite(){
    	TestSuite suite= new TestSuite("integration_test_network");
    	for(Class<?> c : class_list)
    		suite.addTest(new TestSuite(c));
        return suite;
    }
    public static ArrayList<Class<?>> get_class_list(){
      	return AllTests.class_list;
    }   
}
