package jp.alij.paydroid.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * 
 * 正規表現クラス
 * 各カードブランドのバリデーション
 */
public class RegexUtils {

	/*
	 * 文字列と正規表現のマッチングテスト
	 */
	public static boolean regMatch(String p, String s) {
		if (null == s) {
			return false;
		}
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}
	
	/*
	 * カードブランドの正規表現（全桁）
	 */
	public static final String REG_AMEX_FULL 	= "^3[47][0-9]{13}$";
	public static final String REG_DINERS_FULL	= "^3((?:0[0-5]|[689][0-9])[0-9]{11}|095[0-9]{10})$";
	public static final String REG_JCB_FULL 	= "^(?:2131|1800|35\\d{3})\\d{11}$";
	public static final String REG_VISA_FULL 	= "^4[0-9]{15}$";
	public static final String REG_MASTER_FULL 	= "^5[1-5][0-9]{14}$";
	public static final String TEST_CARD 		= "4000000000000000";
	
}
