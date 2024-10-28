package com.example.springCloud.util;



import java.util.ArrayList;

/**
 * @Description 测试发送邮件
 * @Author 林泽鸿
 * @Date 2020/4/3 20:55
 */
public class TestMail {
    public static void main(String[] args) {
        ArrayList<String> emailArray = new ArrayList<>();
        //测试，收取邮件的邮箱，可以填写自己的发送邮件的邮箱
        emailArray.add("1181367274@qq.com");

        MailSenderUtil.sendMailToUserArray(emailArray,MailConst.NOTIFICATION_MAIL_TITLE,MailConst.NOTIFICATION_MAIL_CONTENT);
    }
}
    