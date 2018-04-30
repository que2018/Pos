package com.jiusite.pos.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jiusite.helper.Func;
import com.jiusite.constant.ADDR;
import com.jiusite.constant.CONS;
import com.jiusite.constant.STATS;
import com.jiusite.network.GetNetData;
import com.jiusite.pos.database.SettingTable;
import com.jiusite.pos.database.model.Setting;
import com.jiusite.notification.PosDialog;
import com.jiusite.session.ScreenSession;


public class ServerSetActivity extends Activity {

	private Button connectButton;
	private NumberPicker ip1Picker;
	private NumberPicker ip2Picker;
	private NumberPicker ip3Picker;
	private NumberPicker ip4Picker;
	private ProgressBar progressbar;
	private RelativeLayout panelItems;
	private SettingTable settingTable;

    @Override
    protected void onCreate(Bundle savedInstanceSTATSe) {
        super.onCreate(savedInstanceSTATSe);
        setContentView(R.layout.server_set);

		settingTable = SettingTable.getInstance(ServerSetActivity.this);

		connectButton = (Button)findViewById(R.id.connect);
		progressbar = (ProgressBar)findViewById(R.id.progressbar);
		panelItems = (RelativeLayout)findViewById(R.id.panel_items);
		
		//set number picker
        ip1Picker = (NumberPicker)findViewById(R.id.ip1);
		ip2Picker = (NumberPicker)findViewById(R.id.ip2);
		ip3Picker = (NumberPicker)findViewById(R.id.ip3);
		ip4Picker = (NumberPicker)findViewById(R.id.ip4);

        ip1Picker.setMinValue(0); ip1Picker.setMaxValue(255); ip1Picker.setValue(192);
		ip2Picker.setMinValue(0); ip2Picker.setMaxValue(255); ip2Picker.setValue(168);
		ip3Picker.setMinValue(0); ip3Picker.setMaxValue(255); ip3Picker.setValue(1);
		ip4Picker.setMinValue(0); ip4Picker.setMaxValue(255); ip4Picker.setValue(40);

        ip1Picker.setWrapSelectorWheel(true);
		ip2Picker.setWrapSelectorWheel(true);
		ip3Picker.setWrapSelectorWheel(true);
		ip4Picker.setWrapSelectorWheel(true);
		
		//set button position
		LayoutParams params = (LayoutParams)connectButton.getLayoutParams();
		params.rightMargin = ScreenSession.connbtnRightMargin;
		params.bottomMargin = ScreenSession.connbtnRightMargin;
		connectButton.setLayoutParams(params);
		
		//connect listener
		connectButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
				
				if(networkInfo != null && networkInfo.isConnected()) {
					//display progressbar
					panelItems.setVisibility(View.GONE);
					progressbar.setVisibility(View.VISIBLE);
					
					//connect server
					ConnectTask connectTask = new ConnectTask();
					connectTask.execute();
					
				} else {
					String content = getResources().getString(R.string.no_network_connection);
					String btnText = getResources().getString(R.string.ok);

					final PosDialog posDialog = new PosDialog(ServerSetActivity.this);
					posDialog.setContent(content);
					posDialog.setRightBtnText(btnText);
					posDialog.show();

					Button buttonRight = (Button)posDialog.findViewById(R.id.button_right);

					buttonRight.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							posDialog.dismiss();
							panelItems.setVisibility(View.VISIBLE);
							YoYo.with(Techniques.BounceInUp).duration(400).playOn(connectButton);
						}
					});
				}
			}
		});
    }

	class ConnectTask extends AsyncTask<Void, Void, Void> {

		private int status;
		private String ipaddress;

		@Override
        protected Void doInBackground(Void... params) {
			try {
				String ip1 = Integer.toString(ip1Picker.getValue());
				String ip2 = Integer.toString(ip2Picker.getValue());
				String ip3 = Integer.toString(ip3Picker.getValue());
				String ip4 = Integer.toString(ip4Picker.getValue());
				ipaddress = ip1 + "." + ip2 + "." + ip3 + "." + ip4;

				String serverIP = Func.addHttpHeader(ipaddress);
				String serverLookupADDR = serverIP + "/pos/mobile/identity/is_server";		
				
				JSONObject outdata = GetNetData.getResult(serverLookupADDR);
				int httpCode = outdata.getInt("http_code");
				
				if(httpCode == STATS.HTTP_OK) {
					JSONObject data = (JSONObject)outdata.get("data");
					boolean isServer = data.getBoolean("is_server");
				
					if(isServer) {
						status = STATS.CONN_IS_SERVER;
					} else {
						status = STATS.CONN_NOT_SERVER;
					}
				} else {
					status = STATS.CONN_NOT_SERVER;
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
        protected void onPostExecute(Void v) {
			//hide progressbar
			progressbar.setVisibility(View.GONE);

			if (status == STATS.CONN_NOT_SERVER) {
				String content = getResources().getString(R.string.not_able_find_pos_server);
				String btnText = getResources().getString(R.string.ok);

				final PosDialog posDialog = new PosDialog(ServerSetActivity.this);
				posDialog.setContent(content);
				posDialog.setRightBtnText(btnText);
				posDialog.show();

				Button buttonRight = (Button)posDialog.findViewById(R.id.button_right);

				buttonRight.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						posDialog.dismiss();
						panelItems.setVisibility(View.VISIBLE);
						YoYo.with(Techniques.BounceInUp).duration(400).playOn(connectButton);
					}
				});
			} else {
				//update database
				Setting setting = new Setting();
				setting.setKey("server_ip");
				setting.setValue(ipaddress);
				setting.setType(CONS.SETTING_TEXT);	
				setting.setNameId(R.string.server_ip);
				settingTable.insertSetting(setting);
				
				//update ADDR
				String serverIP = Func.addHttpHeader(ipaddress);
				ADDR.updateADDR(serverIP);

				//go to menu import
				Intent intent = new Intent(ServerSetActivity.this, DataSyncActivity.class);
				startActivity(intent);
				ServerSetActivity.this.finish();
			}
		}
	}
}
