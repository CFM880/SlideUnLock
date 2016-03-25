package com.zhiyuan.slideunlockdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhiyuan.slideunlockdemo.MainActivity.ScreenOnOffReceiver;
import com.zhiyuan.slideunlockdemo.view.SlideUnlockView;
import com.zhiyuan.slideunlockdemo.view.SlideUnlockView.OnUnLockListener;

public class MainActivity extends Activity {

	private SlideUnlockView slideUnlockView;
	private ImageView imageView;
	private Vibrator vibrator;
	private ScreenOnOffReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ע����Ļ�����Ĺ㲥
		registScreenOffReceiver();
		// ��ȡϵͳ��������
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// ��ʼ���ؼ�
		imageView = (ImageView) findViewById(R.id.imageView);
		slideUnlockView = (SlideUnlockView) findViewById(R.id.slideUnlockView);

		// ���û�������-�����ļ���
		slideUnlockView.setOnUnLockListener(new OnUnLockListener() {
			@Override
			public void setUnLocked(boolean unLock) {
				// �����true��֤������
				if (unLock) {
					// �������� 100ms
					vibrator.vibrate(100);
					// ��������ʱ��ִ���߼�����������������ǽ�ͼƬ����չʾ
					imageView.setVisibility(View.VISIBLE);
					// ����һ�»��������Ŀؼ�
					slideUnlockView.reset();
					// �û��������ؼ���ʧ
					slideUnlockView.setVisibility(View.GONE);
				}
			}
		});

	}

	/**
	 * ע��һ����Ļ�����Ĺ㲥
	 */
	private void registScreenOffReceiver() {
		// TODO Auto-generated method stub
		receiver = new ScreenOnOffReceiver();
		// ����һ����ͼ������
		IntentFilter filter = new IntentFilter();
		// �����Ļ�����Ĺ㲥
		filter.addAction("android.intent.action.SCREEN_OFF");
		// �ڴ��������ע��㲥
		this.registerReceiver(receiver, filter);

	}

	class ScreenOnOffReceiver extends BroadcastReceiver {

		private static final String TAG = "ScreenOnOffReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// �����Ĳ���
			if ("android.intent.action.SCREEN_OFF".equals(action)) {
				// ���ֻ�����ʱ������ͬʱҲ����
				slideUnlockView.setVisibility(View.VISIBLE);
				// ����ͼƬ��ʧ
				imageView.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ���ע��Ĺ㲥
		unregisterReceiver(receiver);
		receiver = null;
	}
}
