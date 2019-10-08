package com.wdzg.benc.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: liubin
 * Date: 2018-01-22
 * Time: 上午 11:17
 */
public class SmsUtils {

    /**
     * 发送短信
     * @param mobile 发送号码（单个号码）
     * @param content 发送内容
     * @return
     */
    public static boolean sendSms(String smsUrl,String smsAccount,String smsPassword,String mobile, String content) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsUrl +"?method=Submit");
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] data = { new NameValuePair("account", smsAccount), new NameValuePair("password", smsPassword),
                new NameValuePair("destmobile", mobile), new NameValuePair("msgText", content) };

        method.setRequestBody(data);

        try {
            client.executeMethod(method);
            // 返回值
            String codeStr = method.getResponseBodyAsString();
            int code = Integer.parseInt(codeStr);
            // 提交结果描述
            String msg = "";
            if (code > 0) {
                msg = "提交成功！"+ msg;
                return true;
            } else if (code == -1) {
                msg = "余额不足！"+ msg;

            } else if (code == -2) {
                msg = "帐号或密码错误！"+ msg;

            } else if (code == -3) {
                msg = "连接服务商失败！"+ msg;

            } else if (code == -4) {
                msg = "超时！"+ msg;

            } else if (code == -5) {
                msg = "其他错误，一般为网络问题，IP受限等！"+ msg;

            } else if (code == -6) {
                msg = "短信内容为空！"+ msg;

            } else if (code == -7) {
                msg = "目标号码为空！"+ msg;

            } else if (code == -8) {
                msg = "用户通道设置不对，需要设置三个通道！"+ msg;

            } else if (code == -9) {
                msg = "捕获未知异常！"+ msg;

            } else if (code == -10) {
                msg = "超过最大定时时间限制！"+ msg;

            } else if (code == -11) {
                msg = "目标号码在黑名单里！"+ msg;

            } else if (code == -13) {
                msg = "没有权限使用该网关！"+ msg;

            } else if (code == -14) {
                msg = "找不到对应的Channel ID！"+ msg;

            } else if (code == -17) {
                msg = "没有提交权限，客户端帐号无法使用接口提交。或非绑定IP提交！"+ msg;

            } else if (code == -18) {
                msg = "提交参数名称不正确或确少参数！"+ msg;

            } else if (code == -19) {
                msg = "必须为POST提交！"+ msg;

            } else if (code == -20) {
                msg = "超速提交(一般为每秒一次提交)！"+ msg;

            } else if (code == -21) {
                msg = "扩展参数不正确！"+ msg;

            } else if (code == -22) {
                msg = "Ip 被封停！"+ msg;
            }

            System.out.println("code="+ code +" msg="+ msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 发送短信
     * @param mobile 发送号码（单个号码）
     * @param content 发送内容
     * @return
     */
    public static String sendSmsReturnCode(String smsUrl,String smsAccount,String smsPassword,String mobile, String content) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsUrl +"?method=Submit");
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] data = { new NameValuePair("account", smsAccount), new NameValuePair("password", smsPassword),
                new NameValuePair("destmobile", mobile), new NameValuePair("msgText", content) };

        method.setRequestBody(data);

        String codeStr = "" ;
        int code = 0;
        String msg = "";// 提交结果描述
        try {
            client.executeMethod(method);
            codeStr = method.getResponseBodyAsString();// 返回值
            code = Integer.parseInt(codeStr);
        } catch (Exception e) {
            e.printStackTrace();
            return "-23,执行发送短信功能方法出错";
        }
        if (code > 0) {
            msg = "提交成功！"+ msg;
        } else if (code == -1) {
            msg = "余额不足！"+ msg;

        } else if (code == -2) {
            msg = "帐号或密码错误！"+ msg;

        } else if (code == -3) {
            msg = "连接服务商失败！"+ msg;

        } else if (code == -4) {
            msg = "超时！"+ msg;

        } else if (code == -5) {
            msg = "其他错误，一般为网络问题，IP受限等！"+ msg;

        } else if (code == -6) {
            msg = "短信内容为空！"+ msg;

        } else if (code == -7) {
            msg = "目标号码为空！"+ msg;

        } else if (code == -8) {
            msg = "用户通道设置不对，需要设置三个通道！"+ msg;

        } else if (code == -9) {
            msg = "捕获未知异常！"+ msg;

        } else if (code == -10) {
            msg = "超过最大定时时间限制！"+ msg;

        } else if (code == -11) {
            msg = "目标号码在黑名单里！"+ msg;

        } else if (code == -13) {
            msg = "没有权限使用该网关！"+ msg;

        } else if (code == -14) {
            msg = "找不到对应的Channel ID！"+ msg;

        } else if (code == -17) {
            msg = "没有提交权限，客户端帐号无法使用接口提交。或非绑定IP提交！"+ msg;

        } else if (code == -18) {
            msg = "提交参数名称不正确或确少参数！"+ msg;

        } else if (code == -19) {
            msg = "必须为POST提交！"+ msg;

        } else if (code == -20) {
            msg = "超速提交(一般为每秒一次提交)！"+ msg;

        } else if (code == -21) {
            msg = "扩展参数不正确！"+ msg;

        } else if (code == -22) {
            msg = "Ip 被封停！"+ msg;
        }

//		System.out.println("code="+ code +" msg="+ msg);

        return codeStr+","+msg;

    }

    /**
     * 查询余额（剩余条数）
     * @return
     */
    public static int getBalance(String smsUrl,String smsAccount,String smsPassword) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsUrl +"?method=GetNum");
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] data = { new NameValuePair("account", smsAccount), new NameValuePair("password", smsPassword) };

        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String result = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(result);
            Element root = doc.getRootElement();
            String code = root.elementText("code");// 返回值
            String msg = root.elementText("msg");// 查询结果描述
            String num = root.elementText("num");// 查询结果描述
            if ("2".equals(code)) {
                msg = "查询成功！"+ msg;
                return Integer.parseInt(num);
            } else if ("0".equals(code)) {
                msg = "提交失败！"+ msg;

            } else if ("400".equals(code)) {
                msg = "非法ip访问！"+ msg;

            } else if ("401".equals(code)) {
                msg = "帐号不能为空！"+ msg;

            } else if ("402".equals(code)) {
                msg = "密码不能为空！"+ msg;

            } else if ("405".equals(code)) {
                msg = "用户名或密码不正确！"+ msg;

            } else if ("4050".equals(code)) {
                msg = "账号被冻结！"+ msg;
            }
            System.out.println("code="+ code +" msg="+ msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}
