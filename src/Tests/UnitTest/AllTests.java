package Tests.UnitTest;
import java.util.ArrayList;

import Tests.UnitTest.utils.PasswordEncryptorTest;
import Tests.UnitTest.utils.ValidMessage_test;
import junit.framework.*;


public class AllTests {
	@SuppressWarnings("serial")
   	private static final ArrayList<Class<?>> class_list = new ArrayList<Class<?>>(){{
   		add(UserTest.class);
   		add(UserHandlerTest.class);
   		add(SubForumTest.class);
   		add(PostTest.class);
   		add(ForumTest.class);
   		add(ValidMessage_test.class);
   		add(PasswordEncryptorTest.class);
   	}};
    public static Test suite(){
    	TestSuite suite= new TestSuite("integration_test_server");
    	for(Class<?> c : class_list)
    		suite.addTest(new TestSuite(c));
        return suite;
    }
    public static ArrayList<Class<?>> get_class_list(){
      	return AllTests.class_list;
    }   
}
