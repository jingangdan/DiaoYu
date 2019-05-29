package com.project.dyuapp.entity;

import java.io.File;

/**
 * @author gengqiufang
 * @describtion 网络请求  上传文件
 * @created at 2017/9/19 0019
 */

public class UpLoadFileEntity {
    private String name;
    private String fileName;
    private File file;

    public UpLoadFileEntity(String name, String fileName, File file) {
        this.name = name;
        this.fileName = fileName;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UpLoadFileEntity{" +
                "name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", file=" + file +
                '}';
    }
}
