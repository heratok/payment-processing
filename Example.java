import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class Example {
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "ACb6d700a7351aa53450f7ab0a12fdc40c";
  public static final String AUTH_TOKEN = "3c2401fdcb70a9a8f004f935abfde545";

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
      new com.twilio.type.PhoneNumber("whatsapp:+573207403002"),
      new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
      "HXb5b62575e6e4ff6129ad7c8efe1f983e",
      "{"1":"12/1","2":"3pm"}",
      "Your message")
    .create();

    System.out.println(message.getSid());
  }
}