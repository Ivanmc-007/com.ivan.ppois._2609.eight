package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.model.Answer;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.util.ConsoleUtils;

import static com.ivan.ppois._2609.AppConstants.ERROR;
import static com.ivan.ppois._2609.AppConstants.OK;

public class EditMenuState implements MenuState {

    private final CompetitionContext competitionContext;

    public EditMenuState() {
        this.competitionContext = AppContext.get(CompetitionContext.class);
    }

    @Override
    public void viewMenu() {
        ConsoleUtils.clear();
        System.out.println("1. Добавить строку");
        System.out.println("2. Отредактировать строку");
        System.out.println("3. Удалить строку");
        System.out.println("0. Назад");
        System.out.println("--------------------------");
        System.out.print("Выберите пункт меню: ");
    }

    @Override
    public Answer handelMenuLogic(String input) {
        switch (input.trim()) {
            case "1":
                competitionContext.setCurrentState(AppContext.get(EditAddMenuState.class));
                return new Answer(OK, OK);
            case "2":
                competitionContext.setCurrentState(AppContext.get(EditModifyMenuState.class));
                return new Answer(OK, OK);
            case "3":
                competitionContext.setCurrentState(AppContext.get(EditRemoveMenuState.class));
                return new Answer(OK, OK);
            case "0":
                competitionContext.setCurrentState(AppContext.get(MainMenuState.class));
                return new Answer(OK, OK);
        }
        return new Answer(ERROR, String.format("Пункта %s нет в меню%n", input));
    }
}
