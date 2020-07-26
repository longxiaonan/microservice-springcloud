package com.itmuch.cloud.mail;

/**
 * @ClassName MailService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/2/15 0015 20:51
 */
public interface MailService {
    void sendSimpleMail(String to, String subject, String content);
}
