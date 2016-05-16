package com.cbanner;

/**
 * 在线推荐广告栏信息
 * Created by gongdan on 13-6-21.
 */
public class OnlineRecommendAdPicDomain {
    /**
     *图片下载地址
     */
    private String imgUrl;

    /**
     * 图片点击链接地址
     */
    private String resLink;

    /**
     * 位置
     */
    private int position;

    /**
     * 资源名称
     */
    private String resName;

    /**
     * 资源ID
     */
    private long resId;

    /**
     * 资源类型
     */
    private String resType;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getResLink() {
        return resLink;
    }

    public void setResLink(String resLink) {
        this.resLink = resLink;
    }
}
