package com.egoism.ui.modifier;

import com.egoism.ui.game.GameJFrame;

//修改器线程
public class ModifierThread extends Thread{
    GameJFrame gameJFrame;

    public ModifierThread(GameJFrame gameJFrame) {
        this.gameJFrame = gameJFrame;
    }

    @Override
    public void run() {
        try {
            new Modifier(gameJFrame);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
