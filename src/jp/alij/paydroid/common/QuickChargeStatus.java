package jp.alij.paydroid.common;

/**
 * 
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * 
 * トランザクションのクイックチャージ使用情報
 *
 */
public class QuickChargeStatus {
	
	//クイックチャージ未使用
	public static final int NO_QUICK_CHARGE = 0 ; 
	
	//クイックチャージ使用：初回決済
	public static final int QUICK_CHARGE_FIRST_TIME = 1;
	
	//クイックチャージ使用：２度目以降の決済
	public static final int QUICK_CHARGE_SECOND_TIME_AND_MORE = 2;
	
}
