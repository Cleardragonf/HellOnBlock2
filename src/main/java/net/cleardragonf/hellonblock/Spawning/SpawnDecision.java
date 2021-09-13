package net.cleardragonf.hellonblock.Spawning;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.cleardragonf.hellonblock.Spawning.SpawnDecision;

public class SpawnDecision {
    public void newCreeper(Location spawnLocation, List<EntityType> list2) {

        List<String> list = Arrays.asList("Hostile", "Peaceful", "Neutral");
        Random rand = new Random();
        String ListResults = list.get(rand.nextInt(list.size()));
        if(ListResults =="Hostile"){
            NaturalSpawning hostileSpawns = new NaturalSpawning();
            hostileSpawns.Hostiles(spawnLocation, list2);
        }


        if(ListResults == "Peaceful"){
            NaturalSpawning peacefulSpawns = new NaturalSpawning();
            peacefulSpawns.Hostiles(spawnLocation, list2);

        }
        if(ListResults == "Neutral"){
            NaturalSpawning neutralSpawns = new NaturalSpawning();
            neutralSpawns.Hostiles(spawnLocation, list2);

        }
    }
}
