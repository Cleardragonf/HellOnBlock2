package net.cleardragonf.hellonblock.Spawning;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import net.cleardragonf.hellonblock.HOB;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Game;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.entity.BlockEntity;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.Key;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.persistence.DataContainer;
import org.spongepowered.api.data.value.*;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.fluid.FluidState;
import org.spongepowered.api.fluid.FluidType;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.scheduler.ScheduledUpdate;
import org.spongepowered.api.scheduler.TaskPriority;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.cleardragonf.hellonblock.Spawning.SpawnTesting;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

public class SpawnTesting
{
    static Random random = new Random();

    public void getSpace(Player player)
    {
        Sponge.server().broadcastAudience().sendMessage(Component.text("Step 2"));
        int weekNumber = DayCounter.getWeeklyConfig();
        String week;
        if(weekNumber != 5){
            week = "Week " + weekNumber;
        }else{
            week = "HOB Night";
        }

        Location playersLocation = player.location();
        List<ServerLocation> spawnLocation = new LinkedList();
        if (player != null)
        {
            Sponge.server().broadcastAudience().sendMessage(Component.text("Step 3"));
            List<Class<? extends Entity>> classes = ImmutableList.of(Monster.class, Hostile.class);
            //List<EntityType> list2 = Sponge.getRegistry().getAllOf(EntityType.class).stream()
            List<EntityType> precet = Sponge.game().registry(RegistryTypes.ENTITY_TYPE).stream().collect(Collectors.toList());
            List<EntityType> list2 = new ArrayList<>();
            for (EntityType entity :
                    precet) {
                if(!entity.category().friendly()){
                    list2.add(entity);
                }
            }
            //List<EntityType> list2 = Sponge.server().registry(RegistryTypes.ENTITY_TYPE).stream()
            //        .filter((x) -> classes.stream().anyMatch((y) -> y.isAssignableFrom(x.getClass())))
            //        .filter((p) -> !p.equals(EntityTypes.ENDER_DRAGON))
            //        .filter((a) -> ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", a.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "The Chance of each " + a.tagType().tagRegistry().referenced(Keys.DISPLAY_NAME.key()) + "actually spawning: ").getInt() != 0)
            //        .collect(Collectors.toList());

            Collections.shuffle(list2);

            for (int x = -20; x < 20; x++) {
                for (int y = -20; y < 20; y++) {
                    for (int z = -20; z < 20; z++)
                    {
                        List<String> testing = new LinkedList();
                        int range = 10;
                        Double newSpawnX = Double.valueOf(playersLocation.x() + x);
                        Double newSpawnY = Double.valueOf(playersLocation.y() + y);
                        Double newSpawnZ = Double.valueOf(playersLocation.z() + z);
                        if (Math.pow(newSpawnX.doubleValue() - playersLocation.x(), 2.0D) + Math.pow(newSpawnY.doubleValue() - playersLocation.y(), 2.0D) + Math.pow(newSpawnZ.doubleValue() - playersLocation.z(), 2.0D) <= Math.pow(5.0, 2))
                        {
                            if (Math.pow(newSpawnX.doubleValue() - playersLocation.x(), 2.0D) + Math.pow(newSpawnY.doubleValue() - playersLocation.y(), 2.0D) + Math.pow(newSpawnZ.doubleValue() - playersLocation.z(), 2.0D) >= Math.pow(1.0, 2)){
                                ServerLocation newSpawnLocation = player.serverLocation();
                                newSpawnLocation.add(newSpawnX.doubleValue(), newSpawnY.doubleValue(), newSpawnZ.doubleValue());
                                spawnLocation.add(newSpawnLocation);
                            }

                        }
                    }
                }
            }






            for (int i = 1; i < 5; i++) {

                Collections.shuffle(spawnLocation);
                Optional<ServerLocation> Spawn1 = Sponge.game().server().teleportHelper().findSafeLocation(spawnLocation.get(0));
                if(!Spawn1.isPresent()){

                }else{
                    Location Vector1 = (Location) Spawn1.get();
                    int optional = player.world().emittedLight(Vector1.blockPosition());
                    SpawnDecision TimeToTry = new SpawnDecision();
                    if (optional < 5) {
                        Sponge.server().broadcastAudience().sendMessage(Component.text("Dark Enough"));
                        if (Spawn1.isPresent()) {
                            Random roll = new Random();
                            int answer = roll.nextInt(100) + 1;
                            if (answer <= 100) {
                                Sponge.server().broadcastAudience().sendMessage(Component.text("Well here we go"));
                                Collections.shuffle(spawnLocation);
                                Vector1 = (Location) Spawn1.get();
                                TimeToTry.newCreeper(Vector1, list2);
                            }else{
                            }
                        }else{
                            return;
                        }
                    }else {
                        return;
                    }
                }

            }

        }
        else {
        }
    }
}
