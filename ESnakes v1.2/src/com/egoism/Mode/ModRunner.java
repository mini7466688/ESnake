package com.egoism.Mode;

import com.egoism.ui.game.GameJFrame;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ModRunner {
    public static ModeData modeData;
    public String modName;

    public GameJFrame gameJFrame;

    public ModRunner(String modName, GameJFrame gameJFrame) throws FileNotFoundException {
        this.modName = modName;
        this.gameJFrame = gameJFrame;
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(modName)) {
            modeData = gson.fromJson(reader, ModeData.class);
            if (modeData.widget != 0) {
                gameJFrame.widget = modeData.widget;
//                System.out.println("mod widget ok");
            }
            if (modeData.height != 0) {
                gameJFrame.height = modeData.height;
//                System.out.println("mod height ok");

            }
            gameJFrame.setSize(gameJFrame.widget, gameJFrame.height);
            if (modeData.speed != 0) {
                gameJFrame.moveSpeed = modeData.speed;
//                System.out.println("mod moveSpeed ok");
            }
            if (modeData.resolution != 0) {
                GameJFrame.resolution = modeData.resolution;
//                System.out.println("mod resolution ok");

            }
            if (modeData.life != 0) {
                gameJFrame.life = modeData.life;
//                System.out.println("mod life ok");
            }
            if (modeData.icon != null) {
                gameJFrame.icon = modeData.icon;
//                System.out.println("mod icon ok");
            }
            if (modeData.files != null) {
                gameJFrame.files = modeData.files;
//                System.out.println("mod files ok");
            }
            System.out.println(modeData);
        } catch (IOException e) {

        }

    }
}
