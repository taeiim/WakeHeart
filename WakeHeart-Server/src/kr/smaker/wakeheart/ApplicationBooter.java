package kr.smaker.wakeheart;

import org.servoframework.Servo;

import kr.smaker.wakeheart.controller.HomeController;

public class ApplicationBooter {
	public static void main(String [] args) throws InterruptedException {
        Servo servo = new Servo();
        
        servo.addController(HomeController.class);
        servo.startServer(2000);
    }
}
