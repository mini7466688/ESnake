package com.egoism.ui.game;

import com.egoism.App;
import com.egoism.Mode.ModRunner;
import com.egoism.ui.modifier.ModifierThread;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

//游戏窗口
public class GameJFrame extends JFrame implements KeyListener {

    public static final CountDownLatch latch = new CountDownLatch(1); // 10 is the number of threads

    public static float version = 1.2F;

    //分辨率
    public static int resolution = 32;
    //最高分数记录文件路径
    public static String scoreFileName = "./ESnakes.dat";
    //Icon
    public static String icon = "./image/icon.png";
    //资源文件路径
    public static String files = "./image/";
    //蛇的移动速度
    public long moveSpeed = 150L;
    //历史最高分
    public int maxScore;
    //是否生存
    public int life = 1;
    //窗口宽
    public int widget = 1010;
    //窗口高
    public int height = 610;
    //分数
    public int score = 0;
    //食物的X坐标
    public int foodX = resolution * 4;
    //食物的Y坐标
    public int foodY = resolution * 4;
    // 使用LinkedList管理身体各部分
    public LinkedList<SnakeSegment> body = new LinkedList<>();
    public boolean stop;
    public boolean latticeShow = false;

    //游戏窗口构造函数
    public GameJFrame() throws InterruptedException, IOException {


        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        if (App.MOD_ID != null) {
            try {
                new ModRunner(App.MOD_ID, this);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

        //禁止用户调窗口大小
//        setResizable(false);

        this.setIconImage(new ImageIcon(icon).getImage());

        try {
            //读取最高分数
            maxScore = Integer.parseInt(Files.readString(Path.of(scoreFileName)));
            System.out.println(maxScore);
        } catch (FileNotFoundException e) {
            //如果最高分数文件不存在,在"scoreFileName"变量记录的路径下,创建一个记录为0的分数文件
            FileWriter fileWriter = new FileWriter(scoreFileName);
            fileWriter.write(0);
            fileWriter.close();
        } catch (NumberFormatException e) {
            //如果最高分数文件读取时,无法转换为整数,将默认为0
            maxScore = 0;
        }
        //初始化窗口
        initJFrame();
        //初始化数据
        initData();
        //初始化图片
        initImage();
        //显示窗口
        this.setVisible(true);
        try {
            Robot robot = new Robot();
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            // 模拟鼠标左键释放
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        //窗口等待0.5秒
        Thread.sleep(500);
        //开始游戏
        start();
    }

    //初始化窗口
    public void initJFrame() {
        //设置宽高为变量"widget","height"的值
        this.setSize(widget, height);
        //设置标题为"ESnakes v1.1"
        this.setTitle("ESnakes v1.1");
        //设置窗口一直在别的窗口之上
        this.setAlwaysOnTop(true);
        //取消默认布局
        this.setLocationRelativeTo(null);
        //设置窗口关闭模式,"关一个全关"
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置样式为null
        this.setLayout(null);
        //添加键盘监听
        this.addKeyListener(this);
    }

    //初始换数据
    public void initData() {
        //清除身体容器
        body.clear();
        // 初始化蛇头,蛇头位置15,15,方向:右
        body.addLast(new SnakeSegment(resolution, resolution, KeyEvent.VK_RIGHT));
    }

    //开始游戏
    public void start() throws InterruptedException, IOException {
        //如果蛇还活着,循环执行以下代码
        while (life > 0) {
            //设置移动一次停顿的时间
            Thread.sleep(moveSpeed);
//            autoPlay();
            //蛇移动
            moveSnake();
            //检查是否受伤
            checkCollision();
            if (App.MOD_ID != null) {
//                System.out.println("have mod");
            }
//            System.out.println(ModRunner.modeData);
//            System.out.println("Snake X: " + body.getFirst().x + "   Snake Y: " + body.getFirst().y + " FoodX: " + foodX + " FoodY: " + foodY);
            //是否吃到食物
            if (eatFood()) {
                //添加身体
                addBodyPart();
                //分数加一
                score++;
            }
            //刷新图片
            initImage();
        }
    }

    //蛇移动
    public void moveSnake() {
        //获取蛇头
        SnakeSegment head = body.getFirst();
        //蛇头X坐标
        int nextX = head.x;
        //蛇头Y坐标
        int nextY = head.y;

        //判断方向以移动蛇的坐标
        switch (head.dir) {
            //向上移动
            case KeyEvent.VK_UP -> nextY -= resolution;
            //向下移动
            case KeyEvent.VK_DOWN -> nextY += resolution;
            //向左移动
            case KeyEvent.VK_LEFT -> nextX -= resolution;
            //向右移动
            case KeyEvent.VK_RIGHT -> nextX += resolution;
        }

        //移动身体
        //倒着遍历身体容器
        for (int i = body.size() - 1; i >= 0; i--) {
            if (i == 0) {
                // 更新头部位置
                ((SnakeSegment) body.get(i)).setPos(nextX, nextY);
            } else {

                //获取当前的身体
                SnakeSegment current = body.get(i);
                //获取前一个的身体
                SnakeSegment prev = body.get(i - 1);
                //将当前身体移动到前一个身体的 位置并设置方向
                current.setPos(prev.x, prev.y, prev.dir);
            }
        }
    }

    //判断是否吃到食物
    public boolean eatFood() {
        SnakeSegment head = body.getFirst();

        // 判断蛇头与食物之间的水平和垂直距离是否小于等于5像素
        int deltaX = Math.abs(head.x - foodX);
        int deltaY = Math.abs(head.y - foodY);

        if (deltaX <= 5 && deltaY <= 5) {
            return true; // 如果蛇头与食物的距离在指定范围内，返回true
        }

        return false; // 否则，返回false
    }

    //添加身体
    public void addBodyPart() {
        //在身体最后创建一个对象复制身体最后对象的属性
        SnakeSegment tailCopy = new SnakeSegment(body.getLast());
        //身体容器添加对象
        body.addLast(tailCopy);
        //因为我们是吃到了食物才长出了一截身体,所以我们添加身体之后再召唤一个食物
        spawnFood();
    }

    //检查是否受伤
    public void checkCollision() throws IOException {
        //获取头部对象
        SnakeSegment head = body.getFirst();
        //如果头部 跑出地图边界,判定为死亡
        if (head.x < 0 || head.y < 0 || head.x >= widget || head.y > height) {
            life--;
            //如果游戏分数大于历史最高分,将分数写出
            if (life == 0) {
                if (score > maxScore) {
                    FileWriter fileWriter = new FileWriter(scoreFileName);
                    fileWriter.write(String.valueOf(score));
                    fileWriter.close();
                }
            }
        }
        for (int i = 1; i < body.size(); i++) { // Check collision with its own body
            //如果蛇头碰到了蛇自己的身体,也判定为死亡
            if (body.getFirst().equals(body.get(i))) {
                life--;

                //如果游戏分数大于历史最高分,将分数写出
                if (life == 0) {
                    if (score > maxScore) {
                        FileWriter fileWriter = new FileWriter(scoreFileName);
                        fileWriter.write(String.valueOf(score));
                        fileWriter.close();
                    }
                }
            }
        }
    }

    //召唤食物
    public void spawnFood() {
        Random rand = new Random();
        //生成XY
        int x = rand.nextInt(25, widget - 40);
        int y = rand.nextInt(25, height - 60);
        //处理数据,让它变成 五的倍数
        foodX = x - (x % resolution);
        foodY = y - (y % resolution);

    }

    //初始化图片
    public void initImage() throws InterruptedException {
        //删除游戏窗口里面的所有组件
        this.getContentPane().removeAll();

        //显示标题 和分数以及历史最高分数
        this.setTitle("ESnakes  " + version + "     分数: " + score + "      历史最高: " + maxScore + "    还有:  " + life + "条命");

        JLabel border = new JLabel();
        border.setBounds(0, 0, widget, height);
        border.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.getContentPane().add(border);

        //初始化食物图片
        JLabel foodLabel = new JLabel(new ImageIcon(files + "food.png"));
        foodLabel.setBounds(foodX, foodY, resolution, resolution);
        this.getContentPane().add(foodLabel);

        //加载蛇的图片
        for (SnakeSegment segment : body) {
            JLabel segmentLabel = new JLabel(new ImageIcon(files + "snake.png"));
            segmentLabel.setBounds(segment.x, segment.y, resolution, resolution);
            this.getContentPane().add(segmentLabel);
        }

//        new InitBack().start();
//        for (int i = 5; i < this.widget - 5; i += 10) {
//            for (int j = 5; j < this.height - 5; j += 10) {
//                JLabel jLabel = new JLabel();
//                jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
//                jLabel.setBounds(i, j, 10, 10);
//                 this.getContentPane().add(jLabel);
//            }
//        }
        latticeBackground(latticeShow);
//        latch.await();
        //刷新窗口
        this.getContentPane().repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int dir = e.getKeyCode();
        //方向不和蛇头的相反,并且在移动范围内
        if (dir != getOppositeDir(body.getFirst().dir) && (dir == 37 || dir == 38 || dir == 39 || dir == 40)) {
            SnakeSegment head = body.getFirst();
            head.dir = dir;
        }
        if (dir == 27) {
            //修改器
            new ModifierThread(this).start();
        }
    }

    //获取相反方向,用于判断是否和舌头移动的方向相反
    public int getOppositeDir(int dir) {
        switch (dir) {
            case KeyEvent.VK_UP:
                return KeyEvent.VK_DOWN;
            case KeyEvent.VK_DOWN:
                return KeyEvent.VK_UP;
            case KeyEvent.VK_LEFT:
                return KeyEvent.VK_RIGHT;
            case KeyEvent.VK_RIGHT:
                return KeyEvent.VK_LEFT;
            default:
                return 0;
        }
    }

    //专门弄一个线程加载格子
    /*class InitBack extends Thread {
        @Override
        public void run() {
            for (int i = 5; i < GameJFrame.this.widget - 5; i += 10) {
                for (int j = 5; j < GameJFrame.this.height - 5; j += 10) {
                    JLabel jLabel = new JLabel();
                    jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
                    jLabel.setBounds(i, j, 10, 10);
                    final Component add = GameJFrame.this.getContentPane().add(jLabel);
                }
            }
            GameJFrame.latch.countDown();
        }
    }*/

    public Thread getThread() {
        return Thread.currentThread();
    }

    public void latticeBackground(boolean isOn) {
        if (isOn) {
            for (int i = 5; i < this.widget - 5; i += resolution) {
                for (int j = 5; j < this.height - 5; j += resolution) {
                    JLabel jLabel = new JLabel();
                    jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
                    jLabel.setBounds(i, j, resolution, resolution);
                    this.getContentPane().add(jLabel);
                }
            }
        }
    }

//    public void autoPlay() {
//        if(body.getFirst().x!=foodX)
//        {
//            body.getFirst().dir = new PlayerSnakeAI(this).getXDir();
//        }
//        else{
//            body.getFirst().dir = new PlayerSnakeAI(this).getYDir();
//        }
//    }
}

