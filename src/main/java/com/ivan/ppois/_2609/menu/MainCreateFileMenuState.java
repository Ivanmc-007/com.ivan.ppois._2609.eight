package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.model.Answer;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.service.FileService;
import com.ivan.ppois._2609.util.ConsoleUtils;

import static com.ivan.ppois._2609.AppConstants.OK;

public class MainCreateFileMenuState implements MenuState {
    private final FileService fileService;
    private final CompetitionContext competitionContext;

    public MainCreateFileMenuState() {
        this.fileService = AppContext.get(FileService.class);
        this.competitionContext = AppContext.get(CompetitionContext.class);
    }

    @Override
    public void viewMenu() {
        ConsoleUtils.clear(); // очистить консоль
        fileService.createFile(); // создать файл
        System.out.println("Файл успешно создан");
        System.out.println("0. Назад");
        System.out.print("Выберите пункт меню: ");
    }

    @Override
    public Answer handelMenuLogic(String input) {
        if (input.trim().equals("0")) {
            competitionContext.setCurrentState(AppContext.get(MainMenuState.class));
        }
        return new Answer(OK, OK);
    }
}
