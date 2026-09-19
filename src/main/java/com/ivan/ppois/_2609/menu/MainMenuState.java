package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.model.Answer;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.util.ConsoleUtils;

import static com.ivan.ppois._2609.AppConstants.ERROR;
import static com.ivan.ppois._2609.AppConstants.OK;

public class MainMenuState implements MenuState {

    private final CompetitionContext competitionContext;

    public MainMenuState() {
        this.competitionContext = AppContext.get(CompetitionContext.class);
    }

    @Override
    public void viewMenu() {
        ConsoleUtils.clear(); // очистить консоль
        printMainMenu();
    }

    @Override
    public Answer handelMenuLogic(String input) {
        switch (input.trim()) {
            case "1":
                competitionContext.setCurrentState(AppContext.get(MainCreateFileMenuState.class));
                return new Answer(OK, OK);
            case "2":
                competitionContext.setCurrentState(AppContext.get(MainViewMenuState.class));
                return new Answer(OK, OK);
            case "3":
                competitionContext.setCurrentState(AppContext.get(EditMenuState.class));
                return new Answer(OK, OK);
            case "4":
                competitionContext.setCurrentState(AppContext.get(MainViewGroupSortMenuState.class));
                return new Answer(OK, OK);
            case "5":
                competitionContext.setCurrentState(AppContext.get(MainViewYoungestMenuState.class));
                return new Answer(OK, OK);
            case "6":
                competitionContext.setCurrentState(AppContext.get(MainViewTallestMenuState.class));
                return new Answer(OK, OK);
            case "7":
                competitionContext.setCurrentState(AppContext.get(MainViewLightestMenuState.class));
                return new Answer(OK, OK);
            case "0":
                competitionContext.stop();
                return new Answer(OK, OK);
        }
        return new Answer(ERROR, String.format("Пункта %s нет в меню%n", input));
    }

    public void printMainMenu() {
        System.out.println("Информация об участниках спортивных соревнований");
        System.out.println("1. Создать новый файл");
        System.out.println("2. Просмотреть файл");
        System.out.println("3. Редактировать файл");
        System.out.println("4. Вывести список каждой команды, отсортированный в порядке возрастания веса игроков");
        System.out.println("5. Вывести список самых молодых игроков");
        System.out.println("6. Вывести список самых рослых игроков");
        System.out.println("7. Вывести список самых лёгких игроков");
        System.out.println("0. exit");
        System.out.println("--------------------------");
        System.out.print("Выберите пункт меню: ");
    }
}
