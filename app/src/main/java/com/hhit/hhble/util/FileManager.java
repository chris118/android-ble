package com.hhit.hhble.util;

import android.content.Context;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * Created by andy on 2017/4/1.
 */

public class FileManager {

    private Context mContxt;
    private static FileManager sInstance;
    public static final String PICTURE = "picture";
    public static final String FRESCO = "fresco";
    public static final String FILE = "file";
    public static final String AUDIO = "Audio";
    public static final String TEMP = "temp";
    public static String ROOT = "default";

    private FileManager(Context context) {
        this.mContxt = context.getApplicationContext();
    }

    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        if (sInstance != null) {
            throw new IllegalArgumentException("FileManager is init");
        }
        sInstance = new FileManager(context);
    }

    public static FileManager getInstance() {
        if (sInstance == null) {
            throw new IllegalArgumentException("FileManager is not init");
        }
        return sInstance;
    }

    public void setRoot(String root){
        ROOT = root;
    }


    /**
     * 创建一个聊天文件的file
     *
     * @param type           文件类型
     * @param conversationId 会话id
     * @param localMsgId     消息id
     * @param fileType       文件类型
     * @return
     */
    public File createChatFile(String type, String conversationId, String localMsgId, String fileType) {
        File dir = getTypeDir(type);
        File file = new File(dir, conversationId);
        if (!file.exists()) {
            file.mkdir();
        }
        File newFile = new File(file, localMsgId + "." + fileType);
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newFile;
    }

    /**
     * 创建一个聊天文件的file
     *
     * @param type           文件类型
     * @param conversationId 会话id
     * @param localMsgId     消息id
     * @param fileType       文件类型
     * @return
     */
    public String createChatFilePath(String type, String conversationId, String localMsgId, String fileType) {
        File file = createChatFile(type, conversationId, localMsgId, fileType);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public String createChatAudio(String conversationId, String localMsgId) {
        File file = createChatFile(AUDIO, conversationId, localMsgId, "amr");
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }


    public File getRootDir() {
        File dir = getExternalCacheDir();
        if (dir == null) {
            dir = getInternalCacheDir();
        }
        if (dir == null) {
            throw new RuntimeException("FileManager not exits file");
        }
        File root = new File(dir, ROOT);
        if (!root.exists()) {
            root.mkdir();
        }
        return root;
    }

    public File getTypeDir(String type){
        File dir = getRootDir();
        File file = new File(dir, type);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public File getExternalCacheDir() {
        File dir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = mContxt.getExternalCacheDir();
            if (dir == null) {// 有些手机需要通过自定义目录
                dir = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + mContxt.getPackageName() + "/cache/");
            }
        }
        return dir;
    }

    public File getInternalCacheDir() {
        File dir = mContxt.getCacheDir();
        return dir;
    }

    public File createFrescoDir() {
        File dir = getTypeDir(FRESCO);
        return dir;
    }

    public File createPictureDir() {
        File dir = getTypeDir(PICTURE);
        return dir;
    }

    public File createAudioDir(){
        File dir = getTypeDir(AUDIO);
        return dir;
    }

    public File createTempDir(){
        File dir = getTypeDir(TEMP);
        return dir;
    }

    /**
     * 创建一张临时图片
     * @return
     */
    public File createTempPic(){
        File dir = createTempDir();
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".jpg", dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 创建一张临时图片
     * @return
     */
    public File createTempAudio(){
        File dir = createTempDir();
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".amr", dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 复制文件
     * @param src 源文件
     * @param dst 目标文件
     * @return
     */
    public static boolean copyTo(File src, File dst) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            in = fi.getChannel();//得到对应的文件通道
            fo = new FileOutputStream(dst);
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            close(fi);
            close(in);
            close(fo);
            close(out);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
