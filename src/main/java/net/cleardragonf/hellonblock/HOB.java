package net.cleardragonf.hellonblock;

import com.google.inject.Inject;
import net.cleardragonf.hellonblock.AddOns.EcoRewards;
import net.cleardragonf.hellonblock.Commands.CommandManager;
import net.cleardragonf.hellonblock.Commands.SetDayCommand;
import net.cleardragonf.hellonblock.MobMechanics.BreakBlockMechanic;
import net.cleardragonf.hellonblock.Spawning.SpawnTesting;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.spongepowered.api.util.MinecraftDayTime;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("hellonblocks")
public class HOB {

    /**/
    public static final String FILE_LOCATION = "mods/HellOnSpoangee/portals.dat";
    public static final String NAME = "[HOS]";

    @Inject
    private Logger logger;

    @Inject
    Game game;

    @Inject
    private void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    public Logger getLogger()
    {
        return this.logger;
    }

    private static HOB instance;

    public static HOB getInstance(){
        return instance;
    }

    @Inject public PluginContainer pluginContainer;
    public  PluginContainer getPluginContainer() {
        return pluginContainer;
    }

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    @Listener
    public  void enderDragon(SpawnEntityEvent event){
        Entity spawnedEntity = null;
        for(Entity entity : event.entities()){
            if(entity != null){
                spawnedEntity = entity;
                break;
            }
        }
        if(spawnedEntity == null){
            return;
        }
        if(spawnedEntity.equals(EntityTypes.CREEPER)){
            BreakBlockMechanic test = new BreakBlockMechanic();
            test.onEntitySpawn((EntityType) spawnedEntity);
        }
    }



    @Listener
    public void allhands(RegisterCommandEvent event){
        ConfigurationManager.getInstance().ConfigurationManager2(configDir);
        ConfigurationManager.getInstance().enable();
        instance = this;
        //setting the Commands here.
        Command TimeCommand = Command.builder()
                .shortDescription(Component.text("Tells the time and day of the month"))
                .executor(new CommandManager())
                .build();
        event.register(pluginContainer, TimeCommand, "hobtime");
        Command SetDayCommand = Command.builder()
                .shortDescription(Component.text("Set the Date in Minecraft"))
                .executor(new SetDayCommand())
                .build();
        event.register(pluginContainer, SetDayCommand, "HOB");
    }

    @Listener//entityData is to be fired anytime and Entity is Detected being spawned.
    public void entityData(SpawnEntityEvent event){

        DayCounter labelTest = new DayCounter();

        labelTest.Days();
    }

    //========Game Schedulers ========
    @Listener
    public void daytracker3(StartedEngineEvent<Server> event){
        //replacing below section
        Sponge.server().worldManager().defaultWorld().properties().setDayTime(MinecraftDayTime.of(ConfigurationManager.getInstance().getTimeTrack().node("========Time Tracking========", "Time: ").getInt(), 0,0));

        //Sponge.getServer().getDefaultWorld().get().setWorldTime((ConfigurationManager.getInstance().getTimeTrack().getNode("========Time Tracking========", "Time: ").getLong()));

        //replacing below section
        Sponge.asyncScheduler().submit(Task.builder()
                .delay(20, TimeUnit.SECONDS)
                .name("daytracker")
                .plugin(pluginContainer)
                .execute(scheduledTask -> {
                    DayCounter getStarted = new DayCounter();
                    getStarted.Days();
                }).build()
        );

        /**
        Sponge.getScheduler().createTaskBuilder().execute(task -> {
            DayCounter getStarted = new DayCounter();
            getStarted.Days();
        }).intervalTicks(20).submit(this);
         **/
    }

    @Listener
    public void SpawnTracking(StartedEngineEvent event){
        Sponge.asyncScheduler().submit(Task.builder()
                .delay(20, TimeUnit.SECONDS)
                .name("locationTracking")
                .plugin(pluginContainer)
                .execute(scheduledTask -> {
                    for(ServerPlayer a: Sponge.server().onlinePlayers()){
                        if(a.world().properties().displayName().toString() != "DIM144"){
                            Player player2 = Sponge.server().onlinePlayers().iterator().next();
                            SpawnTesting spawnTest = new SpawnTesting();
                            spawnTest.getSpace(player2);
                        }else{
                            logger.info(a.name() + " is currently sitting in a Compact Machine");
                        }
                    }
                }).build()
        );
    }

    @Listener
    public void payPlayers(LifecycleEvent event){
        Sponge.eventManager().registerListeners(pluginContainer, new EcoRewards());
        //Sponge.getEventManager().registerListeners(this, new EcoRewards());
    }

    private static EconomyService economyService;
    //@Listener
    //public void onChangeServiceProvider(EconomyService event){
    //    if(event.getService().equals(EconomyService.class)){
    //        economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
    //    }
    //    Sponge.game().server().broadcastAudience().sendMessage(Component.text("Change Service is working"));

    //}

    public static EconomyService getEcon(){
        return economyService;
    }


    @Listener
    public void reloading(RefreshGameEvent event){
        ConfigurationManager.getInstance().enable();
        ConfigurationManager.getInstance().notifyAll();
        Sponge.game().server().broadcastAudience().sendMessage(Component.text("HOB's Has Reloaded Successfully!!!"));
    }
    @Listener
    public void Ending(StoppingEngineEvent event) throws SerializationException {
        //how to tell the lifesycle is 'Stoping'
        int days = MinecraftDayTime.minecraftEpoch().day();
        int hour = Sponge.server().worldManager().defaultWorld().properties().dayTime().hour();
        int minute = Sponge.server().worldManager().defaultWorld().properties().dayTime().minute();
        ConfigurationManager.getInstance().getTimeTrack().node("========Time Tracking========", "Day: ").set(days);
        ConfigurationManager.getInstance().getTimeTrack().node("========Time Tracking========", "Hour Time: ").set(hour);
        ConfigurationManager.getInstance().getTimeTrack().node("========Time Tracking========", "Minute Time: ").set(minute);
        ConfigurationManager.getInstance().saveTime();
    }


}
