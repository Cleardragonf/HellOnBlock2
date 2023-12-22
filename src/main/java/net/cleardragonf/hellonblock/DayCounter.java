package net.cleardragonf.hellonblock;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.util.MinecraftDayTime;

public class DayCounter {
    public static int week = 1;

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    // Constructor
    public DayCounter() {
        MinecraftDayTime currentTime = Sponge.game().server().worldManager().defaultWorld().properties().dayTime();

        if (30 > currentTime.day()) {
            if (isBetween(currentTime.day(), 1, 7)) {
                week = 1;
            } else if (isBetween(currentTime.day(), 8, 14)) {
                week = 2;
            } else if (isBetween(currentTime.day(), 15, 21)) {
                week = 3;
            } else if (isBetween(currentTime.day(), 22, 28)) {
                week = 4;
            } else {
                week = 5;
            }
        } else {
            // Set the day to 0 by creating a new MinecraftDayTime object
            MinecraftDayTime newTime = MinecraftDayTime.of(1, 6, 0).subtract(1,0,0);
            Sponge.game().server().worldManager().defaultWorld().properties().setDayTime(newTime);
        }
    }

    public static int getWeeklyConfig() {
        return week;
    }
}