package jp.alij.paydroid.listeners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import jp.alij.paydroid.R;
import jp.alij.paydroid.activities.PaymentActivity;
import jp.alij.paydroid.common.APIConnection;
import jp.alij.paydroid.common.Consts;
import jp.alij.paydroid.common.CustomDialog;
import jp.alij.paydroid.common.QuickChargeStatus;
import jp.alij.paydroid.common.Utils;
import jp.alij.paydroid.data.ValidCreditCard;
import jp.alij.paydroid.data.ValidCreditCardListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author canu johann
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * 
 * 決済画面の【決済】ボタンクリック処理
 *
 */
public class OnClickSettlement implements OnClickListener {

	public PaymentActivity mActivity;
	public String fullCard,month,year,name;
	private ProgressDialog dialog;
		
	public OnClickSettlement(PaymentActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		
		//キーボードを強制的に閉じる
		View focus= mActivity.getCurrentFocus();
		if(focus!=null){
			InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
				
		//カード番号取得
		fullCard = mActivity.cardNo1.getText().toString()
				+ mActivity.cardNo2.getText().toString()
				+ mActivity.cardNo3.getText().toString()
				+ mActivity.cardNo4.getText().toString();
		
		month = mActivity.cardExpMonth.getText().toString();
		year = mActivity.cardExpYear.getText().toString();
		name = mActivity.cardName.getText().toString();

		// データーバリデーション
		if (!validateData()) { return;} 
			
		//決済
		launchSettlement();
						
	}

	/*
	 * 設定可能な項目
	 */
	private boolean validateData() {
		ArrayList<EditText> al = mActivity.getMandoriesFields();		
		
		//必須項目チェック
		if(al!=null && al.size()!=0){			
			for (EditText et : al) {
				if (et.getText().toString().equals("")) {
					displayError(mActivity.getString(jp.alij.paydroid.R.string.mandatory_parameters_issue));
					return false;
				}
			}
		}
		
		//クイックチャージチェック
		if(mActivity.tr.getQuickChargeStatus() != QuickChargeStatus.NO_QUICK_CHARGE){
			if(mActivity.tr.getCustomerId() == null || 
			(mActivity.tr.getCustomerPass() == null && mActivity.userMailText.getText().equals("")) ){
				return false;
			}
		}
		
		return true;
	}

	/*
	 * 決済を行う前に
	 * カード情報をバリデーション
	 */
	public void launchSettlement() {
		
		ValidCreditCard.setup(fullCard, name, month, year).valid(mActivity.tr, new ValidCreditCardListener() {
			
			@Override
			public void onWrongExpirationDate() {
				displayError(mActivity.getString(R.string.data_not_correct_issue));				
			}
			
			@Override
			public void onWrongData() {
				displayError(mActivity.getString(R.string.data_not_correct_issue));				
			}
			
			@Override
			public void onWrongCardNumber() {
				displayError(mActivity.getString(R.string.card_number_issue));				
			}
			
			@Override
			public void onSuccess() {
				
				dialog = new CustomDialog((Activity) mActivity,false);
				dialog.show();
				
				//networkを利用するため、別のthreadでデータを処理します
				new Thread(new Runnable(){
					public void run(){
						
						//android用APIに接続
						APIConnection api = new APIConnection();					
						String result = api.doSettlement(getParametersList());
						
						//結果をbundleに代入
						Bundle b = new Bundle();
						b.putString(Consts.RESULT_BUNDLE,result);
						
						//asynHandlerに情報を渡す
						Message msg = Message.obtain();
						msg.what=Consts.ASYNC_HANDLER_SUCCESS;
						msg.setData(b);
						mActivity.asyncHandler.sendMessage(msg);
						
						//dialogを閉じる
						if(dialog.isShowing()){
							dialog.dismiss();
						}
					}
				}).start();
			}
		});
	}
	
	/*
	 * パラメータをセット
	 */
	private HashMap<String, String> getParametersList() {

		HashMap<String, String> hm = new HashMap<String, String>();

		// 決済ベージック情報
		hm.put(APIConnection.SITE_ID_PARAM, mActivity.tr.getSiteId());
		hm.put(APIConnection.SITE_PASS_PARAM, mActivity.tr.getSitePass() );
		hm.put(APIConnection.AMOUNT_PARAM, String.valueOf(mActivity.tr.getAmount()));

		// quickcharge引数
		hm.put(APIConnection.CUST_ID_PARAM, mActivity.tr.getCustomerId());
		hm.put(APIConnection.CUST_PASS_PARAM, mActivity.tr.getCustomerPass());

		//クイックチャージでない場合
		if(mActivity.tr.getQuickChargeStatus() != QuickChargeStatus.QUICK_CHARGE_SECOND_TIME_AND_MORE){
			
			//カード情報（必須）
			hm.put(APIConnection.CARD_NAME_PARAM, name);
			hm.put(APIConnection.CARD_NO_PARAM, fullCard);
			hm.put(APIConnection.CARD_MONTH_PARAM, month);
			hm.put(APIConnection.CARD_YEAR_PARAM, year);
	
			// 設定可能項目
			hm.put(APIConnection.ZIP_PARAM, mActivity.userZipEdit.getText().toString());
			hm.put(APIConnection.CAPITAL_PARAM, mActivity.userCapitalEdit.getText().toString());
			hm.put(APIConnection.ADR1_PARAM, mActivity.userAdr1Edit.getText().toString());
			hm.put(APIConnection.ADR2_PARAM, mActivity.userAdr2Edit.getText().toString());
			hm.put(APIConnection.NAME_PARAM, mActivity.userNameEdit.getText().toString());
			hm.put(APIConnection.TEL_PARAM, mActivity.userPhoneEdit.getText().toString());
			hm.put(APIConnection.MAIL_PARAM, mActivity.userMailEdit.getText().toString());
			hm.put(APIConnection.NOTE_PARAM, mActivity.userNoteEdit.getText().toString());
			hm.put(APIConnection.COUNTRY_PARAM, mActivity.userCountryEdit.getText().toString());
		}
		
		//その他の項目
		hm.put(APIConnection.TRANS_ID_PARAM, mActivity.tr.getTransactionId());
		hm.put(APIConnection.ITEM_ID_PARAM, mActivity.tr.getItemId());
		
		return hm;
	}

	/* エラー表示 */
	private void displayError(String msg) {
		Toast.makeText(mActivity.getApplicationContext(), msg,
				Toast.LENGTH_SHORT).show();
	}

}
