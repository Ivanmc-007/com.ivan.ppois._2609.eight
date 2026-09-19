package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.model.Answer;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.service.FileService;
import com.ivan.ppois._2609.util.ConsoleUtils;

import static com.ivan.ppois._2609.AppConstants.OK;

public class EditRemoveMenuState implements MenuState {

    private final CompetitionContext competitionContext;
    private final FileService fileService;

    public EditRemoveMenuState() {
        this.competitionContext = AppContext.get(CompetitionContext.class);
        this.fileService = AppContext.get(FileService.class);
    }

    @Override
    public void viewMenu() {
        ConsoleUtils.clear(); // очистить консоль
        fileService.displayData();
        System.out.print("Введите id записи для удаления (0 to exit): ");
    }

    @Override
    public Answer handelMenuLogic(String inputId) {
        inputId = inputId.trim();
        if ("0".equals(inputId)) {
            competitionContext.setCurrentState(AppContext.get(EditMenuState.class));
        } else {
            fileService.deleteLineById(inputId);
        }
        return new Answer(OK, OK);
    }
}
