package com.wdzg.benc.service.impl;

import com.wdzg.benc.mapper.BenCiNoteMapper;
import com.wdzg.benc.mapper.BenCiOrderMapper;
import com.wdzg.benc.po.BenCiNote;
import com.wdzg.benc.po.BenCiOrder;
import com.wdzg.benc.service.IndexService;
import com.wdzg.benc.util.NoteUtil;
import com.wdzg.benc.util.ResultUtil;
import com.wdzg.benc.util.SmsUtils;
import com.wdzg.benc.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private BenCiNoteMapper benCiNoteMapper;

    @Autowired
    private SnowflakeIdWorker idWorker;

    @Resource
    private BenCiOrderMapper benCiOrderMapper;

    @Value("${sms_Url}")
    private String smsUrl;
    @Value("${sms_Account}")
    private String smsAccount;
    @Value("${sms_Password}")
    private String smsPassword;


    @Override
    public ResultUtil sendNote(String phone) {
        //当前时间
        long currTime = System.currentTimeMillis();
        Example example = new Example(BenCiNote.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone",phone);
        List<BenCiNote> benCiNotes = benCiNoteMapper.selectByExample(example);
        String six = NoteUtil.getSix();
        if(benCiNotes.size() == 0){
            //创建记录，首次录入
            BenCiNote benCiNote = BenCiNote.builder().id(idWorker.nextId()).phone(phone).note(six).endTime(currTime+180000).build();
            int i = benCiNoteMapper.insertSelective(benCiNote);
            if(i > 0){
                //发送短信
                boolean b = SmsUtils.sendSms(smsUrl, smsAccount, smsPassword, phone, "【睿博财富】您的验证码是:" + six + "，在三分钟内有效，如非本人操作，请忽略本短信。");
            }
            return ResultUtil.ok();
        }else{
            //更新记录，如果不过期，提示验证码已经发送，过期了，重新生成，重新发送
            BenCiNote benCiNote = benCiNotes.get(0);
            if(currTime < benCiNote.getEndTime()){
                return ResultUtil.build(400,"您的验证码已经发送，勿重新获取");
            }else{
                //重新发送 更新记录
                benCiNote.setEndTime(currTime+180000);
                benCiNote.setNote(six);
                int i = benCiNoteMapper.updateByPrimaryKeySelective(benCiNote);
                if(i > 0){
                    //发送短信
                    boolean b = SmsUtils.sendSms(smsUrl, smsAccount, smsPassword, phone, "【睿博财富】您的验证码是:" + six + "，在三分钟内有效，如非本人操作，请忽略本短信。");
                }
                return ResultUtil.ok();
            }
        }
    }


    @Override
    public ResultUtil addOrder(BenCiOrder benCiOrder,String code) {
        Example exampleNote = new Example(BenCiNote.class);
        Example.Criteria criteriaNote = exampleNote.createCriteria();
        criteriaNote.andEqualTo("phone",benCiOrder.getPhone());
        criteriaNote.andEqualTo("note",code);
        int count = benCiNoteMapper.selectCountByExample(exampleNote);
        if(count == 1){
            Example example = new Example(BenCiOrder.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("phone",benCiOrder.getPhone());
            List<BenCiOrder> benCiOrders = benCiOrderMapper.selectByExample(example);
            if(benCiOrders.size() > 0){
                return ResultUtil.build(400,"您已提交，请勿重复操作！");
            }else{
                benCiOrder.setId(idWorker.nextId());
                benCiOrderMapper.insertSelective(benCiOrder);
            }
            return ResultUtil.ok();
        }else{
            return ResultUtil.build(400,"验证码错误");
        }


    }

}
