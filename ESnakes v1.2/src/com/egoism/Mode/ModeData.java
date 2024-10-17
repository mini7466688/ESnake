package com.egoism.Mode;

public class ModeData {
    //窗体宽度
    public int widget;

    //窗体高度
    public int height;

    //移动速度
    public int speed;

    //分辨率
    public int resolution;

    public int life;

    //Icon
    public String icon;

    //资源文件路径
    public String files;


    public ModeData(int widget, int height, int speed, int resolution, int life, String icon, String files) {
        this.widget = widget;
        this.height = height;
        this.speed = speed;
        this.resolution = resolution;
        this.life = life;
        this.icon = icon;
        this.files = files;
    }

    @Override
    public String toString() {
        return "ModeData{" +
                "widget=" + widget +
                ", height=" + height +
                ", speed=" + speed +
                ", resolution=" + resolution +
                ", life=" + life +
                ", icon='" + icon + '\'' +
                ", files='" + files + '\'' +
                '}';
    }
}
