package com.minbo.javademo.utils;

public enum ResultCode {
	
	/** 成功 */
	SUCCESS("200", "成功"),
	
	/** 数据为空 */
	SUCCESS_NO_DATA("201", "数据为空"),
	
	/** 用户不存在 */
	SUCCESS_NO_USER("202", "用户不存在"),
	
	/** 今天的量已经跑光了 */
	SUCCESS_ALL_GONE("203", "今天的量已经跑光了"),
	
	/** 失败，请重试 */
	SUCCESS_FAIL("204", "失败，请重试"),
	
	/** 失败，余额不足 */
	SUCCESS_NOT_ENOUGH("205", "失败，余额不足"),
	
	/** 不支持此操作 */
	SUCCESS_NOT_RIGHT("206", "不支持此操作"),
	
	/** 同一任务只能做一次 */
	SUCCESS_JUST_ONE("207", "同一任务只能做一次"),
	
	/** 重复记录 */
	SUCCESS_DUPLICATE("208", "重复记录"),
	
	/** 账户已注册 */
	SUCCESS_EXIST("209", "账户已注册"),
	
	/** 账户已注册 */
	SUCCESS_CHECK_IN("210", "已签到过了"),
	
	/** 今日阅读金币领取已达上限 */
	SUCCESS_DAILY_LIMIT("211", "今日阅读金币领取已达上限"),

	/** 没有登录 */
	NOT_LOGIN("400", "没有登录"),

	/** 发生异常 */
	EXCEPTION("401", "发生异常"),

	/** 系统错误 */
	SYS_ERROR("402", "系统错误"),
	
	/** 参数错误 */
	PARAMS_ERROR("403", "参数错误"),

	/** 不支持或已经废弃 */
	NOT_SUPPORTED("410", "不支持或已经废弃"),
	
	/** 无效IMEI值 */
	IMEI_ERROR("411", "无效IMEI值"),

	/** AuthCode错误 */
	INVALID_AUTHCODE("444", "无效的AuthCode"),

	/** 太频繁的调用 */
	TOO_FREQUENT("445", "太频繁的调用"),

	/** 未知的错误 */
	UNKNOWN_ERROR("499", "未知错误");

	private ResultCode(String val, String msg) {
		this.val = val;
		this.msg = msg;
	}

	public String val() {
		return val;
	}

	public String msg() {
		return msg;
	}

	private String val;
	private String msg;
	
}
