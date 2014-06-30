package jp.alij.paydroid.common;


import jp.alij.paydroid.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 *
 * Dialogクラス
 *
 */
public class CustomDialog  extends ProgressDialog {
	
	public CustomDialog(Context context,boolean cancel) {
		super(context);
		setMessage(context.getString(R.string.under_process));
		setCancelable(false);
	}

	/**
	 * ダイアログ開始
	 */
	@Override
	public void show() {
		if(!isShowing()){
			super.show();
		}
	}

	/**
	 * ダイアログを閉じる
	 */
	@Override
	public void dismiss() {
		if(isShowing()){
			super.dismiss();
		}
	}

}