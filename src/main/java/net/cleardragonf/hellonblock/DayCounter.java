//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.cleardragonf.hellonblock;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.util.MinecraftDayTime;

import java.awt.*;

import net.cleardragonf.hellonblock.DayCounter;

public class DayCounter {
    public static int days = ConfigurationManager.getInstance().getTimeTrack().node("========Time Tracking========", "Day: ").getInt();
    public static int CustWeek = 1;
    public static int config = 1;
    public static int week = 1;

    public DayCounter() {
    }

    public void Days() {
        MinecraftDayTime currentTime = Sponge.game().server().worldManager().defaultWorld().properties().dayTime();
        if (31 > currentTime.day()) {

        } else {
            currentTime.subtract(31,0,0);
        }

    }

    public static int getWeeklyConfig() {
        return week;
    }

}
