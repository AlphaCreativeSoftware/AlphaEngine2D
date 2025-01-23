package org.alphacreative.time;

import org.alphacreative.manager.GameManager;

public class Time {
    public static double DeltaTime()
    {
        return 1.0000/GameManager.GetFPS();
    }
    public static double SyncTime()
    {
        return DeltaTime()*80.000;
    }
}
