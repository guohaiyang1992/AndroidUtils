package com.ghy.utils.common;

import java.io.File;

/**
 * Created by guohaiyang on 2017/5/16.
 * 该工具类可以和StringUtils 结合可以判断地址是否符合标准
 */

public class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    //--检查类型方法--------------------

    /**
     * 检查文件是否存在
     *
     * @param path --文件的地址
     * @return true标识存在，false标识不存在
     */
    public static boolean checkFileIsExist(String path) {
        return checkFileIsExist(pathToFile(path));
    }


    /**
     * 检查文件是否存在
     *
     * @param file --文件
     * @return true标识存在，false标识不存在
     */
    public static boolean checkFileIsExist(File file) {
        if (file != null) {
            return file.exists();
        }
        return false;
    }


    //--操作类型方法----------------------

    /**
     * 如果存在旧文件则删除，如果其文件的所在的文件夹不在，则创建-用于清除旧数据，写入新数据
     *
     * @param path --文件地址
     */
    public static void deleteOldFileAndCreateFolder(String path) {
        deleteOldFileAndCreateFolder(pathToFile(path));
    }

    /**
     * * 如果存在旧文件则删除，如果其文件的所在的文件夹不在，则创建-用于清除旧数据，写入新数据
     *
     * @param file --文件
     */
    public static void deleteOldFileAndCreateFolder(File file) {

        if (checkFileIsExist(file)) { //如果存在删除旧的文件
            file.delete();
        } else { //不存在则看看是否存在其父路径，不存在则创建
            File parentFile = file.getParentFile();
            if (!checkFileIsExist(parentFile)) {
                parentFile.mkdirs();
            }
        }
    }

    /**
     * 地址转文件
     *
     * @param path --文件地址
     * @return
     */
    public static File pathToFile(String path) {
        return new File(path);
    }
}
