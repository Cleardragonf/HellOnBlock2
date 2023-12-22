package net.cleardragonf.hellonblock.Spawning;

import com.google.common.collect.ImmutableList;
import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import net.cleardragonf.hellonblock.HOB;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.scheduler.TaskPriority;
import org.spongepowered.api.util.PositionOutOfBoundsException;
import org.spongepowered.api.world.LightType;
import org.spongepowered.api.world.LightTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.api.world.server.ServerWorld;
import org.spongepowered.math.vector.Vector3i;

import java.util.*;
import java.util.stream.Collectors;

public class SpawnTesting {
    static Random random = new Random();
    private final ConfigurationManager configurationManager;

    public SpawnTesting(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }
    public boolean isDarkEnough(Location spawnLocation) {
        try {

            /*orld world = spawnLocation.world();

            int skyLightLevel = world.light(LightTypes.SKY, spawnLocation.blockX(), spawnLocation.blockY(), spawnLocation.blockZ());
            int blockLightLevel = world.light(LightTypes.BLOCK, spawnLocation.blockX(), spawnLocation.blockY(), spawnLocation.blockZ());

            if (skyLightLevel < 5 && blockLightLevel < 5) {
                Sponge.server().broadcastAudience().sendMessage(Component.text("Dark Enough"));
                return true;
            }*/
            return true;

        } catch (PositionOutOfBoundsException e) {
            // Handle exception if the location is outside the world boundaries
            return false;
        }
    }




    public void getSpace(Player player) {
        //Sponge.server().broadcastAudience().sendMessage(Component.text("Step 2"));
        int weekNumber = DayCounter.getWeeklyConfig();
        String week;
        if (weekNumber != 5) {
            week = "Week " + weekNumber;
        } else {
            week = "HOB Night";
        }
        int weekNumber2 = configurationManager.getTimeTrack().getInt();
        Location playersLocation = player.location();
        List<ServerLocation> spawnLocation = new LinkedList<>();

        if (player != null) {
            //Sponge.server().broadcastAudience().sendMessage(Component.text("Step 3"));
            List<EntityType> precet = Sponge.game().registry(RegistryTypes.ENTITY_TYPE).stream().collect(Collectors.toList());
            List<EntityType> list2 = new ArrayList<>();
            for (EntityType entity : precet) {
                if (!entity.category().friendly()) {
                    list2.add(entity);
                }
            }

            List<EntityType> nonZeroSpawnEntities = list2.stream()
                    .filter(entityType -> {
                        int chanceOfSpawning = ConfigurationManager.getInstance().getConfig()
                                .node("=============Entity Control============", entityType.toString(), week,
                                        "=====Natural Spawning=====", "The Chance of each " +
                                                entityType.toString() +
                                                " actually spawning: ").getInt();
                        return chanceOfSpawning > 0;
                    })
                    .collect(Collectors.toList());

            Collections.shuffle(nonZeroSpawnEntities);

            int waveAmount = ConfigurationManager.getInstance().getConfig().node("========General Week Properties========", week, "Wave Size").getInt();
            for (int i = 1; i < waveAmount; i++) {
                for (int x = -20; x < 20; x++) {
                    for (int y = -20; y < 20; y++) {
                        for (int z = -20; z < 20; z++) {
                            int rangeMax = ConfigurationManager.getInstance().getConfig().node("========General Week Properties========", week, "Maximum Range: ").getInt();
                            int rangeMin = ConfigurationManager.getInstance().getConfig().node("========General Week Properties========", week, "Minimum Range: ").getInt();
                            double newSpawnX = playersLocation.x() + x;
                            double newSpawnY = playersLocation.y() + y;
                            double newSpawnZ = playersLocation.z() + z;
                            double distanceToPlayer = Math.sqrt(
                                    Math.pow(newSpawnX - playersLocation.x(), 2) +
                                            Math.pow(newSpawnY - playersLocation.y(), 2) +
                                            Math.pow(newSpawnZ - playersLocation.z(), 2)
                            );

                            if (distanceToPlayer > rangeMin && distanceToPlayer <= rangeMax) {
                                ServerWorld world = player.serverLocation().world();
                                ServerLocation newSpawnLocation = ServerLocation.of(world, newSpawnX, newSpawnY, newSpawnZ);
                                spawnLocation.add(newSpawnLocation);
                            }
                        }
                    }
                }
                Collections.shuffle(spawnLocation);
                int randomSpotAttempt = random.nextInt(spawnLocation.size());
                Optional<ServerLocation> spawn1 = Sponge.game().server().teleportHelper().findSafeLocation(spawnLocation.get(randomSpotAttempt));
                if (spawn1.isPresent()) {
                    Location vector1 = spawn1.get();
                    SpawnDecision spawnNewEntity = new SpawnDecision();

                        EntityType spawnEntity = null;
                        for (EntityType entityType : nonZeroSpawnEntities) {
                            spawnEntity = entityType;
                            Collections.shuffle(spawnLocation);
                            break;  // To spawn only one entity for each iteration
                        }




                        Random roll = new Random();
                        int answer = roll.nextInt(100) + 1;
                        int chanceOfSpawning = ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", spawnEntity.toString(), week, "=====Natural Spawning=====", "The Chance of each " + spawnEntity.toString() + " actually spawning: ").getInt();
                        if (answer <= chanceOfSpawning) {

                            // Shuffle the entity list before entering the loop
                            Collections.shuffle(nonZeroSpawnEntities);


                                vector1 = spawn1.get();
                                if(isDarkEnough(vector1)){
                                    spawnNewEntity.newCreeper(vector1, ImmutableList.of(spawnEntity));
                                }
                    }
                }
            }
        }
    }
}
