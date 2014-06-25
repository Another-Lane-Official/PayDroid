package jp.alij.paydroid.data;

import java.util.Calendar;
import jp.alij.paydroid.common.Utils;

/**
 * 
 * @author canu johann
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 *
 * カード情報を確認して、listenerのメッソードを呼び出す
 * new でインスタンス不可能
 *
 */
public class ValidCreditCard {
	
	//singleton
	public ValidCreditCard validCreditCard ;
	
	//データ
	String number, name, year, month;
		
	//private constructor
	private ValidCreditCard(){}

	//set values
	public static ValidCreditCard setup(String number, String name, String m, String y){		
		ValidCreditCard validCreditCard = new ValidCreditCard();
		validCreditCard.setNumber(number);		
		validCreditCard.setName(name);
		validCreditCard.setYear(y);
		validCreditCard.setMonth(m);		
		return validCreditCard;		
	}
	
	public void valid (TransactionRequest tr, ValidCreditCardListener listener){
		
		//パラメータの不備
		if (isNull(getNumber()) || isNull(getName()) ||isNull(getMonth()) || isNull(getYear())){
			listener.onWrongData();
			return;
		}
		
		//有効期限1
		if (getMonth().length() != 2  || getYear().length() != 2 || !isInt(getMonth()) || !isInt(getYear())){
			listener.onWrongExpirationDate();
			return;
		}
		
		//有効期限2
		Calendar currentCal = Calendar.getInstance();
		Calendar cardCal = Calendar.getInstance();		
		cardCal.set(Calendar.YEAR, 2000 + Integer.parseInt(getYear()));
		cardCal.set(Calendar.MONTH, Integer.parseInt(getMonth()));		
		if (currentCal.after(cardCal)){
			listener.onWrongExpirationDate();
			return;
		}
		
		//カード番号
		if (!Utils.isARealCard(getNumber())) {
			listener.onWrongCardNumber();
			return;
		}
			
		listener.onSuccess();			
		
	}
	
	private boolean isNull(String str){
		return (str == null || str.equals(""));
	}
	
	
	private boolean isInt(String str){  
	  try{  
	    int i = Integer.parseInt(str);  
	  }catch(NumberFormatException nfe){  
	    return false;  
	  }
	  return true;  
	}

	
	/*****************************/
	/***** GETTTER SETTER  ******/
	/*****************************/
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
