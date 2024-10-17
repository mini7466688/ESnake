package com.egoism.ui.modifier;

import com.egoism.ui.game.GameJFrame;
import com.egoism.ui.modifier.buttonAction.closeButtonActionListenerImpl;
import com.egoism.ui.modifier.buttonAction.setLatticeShowButtonActionListenerImpl;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;

public class Modifier extends JFrame implements KeyListener, WindowListener {

    public static int clickSetLatticeShowButtonCount = 0;
    public GameJFrame gameJFrame;
    public JLabel scoreJLable = new JLabel("长度:");
    public JLabel maxScoreJLable = new JLabel("最大分:");
    public JLabel moveSpeed = new JLabel("速度:");
    public JLabel widget = new JLabel("宽度:");
    public JLabel height = new JLabel("高度:");
    public JTextField scoreTextInput = new JTextField(30);
    public JTextField maxScoreTextInput = new JTextField(30);
    public JTextField moveSpeedTextInput = new JTextField(30);
    public JTextField widgetTextInput = new JTextField(30);
    public JTextField heightTextInput = new JTextField(30);
    public JButton closeButton = new JButton("完成");
    public JButton setLatticeShowButton = new JButton("格子");
    public int tempSpeed;

    public boolean isClose = true;

    public Modifier(GameJFrame gameJFrame) throws InterruptedException {
        this.gameJFrame = gameJFrame;
        tempSpeed = (int) gameJFrame.moveSpeed;
        gameJFrame.moveSpeed = 1000;
        initJFrame();
        setVisible(true);
    }

    public void initJFrame() {
        //设置宽高为变量"widget","height"的值
        this.setSize(220, 380);
        //设置标题为"ESnakes v1.1"
        this.setTitle("ESnakes v1.1 修改器");
        //设置窗口一直在别的窗口之上
        this.setAlwaysOnTop(true);
        //取消默认布局
        this.setLocationRelativeTo(null);
        //设置窗口关闭模式,"关一个全关"
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //设置样式为null
        this.setLayout(null);

        scoreTextInput.setColumns(30);
        scoreTextInput.setSize(90, 30);
        scoreTextInput.setLocation(60, 20);
        this.getContentPane().add(scoreTextInput);
        scoreJLable.setBounds(0, 20, 50, 30);
        this.getContentPane().add(scoreJLable);

        maxScoreTextInput.setColumns(30);
        maxScoreTextInput.setSize(90, 30);
        maxScoreTextInput.setLocation(60, 60);
        this.getContentPane().add(maxScoreTextInput);
        maxScoreJLable.setBounds(0, 60, 50, 30);
        this.getContentPane().add(maxScoreJLable);

        moveSpeedTextInput.setColumns(30);
        moveSpeedTextInput.setSize(90, 30);
        moveSpeedTextInput.setLocation(60, 100);
        this.getContentPane().add(moveSpeedTextInput);
        moveSpeed.setBounds(0, 100, 50, 30);
        this.getContentPane().add(moveSpeed);

        widgetTextInput.setColumns(30);
        widgetTextInput.setSize(90, 30);
        widgetTextInput.setLocation(60, 140);
        this.getContentPane().add(widgetTextInput);
        widget.setBounds(0, 140, 50, 30);
        this.getContentPane().add(widget);

        heightTextInput.setColumns(30);
        heightTextInput.setSize(90, 30);
        heightTextInput.setLocation(60, 180);
        this.getContentPane().add(heightTextInput);
        height.setBounds(0, 180, 50, 30);
        this.getContentPane().add(height);

        closeButton.setBounds(60, 260, 90, 30);
        closeButton.addActionListener(new closeButtonActionListenerImpl(this));
        this.getContentPane().add(closeButton);

        setLatticeShowButton.setBounds(60, 300, 90, 30);
        setLatticeShowButton.addActionListener(new setLatticeShowButtonActionListenerImpl(this));
        this.getContentPane().add(setLatticeShowButton);


        this.addWindowListener(this);

        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        boolean noModSpeed = true;

        try {
            int score = Integer.parseInt(scoreTextInput.getText());
            gameJFrame.score = score;
            for (int i = 0; i < score; i++) {
                gameJFrame.addBodyPart();
            }
        } catch (NumberFormatException nfe) {
        }


        try {
            int maxScore = Integer.parseInt(maxScoreTextInput.getText());
            gameJFrame.maxScore = maxScore;
            FileWriter fileWriter = new FileWriter(gameJFrame.scoreFileName);
            fileWriter.write(String.valueOf(maxScore));
            fileWriter.close();
        } catch (NumberFormatException | IOException nfe) {
        }


        try {
            int speed = Integer.parseInt(moveSpeedTextInput.getText());
            gameJFrame.moveSpeed = speed;
        } catch (NumberFormatException nfe) {
//            System.out.println("");
            gameJFrame.moveSpeed = tempSpeed;
        }


        boolean getWidget = false, getHeight = false;
        int height, widget;


        try {
            widget = Integer.parseInt(widgetTextInput.getText());
            gameJFrame.widget = widget;
            getWidget = true;
//            System.out.println("[");
        } catch (NumberFormatException nfe) {
            widget = gameJFrame.widget;
        }

        try {
            height = Integer.parseInt(heightTextInput.getText());
            gameJFrame.height = height;
            getHeight = true;
//            System.out.println("]");
        } catch (NumberFormatException nfe) {
            height = gameJFrame.height;
        }

        gameJFrame.setSize(widget, height);
        if (getWidget || getHeight) {
            gameJFrame.spawnFood();
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
