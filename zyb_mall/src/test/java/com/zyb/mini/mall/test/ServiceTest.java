package com.zyb.mini.mall.test;

import com.zyb.mini.mall.dao.BookTypeMapper;
import com.zyb.mini.mall.framework.component.ExpressComponent;
import com.zyb.mini.mall.framework.component.SmsComponent;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import com.zyb.mini.mall.service.async.AsyncService;
import com.zyb.mini.mall.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Tx
 * @date: 2019/10/26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private ExpressComponent expressComponent;

    @Autowired
    private SmsComponent smsComponent;

    @Resource
    private BookTypeMapper bookTypeMapper;


    @Resource
    private AsyncService asyncService;

    @Autowired
    private CommonService commonService;

    @Test
    public void testExpress() {
        ExpressDTO dto = expressComponent.queryExpress("70360034678097", "huitongkuaidi", "17621197917");
        log.info("ExpressDTO ---> {}", dto);
    }

    @Test
    public void testSendCodeMsg() {
//        smsComponent.sendCodeMsg("17621197917", "9999");
    }

    @Test
    public void testBox() {
        List<DownBoxVO> list = bookTypeMapper.selectDownBox();
        list.forEach(System.out::println);
    }


    @Test
    public void sendCode() {
//        asyncService.sendRefund(13L, BigDecimal.valueOf(5));
        smsComponent.sendMsgByRefund("17077460623", "5");
    }


    @Test
    public void testCacheUser() {
        commonService.updateUserCache(13L);
    }
}
