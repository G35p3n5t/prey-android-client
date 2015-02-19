/*******************************************************************************
 * Created by Orlando Aliaga
 * Copyright 2012 Fork Ltd. All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 ******************************************************************************/
package com.prey.events.retrieves;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.prey.PreyLogger;
import com.prey.actions.HttpDataService;
import com.prey.events.Event;
import com.prey.events.manager.EventManager;
import com.prey.json.actions.Wifi;

import android.content.Context;

public class EventRetrieveDataWifi {

	public void execute(Context context, EventManager manager) {
		HttpDataService wifiHttpDataService = new Wifi().run(context, null, null);
		Map<String, String> wifiMapData = wifiHttpDataService.getDataList();
		JSONObject wifiJSon = new JSONObject();
		String ssid = null;
		try {
			ssid = wifiMapData.get(Wifi.SSID);
			
			JSONObject accessElementJSon = new JSONObject();
			accessElementJSon.put("ssid", ssid);
			accessElementJSon.put("mac_address", wifiMapData.get("mac_address"));
			accessElementJSon.put("signal_strength", wifiMapData.get("signal_strength"));
			accessElementJSon.put("channel", wifiMapData.get("channel"));
			accessElementJSon.put("security", wifiMapData.get("security"));

			if (Event.WIFI_CHANGED.equals(manager.event.getName())) {
				manager.event.setInfo(ssid);
			}

			wifiJSon.put("active_access_point", accessElementJSon);
		} catch (JSONException e) {
		}
		PreyLogger.d("wifi:" + ssid);
		manager.receivesData(EventManager.WIFI, wifiJSon);
	}
}
