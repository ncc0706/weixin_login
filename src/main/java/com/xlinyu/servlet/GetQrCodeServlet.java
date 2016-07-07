package com.xlinyu.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xlinyu.model.User;
import com.xlinyu.util.HttpClientUitl;
import com.xlinyu.util.QrGenUtil;

public class GetQrCodeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		showQrGen(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		showQrGen(req, resp);
	}

	protected void showQrGen(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 生成UUID随机数
		UUID randomUUID = UUID.randomUUID();
		// 通过应用获取共享的uuid集合
		Map<String, User> map = (Map) req.getServletContext().getAttribute("UUID_MAP");
		if (map == null) {
			map = new HashMap<String, User>();
			req.getServletContext().setAttribute("UUID_MAP", map);
		}
		// 把uuid放入map中
		map.put(randomUUID.toString(), null);
		// 二维码图片扫描后的链接
		
		//String url = "http://v.tunnel.qydev.com/login?cmd=loginByQrGen&uuid="+ randomUUID;
		String url = "http://v.tunnel.qydev.com/login?uuid="+ randomUUID;
		
		url= "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx598836973839e0b9&redirect_uri=" 
				+ url +"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		// 生成二维码图片
		ByteArrayOutputStream qrOut = QrGenUtil.createQrGen(url);
		String fileName = randomUUID + ".jpg";
		OutputStream os = new FileOutputStream(new File(req.getServletContext()
				.getRealPath("/temp"), fileName));
		os.write(qrOut.toByteArray());
		os.flush();
		os.close();
		// 返回页面json字符串，uuid用于轮询检查时所带的参数， img用于页面图片显示
		String jsonStr = "{\"uuid\":\"" + randomUUID + "\",\"img\":\""
				+ "/temp/" + fileName + "\"}";
		OutputStream outStream = resp.getOutputStream();
		outStream.write(jsonStr.getBytes());
		outStream.flush();
		outStream.close();
	}
}
