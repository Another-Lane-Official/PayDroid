package jp.alij.paydroid.common;


import jp.alij.paydroid.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * �A�i�U�[���[���i���j
 * http://www.alij.ne.jp
 * @author canu johann
 *
 * Dialog�N���X
 *
 */
public class CustomDialog  extends ProgressDialog {
	
	public CustomDialog(Context context,boolean cancel) {
		super(context);
		setMessage(context.getString(R.string.under_process));
		setCancelable(false);
	}

	/**
	 * �_�C�A���O�J�n
	 */
	@Override
	public void show() {
		if(!isShowing()){
			super.show();
		}
	}

	/**
	 * �_�C�A���O�����
	 */
	@Override
	public void dismiss() {
		if(isShowing()){
			super.dismiss();
		}
	}

}