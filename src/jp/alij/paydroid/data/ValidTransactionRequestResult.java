package jp.alij.paydroid.data;

/**
 * 
 * @author canu johann
 * �A�i�U�[���[���i���j
 * http://www.alij.ne.jp
 * 
 * API����Ԃ��Ă���json���I�u�W�F�N�g�ɓ���Ă����܂�
 *
 */
public class ValidTransactionRequestResult {

	public ValidationState state;
	public String message;
	
	public ValidationState getState() {
		return state;
	}
	public void setState(ValidationState state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 
	
	
}
