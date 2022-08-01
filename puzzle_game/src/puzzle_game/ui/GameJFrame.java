package puzzle_game.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

public class GameJFrame extends MyFrame implements KeyListener {
    private final int row = 3;          //总共有多少行图片
    private final int col = 5;          //总共有多少列图片
    private ArrayList<Integer> picList = Util.getShuffleList(row * col);       //记录每块图片的位置
    private int blank = row * col - 1;         //记录空白图片的位置
    private final String picPath = "image/";      //记录图片位置
    private int step = 0;           //记录当前已进行图片交换的次数

    public GameJFrame() {
        super("Hello Game", 580, 360);
        initJMenuBar();
        initImage();
        addKeyListener(this);
        setVisible(true);
    }

    private void reGame() {                         //重新进行游戏
        picList = Util.getShuffleList(row * col);
        blank = row * col - 1;
        step = 0;
        initImage();
    }

    private void initJMenuBar() {                   //初始化菜单栏
        JMenuBar jMenuBar = new JMenuBar();

        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于");

        JMenuItem remakeItem = new JMenuItem("重开游戏");
        JMenuItem closeItem = new JMenuItem("关闭游戏");

        JMenuItem githubLink = new JMenuItem("Github链接");

        //重开游戏
        remakeItem.addActionListener(e -> {
            System.out.println("重开游戏");
            reGame();
        });
        closeItem.addActionListener(e -> {
            System.out.println("关闭游戏");
            System.exit(0);
        });
        githubLink.addActionListener(e -> {
            JDialog jDialog = new JDialog();
            JLabel jlabel = new JLabel("    https://github.com/Luyabs");
            jlabel.setBounds(0, 0, 600, 100);
            jDialog.getContentPane().add(jlabel);
            jDialog.setSize(200, 75);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);             //只有关闭弹窗后才能操作
            jDialog.setTitle("My github");
            jDialog.setVisible(true);
        });

        functionJMenu.add(remakeItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(githubLink);

        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        setJMenuBar(jMenuBar);
    }   //初始化菜单栏

    private void initImage() {          //初始化图片
        refresh();
        addWords("当前步数: " + step, 30, 10, 100, 20);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                addImage(picList.get(i * col + j), j * 105 + 20, i * 80 + 40, 105, 80);
            }
        }
        if (isWinGame()) {                 //游戏胜利,结束
            System.out.println("YOU WIN!!");
            System.out.println("你一共花了" + step + "步");
        }
    }      //初始化图片

    private void addImage(int num, int x, int y, int width, int height) {   //添加图片 参数:次序, 位置(x, y), 长宽
        String path;
        if (num == -1)
            path = picPath + "FullPicture.png";
        else if (num < 10)
            path = picPath + "Picture0" + num + ".png";
        else
            path = picPath + "Picture" + num + ".png";
        JLabel jlabel = new JLabel(new ImageIcon(path));
        jlabel.setBounds(x, y, width, height);
        jlabel.setBorder(new BevelBorder(BevelBorder.LOWERED));             //设置边框
        getContentPane().add(jlabel);
    }   //添加图片 参数:次序, 位置(x, y), 长宽

    private void addWords(String words, int x, int y, int width, int height) {      //添加文字
        JLabel jlabel = new JLabel(words);
        jlabel.setBounds(x, y, width, height);
        getContentPane().add(jlabel);
    }

    private void refresh() {                //刷新画面
        getContentPane().removeAll();
        getContentPane().repaint();
    }

    private boolean isWinGame() {           //判断是否胜利
        for (int i = 0; i < picList.size(); i++) {
            if (picList.get(i) != i + 1)
                return false;
        }
        return true;
    }

    private void exchange(int length) {      //交换相邻的两格图片
        Collections.swap(picList, blank, blank + length);
        blank += length;
        step++;
    }

    private void reset() {                   //重置游戏至完整图片
        for (int i = 0; i < picList.size(); i++) {
            picList.set(i, i + 1);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isWinGame())                        //游戏胜利直接结束
            return;
        if (e.getKeyCode() == 81) {              //按住不松Q看完整图片
            refresh();
            addImage(-1, 50, 50, 475, 216);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isWinGame())                        //游戏胜利直接结束
            return;
        switch (e.getKeyCode()) {
            case 87 -> {
                if (blank >= col) {               //上移
                    exchange(-col);
                    initImage();
                } else {
                    System.out.println("已达到最上边");
                }
            }
            case 65 -> {                //左移
                if (blank % col >= 1) {
                    exchange(-1);
                    initImage();
                } else {
                    System.out.println("已达到最左边");
                }
            }
            case 83 -> {               //下移
                if (blank <= row * col - col - 1) {
                    exchange(col);
                    initImage();
                } else {
                    System.out.println("已达到最下边");
                }
            }
            case 68 -> {               //右移
                if (blank % col <= row) {
                    exchange(1);
                    initImage();
                } else {
                    System.out.println("已达到最右边");
                }
            }
            case 81 -> initImage();    //松Q恢复
            case 69 -> {              //按住E直接过关
                reset();
                initImage();
            }
        }
    }

}