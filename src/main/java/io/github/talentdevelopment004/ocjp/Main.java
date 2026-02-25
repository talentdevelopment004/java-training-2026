package io.github.talentdevelopment004.ocjp;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path p1 = Paths.get("/Pics/MyPic.jpeg");
        System.out.println(p1.getNameCount() +
                ":" + p1.getName(1) +
                ":" + p1.getFileName());

        Path configPath = Paths.get("src", "main", "resources", "text.config");
        System.out.println(configPath.toString()); // src\main\resources\text.config

        System.out.println(configPath.getNameCount() +
                ":" + configPath.getName(1) +
                ":" + configPath.getFileName());
    }
}
// ACID
// A - Atomicity
// C - Consistency
// I - Isolation
// D - Durability
