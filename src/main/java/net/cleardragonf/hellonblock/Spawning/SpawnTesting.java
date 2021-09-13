package net.cleardragonf.hellonblock.Spawning;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.entity.BlockEntity;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.fluid.FluidState;
import org.spongepowered.api.registry.RegistryTypes;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.BlockChangeFlag;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.cleardragonf.hellonblock.Spawning.SpawnTesting;
import org.spongepowered.api.world.biome.Biome;
import org.spongepowered.api.world.server.ServerLocation;
import org.spongepowered.math.vector.Vector3d;
import org.spongepowered.math.vector.Vector3i;

public class SpawnTesting
{
    static Random random = new Random();

    public void getSpace(Player player)
    {
        int weekNumber = DayCounter.getWeeklyConfig();
        String week;
        if(weekNumber != 5){
            week = "Week " + weekNumber;
        }else{
            week = "HOB Night";
        }

        Location playersLocation = player.location();
        List<Location> spawnLocation = new LinkedList();
        if (player != null)
        {
            List<Class<? extends Entity>> classes = ImmutableList.of(Monster.class, Hostile.class);
            //List<EntityType> list2 = Sponge.getRegistry().getAllOf(EntityType.class).stream()
            List<EntityType> list2 = Sponge.server().registry(RegistryTypes.ENTITY_TYPE).stream()
                    .filter((x) -> classes.stream().anyMatch((y) -> y.isAssignableFrom(x.getClass())))
                    .filter((p) -> !p.equals(EntityTypes.ENDER_DRAGON))
                    .filter((a) -> ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", a.tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "The Chance of each " + a.tagType().tagRegistry().referenced(Keys.DISPLAY_NAME.key()) + "actually spawning: ").getInt() != 0)
                    .collect(Collectors.toList());

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
                        if (Math.pow(newSpawnX.doubleValue() - playersLocation.x(), 2.0D) + Math.pow(newSpawnY.doubleValue() - playersLocation.y(), 2.0D) + Math.pow(newSpawnZ.doubleValue() - playersLocation.z(), 2.0D) <= Math.pow(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "Maximum Range: ").getDouble(), 2))
                        {
                            if (Math.pow(newSpawnX.doubleValue() - playersLocation.x(), 2.0D) + Math.pow(newSpawnY.doubleValue() - playersLocation.y(), 2.0D) + Math.pow(newSpawnZ.doubleValue() - playersLocation.z(), 2.0D) >= Math.pow(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "Minimum Range: ").getDouble(), 2)){
                                World world = player.world();
                                Location newSpawnLocation = new Location() {
                                    @Override
                                    public World world() {
                                        return null;
                                    }

                                    @Override
                                    public Optional worldIfAvailable() {
                                        return Optional.empty();
                                    }

                                    @Override
                                    public boolean isAvailable() {
                                        return false;
                                    }

                                    @Override
                                    public boolean isValid() {
                                        return false;
                                    }

                                    @Override
                                    public Vector3d position() {
                                        return null;
                                    }

                                    @Override
                                    public Vector3i blockPosition() {
                                        return null;
                                    }

                                    @Override
                                    public Vector3i chunkPosition() {
                                        return null;
                                    }

                                    @Override
                                    public Vector3i biomePosition() {
                                        return null;
                                    }

                                    @Override
                                    public double x() {
                                        return 0;
                                    }

                                    @Override
                                    public double y() {
                                        return 0;
                                    }

                                    @Override
                                    public double z() {
                                        return 0;
                                    }

                                    @Override
                                    public int blockX() {
                                        return 0;
                                    }

                                    @Override
                                    public int blockY() {
                                        return 0;
                                    }

                                    @Override
                                    public int blockZ() {
                                        return 0;
                                    }

                                    @Override
                                    public boolean inWorld(World world) {
                                        return false;
                                    }

                                    @Override
                                    public Location withWorld(World world) {
                                        return null;
                                    }

                                    @Override
                                    public Location withPosition(Vector3d position) {
                                        return null;
                                    }

                                    @Override
                                    public Location withBlockPosition(Vector3i position) {
                                        return null;
                                    }

                                    @Override
                                    public Location sub(Vector3d v) {
                                        return null;
                                    }

                                    @Override
                                    public Location sub(Vector3i v) {
                                        return null;
                                    }

                                    @Override
                                    public Location sub(double x, double y, double z) {
                                        return null;
                                    }

                                    @Override
                                    public Location add(Vector3d v) {
                                        return null;
                                    }

                                    @Override
                                    public Location add(Vector3i v) {
                                        return null;
                                    }

                                    @Override
                                    public Location add(double x, double y, double z) {
                                        return null;
                                    }

                                    @Override
                                    public Location relativeTo(Direction direction) {
                                        return null;
                                    }

                                    @Override
                                    public Location relativeToBlock(Direction direction) {
                                        return null;
                                    }

                                    @Override
                                    public Biome biome() {
                                        return null;
                                    }

                                    @Override
                                    public boolean hasBlock() {
                                        return false;
                                    }

                                    @Override
                                    public BlockState block() {
                                        return null;
                                    }

                                    @Override
                                    public FluidState fluid() {
                                        return null;
                                    }

                                    @Override
                                    public boolean hasBlockEntity() {
                                        return false;
                                    }

                                    @Override
                                    public Optional<? extends BlockEntity> blockEntity() {
                                        return Optional.empty();
                                    }

                                    @Override
                                    public boolean setBlock(BlockState state) {
                                        return false;
                                    }

                                    @Override
                                    public boolean setBlock(BlockState state, BlockChangeFlag flag) {
                                        return false;
                                    }

                                    @Override
                                    public boolean setBlockType(BlockType type) {
                                        return false;
                                    }

                                    @Override
                                    public boolean setBlockType(BlockType type, BlockChangeFlag flag) {
                                        return false;
                                    }
                                };
                                newSpawnLocation.add(newSpawnX.doubleValue(), newSpawnY.doubleValue(), newSpawnZ.doubleValue());
                                spawnLocation.add(newSpawnLocation);
                            }

                        }
                    }
                }
            }






            for (int i = 1; i < ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "Number of " + list2.get(0).tagType().tagRegistry().referenced(Keys.DISPLAY_NAME.key()) + "'s to attempt: ").getInt(); i++) {

                Collections.shuffle(spawnLocation);
                Optional<ServerLocation> Spawn1 = Sponge.game().server().teleportHelper().findSafeLocation((ServerLocation) spawnLocation.get(0));
                if(!Spawn1.isPresent()){

                }else{
                    Location Vector1 = (Location) Spawn1.get();
                    int optional = player.world().light(Vector1.blockPosition());
                    SpawnDecision TimeToTry = new SpawnDecision();
                    if (optional < 5) {
                        if (Spawn1.isPresent()) {
                            Random roll = new Random();
                            int answer = roll.nextInt(100) + 1;
                            if (answer <= ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Natural Spawning=====", "The Chance of each " + list2.get(0).tagType().tagRegistry().referenced(Keys.DISPLAY_NAME.key()) + "actually spawning: ").getInt()) {
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
