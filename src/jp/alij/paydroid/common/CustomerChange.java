package jp.alij.paydroid.common;

import java.util.HashMap;

import jp.alij.paydroid.data.CustomerChangeCallback;
import jp.alij.paydroid.data.TransactionRequest;
import jp.alij.paydroid.data.TransactionResult;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * @version　1.0
 * 
 */
public class CustomerChange {

	public HashMap<String, String> hm ;
	public Activity mActivity ;
	public CustomerChangeCallback mCallback;
	public TransactionRequest mTransaction;
	
	public CustomerChange(Activity activity, TransactionRequest tr, CustomerChangeCallback callback){
		mActivity =activity;
		mCallback = callback;
		mTransaction = tr;
	}
	
	//既存顧客の情報を変更
	public void changeInfo(){		
		//パラメータ設定
		hm = new HashMap<String, String>();
		hm.put(APIConnection.SITE_ID_PARAM, mTransaction.getSiteId());
		hm.put(APIConnection.SITE_PASS_PARAM, mTransaction.getSitePass());
		hm.put(APIConnection.CARD_NAME_PARAM, mTransaction.getCardName());
		hm.put(APIConnection.CARD_NO_PARAM, mTransaction.getCardNo());
		hm.put(APIConnection.CARD_MONTH_PARAM, mTransaction.getCardMonth());
		hm.put(APIConnection.CARD_YEAR_PARAM, mTransaction.getCardYear());
		
		new getUserInfoAsync().execute();
		
	}
	
	/*APIに接続し、情報を取得*/
	class getUserInfoAsync extends AsyncTask<Void, Void, Void> {

		String result;
		ProgressDialog dialog ;
		
		@Override
		protected void onPreExecute(){
			dialog = new CustomDialog(mActivity,false);
			dialog.show();
		}
		
		protected Void doInBackground(Void... arg0) {
			try {
				APIConnection api = new APIConnection();					
				result = api.changeQuickChargeUser(hm);			
				return null;
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(Void v) {
			
			if(dialog.isShowing()){dialog.dismiss();}
			
			if (result!= null){
				//情報をjson化
				TransactionResult res = (TransactionResult) new Gson().fromJson(result, TransactionResult.class);
				mCallback.onCustomerChange(res);
			}else{
				mCallback.onCustomerChange(null);
			}

		}

	}
	
}
