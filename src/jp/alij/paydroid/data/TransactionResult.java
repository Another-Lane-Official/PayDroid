package jp.alij.paydroid.data;

/**
 * @author canu johann
 * �A�i�U�[���[���i���j
 * http://www.alij.ne.jp
 *
 */
public class TransactionResult {

	public String msg;
	public int state ;
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	} 
}
