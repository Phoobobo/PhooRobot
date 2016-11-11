package com.phoobobo.robot;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.phoobobo.robot.bean.ChatMessage;
import com.phoobobo.robot.bean.ChatMessage.Type;
import com.phoobobo.robot.utils.HttpUtils;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	protected static final String TAG = "MainActivity";
	private ListView mMsgsList;
	private ChatAdapter mAdapter;
	private List<ChatMessage> mDatas;
	
	private EditText mInputMsg;
	private Button mSendMsg;
	
	//或者使用异步任务
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			//wait for the sub thread message returning
			ChatMessage fromMsg = (ChatMessage) msg.obj;
			//Log.d(TAG, "baobao said: " + fromMsg.getMsg()); 
			mDatas.add(fromMsg);
			mAdapter.notifyDataSetChanged();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mian);
		initView();
		initDatas();
		initListener();
	}
	private void initListener() {
		mSendMsg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final String toMsg = mInputMsg.getText().toString();
				Log.d(TAG, "I said : " + toMsg);
				if(TextUtils.isEmpty(toMsg)) {
					Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT)
						.show();
					return;
				}
				
				//封装发送消息并更新UI
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTGOING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				
				mInputMsg.setText("");
				
				//网络任务放到子线程去做
				new Thread(){

					@Override
					public void run() {
						ChatMessage fromMsg = HttpUtils.sendMessage(toMsg);
						Message m = Message.obtain();
						m.obj = fromMsg;
						mHandler.sendMessage(m);
					}
					
				}.start();
				
			}
			
			
			
		});;
	}
	private void initDatas() {
		mDatas = new ArrayList<ChatMessage>();
		mAdapter = new ChatAdapter(this, mDatas);
		mMsgsList.setAdapter(mAdapter);
	}
	private void initView() {
		
		mMsgsList = (ListView) findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) findViewById(R.id.id_input_text);
		mSendMsg = (Button) findViewById(R.id.id_send_msg);
		
		
	}
	

}
