package com.ivan.ppois._2609;

import com.ivan.ppois._2609.menu.EditAddMenuState;
import com.ivan.ppois._2609.menu.EditMenuState;
import com.ivan.ppois._2609.menu.EditModifyMenuState;
import com.ivan.ppois._2609.menu.EditRemoveMenuState;
import com.ivan.ppois._2609.menu.MainCreateFileMenuState;
import com.ivan.ppois._2609.menu.MainMenuState;
import com.ivan.ppois._2609.menu.MainViewGroupSortMenuState;
import com.ivan.ppois._2609.menu.MainViewLightestMenuState;
import com.ivan.ppois._2609.menu.MainViewMenuState;
import com.ivan.ppois._2609.menu.MainViewTallestMenuState;
import com.ivan.ppois._2609.menu.MainViewYoungestMenuState;
import com.ivan.ppois._2609.service.CompetitionContext;
import com.ivan.ppois._2609.service.FileService;

public class Application {
    public static void main(String[] args) {
        // Глобальная кодировка UTF-8 для проекта
        System.setProperty("file.encoding", "UTF-8");
        // init context
        // Конекст участников (основной держатель логики по работе с участниками соревнования)
        CompetitionContext competitionContext = new CompetitionContext();
        initContext(competitionContext);
        // Вперёд!
        competitionContext.run();
    }

    private static void initContext(CompetitionContext competitionContext) {
        // init context
        AppContext.register(CompetitionContext.class, competitionContext); // С ленивой инициализацией
        // Сервис по работе с файловой системой
        AppContext.register(FileService.class, new FileService("sport_participants"));
        // Menu
        AppContext.register(MainViewMenuState.class, new MainViewMenuState());
        AppContext.register(MainViewGroupSortMenuState.class, new MainViewGroupSortMenuState());
        AppContext.register(MainMenuState.class, new MainMenuState());
        AppContext.register(MainCreateFileMenuState.class, new MainCreateFileMenuState());
        AppContext.register(MainViewLightestMenuState.class, new MainViewLightestMenuState());
        AppContext.register(MainViewTallestMenuState.class, new MainViewTallestMenuState());
        AppContext.register(MainViewYoungestMenuState.class, new MainViewYoungestMenuState());
        AppContext.register(EditMenuState.class, new EditMenuState());
        AppContext.register(EditAddMenuState.class, new EditAddMenuState());
        AppContext.register(EditModifyMenuState.class, new EditModifyMenuState());
        AppContext.register(EditRemoveMenuState.class, new EditRemoveMenuState());
    }
}
