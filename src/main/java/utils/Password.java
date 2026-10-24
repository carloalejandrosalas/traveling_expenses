package main.java.utils;

public class Password {
    public static String hashPassword(String password) {
       return com.password4j.Password.hash(password).addRandomSalt().withArgon2().getResult();
    }

    public static boolean check(String password, String hashedPassword) {
        return com.password4j.Password.check(password, hashedPassword).withArgon2();
    }
}
