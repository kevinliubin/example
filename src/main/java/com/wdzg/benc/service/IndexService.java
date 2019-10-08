package com.wdzg.benc.service;

import com.wdzg.benc.po.BenCiOrder;
import com.wdzg.benc.util.ResultUtil;

public interface IndexService {

    ResultUtil sendNote(String phone);

    ResultUtil addOrder(BenCiOrder benCiOrder,String code);
}
