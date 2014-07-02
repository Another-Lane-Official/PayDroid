package jp.alij.paydroid.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author canu johann
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 *
 */
public class TransactionResult implements Parcelable {

	public String msg;
	public String TransactionId;
	public int state ;
	
	public TransactionResult() {}
	
	public TransactionResult(Parcel in) {
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.msg);
		dest.writeString(this.TransactionId);
		dest.writeInt(this.state);		
	}

	private void readFromParcel(Parcel in) {
		this.msg = in.readString();
		this.TransactionId = in.readString();		
		this.state = in.readInt();				
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public TransactionResult createFromParcel(Parcel in) {
			return new TransactionResult(in);
		}
		public TransactionResult[] newArray(int size) {
			return new TransactionResult[size];
		}
	};
		
	
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
	public String getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(String transactionId) {
		TransactionId = transactionId;
	} 
}
