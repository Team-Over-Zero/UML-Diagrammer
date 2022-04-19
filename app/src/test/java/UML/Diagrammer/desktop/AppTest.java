/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package UML.Diagrammer.desktop;

import UML.Diagrammer.backend.apis.Database_Client;
import UML.Diagrammer.backend.objects.NodeFactory.ClassNode;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.ListViewMatchers;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxRobot.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class AppTest {
    private FxRobot robo;
    private Database_Client db;
    @BeforeEach
    public void setRobo() throws Exception{
        ApplicationTest.launch(App.class);
        robo = new FxRobot();

        db = new Database_Client();
        db.spinUp();
    }

    @AfterEach
    public void reset() throws TimeoutException{
        FxToolkit.hideStage();
        robo.release(new KeyCode[]{});
        robo.release(new MouseButton[]{});
        db.spinDown();
    }


    @Test
    public void largeTest() {

        robo.clickOn("Register New Account");

        robo.clickOn("Cancel");

        robo.clickOn("Log In");

        /*verifyThat(robo.lookup("Log Out"), (Button b) -> b.isVisible());
        verifyThat(robo.lookup("Edit"), (Button b) -> b.isVisible());
        verifyThat(robo.lookup("Delete"), (Button b) -> b.isVisible());
        verifyThat("Load", org.testfx.matcher.control.LabeledMatchers.hasText("Load"));*/

        robo.clickOn("Class");

        //robo.doubleClickOn("Class Name");
        robo.clickOn("Class Name");

        //robo.type(KeyCode.S);

        //robo.clickOn("Confirm");

        robo.clickOn("Delete");

        robo.clickOn("Oval");

        //robo.doubleClickOn("Oval Name");


        //robo.type(KeyCode.E);

        //robo.clickOn("Confirm");

        //robo.clickOn("e");

        robo.clickOn("Delete");

        robo.clickOn("Line");

        robo.clickOn("Note");

        robo.clickOn("Folder");

        robo.clickOn("Life Line");

        robo.clickOn("Square");

        robo.clickOn("Stick Figure");

        robo.clickOn("Stick Figure Name");

        robo.clickOn("Text Box");

        robo.clickOn("Loop");

        robo.clickOn("Export");

        robo.clickOn("SVG");




    }






}
