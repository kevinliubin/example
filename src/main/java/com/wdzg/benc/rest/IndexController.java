package com.wdzg.benc.rest;

import com.wdzg.benc.po.BenCiOrder;
import com.wdzg.benc.service.IndexService;
import com.wdzg.benc.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;


    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    /**
     * 发短信
     * @param phone
     * @return
     */
    @PostMapping("sendNote")
    public Object sendNote(String phone){
        ResultUtil resultUtil = indexService.sendNote(phone);
        return resultUtil;
    }

    /**
     * 预约页面
     * @param benCiOrder
     * @return
     */
    @PostMapping("order")
    public Object order(BenCiOrder benCiOrder,String code){
        ResultUtil resultUtil = indexService.addOrder(benCiOrder,code);
        return resultUtil;
    }



}
