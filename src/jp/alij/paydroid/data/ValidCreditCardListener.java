package jp.alij.paydroid.data;

public interface ValidCreditCardListener {

	//データ不備
	public void onWrongData();
	
	//有効期限の問題
	public void onWrongExpirationDate();
	
	//カード番号が正しくない
	public void onWrongCardNumber();
	
	//成功
	public void onSuccess();
	
}
