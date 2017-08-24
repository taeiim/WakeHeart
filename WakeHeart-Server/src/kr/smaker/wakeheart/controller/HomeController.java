package kr.smaker.wakeheart.controller;

import org.servoframework.annotation.Route;
import org.servoframework.request.Request;
import org.servoframework.response.Response;

public class HomeController {
	@Route(route = "/", method = Route.RouteMethod.GET)
    public static void home(Request req, Response res) {
        res.setHeader("Content-Type", "text/html");
        res.write("Hello World");
        res.end();
    }
}
