package com.aiyakeji.mytest.bean;

/**
 * @author CWQ
 * @date 2020/7/20
 */
public class PicBean {

    private String img;
    private int width;
    private int height;
    private String name;

    public PicBean(String img, int width, int height, String name) {
        this.img = img;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
