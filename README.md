# PayDroid :Androidアプリ内課金　

*by Another Lane*

##目次

```
	1. 目次

	2. 導入の前に
		I   本仕様書について
		II  ご注意
		III お問い合わせ

	3. システム概要
		I   Paydroid方式
		II  パラメータ
		III リダイレクト

	4. 都度決済概要
		I   都度決済
		II  クイックチャージ
				● 初回決済時の送信必須パラメータ
			 	● 初回以降決済時の送信必須パラメータ
		III 会員カード情報変更

	5. 導入方法
		I   環境設定
		II  都度決済実装
				● manifestファイルを更新	
				● Activity修正
				● クイックチャージご利用の場合
					⇒ 初回決済
					⇒ 二度も以降の決済
				● 決済画面起動
				● 決済完了後
		III 会員カード情報変更

	6. リファレンス
		I   表示状態
		II  クイックチャージ状態
		III 必須パラメータ
		IV  クイックチャージパラメータ
		V   顧客パラメータ (顧客にご入力頂く項目)

```

## 導入の前に

### 本仕様書について

本仕様書は**アナザーレーンインク**が提供する、オンラインクレジット決済サービスの接続仕様書です。

### ご注意

* Androidアプリ専用のSDKとなります。
* Eclipseユーザー向けの仕様書となります。
* 実装サンプールは[こちら](https://github.com/Another-Lane-Official/PayDroid-sample)にあります。

### お問い合わせ

アナザーレーン加盟店専用窓口		03-5909-0956
お問い合わせフォーム	[https://www.alij.ne.jp/contact/contact.php](https://www.alij.ne.jp/contact/contact.php)



## システム概要

### Paydroid方式

PayDroid方式は、加盟店様のAndroidアプリ内にSDKを組み込んでいただくことで、**簡単にアプリ内課金を実施いただきます**。

弊社システムで決済処理終了後、**決済結果をリダイレクト**（返信）します。

加盟店様では、送られて来た決済結果を解析し、決済結果に応じた処理を行ってください。

> 決済結果は、決済情報を送っていただいたAPIにしか返すことができません。加盟店様のAPIで送信と受信の機能が必要となります。


### パラメータ

* 弊社指定のパラメータ（必須・任意）のみご利用になれます。
* 全角文字を送信する場合は文字コードをUTF-8で送信する必要があります。

### リダイレクト

PayDroid方式では、テキストとして決済結果情報を返信しますので、加盟店様で解析し処理してください。


## 都度決済概要


### 都度決済

決済が発生するごとに、その都度決済を行うことから、都度決済と称しています。
支払回数は1回のみで分割やリボ払いは不可です。

### クイックチャージ

初回の決済時にお客様のカード情報を弊社サーバへ保持することにより、次回以降はカード情報入力の手間を省く事ができ、
お客様の利便性の向上が可能です。**お客様の特定方法はCustomerPassかemailから選択することができます**。

尚、次回以降の決済時には初回で決済したお客様の特定情報が必要になりますので加盟店様で
お客様のデータを管理して頂く必要があります。

#### 初回決済時の送信必須パラメータ

|パラメータ  |参照      |
|:--------|:--------|
|加盟店ID|加盟店様のユニックID|
|加盟店パス|加盟店様のパスワード|
|金額|決済金額|
|カード名義|クレジットカードの所有者の名義|
|カード番号|クレジットカードの所有者の番号|
|カード期限（月）|クレジットカードの所有者の有効期限（月）|
|カード期限（年）|クレジットカードの所有者の有効期限（年）|
|カスタマーID|顧客のユニックID（加盟店様に管理していただく）|
|カスタマーパス|顧客のパスワード（加盟店様に管理していただく）|
|カスタマーメール|顧客のメール（加盟店様に管理していただく）|


#### 初回以降決済時の送信必須パラメータ

|パラメータ  |参照      |
|:--------|:--------|
|加盟店ID|加盟店様のユニックID|
|加盟店パス|加盟店様のパスワード|
|カスタマーID|顧客のユニックID（加盟店様に管理していただく）|
|カスタマーパス|顧客のパスワード（加盟店様に管理していただく）|
|カスタマーメール|顧客のメール（加盟店様に管理していただく）|
|金額|決済金額|


### 会員カード情報変更

初回決済時に登録された顧客ID、顧客パスワードに紐づくカード情報の変更ができる機能です。更新が完了すると次回クイックチャージ決済時のカード情報が可能されます。

> ※クイックチャージ機能を使用している加盟店様に限ります。

## 導入方法


### 環境設定

* 本SDKファイルをダウンロード　(*1)
* Android新規プロジェクト作成
* プロジェクトに右クリック【properties】
* 【properties】から【android】を選択
* 【ライブラリー追加】でSDKを追加　(*2)

##### SDKのダウンロード(*1)

```
#gitでダウンロードする場合は
git clone https://github.com/Another-Lane-Official/paydroid-sdk.git

#gitがインストールされていない場合
[コチラ](https://github.com/Another-Lane-Official/paydroid-sdk)からダウンロード
```

#####  ライブラリー追加(*1)

![ライブラリー追加](http://s14.postimg.org/m8miizbdd/import_project.png)


### 都度決済実装

#### manifestファイルを更新

```xml
<!--下記パーミッションを追加-->
<uses-permission android:name="android.permission.INTERNET" />

<!-- 下記activityを追加 -->
<activity
    android:name="jp.alij.paydroid.activities.PaymentActivity"
    android:label="@string/app_name" >
</activity>
```

#### Activity修正

>  一つの【TransactionRequest】オブジェクトを生成し、設定を行います。

```java
TransactionRequest tr = new TransactionRequest();

//必須パラメータ
tr.setAmount(210);	　　　　　　　//金額
tr.setSiteId("99999928");　　　//加盟店ID
tr.setSitePass("qYhWsRLH"); //加盟店パスワード

/*
 * 任意パラメータ
 * 契約に合わせて表示・非表示を設定してください
 * (未設定の場合=false）
 * 
 * 項目やステータス一覧は【リファレンス】をご参照ください
 */
tr.setVisibility_mail(InputStatus.INPUT_STATUS_MANDATORY);
tr.setVisibility_adr1(InputStatus.INPUT_STATUS_OPTIONAL);
tr.setVisibility_capital(InputStatus.INPUT_STATUS_OPTIONAL);
```


#### クイックチャージご利用の場合

##### 初回決済

上記のactivity修正にコードを加えます：

```java
tr.setQuickChargeStatus(QuickChargeStatus.QUICK_CHARGE_FIRST_TIME);

//契約に合わせて, idとpass(もしくはメール)を渡す
tr.setCustomerId("xxx");
tr.setCustomerPass("fsdf"); //customerPassかCustomerMail
```

##### 二度も以降の決済

```java
//必須パラメータ
tr.setAmount(210);
tr.setSiteId("99999928");
tr.setSitePass("qYhWsRLH");    		

//契約に合わせて, idとpass(もしくはメール)を渡す
tr.setCustomerId("xxx");
tr.setCustomerPass("fsdf");

//クイックチャージフラッグ
tr.setQuickChargeStatus(QuickChargeStatus.QUICK_CHARGE_SECOND_TIME_AND_MORE);
```

####　決済画面起動

```java
//SDKを呼び出す
Intent i = new Intent(this, jp.alij.paydroid.activities.PaymentActivity.class);		
i.putExtra(Consts.TRANSACTION_INDENT, tr);
startActivity(i);
```

#### 決済完了後

> broadcastReceiverで配信された決済情報を取得

```java    
/* ReceiverBroadcastを設定*/
private void setReceiver() {
    IntentFilter filter = new IntentFilter(Consts.RESPONSE_PAYMENT);
    receiver = new SettlementReceiver();
    registerReceiver(receiver, filter);
}
  
/*
 * 決済が完了した時または「戻る」ボタンが押された時にBroadcastが配信され
 * 下記receiverが情報を取得し、データを処理します。
 */
private class SettlementReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context arg0, Intent arg1) {

		//決済情報を持つオブジェクト
		TransactionResult result = arg1.getExtras().getParcelable(Consts.RESPONSE_DATA); 
		switch (result.getState()){
		case Consts.RESPONSE_STATE_DATA_SUCCESS:	//決済成功
			Toast.makeText(getApplicationContext(), "決済は完了しました。決済ID：" + result.getTransactionId(), Toast.LENGTH_LONG).show();
			break;				
		case Consts.RESPONSE_STATE_DATA_CANCEL:	//決済中止
			Toast.makeText(getApplicationContext(), "決済が中止されました。" , Toast.LENGTH_LONG).show();
			break;
		}			
	}
}
```

### 会員カード情報変更

必要なパラメータを設定し、CustomerChangeをインスタンス化します。changeInfoメッソードを呼び出すと、
別threadで顧客情報が送信され、データ処理後に3つ目のパラメータとして定義したCustomerChangeCallbackのonCustomerChangeが実行されます。

```java

//加盟店様関連パラメータ
tr.setSiteId("99999928");
tr.setSitePass("qYhWsRLH"); 

//顧客関連パラメータ
tr.setCustomerId("xxx");
tr.setCustomerPass("fsdf");

//カード関連パラメータ
tr.setCardName("test");
tr.setCardNo("44444444444444444");
tr.setCardMonth("12");
tr.setCardYear("18");

CustomerChange customerChange = new CustomerChange(this, tr, new CustomerChangeCallback() {
	@Override
	public void onCustomerChange(TransactionResult tr) {
		if(tr!= null && tr.getState()== Consts.RESPONSE_STATE_DATA_SUCCESS)
		Toast.makeText(getApplicationContext(), "顧客情報の変更は完了になりました。", Toast.LENGTH_LONG).show();
		else
		Toast.makeText(getApplicationContext(), tr.getMsg(), Toast.LENGTH_LONG).show();
	}
});

//情報取得
customerChange.changeInfo();    
```

## リファレンス

### 表示状態

|表示状態 |必須性|コード                              |
|:-------|:-----|:---------------------------------|
|表示     |必須  |InputStatus.INPUT_STATUS_MANDATORY|
|表示     |任意  |InputStatus.INPUT_STATUS_OPTIONAL |
|非表示   |-     |InputStatus.INPUT_STATUS_NOT_USED |

### クイックチャージ状態

|表示状態                        |コード                              |
|:------------------------------|:---------------------------------|
|クイックチャージ未使用               |QuickChargeStatus.NO_QUICK_CHARGE|
|クイックチャージ使用：初回決済        |QuickChargeStatus.QUICK_CHARGE_FIRST_TIME |
|クイックチャージ使用：２度目以降の決済 |QuickChargeStatus.QUICK_CHARGE_SECOND_TIME_AND_MORE |


### 必須パラメータ

|項目     |セットメッソード   |型    |デフォルト|
|:------- |:------------|:-----|:------|
|加盟店ID  |setSiteId()  |String|null   |
|加盟店パス |setSitePass()|String|null   |
|金額      |setAmount()  |int   |0      |

### クイックチャージパラメータ

|項目             |セットメッソード        |型     |デフォルト|参考|
|:----------------|:-----------------|:------|:-----|:----|
|クイックチャージのご利用|setQuickChargeStatus()|int|false |【】をご参照ください|
|顧客ID            |setCustomerId     |String|null   |-              |
|顧客パス           |setCustomerPass   |String|null   |-              |


### 顧客パラメータ (顧客にご入力頂く項目)

|項目              |セットメッソード(表示）   |型     |デフォルト|
|:----------------|:------------------- |:------|:-----|
|IPアドレスの表示・非表示|setVisibility_ipAdr  |boolean|false|
|郵便番号の表示・非表示|setVisibility_zip    |boolean|false|
|都道府県の表示・非表示|setVisibility_capital|boolean|false|
|住所１の表示・非表示  |setVisibility_adr1   |boolean|false|
|住所２の表示・非表示  |setVisibility_adr2   |boolean|false|
|名前の表示・非表示    |setVisibility_name   |boolean|false|
|メールの表示・非表示    |setVisibility_mail  |boolean|false|
|国の表示・非表示      |setVisibility_country|boolean|false|
|電話番号の表示・非表示 |setVisibility_tel   |boolean|false|

### その他のパラメータ(表示なし・キック用)

|      パラメータ    |メッソード           |型     |
|:----------------|:----------------|:------|
|メモ               |setNote         |String |
|決済ID            |setTransactionId|String|
|アイテムID          |setItemId        |String|
