package jp.alij.paydroid.common;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * 
 * staticなもの
 *
 */
public class Consts {
	
	public static final String API_ROOT = "http://android.alij.ne.jp/";
	public static final String API_SETTLEMENT_URI = API_ROOT + "do_settlement";
	public static final String API_QUICK_CHARGE_CHANGE_URI = API_ROOT + "quick_charge_change";
	
	public static final String TRANSACTION_INDENT = "transaction_indent";
	public static final String RESPONSE_PAYMENT = "another_lane_response_settlement" ;
	public static final String RESPONSE_MSG_DATA = "msg" ;
	public static final String RESPONSE_STATE_DATA = "state" ;
	public static final String RESPONSE_DATA = "another-lane-response_data" ;
	
	public static final String RESULT_BUNDLE = "result";
	
	//asyncHandler
	public static final int ASYNC_HANDLER_SUCCESS = 1;
	public static final int ASYNC_HANDLER_CANCEL = -99;
	
	//state
	public static final int RESPONSE_STATE_DATA_SUCCESS = 1; 
	public static final int RESPONSE_STATE_DATA_ERROR = 2; 
	public static final int RESPONSE_STATE_DATA_CANCEL = -101; 
	
	
	

}
