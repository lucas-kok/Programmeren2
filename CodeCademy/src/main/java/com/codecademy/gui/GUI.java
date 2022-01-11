package com.codecademy.gui;

import com.codecademy.Course;
import com.codecademy.gui.course.EditCourseScene;
import com.codecademy.gui.course.NewCourseScene;
import com.codecademy.gui.course.OverviewCoursesScene;
import com.codecademy.gui.course.ViewCourseScene;
import com.codecademy.gui.student.EditStudentScene;
import com.codecademy.informationhandling.InformationHandler;
import com.codecademy.Student;
import com.codecademy.gui.student.NewStudentScene;
import com.codecademy.gui.student.OverviewStudentsScene;
import com.codecademy.gui.student.ViewStudentScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GUI extends Application {
    private final InformationHandler informationHandler;
    private Map<String, GUIScene> scenes = new HashMap<>();
    private Stage mainStage;

    public GUI() {
        informationHandler = new InformationHandler();
    }

    @Override
    public void start(Stage mainStage) {
        this.mainStage = mainStage;
        mainStage.setTitle("Lucas Kok(2193968), Renzo Remmers(217), Daan van der Meulen() & Anne van den Bosch()");

        // Creating all the scenes
        MainScene mainScene = new MainScene(this, 500, 750);

        OverviewStudentsScene overviewStudentScene = new OverviewStudentsScene(this, 500, 750);
        NewStudentScene newStudentScene = new NewStudentScene(this, 500, 750);
        ViewStudentScene viewStudentScene = new ViewStudentScene(this, 500, 750, null);
        EditStudentScene editStudentScene = new EditStudentScene(this, 500, 750, null);

        OverviewCoursesScene overviewCoursesScene = new OverviewCoursesScene(this, 500, 750);
        ViewCourseScene viewCourseScene = new ViewCourseScene(this, 500, 750, null);
        NewCourseScene newCourseScene = new NewCourseScene(this, 500, 750);
        EditCourseScene editCourseScene = new EditCourseScene(this, 500, 750, null);

        // Adding the GUIScene's to a map that will be used to show the scene's
        scenes.put("mainScene", mainScene);
        scenes.put("overviewStudentsScene", overviewStudentScene);
        scenes.put("newStudentScene", newStudentScene);
        scenes.put("viewStudentScene", viewStudentScene);
        scenes.put("editStudentScene", editStudentScene);
        scenes.put("overviewCoursesScene", overviewCoursesScene);
        scenes.put("viewCourseScene", viewCourseScene);
        scenes.put("newCourseScene", newCourseScene);
        scenes.put("editCourseScene", editCourseScene);

        showScene("mainScene");

        mainStage.show();
    }

    // Function that will show the Scene connected to the given String
    public void showScene(String sceneName)  {
        if (scenes.get(sceneName) != null) {
            mainStage.setScene(scenes.get(sceneName).getScene());
        } else {
            System.out.println(sceneName + " was not found!");
        }
    }

    public GUIScene getSceneObject(String sceneName) {
        if (scenes.get(sceneName) != null) {
            return scenes.get(sceneName);
        }

        return null;
    }

    public Map<String, Student> getStudents() {
        return informationHandler.getStudents();
    }

    public Map<String, Course> getCourses() { return informationHandler.getCourses(); }

}
