package jp.alij.paydroid.common;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 *
 */
public class Utils {
	
	public static boolean isARealCard(String str){
		if(isDinersCard(str) || isVisaCard(str) || isMasterCard(str)
				|| isAmexCard(str) || isJcbCard(str) || isTestCard(str) ){
			return true;			
		}else{
			return false;
		}
	}
	
	/**
	 * AMEXカード
	 * @param str
	 * @return
	 */
	public static boolean isAmexCard(String str) {
		return RegexUtils.regMatch(RegexUtils.REG_AMEX_FULL, str);
	}
	
	/**
	 * MASTERカードバリデーション
	 * @param str
	 * @return
	 */
	public static boolean isMasterCard(String str) {
		return RegexUtils.regMatch(RegexUtils.REG_MASTER_FULL, str);
	}
	
	/**
	 * VISAカードバリデーション
	 * @param str
	 * @return
	 */
	public static boolean isVisaCard(String str) {
		return RegexUtils.regMatch(RegexUtils.REG_VISA_FULL, str);
	}
	
	/**
	 * DINERSカードバリデーション
	 * @param str
	 * @return
	 */
	public static boolean isDinersCard(String str) {
		return RegexUtils.regMatch(RegexUtils.REG_DINERS_FULL, str);
	}
	
	/**
	 * JCBカードバリデーション
	 * @param str
	 * @return
	 */
	public static boolean isJcbCard(String str) {
		return RegexUtils.regMatch(RegexUtils.REG_JCB_FULL, str);
	}
	
	public static boolean isTestCard(String str) {
		if(str.equals(RegexUtils.TEST_CARD)){
			return true;
		}else{
			return false;
		}
		
	}

}
