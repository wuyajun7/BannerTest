package com.cbanner;

/**
 * 在线推荐歌曲列表
 * Created by gongdan on 13-6-21.
 */
public class OnlineRecommendSongListDomain {
    /**
     * 歌曲名称
     */
    private String songName;

    /**
     * 歌曲ID
     */
    private String songId;

    /**
     * 歌手名称
     */
    private String artistName;

    /**
     * 歌手ID
     */
    private String artistId;

    /**
     * 专辑ID
     */
    private String albumId;

    /**
     * 专辑名称
     */
    private String albumName;

    /**
     * 封面路径
     * @return
     */
    private String musicIcon;

    public String getMusicIcon() {
        return musicIcon;
    }

    public void setMusicIcon(String musicIcon) {
        this.musicIcon = musicIcon;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * 歌曲收听数
     */
    private int listenedCount;

    /**
     * 彩铃订购数
     */
    private int crbtCount;

    /**
     * 全曲下载数
     */
    private int fullSongDlCount;

    /**
     * 振铃下载数
     */
    private int ringDlCount;

    /**
     * 热门状态,还有其他状态
     */
    private String musicState;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getListenedCount() {
        return listenedCount;
    }

    public void setListenedCount(int listenedCount) {
        this.listenedCount = listenedCount;
    }

    public int getCrbtCount() {
        return crbtCount;
    }

    public void setCrbtCount(int crbtCount) {
        this.crbtCount = crbtCount;
    }

    public int getFullSongDlCount() {
        return fullSongDlCount;
    }

    public void setFullSongDlCount(int fullSongDlCount) {
        this.fullSongDlCount = fullSongDlCount;
    }

    public int getRingDlCount() {
        return ringDlCount;
    }

    public void setRingDlCount(int ringDlCount) {
        this.ringDlCount = ringDlCount;
    }

    public String getMusicState() {
        return musicState;
    }

    public void setMusicState(String musicState) {
        this.musicState = musicState;
    }

    @Override
    public String toString() {
        return "OnlineRecommendSongListDomain{" +
                "songName='" + songName + '\'' +
                ", songId='" + songId + '\'' +
                ", artistName='" + artistName + '\'' +
                ", artistId='" + artistId + '\'' +
                ", albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                ", listenedCount=" + listenedCount +
                ", crbtCount=" + crbtCount +
                ", fullSongDlCount=" + fullSongDlCount +
                ", ringDlCount=" + ringDlCount +
                ", musicState='" + musicState + '\'' +
                '}';
    }
}
