package net.cleardragonf.hellonblock.AddOns;

import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import net.cleardragonf.hellonblock.HOB;
import net.cleardragonf.hellonblock.MobMechanics.CustomKeys;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import javax.xml.soap.Text;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class EcoPunishments {

    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death event, @First EntityDamageSource src){
        if(event.entity() instanceof Player){
            Entity killer = src.source();
            Entity victim = event.entity();


            int weekNumber = DayCounter.getWeeklyConfig();
            String week = null;
            if (weekNumber != 5){
                week = "Week " + weekNumber;
            }else{
                week = "HOB Night";
            }

            // not currently used except against other players************
            //if (event.getTargetEntity() instanceof Player){
            //	String a = event.getClass().getName().toString();
            //	Sponge.getServer().getBroadcastChannel().send(Text.of("HIi " + a));
            //}
            Sponge.game().server().broadcastAudience().sendMessage(Component.text("firing!"));
            //if the item that kills a mob is a projectile ie arrow etc.
            if (src.source() instanceof Projectile){
                Projectile projectile = (Projectile) src.source();
                Optional<Value.Mutable<UUID>> arrow = projectile.creator();

                if(!arrow.isPresent()){
                    return;
                }

                Optional<ServerPlayer> optionalPlayer = Sponge.game().server().player(arrow.toString());

                if(optionalPlayer.isPresent()){
                    Player player = optionalPlayer.get();

                    UUID player2 = player.uniqueId();
                    Cause cause = event.cause();

                    int cost = HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().balance(HOB.getEcon().defaultCurrency()).intValue() / 2;
                    BigDecimal bd = new BigDecimal(cost);
                    //Sponge.server().broadcastAudience().sendMessage(Component.text(bd.toString()));
                    player.sendMessage(Component.text("You killed a player and stole $" + bd + " from them. Better be careful."));
                    player.sendMessage(Component.text(player + " killed you and stole $" + bd));
                    //Killers payout
                    HOB.getEcon().findOrCreateAccount(player2).get().deposit(HOB.getEcon().defaultCurrency(), bd, cause);
                    //Victims Punishment
                    HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(), bd, cause);
                    //Sponge.server().broadcastAudience().sendMessage(Component.text("Need the following added to HOB config : " + entity2));
                }else{
                    Player player = (Player) victim;
                    UUID player2 = player.uniqueId();
                    Cause cause = event.cause();

                    BigDecimal bd = new BigDecimal(5* weekNumber);
                    //Sponge.server().broadcastAudience().sendMessage(Component.text(bd.toString()));
                    player.sendMessage(Component.text(cause + " killed you and stole $" + bd));
                    //Victims Punishment
                    HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(), bd, cause);

                }

            }

            else if (killer instanceof Player){
                Sponge.game().server().broadcastAudience().sendMessage(Component.text("Player died!"));
                Player player = (Player)killer;
                UUID player2 = player.uniqueId();

                Cause cause = event.cause();

                int cost = HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().balance(HOB.getEcon().defaultCurrency()).intValue() / 2;
                BigDecimal bd = new BigDecimal(cost);
                //Sponge.server().broadcastAudience().sendMessage(Component.text(bd.toString()));
                player.sendMessage(Component.text("You killed a player and stole $" + bd + " from them. Better be careful."));
                player.sendMessage(Component.text(player + " killed you and stole $" + bd));
                //Killers payout
                HOB.getEcon().findOrCreateAccount(player2).get().deposit(HOB.getEcon().defaultCurrency(), bd, cause);
                //Victims Punishment
                HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(), bd, cause);
                //Sponge.server().broadcastAudience().sendMessage(Component.text("Need the following added to HOB config : " + entity2));

            }else if(killer instanceof Entity){
                Sponge.game().server().broadcastAudience().sendMessage(Component.text("Killed by a mob: " + killer));
            }

            else{
                //USED TO DETECT OTHERWAYS OF KILLING SOMETHING?
                BigDecimal bd = new BigDecimal(10* weekNumber);
                Cause cause = event.cause();
                HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(), bd, cause);
                Sponge.game().server().broadcastAudience().sendMessage(Component.text("This is what killed you: " + killer));
            }

            //String entity = event.getTargetEntity().getType().getName();
            //player.sendMessage(Text.of(entity));
        }
    }
}
