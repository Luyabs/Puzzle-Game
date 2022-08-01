package puzzle_game.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// 这是一个工具类 存放一些与窗口无关的函数

public class Util {
    public static ArrayList<Integer> getShuffleList(int bound) {
        Random ran = new Random();
        ArrayList<Integer> shuffleList = new ArrayList<>();    //创建打乱位置数组
        for (int i = 0; i < bound - 1; i++) {
            shuffleList.add(i + 1);
        }
        for (int i = bound - 2; i > 0; i--) {
            Collections.swap(shuffleList, i, ran.nextInt(i));
        }
        shuffleList.add(15);
        return shuffleList;
    }
}
