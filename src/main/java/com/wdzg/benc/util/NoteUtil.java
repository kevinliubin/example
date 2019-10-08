package com.wdzg.benc.util;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description: 随机生成6位短信验证码
 * User: liubin
 * Date: 2018-01-22
 * Time: 下午 5:09
 */
public class NoteUtil {

    /**
     * 返回4位验证码
     * @return
     */
    public static String getSix(){
        Random rad=new Random();
        String result  = rad.nextInt(1000000) +"";
        int size = 4;
        if(result.length()!= size){
            return getSix();
        }
        return result;
    }


}
