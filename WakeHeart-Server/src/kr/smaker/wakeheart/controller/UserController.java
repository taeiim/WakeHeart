package kr.smaker.wakeheart.controller;

import java.util.HashMap;

import org.servoframework.annotation.Route;
import org.servoframework.request.Request;
import org.servoframework.response.Response;

import kr.smaker.wakeheart.model.User;

public class UserController {
	private static User user = new User();

	@Route(route = "/API/register", method = Route.RouteMethod.POST)
	public static void register(Request req, Response res) {
		user.connect();
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		int gender = Integer.parseInt(req.getParameter("gender"));
		int age = Integer.parseInt(req.getParameter("age"));

		user.insertAll(id, password, gender, age);

		HashMap<String, Object> data = new HashMap<String, Object>();

		try {
			data.put("success", true);
			data.put("id", id);

			res.cookie("user", id);
			res.json(data);
		} catch (Exception e) {
			data.put("success", false);
			data.put("error", e.toString());
			res.json(data);
		}

		res.end();
	}

	@Route(route = "/API/login", method = Route.RouteMethod.POST)
	public static void login(Request req, Response res) {
		user.connect();
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		HashMap<String, Object> data = new HashMap<String, Object>();

		try {
			if (user.auth(id, password) == true) {
				data.put("success", true);

				res.cookie("user", id);
				res.json(data);
			} else {
				data.put("success", false);
				res.json(data);
			}
		} catch (Exception e) {
			data.put("success", false);
			data.put("error", e.toString());
			e.printStackTrace();
			res.json(data);
		}

		res.end();
	}

	@Route(route = "/API/logout", method = Route.RouteMethod.POST)
	public static void logout(Request req, Response res) {
		HashMap<String, Object> data = new HashMap<String, Object>();

		try {
			res.expireCookie("user");
			data.put("success", true);
			res.json(data);
		} catch (Exception e) {
			data.put("success", false);
			data.put("error", e.toString());
			res.json(data);
		}
		
		res.end();
	}
	
	
	@Route(route = "/API/find/user", method = Route.RouteMethod.GET)
	public static void findUser(Request req, Response res) {
		user.connect();
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("items", user.findAll());
		res.json(data);
		res.end();
	}

}
