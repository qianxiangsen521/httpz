package com.cnr.utils;


public class MSConfig {

    //请求分页界面中每次请求的个数
    public static final int COMMON_PAGE_NUM = 10;//108

    public static final int COMMON_PAGE_START = 0;

    public static final double COMMON_PROGRAM_PIC_RATE = 0.4;
    //**播放器播放类型**//
    //电视直播
    public final static int PLAY_TYPE_TV_LIVE = 0;

    public final static int PLAY_TYPE_THRE_LIVE = 2;

    //预告
    public final static int PLAY_TYPE_FORESHOW = PLAY_TYPE_TV_LIVE + 1;

    //外链
    public final static int PLAY_TYPE_LINK = PLAY_TYPE_FORESHOW + 1;

    public static final int ACTION_SEZRCH_NAME = 20;
    public static final int ACTION_NAME = 1;
    public static final int ACTION_GENDER = 2;
    public static final int ACTION_INDIVIDUALITY = 3;
    /* 头像名称 */
    public static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    // 拍照
    public static final int PHOTO_REQUEST_CAMERA = 3011;
    // 从相册中选择
    public static final int PHOTO_REQUEST_GALLERY = 3012;
    // 结果
    public static final int PHOTO_REQUEST_CUT = 3013;

    public static final int REQUEST_SUCCESS_CODE = 10000;

}
