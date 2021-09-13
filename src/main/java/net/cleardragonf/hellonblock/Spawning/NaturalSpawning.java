package net.cleardragonf.hellonblock.Spawning;

import com.google.inject.Key;
import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import net.cleardragonf.hellonblock.MobMechanics.CustomHealth;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

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
        SpawnTesting nextLocation = new SpawnTesting();

        Entity entity = spawnLocation.world().createEntity(list2.get(0), spawnLocation.position());
        Entity creeper = entity;
        //creeper.offer(Keys.GLOWING, true);

        if(creeper.supports(Keys.HEALTH)){
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Health: ").getBoolean() == true){
                creeper.offer(Keys.MAX_HEALTH, ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Custom Health: ").getDouble());
                CustomHealth giveFullHealth = new CustomHealth();
                giveFullHealth.heal(creeper);
            }
        }
        if(creeper.supports(Keys.ANGER_LEVEL)){
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Anger: ").getBoolean() == true){
                creeper.offer(Keys.ANGER_LEVEL, 5);
            }
        }
        if(creeper.supports(Keys.EXPLOSION_RADIUS)){
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Explosion Damage: ").getBoolean() == true){
                Integer blast = ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Explosion Radius: ").getInt();
                creeper.offer(Keys.EXPLOSION_RADIUS, blast);
            }
        }
        if(creeper.supports(Keys.ATTACK_DAMAGE)){
            if(ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Enable Custom Projectile Damage: ").getBoolean() == true){
                creeper.offer(Keys.ATTACK_DAMAGE, ConfigurationManager.getInstance().getConfig().node("=============Entity Control============", list2.get(0).tagType().tagRegistry().referenced(Keys.UNIQUE_ID.key()), week, "=====Custom Properties=====", "Projectile Damage: ").getDouble());
            }
        }
        spawnLocation.world().spawnEntity(creeper);
        ///Sponge.getServer().getBroadcastChannel().send(Text.of(list2.get(0).getName()));
        //Songe.getServer().getBroadcastChannel().send(Text.of(list2.get(0).getId()));
    }
    public String getVariableConfig1(){
        return Config;
    }
}
