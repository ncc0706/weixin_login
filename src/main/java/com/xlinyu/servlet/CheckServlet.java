package com.xlinyu.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xlinyu.model.User;

public class CheckServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		checkScan(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		checkScan(req, resp);
	}
	
	protected void checkScan(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
	    //获取页面的uuid参数
	    String uuid = req.getParameter("uuid");
	    //通过应用获取共享的uuid集合
	    Map map = (Map) req.getServletContext().getAttribute("UUID_MAP");
	    if (map != null) {
	        //查询该uuid是否存在，且二维码已经被扫描并匹配到账号
	        if(map.containsKey(uuid)){
	            User user = (User) map.get(uuid);
	            if (user != null) {
	                //从集合中移除
	                map.remove(uuid);
	                //设置登录信息
	                req.getSession().setAttribute("USER_IN_SESSION", user);
	                resp.getOutputStream().write("ok".getBytes());
	            }else{
	                resp.getOutputStream().write("native".getBytes());
	            }
	        }
	    }
	}
	
}
