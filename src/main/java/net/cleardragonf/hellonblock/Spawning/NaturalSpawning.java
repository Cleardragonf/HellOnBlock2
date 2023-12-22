package net.cleardragonf.hellonblock.Spawning;

import com.google.inject.Key;
import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import net.cleardragonf.hellonblock.MobMechanics.CustomHealth;
import net.cleardragonf.hellonblock.MobMechanics.CustomKeys;
import net.kyori.adventure.text.Component;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.data.value.MapValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

import net.cleardragonf.hellonblock.Spawning.NaturalSpawning;
import org.spongepowered.api.world.server.ServerWorld;

public class NaturalSpawning {
    public void onEntitySpawn(SpawnEntityEvent event){
        //This is the testing code v

		  /*Entity spawnedEntity = null;
		  for(Entity entity : event.getEntities()){
			  if(entity != null){
				  spawnedEntity = entity;
				  break;
			  }
		  }
		  if(spawnedEntity == null){
			  return;
		  }
		  if(DayCounter.getWeeklyConfig() == 1){
			  if(spawnedEntity.getType().equals(EntityTypes.BAT)){
		  		  for (int i = 0; i< (ConfigurationManager.getInstance().getConfig1().getNode("Natural Spawning!", "Bat", "#").getInt()); i++){
		  			  Random roll = new Random();
		  			  int answer = roll.nextInt(100) + 1;
		  			  if (answer <= (ConfigurationManager.getInstance().getConfig1().getNode("Natural Spawning!", "Bat", "%").getInt())){
		  				  Location<World> location = spawnedEntity.getLocation();
		  				  World world = location.getExtent();
		  				  Entity newEntity = world.createEntity(EntityTypes.BAT, location.getPosition());
		  				  	event.getEntities().add(newEntity);
		  			  }
		   		  }
			  }
		  }*/





    }
    public double mobHealth = 0;
    public int explosionRadius = 0;
    public
    int test = DayCounter.getWeeklyConfig();
    public String Config = "Config" + test;


    public void Hostiles(Location spawnLocation, List<EntityType> list2) {
        int weekNumber = DayCounter.getWeeklyConfig();
        String week;
        if(weekNumber != 5){
            week = "Week " + weekNumber;
        }else{
            week = "HOB Night";
        }
        //SpawnTesting nextLocation = new SpawnTesting();

        Entity entity = spawnLocation.world().createEntity(list2.get(0), spawnLocation.position());
        EntityType entityType = list2.get(0);
        Entity creeper = entity;
        //creeper.offer(Keys.GLOWING, true);

        if(creeper.supports(Keys.HEALTH)){
            Sponge.server().broadcastAudience().sendMessage(Component.text("here's the Entity: " + entityType));
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", entityType.toString(), week, "=====Custom Properties=====", "Enable Custom Health: ").getBoolean() == true){
                int maxHealth = ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", entityType.toString(), week, "=====Custom Properties=====", "Custom Health: ").getInt();
                Random random = new Random();
                // Generate a random health value between 1 and customHealth
                mobHealth = random.nextInt(maxHealth - 1) + 1;

                creeper.offer(Keys.MAX_HEALTH, mobHealth);

                CustomHealth giveFullHealth = new CustomHealth();
                giveFullHealth.heal(creeper);
            }
        }

        if(creeper.supports(Keys.ANGER_LEVEL)){
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", entityType.toString(), week, "=====Custom Properties=====", "Enable Anger: ").getBoolean() == true){
                creeper.offer(Keys.ANGER_LEVEL, 5);
            }
        }
        if(creeper.supports(Keys.EXPLOSION_RADIUS)){
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", entityType.toString(), week, "=====Custom Properties=====", "Enable Custom Explosion Damage: ").getBoolean() == true){
                int blast = ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", entityType.toString(), week, "=====Custom Properties=====", "Explosion Radius: ").getInt();

                // Generate a random health value between 1 and customHealth
                explosionRadius = (int) (Math.random() * (blast - 1) + 1);

                creeper.offer(Keys.EXPLOSION_RADIUS, explosionRadius);
            }
        }
        if(creeper.supports(CustomKeys.COST)){
            // Get the existing map value

            // Set the modified map value back to the entity
            creeper.offer(CustomKeys.COST, 5);

        }

        spawnLocation.world().spawnEntity(creeper);
        Sponge.server().broadcastAudience().sendMessage(Component.text(creeper.toString() + " has been spawned. It's keys are the following " + entity.getKeys().toString()));



        ///Sponge.getServer().getBroadcastChannel().send(Text.of(list2.get(0).getName()));
        //Songe.getServer().getBroadcastChannel().send(Text.of(list2.get(0).getId()));
    }
    public String getVariableConfig1(){
        return Config;
    }
}
