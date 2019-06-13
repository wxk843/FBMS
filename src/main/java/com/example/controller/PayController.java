package com.example.controller;

import com.alipay.api.*;
import com.alipay.api.request.*;
import com.example.util.ChanpaySCP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.example.config.ChanpayConfig.*;

import static com.example.config.AlipayConfig.*;
import static com.example.config.ChanpayConfig.MERCHANT_PRIVATE_KEY;

/**
 * 支付管理
 *@Controller
 * @author deray.wang
 * @date 2017/12/28 15:58
 */

@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @RequestMapping("/doPost")
    public void doPost(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse) throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, APP_ID, APP_PRIVATE_KEY, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping("/doPay")
    public String doPay(){
        ChanpaySCP test = new ChanpaySCP();

        //return test.mag_init_code_pay();// 主扫预下单

        Date date = new Date();
        Map<String, String> origMap = new HashMap<String, String>();
        // 基本参数
        origMap.put("Service", "mag_wx_wap_pay");//mag_wx_wap_pay 微信h5
        origMap.put("Version", "1.0");
        //origMap.put("PartnerId", "200000140001");//T环境
        origMap.put("PartnerId", "200001220037");//生产环境测试商户号
        origMap.put("InputCharset", CHARSET);
        origMap.put("TradeDate", test.getDateFormat("yyyyMMdd").format(date));
        origMap.put("TradeTime", test.getDateFormat("HHmmss").format(date));
        // origMap.put("SignType","RSA");
        origMap.put("ReturnUrl", "http://dev.chanpay.com/receive.php");// 前台跳转url
        origMap.put("Memo", "备注");

        // 4.2.1.1. 公众号/服务窗确认支付 api 业务参数
        origMap.put("OutTradeNo", Long.toString(System.currentTimeMillis()));
        //origMap.put("MchId", "200000140001");
        origMap.put("MchId", "200001220037");
        origMap.put("SubMchId", "");
        origMap.put("TradeType", "11");
        origMap.put("BankCode", "WXPAY");
        //origMap.put("BankCode", "ALIPAY");
        origMap.put("AppId", "wx90192dels817xla0");
        origMap.put("DeviceInfo", "wx90192dels817xla0");
        origMap.put("Currency", "CNY");
        origMap.put("TradeAmount", "0.01");
        origMap.put("EnsureAmount", "");
        origMap.put("GoodsName", "11");
        origMap.put("TradeMemo", "1111");
        origMap.put("Subject", "0153");
        origMap.put("OrderStartTime",
                test.getDateFormat("yyyyMMddHHmmss").format(date));
        origMap.put("OrderEndTime", "");
        origMap.put("NotifyUrl", "http://www.baidu.com");
        origMap.put("SpbillCreateIp", "127.0.0.1");
        origMap.put("SplitList", "");
        origMap.put("Ext", "{'ext':'ext1'}");
        return test.gatewayPost(origMap, CHARSET, MERCHANT_PRIVATE_KEY);
    }

}
