package com.carryjey.social.service.inf;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface EmailService {
    boolean sendEmail(String toMmail, String title, String content);
}
