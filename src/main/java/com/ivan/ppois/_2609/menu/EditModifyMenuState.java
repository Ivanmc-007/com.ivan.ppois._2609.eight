package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.model.Answer;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.service.FileService;
import com.ivan.ppois._2609.util.ConsoleUtils;

import static com.ivan.ppois._2609.AppConstants.OK;

public class EditModifyMenuState implements MenuState {

    private final FileService fileService;
    private final CompetitionContext competitionContext;

    private String id;
    private int column;
    int index = 0;

    public EditModifyMenuState() {
        this.fileService = AppContext.get(FileService.class);
        this.competitionContext = AppContext.get(CompetitionContext.class);
    }

    @Override
    public void viewMenu() {
        if (index == 0) {
            ConsoleUtils.clear();
            fileService.displayData();
            System.out.print("Введите id записи для редактирования (0 to exit): ");
        } else if (index == 1) {
            System.out.print("Введите номер колонки для редактирования (0 to exit): ");
        } else {
            System.out.print("Введите новое значение: ");
        }
    }

    @Override
    public Answer handelMenuLogic(String input) {
        // Преждевременный выход пользователя
        if ("0".equals(input.trim())) {
            index = 0;
            competitionContext.setCurrentState(AppContext.get(EditMenuState.class));
            return new Answer(OK, OK);
        }

        if (index == 0) {
            id = input.trim();
            index++;
        } else if (index == 1) {
            try {
                column = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Не удалось идентифицировать данные как число");
                return new Answer(OK, OK);
            }
            if (column == 1) {
                System.out.println("Id записи не доступен для изменения");
                return new Answer(OK, OK);
            }
            index++;
        } else {
            index = 0;
            fileService.editRow(id, column, input);
        }
        return new Answer(OK, OK);
    }
}
