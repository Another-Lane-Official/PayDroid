package jp.alij.paydroid.common;

import java.util.HashMap;

import jp.alij.paydroid.data.CustomerChangeCallback;
import jp.alij.paydroid.data.TransactionRequest;
import jp.alij.paydroid.data.TransactionResult;
import android.app.Activity;
import android.app.ProgressDialog;

import com.google.gson.Gson;

/**
 * �A�i�U�[���[���i���j
 * http://www.alij.ne.jp
 * @author canu johann
 * @version�@1.0
 * 
 * 
 *
 */
public class CustomerChange {

	
	//�����ڋq�̏���ύX
	public static void changeInfo(Activity mActivity, TransactionRequest tr, final CustomerChangeCallback callback){
		
		//�p�����[�^�ݒ�
		final HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(APIConnection.SITE_ID_PARAM, tr.getSiteId());
		hm.put(APIConnection.SITE_PASS_PARAM, tr.getSitePass());
		hm.put(APIConnection.CARD_NAME_PARAM, tr.getCardName());
		hm.put(APIConnection.CARD_NO_PARAM, tr.getCardNo());
		hm.put(APIConnection.CARD_MONTH_PARAM, tr.getCardMonth());
		hm.put(APIConnection.CARD_YEAR_PARAM, tr.getCardYear());
		
		//dialog�\��
		final ProgressDialog dialog = new CustomDialog((Activity) mActivity,false);
		dialog.show();
		
		//network�𗘗p���邽�߁A�ʂ�thread�Ńf�[�^���������܂�
		new Thread(new Runnable(){
			public void run(){
				
				//android�pAPI�ɐڑ�
				APIConnection api = new APIConnection();					
				String result = api.changeQuickChargeUser(hm);
				
				//����json��
				TransactionResult res = (TransactionResult) new Gson().fromJson(result, TransactionResult.class);
				
				//���̃A�v����callback���Ăяo��
				callback.onCustomerChange(res);
				
				//dialog�����
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
		}).start();
		
	}
}
