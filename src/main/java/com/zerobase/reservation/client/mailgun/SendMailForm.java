package com.zerobase.reservation.client.mailgun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;
}
