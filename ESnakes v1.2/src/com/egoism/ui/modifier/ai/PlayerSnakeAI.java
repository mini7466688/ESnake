package com.egoism.ui.modifier.ai;

import com.egoism.ui.game.GameJFrame;

public class PlayerSnakeAI {
    public GameJFrame gameJFrame;

    public PlayerSnakeAI(GameJFrame gameJFrame) {
        this.gameJFrame = gameJFrame;
    }

    public void start() {

    }

    public int getXDir() {
        if (gameJFrame.foodX - gameJFrame.body.getFirst().x > 0) {
            return 39;
        } else
            return 36;
    }


    public int getYDir() {

        if (gameJFrame.foodY - gameJFrame.body.getFirst().y > 0) {
            return 37;
        } else
            return 40;
    }
}

