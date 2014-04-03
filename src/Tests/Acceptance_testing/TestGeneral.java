package Tests.Acceptance_testing;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGeneral extends ForumTest{
	Member admin;
	
   @Before
    public void setUp() {
        super.setUp();
        setUpAdmin();
    }
   @Before
   public void setUpAdmin() {
		this.admin = new Member("oren" , "pass",null);		
	}
	@After
    public void tearDown() {

    }
	
	
	@Test
	public void testAdd2PostsWithSameName() {
		
		assertFalse(false);
	}
	@Test
	public void testAdd2MembersWithSameName() {

		assertFalse(false);
	}
	@Test
	public void testAdd2ForumsWithSameName() {
		
		assertFalse(false);
	}
	@Test
	public void testEditPostByAnotherMember() {
		
		assertFalse(false);
	}
	@Test
	public void testBanMemberByNotAdmin() {
		
		assertFalse(false);
	}
	@Test
	public void testChangePolicy() {
		
		assertFalse(false);
	}
	@Test
	public void testCreateForum() {
		
		assertFalse(false);
	}
}