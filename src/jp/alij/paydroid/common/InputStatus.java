package jp.alij.paydroid.common;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * 
 * 決済画面の入力項目のカテゴリ
 * 0＝未使用
 * 1＝任意
 * 2＝必須
 *
 */
public class InputStatus {

	//Input is not displayed
	public static final int INPUT_STATUS_NOT_USED = 0;
	
	//Input is optional
	public static final int INPUT_STATUS_OPTIONAL = 1;
	
	//Input is mandatory
	public static final int INPUT_STATUS_MANDATORY = 2;
}
