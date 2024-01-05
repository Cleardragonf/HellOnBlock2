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
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;

import javax.xml.soap.Text;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class EcoPunishments {

    @Listener
    public void onPlayerDeath(DestructEntityEvent event, @Getter("entity")Player player, @First EntityDamageSource src){
            HOB.LOGGER.warn("firing!");

            int weekNumber = DayCounter.getWeeklyConfig();
            String week = null;
            if (weekNumber != 5){
                week = "Week " + weekNumber;
            }else{
                week = "HOB Night";
            }

            Player victim = (Player) event.entity();
            if(event.cause().contains(DamageSources.DROWNING) || event.cause().contains(DamageSources.FALLING)||event.cause().contains(DamageSources.STARVATION)||
                    event.cause().contains(DamageSources.DRYOUT) || event.cause().contains(DamageSources.FIRE_TICK) || event.cause().contains(DamageSources.MAGIC)||
                    event.cause().contains(DamageSources.WITHER)){
                int cost = 100 * weekNumber;
                Cause cause = event.cause();
                BigDecimal bd = new BigDecimal(cost);
                victim.sendMessage(Component.text("You died from " + cause.toString() + " thus you lost $" + bd));
                HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(), bd,cause);
            }
            if(src.source() instanceof Entity){

                Entity killer = (Entity) src.source();
                Cause cause = event.cause();
                int cost = 5 * weekNumber;
                BigDecimal bd = new BigDecimal(cost);

                victim.sendMessage(Component.text("You were killed by " + killer.type() + " and lost $" + bd));
                HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(),bd,cause);
            }
            if(src.source() instanceof Projectile){
                HOB.LOGGER.warn("Firing Arrows!");
                Projectile projectile = (Projectile) src.source();
                Optional<Value.Mutable<UUID>> arrow = projectile.creator();
                if(!arrow.isPresent()){return;}

                Optional<ServerPlayer> optionalPlayer = Sponge.game().server().player(arrow.toString());
                if(optionalPlayer.isPresent()){
                    Player killer = optionalPlayer.get();
                    Cause cause = event.cause();

                    int cost = HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().balance(HOB.getEcon().defaultCurrency()).intValue() /2;
                    BigDecimal bd = new BigDecimal(cost);
                    killer.sendMessage(Component.text("You Killed " + victim + " and stole $" + bd + " from them.  Better be careful."));
                    victim.sendMessage(Component.text(killer + " killed you and stole$" + bd));
                    HOB.getEcon().findOrCreateAccount(killer.uniqueId()).get().deposit(HOB.getEcon().defaultCurrency(), bd,cause);
                    HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(), bd,cause);
                }else{
                    BigDecimal bd = new BigDecimal(5 * weekNumber);
                    victim.sendMessage(Component.text(event.cause() + " hit you so now you've lost $" + bd));
                    HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(),bd,event.cause());
                }
            }

            if(src.source() instanceof Player){
                Player killer = (Player)src.source();
                Cause cause = event.cause();

                int cost = HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().balance(HOB.getEcon().defaultCurrency()).intValue() /2;
                BigDecimal bd = new BigDecimal(cost);

                killer.sendMessage(Component.text("You killed " + victim + " and stole $" + bd + " from them. Better be careful."));
                victim.sendMessage(Component.text(killer + " killed you and stole $" + bd));

                HOB.getEcon().findOrCreateAccount(killer.uniqueId()).get().deposit(HOB.getEcon().defaultCurrency(),bd,cause);
                HOB.getEcon().findOrCreateAccount(victim.uniqueId()).get().withdraw(HOB.getEcon().defaultCurrency(),bd,cause);
            }
        }
}
