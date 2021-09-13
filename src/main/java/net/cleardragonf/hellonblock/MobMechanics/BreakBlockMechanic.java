package net.cleardragonf.hellonblock.MobMechanics;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import net.cleardragonf.hellonblock.DayCounter;

import java.util.Optional;


public class BreakBlockMechanic {

    public void onEntitySpawn(EntityType spawnedEntity){
        /*Currently works...just removed from Natural spawn
        Agent entity2 = (Agent) creeper;
        if (entity2.getAgentData().aiEnabled().get()){
            Optional<Goal<Agent>> normalGoal = entity2.getGoal(GoalTypes.NORMAL);
            if(normalGoal.isPresent()){
                normalGoal.get().clear();
            }
            Optional<Goal<Agent>> targetGoal = entity2.getGoal(GoalTypes.TARGET);
            if(targetGoal.isPresent()){
                targetGoal.get().clear();
            }
            WatchClosestAITask watchClosestAITask = WatchClosestAITask.builder()
                    .chance(1)
                    .maxDistance(30)
                    .watch(Player.class)
                    .build(entity2);
            normalGoal.get().addTask(0, watchClosestAITask);
        }            */
        /*
            @Listener
    public void creeper(AITaskEvent event, @First EntityType creeper){
        if(event.getTask() instanceof AttackLivingAITask){
            Agent m = (Agent) creeper;
            @SuppressWarnings("null")
            Player p = (Player) ((Agent)m).getTarget().get();

            boolean a =                         BlockRay.from(m.getLocation()).to(p.getLocation().getPosition())
                    .skipFilter(h -> h.getLocation().getBlockType().getProperty(PassableProperty.class).map(Property::getValue).orElse(false)).build().hasNext();
            if (a == true){
                p.sendMessage(Text.of("Watch out!"));
                Sponge.getServer().getBroadcastChannel().send(Text.of("Oh Boy"));
            }
            Sponge.getServer().getBroadcastChannel().send(Text.of("AFTER YOUR LUCKY ENCHANTS!"));
        }
    }
         */


    }
}
