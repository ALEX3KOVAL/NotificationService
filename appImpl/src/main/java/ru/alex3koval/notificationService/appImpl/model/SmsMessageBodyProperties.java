package ru.alex3koval.notificationService.appImpl.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;
import ru.alex3koval.notificationService.domain.vo.OtpReason;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SmsMessageBodyProperties {
    @JsonSetter("otpSubject")
    private String subject;
    @JsonSetter("otpText")
    private String body;

    public void replaceAnchors(String code, OtpReason reason) {
        subject = subject.replace("{otpReason}", reason.toString());
        body = body.replace("{code}", code);
    }
}
