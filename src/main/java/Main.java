package main.java;

import main.java.screens.HomeScreen;
import main.java.utils.ScreenUtils;

import java.util.Scanner;

public class Main {
    static void main() {
        var scanner = new Scanner(System.in);
        showWelcomeMessage();

        new HomeScreen(scanner).render();

        scanner.close();
    }

    static void showWelcomeMessage () {
        ScreenUtils.printUI("*********************************************");
        ScreenUtils.printUI("* Welcome to the Project Management System! *");
        ScreenUtils.printUI("*********************************************");
    }
}
