package BackEndTesting;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.FCI.SWE.Models.MessageEntity;



public class MessageEntityTest {
//  @Test(dataProvider = "dp")
  //public void f(ArrayList<MessageEntity> retMessages, String receiverMail, String senderMail ) {
  //}
	 private final LocalServiceTestHelper helper =
		      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	 @BeforeClass
	  public void setUp() {
	    helper.setUp();
	  }

	  @AfterClass
	  public void tearDown() {
	    helper.tearDown();
	  }

	  
	  
  @DataProvider (name = "dp")
  public Object[][] dp() {
	  
	  ArrayList<MessageEntity> excpectedResult = new ArrayList<>();
	  ArrayList<MessageEntity> excpectedResult1 = new ArrayList<>();
	  excpectedResult.add(new MessageEntity("eom_realmadrid@live.com", "hassan@yahoo.com", "Hi Hassan", "25/3/2015"));
	  excpectedResult.add(new MessageEntity("hassan@yahoo.com", "eom_realmadrid@live.com", "Hey Eslam", "25/3/2015"));
	  excpectedResult.add(new MessageEntity("eom_realmadrid@live.com", "hassan@yahoo.com", "Hi Hassan", "19/4/2015"));
    return new Object[][] {
      new Object[] {excpectedResult , 
    		  "hassan@yahoo.com","eom_realmadrid@live.com"},
      new Object[] { excpectedResult1, "hassan@yahoo.com","" },
      new Object[] { excpectedResult1, "","eom_realmadrid@live.com" },
      new Object[] { excpectedResult1, "","" },
      new Object[] { excpectedResult1, "123","" },
      new Object[] { excpectedResult1, "","123" },
      new Object[] { excpectedResult1, "123","123" },
    };
  }

  @Test(enabled=false)
  public void parseMessageInfo() {
    throw new RuntimeException("Test not implemented");
  }

  @Test (dataProvider = "dp")
  public void retrieveMessages(ArrayList<MessageEntity> retMessages, String receiverMail, String senderMail) {
  
    Assert.assertEquals(retMessages, MessageEntity.retrieveMessages("hassan@yahoo.com", "eom_realmadrid@live.com"));
  }

  @DataProvider (name = "saveMessageDP")
  public static Object [][]saveMessageDP() 
  {
	  return new Object [][] {
			  {true,"eom_realmadrid@live.com","hassan@yahoo.com","Content"},
			  {false,"","hassan@yahoo.com","Content"},
			  {false,"eom_realmadrid@live.com","","Content"},
			  {false,"eom_realmadrid@live.com","hassan@yahoo.com",""},
	  }; 
  }
  @Test(dataProvider = "saveMessageDP")
  public void saveMessage(boolean result,String senderMail , String receiverMail , String content) {
	
	  Assert.assertEquals(result, MessageEntity.saveMessage(senderMail, receiverMail, content));
	
  }
}
