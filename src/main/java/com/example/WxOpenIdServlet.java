package com.example;

import com.example.util.HttpGetUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 * @author deray.wang
 * @date 2018/4/9 10:11
 */
@WebServlet(name="myOpenid",urlPatterns="/serv/myOpenid")
public class WxOpenIdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String code = request.getParameter("code");//获取code

        if(null == code){
            System.out.println("得到的code为:"+code);
        }else {
            Map params = new HashMap();
            params.put("secret", "6abcdf9114aebddcb5b06f56daf58dca");
            params.put("appid", "wx9d3cb176a343a7a2");
            params.put("grant_type", "authorization_code");
            params.put("code", code);
            String result = HttpGetUtil.httpRequestToString(
                    "https://api.weixin.qq.com/sns/oauth2/access_token", params);
            JSONObject jsonObject = JSONObject.fromObject(result);

            String openid = jsonObject.get("openid").toString();
            System.out.println("得到的openid为:"+openid);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
