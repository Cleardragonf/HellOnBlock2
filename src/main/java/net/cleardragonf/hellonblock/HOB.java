package net.cleardragonf.hellonblock;

import com.google.inject.Inject;
import net.cleardragonf.hellonblock.AddOns.Balance;
import net.cleardragonf.hellonblock.AddOns.EcoPunishments;
import net.cleardragonf.hellonblock.AddOns.EcoRewards;
import net.cleardragonf.hellonblock.Commands.CommandManager;
import net.cleardragonf.hellonblock.Commands.SetDayCommand;
import net.cleardragonf.hellonblock.MobMechanics.BreakBlockMechanic;
import net.cleardragonf.hellonblock.MobMechanics.CustomKeys;
import net.cleardragonf.hellonblock.Spawning.SpawnTesting;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.entity.living.player.RespawnPlayerEvent;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.spongepowered.api.util.MinecraftDayTime;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.math.vector.Vector3i;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("hellonblocks")
public class HOB {

    /**/
    public static final String FILE_LOCATION = "mods/HellOnSpoangee/portals.dat";
    public static final String NAME = "[HOS]";

    @Inject
    public Logger logger;

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

    public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    private static HOB instance;

    public static HOB getInstance(){
        return instance;
    }

    @Inject public PluginContainer pluginContainer;
    public  PluginContainer getPluginContainer() {
        return pluginContainer;
    }

    @Inject
    public HOB(PluginContainer container){
        this.pluginContainer = container;
        instance = this;
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
    public void onRegisterData(RegisterDataEvent event) {
        event.register(DataRegistration.of(CustomKeys.COST, Entity.class));
    }




    @Listener
    public void allhands(RegisterCommandEvent<Command.Parameterized> event){
        instance = this;
        //setting the Commands here.
        Command.Parameterized TimeCommand = Command.builder()
                .shortDescription(Component.text("Tells the time and day of the month"))
                .executor(new CommandManager())
                .build();
        event.register(pluginContainer, TimeCommand, "hobtime");
        Command.Parameterized SetDayCommand = Command.builder()
                .shortDescription(Component.text("Set the Date in Minecraft"))
                .executor(new SetDayCommand())
                .build();
        event.register(pluginContainer, SetDayCommand, "HOB");
        Command.Parameterized Balance = Command.builder()
                .shortDescription(Component.text("Get's your Balance"))
                .executor(new Balance())
                .build();
        event.register(pluginContainer, Balance, "bal");
    }

    @Listener//entityData is to be fired anytime and Entity is Detected being spawned.
    public void entityData(SpawnEntityEvent event){

        DayCounter labelTest = new DayCounter();

        //labelTest.Days();
    }

    //========Game Schedulers ========
    @Listener
    public void daytracker3(StartedEngineEvent<Server> event){
        ConfigurationManager.getInstance().ConfigurationManager2(configDir);
        ConfigurationManager.getInstance().enable();
        //replacing below section
        ///doesn't work yet because config doesn't work\
        //Sponge.server().worldManager().defaultWorld().properties().setDayTime(MinecraftDayTime.of(ConfigurationManager.getInstance().getTimeTrack().node("========Time Tracking========", "Time: ").getInt(), 0,0));

        //Sponge.getServer().getDefaultWorld().get().setWorldTime((ConfigurationManager.getInstance().getTimeTrack().getNode("========Time Tracking========", "Time: ").getLong()));

        //replacing below section
        Sponge.asyncScheduler().submit(Task.builder()
                .delay(20, TimeUnit.SECONDS)
                .plugin(pluginContainer)
                .execute(scheduledTask -> {
                    new DayCounter();

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
    public void payPlayers(StartedEngineEvent<Server> event){
        Sponge.eventManager().registerListeners(pluginContainer, new EcoRewards());
        Sponge.eventManager().registerListeners(pluginContainer, new EcoPunishments());

        Optional<EconomyService> serviceOpt = Sponge.server().serviceProvider().economyService();
        if (!serviceOpt.isPresent()) {
            logger.info("great still not working");
        }
        economyService = serviceOpt.get();

        Sponge.server().scheduler().submit(Task.builder()
                .interval(30, TimeUnit.SECONDS)
                .plugin(pluginContainer)
                .execute(scheduledTask -> {
                    if(Sponge.game().server().worldManager().defaultWorld().properties().dayTime().hour() <= 4 || Sponge.game().server().worldManager().defaultWorld().properties().dayTime().hour() >= 18){
                        for(ServerPlayer a: Sponge.server().onlinePlayers()){
                            if(a.world().properties().displayName().toString() != "compactmachines"){
                                Player player2 = Sponge.server().onlinePlayers().iterator().next();
                                //Sponge.server().broadcastAudience().sendMessage(Component.text("Firing Spawn..."));
                                SpawnTesting spawnTest = new SpawnTesting(ConfigurationManager.getInstance());
                                spawnTest.getSpace(player2);
                            }else{
                                logger.info(a.name() + " is currently sitting in a Compact Machine");
                            }
                        }
                    }
                }).build()
        );
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
    public void Ending(StoppingEngineEvent<Server> event) throws SerializationException {
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
