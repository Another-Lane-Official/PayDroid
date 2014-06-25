package jp.alij.paydroid.data;


import jp.alij.paydroid.R;
import android.app.Activity;

/**
 * 
 * @author canu johann
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * 
 * indentデーターのバリデーション
 * 【サイトID】,【サイトパス】と【金額】が存在するかチェック
 *
 *　staticメッソード： valid
 *
 */
public class ValidTransactionRequest {
		
	public static ValidTransactionRequestResult valid (Activity mActivity, TransactionRequest tr){
		
		ValidTransactionRequestResult result = new ValidTransactionRequestResult();
		if (!validMandatoryFields(tr) || !validQuickCharge(tr)){
			result.setState(ValidationState.NG);
			result.setMessage(mActivity.getString(R.string.site_info_needed));
			return result;
		}		
		result.setState(ValidationState.OK);		
		return result;
	}
	
	/*
	 * 最低限のパラメータをチェック(サイトID、サイトPASS、金額)
	 */
	public static boolean validMandatoryFields(TransactionRequest tr){		
		return (tr.getSiteId()!=null && tr.getSitePass()!=null && tr.getAmount() != 0) ;
	}
	
	/*
	 * クイックチャージの場合はcustomerIDをチェック
	 */
	public static boolean validQuickCharge(TransactionRequest tr){
		return ( !tr.getIsQuickCharge() || (tr.getIsQuickCharge() && tr.getCustomerId()!=null)  );
	}

}
