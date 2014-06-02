package Tests.Acceptance_testing;

import junit.framework.TestCase;

public class ForumTest extends TestCase{
	protected BridgeForum bridge;

	

    public void setUp() {
        this.bridge=Driver.getBridge();
        setUpMembers();
        setUpAdministrators();
        setUpPosts();
    }

    private void setUpPosts() {

		
	}
    
    

	private void setUpMembers() {
   	//	for(String[] userInfo : DBData.users) {
   		//	this.bridge.registerNewTechnicalAdviser(userInfo[USER_USER], userInfo[USER_PASS]);
   		//}
   	}

    private void setUpAdministrators() {
     //   for(String[] studentInfo : DBData.students) {
      //     this.bridge.addNewStudent(studentInfo[USER_USER], studentInfo[USER_PASS]);
     //}
    }



}
