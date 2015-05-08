package FrontEndTesting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.FCI.SWE.Models.MessageEntity;
import com.google.appengine.api.mail.MailService.Message;

public class MessageServicesTest {
 
  @DataProvider(name="DP")
  public Object[][] dp() {
    
	  ArrayList<MessageEntity> excpectedResult = new ArrayList<>();
	  
	  return new Object[][] {
		      new Object[] {excpectedResult , "Eslam@hotmail.com","mostafa@hotmail.com"}
	  
    };
  }

  @Test(dataProvider = "DP")
  public void RetriveMessageHistory(ArrayList<MessageEntity>expectedResult,String receiverMail,String senderMail) {
	  String serviceUrl = "http://localhost:8888/rest/RetriveMessageHistory";
		
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "receiverMail=" + receiverMail+"&senderMail=" + senderMail ;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line ,retjson= "";
			Map<String, ArrayList<MessageEntity>> map = new HashMap<String, ArrayList<MessageEntity>>();
			while ((line = reader.readLine()) != null) {
				retjson+=line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			JSONArray retArr = (JSONArray) parser.parse(retjson);
			ArrayList<MessageEntity> retMessages = new ArrayList<>();
			for (int i = 0; i < retArr.size(); ++i) {
				JSONObject object  = (JSONObject) retArr.get(i);
				retMessages.add(MessageEntity.parseMessageInfo(object.toJSONString()));
				
			}
		
			System.out.println("EXP->"+expectedResult);
			 System.out.println("Act->"+retMessages);
			if(expectedResult.equals(retMessages))
			{System.out.println("True");}

				Assert.assertEquals(expectedResult, retMessages);
		}catch(Exception e){
			
		}

	  
	
  }

  @DataProvider(name = "dp2")
  public Object[][] dp2() {
    
	  String excpectedResult="";
	  
	  return new Object[][] {
		      new Object[] {excpectedResult , "Eslam@hotmail.com","mostafa@hotmail.com","first messageto be send"}
	  
    };
  }
  
  
  
  @Test(dataProvider = "dp2")
  public void sendMessage(String expectedResult,String senderMail,String recieverMail,String content) {
    
	  String serviceUrl = "http://localhost:8888/rest/sendMessage";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "senderMail=" + senderMail + "&recieverMail=" + recieverMail
					+ "&messageContents=" + content;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();

	  //Assert.assertEquals(expectedResult, senderMail,recieverMail,content);
	  
		}catch(Exception e)
		{
			
		}
	  
	 
  }
}
