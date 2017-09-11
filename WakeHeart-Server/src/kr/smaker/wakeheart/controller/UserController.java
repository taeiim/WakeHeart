package kr.smaker.wakeheart.controller;

import org.servoframework.annotation.Route;
import org.servoframework.request.Request;
import org.servoframework.response.Response;

import kr.smaker.wakeheart.model.User;

public class UserController {
	private static User user = new User();
	
	@Route(route = "/API/register", method = Route.RouteMethod.POST)
    public static void register(Request req, Response res) {
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		String nickname = req.getParameter("nickname");
		int gender = Integer.parseInt(req.getParameter("gender"));
		int age = Integer.parseInt(req.getParameter("age"));
		
		user.insertAll(id, password, nickname, gender, age);
		
		
//        res.setHeader("Content-Type", "text/html");
//        res.write("Hello World");
        res.end();
	}
}
