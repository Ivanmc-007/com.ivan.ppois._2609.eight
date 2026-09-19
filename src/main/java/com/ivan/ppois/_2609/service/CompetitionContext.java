package com.ivan.ppois._2609.service;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.menu.MainMenuState;
import com.ivan.ppois._2609.menu.MenuState;

import java.util.Scanner;

public class CompetitionContext {
    private MenuState currentState;
    private boolean running = true;

    public CompetitionContext() {
    }

    public void run() {
        if (currentState == null) {
            currentState = AppContext.get(MainMenuState.class);
        }
        try (Scanner scanner = new Scanner(System.in)) {
            while (running) {
                this.currentState.viewMenu();
                String userInput = scanner.nextLine();
                currentState.handelMenuLogic(userInput);
            }
        }
    }

    /**
     * @param currentState Изменить текущий контекст
     */
    public void setCurrentState(MenuState currentState) {
        this.currentState = currentState;
    }

    public void stop() {
        this.running = false;
    }
}
