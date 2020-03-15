package com.techprimers.db.uitls;

import com.sendgrid.*;
import java.io.IOException;
import java.util.Random;

public class MailClient {
    public String sendMessage(String mailId) throws IOException {
        Email from = new Email("mikestark605@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email(mailId);
        String otp = String.valueOf(gen());
        Content content = new Content("text/plain", "Your OTP for voting is : " + otp);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }

        return  otp;
    }

    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }
}
