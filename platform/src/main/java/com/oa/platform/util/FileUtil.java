package com.oa.platform.util;

import com.google.common.collect.Maps;

import java.io.File;
import java.util.Map;

/**
 * 文件操作工具类
 * @author jianbo.feng
 * @date 2019/11/26
 */
public class FileUtil {

    private FileUtil() {}

    /**
     * 白名单列表(避免类似..字符)
     */
    public final static Map<String, String> PATH_CHAR_WHITE_LIST = Maps.newHashMap();

    static {
        PATH_CHAR_WHITE_LIST.put("a", "a");
        PATH_CHAR_WHITE_LIST.put("b", "b");
        PATH_CHAR_WHITE_LIST.put("c", "c");
        PATH_CHAR_WHITE_LIST.put("d", "d");
        PATH_CHAR_WHITE_LIST.put("e", "e");
        PATH_CHAR_WHITE_LIST.put("f", "f");
        PATH_CHAR_WHITE_LIST.put("g", "g");
        PATH_CHAR_WHITE_LIST.put("h", "h");
        PATH_CHAR_WHITE_LIST.put("i", "i");
        PATH_CHAR_WHITE_LIST.put("j", "j");
        PATH_CHAR_WHITE_LIST.put("k", "k");
        PATH_CHAR_WHITE_LIST.put("l", "l");
        PATH_CHAR_WHITE_LIST.put("m", "m");
        PATH_CHAR_WHITE_LIST.put("n", "n");
        PATH_CHAR_WHITE_LIST.put("o", "o");
        PATH_CHAR_WHITE_LIST.put("p", "p");
        PATH_CHAR_WHITE_LIST.put("q", "q");
        PATH_CHAR_WHITE_LIST.put("r", "r");
        PATH_CHAR_WHITE_LIST.put("s", "s");
        PATH_CHAR_WHITE_LIST.put("t", "t");
        PATH_CHAR_WHITE_LIST.put("u", "u");
        PATH_CHAR_WHITE_LIST.put("v", "v");
        PATH_CHAR_WHITE_LIST.put("w", "w");
        PATH_CHAR_WHITE_LIST.put("x", "x");
        PATH_CHAR_WHITE_LIST.put("y", "y");
        PATH_CHAR_WHITE_LIST.put("z", "z");

        PATH_CHAR_WHITE_LIST.put("A", "A");
        PATH_CHAR_WHITE_LIST.put("B", "B");
        PATH_CHAR_WHITE_LIST.put("C", "C");
        PATH_CHAR_WHITE_LIST.put("D", "D");
        PATH_CHAR_WHITE_LIST.put("E", "E");
        PATH_CHAR_WHITE_LIST.put("F", "F");
        PATH_CHAR_WHITE_LIST.put("G", "G");
        PATH_CHAR_WHITE_LIST.put("H", "H");
        PATH_CHAR_WHITE_LIST.put("I", "I");
        PATH_CHAR_WHITE_LIST.put("J", "J");
        PATH_CHAR_WHITE_LIST.put("K", "K");
        PATH_CHAR_WHITE_LIST.put("L", "L");
        PATH_CHAR_WHITE_LIST.put("M", "M");
        PATH_CHAR_WHITE_LIST.put("N", "N");
        PATH_CHAR_WHITE_LIST.put("O", "O");
        PATH_CHAR_WHITE_LIST.put("P", "P");
        PATH_CHAR_WHITE_LIST.put("Q", "Q");
        PATH_CHAR_WHITE_LIST.put("R", "R");
        PATH_CHAR_WHITE_LIST.put("S", "S");
        PATH_CHAR_WHITE_LIST.put("T", "T");
        PATH_CHAR_WHITE_LIST.put("U", "U");
        PATH_CHAR_WHITE_LIST.put("V", "V");
        PATH_CHAR_WHITE_LIST.put("W", "W");
        PATH_CHAR_WHITE_LIST.put("X", "X");
        PATH_CHAR_WHITE_LIST.put("Y", "Y");
        PATH_CHAR_WHITE_LIST.put("Z", "Z");

        PATH_CHAR_WHITE_LIST.put(":", ":");
        PATH_CHAR_WHITE_LIST.put("/", "/");
        PATH_CHAR_WHITE_LIST.put("\\", "\\");
        PATH_CHAR_WHITE_LIST.put(".", ".");
        PATH_CHAR_WHITE_LIST.put("-", "-");
        PATH_CHAR_WHITE_LIST.put("_", "_");
        PATH_CHAR_WHITE_LIST.put(" ", " ");

        PATH_CHAR_WHITE_LIST.put("0", "0");
        PATH_CHAR_WHITE_LIST.put("1", "1");
        PATH_CHAR_WHITE_LIST.put("2", "2");
        PATH_CHAR_WHITE_LIST.put("3", "3");
        PATH_CHAR_WHITE_LIST.put("4", "4");
        PATH_CHAR_WHITE_LIST.put("5", "5");
        PATH_CHAR_WHITE_LIST.put("6", "6");
        PATH_CHAR_WHITE_LIST.put("7", "7");
        PATH_CHAR_WHITE_LIST.put("8", "8");
        PATH_CHAR_WHITE_LIST.put("9", "9");
    }

    /**
     * 创建文件
     * @param parent 文件(父)目录
     * @param name 文件名
     * @return
     */
    public static File createFile(String parent, String name) {
        parent = StringUtil.trim(parent);
        name = StringUtil.trim(name);
        parent = pathManipulationFilter(parent);
        name = pathManipulationFilter(name);
        if ("".equals(parent) || "".equals(name)) {
            return null;
        }
        else {
            File dir = new File(parent);
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdirs();
            }
            return new File(dir, name);
        }
    }

    /**
     * 创建目录
     * @param dir 目录路径
     */
    public static void createDir(String dir) {
        dir = StringUtil.trim(dir);
        dir = pathManipulationFilter(dir);
        if (!"".equals(dir)) {
            File file = new File(dir);
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 文件路径操纵过滤(Fortify漏洞之 Path Manipulation 路径篡改问题解决)
     * @param path 文件路径
     * @return
     */
    public static String pathManipulationFilter(String path) {
        path = StringUtil.trim(path);
        String temp = "";
        for (int i = 0; i < path.length(); i++) {
            String chr = String.valueOf(path.charAt(i));
            if (PATH_CHAR_WHITE_LIST.containsKey(chr)) {
                temp += PATH_CHAR_WHITE_LIST.get(chr);
            }
        }
        return temp;
    }
}
