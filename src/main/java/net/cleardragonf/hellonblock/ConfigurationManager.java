package net.cleardragonf.hellonblock;

import com.google.common.collect.ImmutableList;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.Monster;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.cleardragonf.hellonblock.ConfigurationManager;
import org.spongepowered.api.registry.DefaultedRegistryReference;
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
        File Week1 = new File(String.valueOf(this.configDir), "Entity.conf");
        File TimeTrackerTime = new File(String.valueOf(this.configDir), "TimeTracking.conf");

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
                    this.TimeTracker.node("========Time Tracking========", "Day: ").comment("Day number in Game. Between 1-30").getString("1");
                    this.TimeTracker.node("========Time Tracking========", "Time: ").comment("Set the Time in Game. Between 0 - 24000").getString("0");
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
                    List<EntityType> precet = Sponge.game().registry(RegistryTypes.ENTITY_TYPE).stream().collect(Collectors.toList());
                    List<EntityType> cet = new ArrayList<>();
                    for (EntityType entity :
                            precet) {
                        if(!entity.category().friendly()){
                            cet.add(entity);
                        }
                    }


                    /**
                    List<EntityType> cet = Sponge.game().registry(RegistryTypes.ENTITY_TYPE).stream().filter((x) -> {

                        return classes.stream().anyMatch((y) -> {
                            return y.isAssignableFrom(x.getClass());
                        });
                    }).collect(Collectors.toList());
                     **/
                    //List<EntityType> cet = new ArrayList<>();
                    //cet.add(EntityTypes.BEE.get());cet.add(EntityTypes.BAT.get());cet.add(EntityTypes.BLAZE.get());cet.add(EntityTypes.CAVE_SPIDER.get());cet.add(EntityTypes.CREEPER.get());cet.add(EntityTypes.DROWNED.get());
                    //cet.add(EntityTypes.ELDER_GUARDIAN.get());cet.add(EntityTypes.ENDERMAN.get());cet.add(EntityTypes.ENDERMITE.get());cet.add(EntityTypes.EVOKER.get());cet.add(EntityTypes.GHAST.get());cet.add(EntityTypes.GUARDIAN.get());
                    //cet.add(EntityTypes.GUARDIAN.get());cet.add(EntityTypes.HOGLIN.get());cet.add(EntityTypes.HUSK.get());cet.add(EntityTypes.ILLUSIONER.get());cet.add(EntityTypes.MAGMA_CUBE.get());cet.add(EntityTypes.PHANTOM.get());
                    //cet.add(EntityTypes.PIGLIN.get());cet.add(EntityTypes.PIGLIN_BRUTE.get());cet.add(EntityTypes.PILLAGER.get());cet.add(EntityTypes.RAVAGER.get());cet.add(EntityTypes.SILVERFISH.get());cet.add(EntityTypes.SKELETON.get());
                    //cet.add(EntityTypes.SLIME.get());cet.add(EntityTypes.SPIDER.get());cet.add(EntityTypes.STRIDER.get());cet.add(EntityTypes.VEX.get());cet.add(EntityTypes.VINDICATOR.get());cet.add(EntityTypes.WITCH.get());
                    //cet.add(EntityTypes.WITHER.get());cet.add(EntityTypes.WITHER_SKELETON.get());cet.add(EntityTypes.WOLF.get());cet.add(EntityTypes.ZOGLIN.get());cet.add(EntityTypes.ZOMBIE.get());cet.add(EntityTypes.ZOMBIE_HORSE.get());cet.add(EntityTypes.ZOMBIE_VILLAGER.get());cet.add(EntityTypes.ZOMBIFIED_PIGLIN.get());
                    this.config1.node("=============Entity Control============").comment("This Portion of the Config is dedicated to the Control of Entities");
                    for (EntityType entity : cet){
                        for (int weekNumber = 1; weekNumber <= 5; weekNumber++) {
                            String week = null;
                            if(weekNumber != 5){
                                week = "Week " + weekNumber;
                            }else{
                                week = "HOB Night";
                            }
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Natural Spawning=====", "Number of " + entity.toString() + "'s to attempt: ").comment("Any Whole Number ~99999").set(10);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Natural Spawning=====", "The Chance of each " + entity.toString() + " actually spawning: ").comment("0-100").set(100);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Enable Custom Health: ").set(false);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Enable Anger: ").set(false);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Enable Custom Projectile Damage: ").set(false);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Projectile Damage: ").set(10);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Enable Custom Explosion Damage: ").set(false);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Explosion Radius: ").set(5);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Custom Health: ").set(100);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Enable Custom Explosion: ").set(false);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Properties=====", "Explosion Radius: ").set(10);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Drops=====", "Enable Custom Drops: ").set(false);
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Custom Drops=====", "Custom Drops: ").set("");
                            this.config1.node("=============Entity Control============", entity.toString(), week, "=====Monetary Benifits=====", "Per Kill: ").comment("For Each Kill this is what you recieve").set(5);


                        }
                    }

                    this.config1.node("========General Week Properties========").comment("The Below is used for Week 1's General Configuration");
                    this.config1.node("========General Week Properties========", "Time Between Waves");
                    this.config1.node("========General Week Properties========", "Time Between Waves", "Time").comment("In Ticks(20ticks per second)").set("460");
                    this.config1.node("========General Week Properties========", "Week 1", "Wave Size").comment("Number of Entities per wave in Week 1").set(20);
                    this.config1.node("========General Week Properties========", "Week 1", "Maximum Range: ").comment("Furthers away from a Player").set(30);
                    this.config1.node("========General Week Properties========", "Week 1", "Minimum Range: ").comment("Closest to a Player").set(5);
                    this.config1.node("========General Week Properties========", "Week 2", "Wave Size").comment("Number of Entities per wave in Week 1").set(30);
                    this.config1.node("========General Week Properties========", "Week 2", "Maximum Range: ").comment("Furthers away from a Player").set(30);
                    this.config1.node("========General Week Properties========", "Week 2", "Minimum Range: ").comment("Closest to a Player").set(5);
                    this.config1.node("========General Week Properties========", "Week 3", "Wave Size").comment("Number of Entities per wave in Week 1").set(40);
                    this.config1.node("========General Week Properties========", "Week 3", "Maximum Range: ").comment("Furthers away from a Player").set(30);
                    this.config1.node("========General Week Properties========", "Week 3", "Minimum Range: ").comment("Closest to a Player").set(5);
                    this.config1.node("========General Week Properties========", "Week 4", "Wave Size").comment("Number of Entities per wave in Week 1").set(50);
                    this.config1.node("========General Week Properties========", "Week 4", "Maximum Range: ").comment("Furthers away from a Player").set(30);
                    this.config1.node("========General Week Properties========", "Week 4", "Minimum Range: ").comment("Closest to a Player").set(5);
                    this.config1.node("========General Week Properties========", "HOB Night", "Wave Size").comment("Number of Entities per wave in Week 1").set(60);
                    this.config1.node("========General Week Properties========", "HOB Night", "Maximum Range: ").comment("Furthers away from a Player").set(30);
                    this.config1.node("========General Week Properties========", "HOB Night", "Minimum Range: ").comment("Closest to a Player").set(5);
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
