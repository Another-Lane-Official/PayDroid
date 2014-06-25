package jp.alij.paydroid.listeners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import jp.alij.paydroid.R;
import jp.alij.paydroid.activities.PaymentActivity;
import jp.alij.paydroid.common.APIConnection;
import jp.alij.paydroid.common.Consts;
import jp.alij.paydroid.common.CustomDialog;
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
 * �A�i�U�[���[���i���j
 * http://www.alij.ne.jp
 * 
 * ���ω�ʂ́y���ρz�{�^���N���b�N����
 *
 */
public class OnClickSettlement implements OnClickListener, DialogInterface.OnClickListener {

	public PaymentActivity mActivity;
	public String fullCard,month,year,name;
	private ProgressDialog dialog;
		
	public OnClickSettlement(PaymentActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		
		//�L�[�{�[�h�������I�ɕ���
		View focus= mActivity.getCurrentFocus();
		if(focus!=null){
			InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
				
		//�J�[�h�ԍ��擾
		fullCard = mActivity.cardNo1.getText().toString()
				+ mActivity.cardNo2.getText().toString()
				+ mActivity.cardNo3.getText().toString()
				+ mActivity.cardNo4.getText().toString();
		
		month = mActivity.cardExpMonth.getText().toString();
		year = mActivity.cardExpYear.getText().toString();
		name = mActivity.cardName.getText().toString();

		// �f�[�^�[�o���f�[�V����
		if (!validateData()) { return;} 
			
		//����
		prepareForSettlement();
						
	}

	/*
	 * �p�����[�^���Z�b�g
	 */
	private HashMap<String, String> getParametersList() {

		HashMap<String, String> hm = new HashMap<String, String>();

		// ���σx�[�W�b�N���
		hm.put(APIConnection.SITE_ID_PARAM, mActivity.tr.getSiteId());
		hm.put(APIConnection.SITE_PASS_PARAM, mActivity.tr.getSitePass() );
		hm.put(APIConnection.AMOUNT_PARAM, String.valueOf(mActivity.tr.getAmount()));

		// quickcharge����
		hm.put(APIConnection.CUST_ID_PARAM, mActivity.tr.getCustomerId());
		hm.put(APIConnection.CUST_PASS_PARAM, mActivity.tr.getCustomerPass());

		if(!mActivity.tr.getIsQuickCharge()){
			
			//�J�[�h���i�K�{�j
			hm.put(APIConnection.CARD_NAME_PARAM, name);
			hm.put(APIConnection.CARD_NO_PARAM, fullCard);
			hm.put(APIConnection.CARD_MONTH_PARAM, month);
			hm.put(APIConnection.CARD_YEAR_PARAM, year);
	
			// �ݒ�\����
			hm.put(APIConnection.ZIP_PARAM, mActivity.userZipEdit.getText().toString());
			hm.put(APIConnection.CAPITAL_PARAM, mActivity.userCapitalEdit.getText().toString());
			hm.put(APIConnection.ADR1_PARAM, mActivity.userAdr1Edit.getText().toString());
			hm.put(APIConnection.ADR2_PARAM, mActivity.userAdr2Edit.getText().toString());
			hm.put(APIConnection.NAME_PARAM, mActivity.userNameEdit.getText().toString());
			hm.put(APIConnection.TEL_PARAM, mActivity.userPhoneEdit.getText().toString());
			hm.put(APIConnection.MAIL_PARAM, mActivity.userMailEdit.getText().toString());
			hm.put(APIConnection.OTHER_PARAM, mActivity.userNameText.getText().toString());
		}
		
		return hm;
	}

	/*
	 * �ݒ�\�ȍ���
	 */
	private boolean validateData() {
		ArrayList<EditText> al = mActivity.getMandoriesFields();
		for (EditText et : al) {
			if (et.getText().toString().equals("")) {
				displayError(mActivity.getString(jp.alij.paydroid.R.string.mandatory_parameters_issue));
				return false;
			}
		}		
		return true;
	}

	/*
	 * ���ς��s���O��
	 * �J�[�h�����o���f�[�V����
	 */
	public void prepareForSettlement() {
		
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
				
				dialog = new CustomDialog((Activity) mActivity,false,OnClickSettlement.this);
				dialog.show();
				
				//network�𗘗p���邽�߁A�ʂ�thread�Ńf�[�^���������܂�
				new Thread(new Runnable(){
					public void run(){
						
						//android�pAPI�ɐڑ�
						APIConnection api = new APIConnection();					
						String result = api.doSettlement(getParametersList());
						
						//���ʂ�bundle�ɑ��
						Bundle b = new Bundle();
						b.putString(Consts.RESULT_BUNDLE,result);
						
						//asynHandler�ɏ���n��
						Message msg = Message.obtain();
						msg.what=Consts.ASYNC_HANDLER_SUCCESS;
						msg.setData(b);
						mActivity.asyncHandler.sendMessage(msg);
						
						//dialog�����
						if(dialog.isShowing()){
							dialog.dismiss();
						}
					}
				}).start();
				
			}
		});
		
	}

	private void displayError(String msg) {
		Toast.makeText(mActivity.getApplicationContext(), msg,
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub		
	}

}
