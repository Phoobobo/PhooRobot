package com.phoobobo.robot.test;

import com.phoobobo.robot.utils.HttpUtils;

import android.test.AndroidTestCase;
import android.util.Log;

public class TestHttpUtils extends AndroidTestCase {
	
	private static final String TAG = "TestHttpUtils";

	public void testSendInfo() {
		String res = HttpUtils.doGet("给我讲个笑话");
		Log.e(TAG, res);
		res = HttpUtils.doGet("你怎么看美国大选结果");
		Log.e(TAG, res);
		res = HttpUtils.doGet("今天西安天气如何");
		Log.e(TAG, res);		
	}
}
