package com.xlinyu.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xlinyu.util.HttpClientUitl;

public class LoginServlet extends HttpServlet {

	//应用ID
	public static final String APPID = "wx598836973839e0b9";
	
	//应用密钥
	public static final String APPSECRET = "bb9ddd84543bc0d1b60b6bbe3ad1d23b";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		loginByQrGen(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		loginByQrGen(req, resp);
	}

	protected void loginByQrGen(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 获取二维码链接中的uuid
		String uuid = req.getParameter("uuid");
		System.out.println("uuid: " + uuid);
		
		// 根据微信传来的code来获取用户的openID
		String code = req.getParameter("code");
		System.out.println("code: " +code);
		
		// 通过应用获取共享的uuid集合
		Map uuidMap = (Map) req.getServletContext().getAttribute("UUID_MAP");
		// 如果集合内没有这个uuid，则响应结果
		if (uuidMap == null || !uuidMap.containsKey(uuid)) {
			resp.getOutputStream().write("二维码不存在或已失效！".getBytes());
			return;
		}
		
		try {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID
					+ "&secret="+APPSECRET
					+ "&grant_type=authorization_code"
					+ "&code=" + code;
			Gson gson = new Gson();
			Map map = gson.fromJson(HttpClientUitl.doGet(url),
					new TypeToken<Map>() {
					}.getType());
			Object openID = map.get("openid");
			System.out.println("openID: " + openID);
			if (openID != null && !"".equals(openID)) {
				// 通过openID获取user对象
				/*User user = dao.getUserByOpenId(openID.toString());
				if (user != null) {
					// 如果查询到某个user拥有该openID，则设置到map集合内
					uuidMap.put(uuid, user);
					// 并返回手机端扫描结果
					resp.getOutputStream().write("登陆成功！".getBytes());
					return;
				}*/
			}
			// 如果没有openID参数，或查询不到openID对应的user对象，则移除该uuid，并响应结果
			uuidMap.remove(uuid);
			resp.getOutputStream().write(
					"你还未绑定，请关注微信号并绑定账号！并使用微信客户端扫描！".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
