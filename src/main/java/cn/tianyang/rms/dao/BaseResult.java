package cn.tianyang.rms.dao;

public class BaseResult<T> {
	private boolean success;
	private String msg;
	private String code;
	private T result;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	//返回错误信息
	public void setResultError(String msg){
		setResultInfo(false, "003", msg,null);
	}
	//参数不全
	public void setParamError(String msg){
		setResultInfo(false, "002", msg,null);
	}
	//返回正确结果
	public void setSuccessInfo(T result){
		setResultInfo(true, "001", "请求成功",result);
	}
	//查询结果为空
	public void setResultEmpty(){
		setResultInfo(true, "001", "查询结果为空",null);
	}

	public void setResultInfo(boolean success,String code,String msg,T result){
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.result = result;
	}
	
}
