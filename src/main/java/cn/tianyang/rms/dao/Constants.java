package cn.tianyang.rms.dao;

public class Constants {
    /******************************报表系统的请求报文*********************************/
    //任务ID
    public static final String ID = "ID";
    //任务序列号
    public static final String TASKID = "TASKID";
    //请求时间
    public static final String REQUEST_TIME = "REQUEST_TIME";
    //报表中文名称
    public static final String REPORT_NAME = "REPORT_NAME";
    //查询条件
    public static final String REPORT_QUERY = "REPORT_QUERY";
    //报表编号
    public static final String QUERYID = "QUERYID";
    //报表使用者ID
    public static final String USERID = "USERID";
    //执行开始时间
    public static final String BEGIN_TIME = "BEGIN_TIME";
    //执行结束时间
    public static final String END_TIME = "END_TIME";
    //执行状态
    public static final String EXECUTE_STATE = "EXECUTE_STATE";
    //错误编码
    public static final String ERROR_CODE = "ERROR_CODE";
    //描述
    public static final String DESCRIBE = "DESCRIBE";
    //压缩文件数量
    public static final String ZIP_FILE_NUMBER = "ZIP_FILE_NUMBER";
    //压缩文件详情
    public static final String ZIP_FILE_DETAILS = "ZIP_FILE_DETAILS";

    //操作类型  Submit：新增请求   Cancel：取消请求
    public static final String OPERTYPE = "OPERTYPE";
    //操作类型  Submit：新增请求
    public static final String OPERTYPE_SUBMIT = "Submit";
    //操作类型  Cancel：取消请求
    public static final String OPERTYPE_CANCEL = "Cancel";

    //返回的执行状态 0成功
    public static final String REQ_EXECUTE_STATE_0 = "0";
    //返回的执行状态 1失败
    public static final String REQ_EXECUTE_STATE_1 = "1";
    //返回的执行状态 2执行中
    public static final String REQ_EXECUTE_STATE_2 = "2";


    /*****************************Oracle数据库字段值**********************************/
    //执行状态  未开始
    public static final Integer EXECUTE_STATE_0 = 0;
    //执行状态  执行中
    public static final Integer EXECUTE_STATE_1 = 1;
    //执行状态  已完成
    public static final Integer EXECUTE_STATE_2 = 2;
    //执行状态  异常
    public static final Integer EXECUTE_STATE_3 = 3;
    //取消状态  未取消
    public static final Integer CANCEL_STATE_0 = 0;
    //取消状态  已取消
    public static final Integer CANCEL_STATE_1 = 1;
    //推送状态  未推送
    public static final Integer MSG_PRODUCE_0 = 0;
    //推送状态  已推送
    public static final Integer MSG_PRODUCE_1 = 1;

    /*****************************程序的模块编号**********************************/
    //监控kafka功能
    public static final Integer MODULE_200 = 200;
    //发送文件到nas功能
    public static final Integer MODULE_201 = 201;


    /*****************************压缩文件详情数组中的字段**********************************/
    //压缩文件名称
    public static final String ZIP_FILE_NAME = "ZIP_FILE_NAME";
    //压缩文件地址
    public static final String ZIP_FILE_SRC = "ZIP_FILE_SRC";
    //压缩文件MD5
    public static final String ZIP_HASHMD5 = "ZIP_HASHMD5";
    //CSV文件MD5
    public static final String CVS_HASHMD5 = "CVS_HASHMD5";

}
