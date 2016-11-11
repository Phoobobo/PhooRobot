package com.phoobobo.robot;

import java.text.SimpleDateFormat;
import java.util.List;

import com.phoobobo.robot.bean.ChatMessage;
import com.phoobobo.robot.bean.ChatMessage.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<ChatMessage> mDatas;
	
	public ChatAdapter(Context context, List<ChatMessage> datas) {
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ChatMessage chatMessage = mDatas.get(position);
		ViewHolder viewHolder = null;
		if(convertView == null) {
			//set different item layout through item type
			if(getItemViewType(position) == 0){
				convertView = mInflater.inflate(R.layout.item_incoming_msg, 
						parent, false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_income_msg_date);
				viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_income_msg);
			} else {
				convertView = mInflater.inflate(R.layout.item_outgoing_msg, 
						parent, false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_outgo_msg_date);
				viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_outgo_msg);
			}
			convertView.setTag(viewHolder); //save viewHolder
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		//set data
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.mDate.setText(sdf.format(chatMessage.getDate()));
		viewHolder.mMsg.setText(chatMessage.getMsg());
		return convertView;
	}
	
	private final class ViewHolder{
		
		TextView mDate;
		TextView mMsg;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMessage chatMessage = mDatas.get(position);
		if(chatMessage.getType() == Type.INCOMING) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	
}
