package com.zerobase.reservation.client;

import com.zerobase.reservation.client.mailgun.SendMailForm;
import feign.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping("sandboxe93ffacfad714a4d8422d462ff844fb2.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);
}
