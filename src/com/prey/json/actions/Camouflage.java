/*******************************************************************************
 * Created by Orlando Aliaga
 * Copyright 2012 Fork Ltd. All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 ******************************************************************************/
package com.prey.json.actions;

import java.util.List;

import org.json.JSONObject;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import com.prey.PreyConfig;
import com.prey.actions.ActionResult;
import com.prey.actions.HttpDataService; 
import com.prey.json.JsonAction;
import com.prey.json.UtilJson;
import com.prey.net.PreyWebServices;

public class Camouflage extends JsonAction {

	public HttpDataService run(Context ctx, List<ActionResult> lista, JSONObject parameters) {
		return null;
	}

	public void start(Context ctx, List<ActionResult> lista, JSONObject parameters) {
		PreyWebServices.getInstance().sendNotifyActionResultPreyHttp(ctx, UtilJson.makeMapParam("start", "camouflage", "started"));
		PreyConfig.getPreyConfig(ctx).setCamouflageSet(true);

		ComponentName componentToDisabled = new ComponentName("com.prey", "com.prey.activities.LoginActivity");
		PackageManager pm = ctx.getPackageManager();
		pm.setComponentEnabledSetting(componentToDisabled, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		
		PreyConfig.getPreyConfig(ctx).setLastEvent("camouflage_start");
	}

	public void stop(Context ctx, List<ActionResult> lista, JSONObject options) {
		PreyWebServices.getInstance().sendNotifyActionResultPreyHttp(ctx, UtilJson.makeMapParam("stop", "camouflage", "stopped"));
		PreyConfig.getPreyConfig(ctx).setCamouflageSet(false);

		ComponentName componentToEnabled = new ComponentName("com.prey", "com.prey.activities.LoginActivity");
		PackageManager pm = ctx.getApplicationContext().getPackageManager();
		pm.setComponentEnabledSetting(componentToEnabled, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

		PreyConfig.getPreyConfig(ctx).setLastEvent("camouflage_stop");
	}
}
