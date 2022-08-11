package com.tobProfitTracker;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;
import tobProfitTracker.TobProfitTrackerPlugin;

public class TobProfitTrackerTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TobProfitTrackerPlugin.class);
		RuneLite.main(args);
	}
}