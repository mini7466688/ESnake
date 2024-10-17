package com.egoism.ui.game;

//蛇类
public class SnakeSegment {
    //坐标
    public int x, y;
    //方向
    public int dir;

    //构造函数
    public SnakeSegment(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    //构造拷贝函数
    public SnakeSegment(SnakeSegment segment) {
        this.x = segment.x;
        this.y = segment.y;
        this.dir = segment.dir;
    }

    //设置位置
    public void setPos(int newX, int newY) {
        x = newX;
        y = newY;
    }

    //设置位置 和方向
    public void setPos(int newX, int newY, int newDir) {
        x = newX;
        y = newY;
        dir = newDir;
    }


}
