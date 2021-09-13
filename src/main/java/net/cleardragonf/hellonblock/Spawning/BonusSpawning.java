package net.cleardragonf.hellonblock.Spawning;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import net.cleardragonf.hellonblock.Spawning.BonusSpawning;

public class BonusSpawning {
    @Listener
    public void checkEntitySpawn(SpawnEntityEvent event){
        //This is the testing code v
        Entity spawnedEntity = null;
        for(Entity entity : event.entities()){//
            if(entity != null){
                spawnedEntity = entity;
                break;
            }
        }
        if(spawnedEntity == null){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.GHAST)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.BLAZE)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.CHEST_MINECART)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.FALLING_BLOCK)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.ITEM)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.ARROW)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.SPECTRAL_ARROW)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.SMALL_FIREBALL)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.FIREBALL)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.ITEM_FRAME)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.EXPERIENCE_BOTTLE)){
            return;
        }
        else if(spawnedEntity.type().equals(EntityTypes.EXPERIENCE_ORB)){
        }

        else{
        }

    }
}
