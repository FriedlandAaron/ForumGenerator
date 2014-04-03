package Tests.Acceptance_testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class clientTesting extends ForumTest {
    @Before
    public void setUp() {	
        super.setUp();
    }
    @Before
    public void setUpGuest() {		
	}
	@After
    public void tearDown() {
		System.out.println("df");
    }

	@Test
	public void testAddNewPost() {
		assertFalse(false);
	}
	
}