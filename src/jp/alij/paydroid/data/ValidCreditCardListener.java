package jp.alij.paydroid.data;

public interface ValidCreditCardListener {

	//�f�[�^�s��
	public void onWrongData();
	
	//�L�������̖��
	public void onWrongExpirationDate();
	
	//�J�[�h�ԍ����������Ȃ�
	public void onWrongCardNumber();
	
	//����
	public void onSuccess();
	
}
