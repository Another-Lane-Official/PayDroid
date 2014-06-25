package jp.alij.paydroid.listeners;

import jp.alij.paydroid.activities.PaymentActivity;
import jp.alij.paydroid.common.Consts;
import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author canu johann
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * 
 * 決済画面の【中止】ボタンクリック処理
 *
 */
public class OnClickCancel implements OnClickListener {
	
	public PaymentActivity mActivity;

	public OnClickCancel(PaymentActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		Message msg = Message.obtain();
		msg.what=Consts.ASYNC_HANDLER_CANCEL;
		mActivity.asyncHandler.sendMessage(msg);
	}

}
