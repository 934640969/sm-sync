package com.eetrust.contant;

/**
 * @Author huangg
 * @create 2025/6/17 10:23
 */
public class syncContant {

    /** 同步类型：部门 */
    public static final int SYNC_TYPE_DEPT = 1;
    /** 同步类型：用户 */
    public static final int SYNC_TYPE_USER = 2;

    /** 同步状态：待同步 */
    public static final int SYNC_STATUS_PENDING = 0;
    /** 同步状态：失败 */
    public static final int SYNC_STATUS_FAILED = 1;
    /** 同步状态：成功 */
    public static final int SYNC_STATUS_SUCCESS = 2;
    /** 同步状态：已停止 */
    public static final int SYNC_STATUS_STOP = -1;

}
