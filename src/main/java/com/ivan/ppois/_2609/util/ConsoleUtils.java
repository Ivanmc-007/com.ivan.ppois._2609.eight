package com.ivan.ppois._2609.util;

public class ConsoleUtils {

    private final static boolean isWindows;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        isWindows = osName.contains("win");
    }

    private ConsoleUtils() {
    }

    public static void clear() {
        try {
            if (isWindows) {
                // Для Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Для Linux / macOS / Unix (ANSI-код)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Если очистка не сработала, то переводим строки
            for (int i = 0; i < 50; i++)
                System.out.println();
        }
    }
}
