package kr.smaker.wakeheart.controller;

import org.servoframework.annotation.Route;
import org.servoframework.request.Request;
import org.servoframework.response.Response;

import kr.smaker.wakeheart.model.Status;


public class StatusController {
	private static Status status = new Status();
	
	@Route(route = "/status/add", method = Route.RouteMethod.GET)
    public static void addStatus(Request req, Response res) {
		status.connect();
		
		if (status.count() == 0) {
			status.init();
		}
		Float status = Float.parseFloat(req.getParameter("status"));
		int user = Integer.parseInt(req.getParameter("user"));
		String time = req.getParameter("time");
		int gender = Integer.parseInt(req.getParameter("gender"));
		int age = Integer.parseInt(req.getParameter("age"));
		
		status.insertAll(status, user, time, gender, age);
		
        res.setHeader("Content-Type", "text/html");
        res.write("Hello World");
        res.end();
        
        status.close();
	}
	
}
