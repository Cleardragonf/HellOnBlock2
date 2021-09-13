package net.cleardragonf.hellonblock.MobMechanics;

import java.util.Optional;

//import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
//import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.Entity;

import net.cleardragonf.hellonblock.MobMechanics.CustomHealth;

public class CustomHealth {

    public void heal(Entity spawnedEntity){
        //health data is missing...along with heal
        /**
        Optional<Heal> healthOptional = spawnedEntity.get(HealthData.class);
        if (healthOptional.isPresent()) {
            HealthData healthData = healthOptional.get();

            double maxHealth = healthData.maxHealth().get();
            MutableBoundedValue<Double> currentHealth = healthData.health();
            currentHealth.set(maxHealth);
            healthData.set(currentHealth);

            spawnedEntity.offer(healthData);
        }
         **/
    }
}
