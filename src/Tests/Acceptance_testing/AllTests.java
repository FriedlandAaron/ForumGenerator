package Tests.Acceptance_testing;
import junit.framework.*;


public class AllTests {
    public static Test suite(){
        TestSuite suite= new TestSuite("ForumManagement");
        suite.addTest(new TestSuite(TestGeneral.class));
        suite.addTest(new TestSuite(TestSuperAdmin.class));
        suite.addTest( new TestSuite(TestAdmin.class));
        suite.addTest(new TestSuite(TestMember.class));
        suite.addTest(new TestSuite(TestGuest.class));
        //suite.addTest(new TestSuite(databaseTesting.class));
       // suite.addTest(new TestSuite(clientTesting.class));
        //suite.addTest(new TestSuite(serverTesting.class));
        

        return suite;
    }


}
