package jp.alij.paydroid.common;

import java.util.HashMap;

import jp.alij.paydroid.data.CustomerChangeCallback;
import jp.alij.paydroid.data.TransactionRequest;
import jp.alij.paydroid.data.TransactionResult;
import android.app.Activity;
import android.app.ProgressDialog;

import com.google.gson.Gson;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * @version　1.0
 * 
 * 
 *
 */
public class CustomerChange {

	
	//既存顧客の情報を変更
	public static void changeInfo(Activity mActivity, TransactionRequest tr, final CustomerChangeCallback callback){
		
		//パラメータ設定
		final HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(APIConnection.SITE_ID_PARAM, tr.getSiteId());
		hm.put(APIConnection.SITE_PASS_PARAM, tr.getSitePass());
		hm.put(APIConnection.CARD_NAME_PARAM, tr.getCardName());
		hm.put(APIConnection.CARD_NO_PARAM, tr.getCardNo());
		hm.put(APIConnection.CARD_MONTH_PARAM, tr.getCardMonth());
		hm.put(APIConnection.CARD_YEAR_PARAM, tr.getCardYear());
		
		//dialog表示
		final ProgressDialog dialog = new CustomDialog((Activity) mActivity,false);
		dialog.show();
		
		//networkを利用するため、別のthreadでデータを処理します
		new Thread(new Runnable(){
			public void run(){
				
				//android用APIに接続
				APIConnection api = new APIConnection();					
				String result = api.changeQuickChargeUser(hm);
				
				//情報をjson化
				TransactionResult res = (TransactionResult) new Gson().fromJson(result, TransactionResult.class);
				
				//元のアプリのcallbackを呼び出す
				callback.onCustomerChange(res);
				
				//dialogを閉じる
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
		}).start();
		
	}
}
