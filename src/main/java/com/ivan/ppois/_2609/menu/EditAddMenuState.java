package com.ivan.ppois._2609.menu;

import com.ivan.ppois._2609.AppContext;
import com.ivan.ppois._2609.model.Answer;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.service.FileService;

import static com.ivan.ppois._2609.AppConstants.OK;

public class EditAddMenuState implements MenuState {

    private final CompetitionContext competitionContext;
    private final FileService fileService;

    private final String [] newLineArray = new String[7];
    int index = 1;

    public EditAddMenuState() {
        this.fileService = AppContext.get(FileService.class);
        this.competitionContext = AppContext.get(CompetitionContext.class);
    }

    @Override
    public void viewMenu() {
        if (index == 1) {
            System.out.print("Команда: ");
        } else if (index == 2) {
            System.out.print("Имя игрока: ");
        } else if (index == 3) {
            System.out.print("Номер игрока: ");
        } else if (index == 4) {
            System.out.print("Год рождения: ");
        } else if (index == 5) {
            System.out.print("Рост игрока: ");
        } else if (index == 6) {
            System.out.print("Вес игрока: ");
        }
    }

    @Override
    public Answer handelMenuLogic(String input) {
        newLineArray[index++] = input;
        if (index == 7) {
            index = 1;
            fileService.addLine(newLineArray);
            competitionContext.setCurrentState(AppContext.get(EditMenuState.class));
        }
        return new Answer(OK, OK);
    }
}
