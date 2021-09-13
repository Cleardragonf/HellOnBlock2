package net.cleardragonf.hellonblock;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.entity.living.animal.Animal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import net.cleardragonf.hellonblock.ConfigurationManager;
import org.spongepowered.api.registry.RegistryType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

public class ConfigurationManager
{
    private static ConfigurationManager instance = new ConfigurationManager();
    private Path configDir;
    private ConfigurationLoader<CommentedConfigurationNode> configLoader1;
    private ConfigurationLoader<CommentedConfigurationNode> TimeTrackLoader;
    private CommentedConfigurationNode config1;
    private CommentedConfigurationNode TimeTracker;

    public static ConfigurationManager getInstance()
    {
        return instance;
    }

    public void ConfigurationManager2(Path configDir)
    {
        this.configDir = configDir;
    }

    public void enable()
    {
        File Week1 = new File(String.valueOf(configDir), "Entity.config");
        File TimeTrackerTime = new File(String.valueOf(configDir), "TimeTracking.conf");

        this.configLoader1 = HoconConfigurationLoader.builder().file(Week1).build();
        this.TimeTrackLoader = HoconConfigurationLoader.builder().file(TimeTrackerTime).build();
        try
        {
            if (!Files.exists(configDir)) {
                Files.createDirectory(configDir);
            }
            if (!TimeTrackerTime.isFile()) {
                try
                {
                    TimeTrackerTime.createNewFile();
                    loadTime();
                    this.TimeTracker.node("========Time Tracking========").comment("The Point of this config is to keep track of the Time and Date");
                    this.TimeTracker.node("========Time Tracking========", "Day: ").comment("Day number in Game. Between 1-30").set("1");
                    this.TimeTracker.node("========Time Tracking========", "Time: ").comment("Set the Time in Game. Between 0 - 24000").set("0");
                    saveTime();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            if (!Week1.isFile()) {
                try
                {
                    Week1.createNewFile();
                    load1();
                    List<Class<? extends Entity>> classes = ImmutableList.of(Monster.class, Hostile.class);
                    List<EntityType> cet = Sponge.server().registry(RegistryTypes.ENTITY_TYPE).stream().filter((x) -> {
                        return classes.stream().anyMatch((y) -> {
                            return y.isAssignableFrom(x.getClass());
                        });
                    }).collect(Collectors.toList());
                    for (EntityType a :
                            cet) {
                        Sponge.server().broadcastAudience().sendMessage(Component.text("HOB Says ").append(a));
                    }
                    this.config1.node("=============Entity Control============").comment("This Portion of the Config is dedicated to the Control of Entities");
                    for (EntityType entity : cet){
                        for (int weekNumber = 1; weekNumber <= 5; weekNumber++) {
                            String week = null;
                            if(weekNumber != 5){
                                week = "Week " + weekNumber;
                            }else{
                                week = "HOB Night";
                            }
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "Number of " + entity.tagType().tagRegistry().referenced(Keys.DISPLAY_NAME.key()) + "'s to attempt: ").comment("Any Whole Number ~99999").set(10);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "The Chance of each " + entity.tagType().tagRegistry().referenced(Keys.DISPLAY_NAME.key()) + "actually spawning: ").comment("0-100").set(100);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "Maximum Range: ").set(5);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Health: ").set(false);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "Minimum Range: ").set(3);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Anger: ").set(false);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Projectile Damage: ").set(false);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Projectile Damage: ").set(10);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Explosion Damage: ").set(false);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Explosion Radius: ").set(5);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Custom Health: ").set(100);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Explosion: ").set(false);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Explosion Radius: ").set(10);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Drops=====", "Enable Custom Drops: ").set(false);
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Drops=====", "Custom Drops: ").set("");
                            this.config1.node("=============Entity Control============", entity.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Monetary Benifits=====", "Per Kill: ").comment("For Each Kill this is what you recieve").set(5);



                        }
                    }

                    this.config1.node("========General Week Properties========").comment("The Below is used for Week 1's General Configuration");
                    this.config1.node("========General Week Properties========", "Time Between Waves");
                    this.config1.node("========General Week Properties========", "Time Between Waves", "Time").comment("In Ticks(20ticks per second)").set("460");

                    save1();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            this.config1 = this.configLoader1.load();
            this.TimeTracker = this.TimeTrackLoader.load();
        }
        catch (IOException e) {}
    }

    private void loadTime()
    {
        try
        {
            this.TimeTracker = this.TimeTrackLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void load1()
    {
        try
        {
            this.config1 = this.configLoader1.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveTime()
    {
        try
        {
            this.TimeTrackLoader.save(this.TimeTracker);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void save1()
    {
        try
        {
            this.configLoader1.save(this.config1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadConfig()
    {
        try
        {
            this.config1 = this.configLoader1.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public CommentedConfigurationNode getConfig()
    {
        return this.config1;
    }

    public CommentedConfigurationNode getTimeTrack()
    {
        return this.TimeTracker;
    }
}
