package jp.alij.paydroid.data;


import jp.alij.paydroid.R;
import android.app.Activity;

/**
 * 
 * @author canu johann
 * �A�i�U�[���[���i���j
 * http://www.alij.ne.jp
 * 
 * indent�f�[�^�[�̃o���f�[�V����
 * �y�T�C�gID�z,�y�T�C�g�p�X�z�Ɓy���z�z�����݂��邩�`�F�b�N
 *
 *�@static���b�\�[�h�F valid
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
	 * �Œ���̃p�����[�^���`�F�b�N(�T�C�gID�A�T�C�gPASS�A���z)
	 */
	public static boolean validMandatoryFields(TransactionRequest tr){		
		return (tr.getSiteId()!=null && tr.getSitePass()!=null && tr.getAmount() != 0) ;
	}
	
	/*
	 * �N�C�b�N�`���[�W�̏ꍇ��customerID���`�F�b�N
	 */
	public static boolean validQuickCharge(TransactionRequest tr){
		return ( !tr.getIsQuickCharge() || (tr.getIsQuickCharge() && tr.getCustomerId()!=null)  );
	}

}
