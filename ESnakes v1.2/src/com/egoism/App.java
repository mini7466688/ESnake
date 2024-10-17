package com.egoism;

import com.egoism.ui.game.GameJFrame;

import java.io.IOException;

public class App {

    public static String MOD_ID;

    public static GameJFrame gameJFrame;


    public static void main(String[] args) throws InterruptedException, IOException {
        try {
            //"-nm"没有模组
            if (args.length > 0 && args[0] != "-nm") {
                MOD_ID = args[0];
            }
            //"-ds"默认存储文件 路径
            if (args.length > 1 && args[1] != "-ds") {
                GameJFrame.scoreFileName = args[1];
            }
            //"di"默认应用图标
            if (args.length > 3 && args[3] != "-di") {
                GameJFrame.icon = args[3];
            }
            //"dr"默认资源路径
            if (args.length > 2 && args[2] != "-dr") {
                GameJFrame.files = args[2];
            }
            //"-help"帮助
            if (args.length > 0 && args[1] == "-help") {

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        gameJFrame = new GameJFrame();
    }
}