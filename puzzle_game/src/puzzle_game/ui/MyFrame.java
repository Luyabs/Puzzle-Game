package puzzle_game.ui;

import javax.swing.*;

// 这是用于设置窗口数据的抽象基类

public abstract class MyFrame extends JFrame{
    static {
        System.out.println("程序开始运行...");
    }

    public MyFrame() {
        this("Hello Window", 100, 100);
    }

    public MyFrame (String title, int width, int height) {
        setTitle(title);                    //窗口标题
        setSize(width, height);             //窗口大小
        setAlwaysOnTop(true);               //置顶
        setLocationRelativeTo(null);        //参数null时居中
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);        //设置默认关闭方式, Ctrl+B查看不同常数下的关闭方式
        setLayout(null);        //不使图片处于默认居中位置， 只有取消了才能用x,y值放置图片
    }
}
