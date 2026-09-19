package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.model.Answer;

public interface MenuState {

    void viewMenu();

    Answer handelMenuLogic(String input);

    default void clearConsole() {
        // \033[H — перемещает курсор в начало, \033[2J — очищает экран
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
