package kr.smaker.wakeheart;

import org.servoframework.Servo;

import kr.smaker.wakeheart.config.Config;
import kr.smaker.wakeheart.controller.HomeController;
import kr.smaker.wakeheart.controller.PhraseController;
import kr.smaker.wakeheart.controller.StatusController;
import kr.smaker.wakeheart.controller.UserController;

public class ApplicationBooter {
	public static void main(String [] args) throws InterruptedException {
        Servo servo = new Servo();
        
        servo.addController(HomeController.class);
        servo.addController(UserController.class);
        servo.addController(StatusController.class);
        servo.addController(PhraseController.class);

        
        servo.startServer(Config.PORT);
    }
}
