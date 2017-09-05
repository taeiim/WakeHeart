package kr.smaker.wakeheart.config;

import java.util.HashMap;

public class Config {
    // port
    public static int PORT = 5000;

    // database info
    public static String DB_NAME = "wakeheart";
    public static String [] collection = {"users", "status", "devices", "phrases", "rests", "push_data"};
}
