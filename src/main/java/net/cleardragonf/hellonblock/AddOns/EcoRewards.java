package net.cleardragonf.hellonblock.AddOns;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import net.cleardragonf.hellonblock.ConfigurationManager;
import net.cleardragonf.hellonblock.DayCounter;
import net.cleardragonf.hellonblock.HOB;
import net.cleardragonf.hellonblock.MobMechanics.CustomKeys;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.units.qual.C;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.value.Value;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.w3c.dom.Text;

public class EcoRewards {

    //@Listener
    //public void derp(DamageEntityEvent event) {
    //  Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(("*****Starting Damge Event*****")));
    //  int i= 0;
    //  for (Object causeObject : event.getCause()) {
    //	  Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(" - " + i++ + causeObject.toString()));
    //  }
    //  for (Map.Entry<EventContextKey<?>, Object> entry : event.getContext().asMap().entrySet()) {
    //	  Sponge.getGame().getServer().getBroadcastChannel().send(Text.of("- " + entry.getKey().getId() + " : " + entry.getValue()));
    //  }
    //}


    @Listener
    public void MobDeath(DestructEntityEvent.Death event, @First EntityDamageSource src){
        Entity killer = src.source();


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

        //if the item that kills a mob is a projectile ie arrow etc.
        if (src.source() instanceof Projectile){
            Projectile projectile = (Projectile) src.source();
            Optional<Value.Mutable<UUID>> arrow = projectile.creator();

            if(!arrow.isPresent()){
                return;
            }

            Optional<ServerPlayer> optionalPlayer = Sponge.game().server().player(arrow.toString());

            if(!optionalPlayer.isPresent()){
                return;
            }

            Player player = optionalPlayer.get();

            UUID player2 = player.uniqueId();
            Cause cause = event.cause();

            BigDecimal bd = new BigDecimal(event.entity().get(CustomKeys.COST).get());
            //Sponge.server().broadcastAudience().sendMessage(Component.text(bd.toString()));
            player.sendMessage(Component.text("You killed a ").append(event.entity().type()).append(Component.text(" and earned $" + event.entity().get(CustomKeys.COST).get())));
            HOB.getEcon().findOrCreateAccount(player2).get().deposit(HOB.getEcon().defaultCurrency(), bd, cause);


        }

        else if ((killer instanceof Player)){

            Player player = (Player)killer;
            UUID player2 = player.uniqueId();

            Cause cause = event.cause();
            //if(entity2 == null){
            //  BigDecimal bd = new BigDecimal("5.00");
            //player.sendMessage(Text.of("You Killed an unknown entity you've earned $5.00"));
            //HOB.getEcon().getOrCreateAccount(player2).get().deposit(HOB.getEcon().getDefaultCurrency(), bd, cause);
            //Sponge.getServer().getConsole().sendMessage(Text.of(entity2 + " and it's name " + entity));
            //}

                BigDecimal bd = new BigDecimal(event.entity().get(CustomKeys.COST).get());
                //Sponge.server().broadcastAudience().sendMessage(Component.text(bd.toString()));
                player.sendMessage(Component.text("You killed a ").append(event.entity().type()).append(Component.text(" and earned $" + event.entity().get(CustomKeys.COST).get())));
                HOB.getEcon().findOrCreateAccount(player2).get().deposit(HOB.getEcon().defaultCurrency(), bd, cause);
                //Sponge.server().broadcastAudience().sendMessage(Component.text("Need the following added to HOB config : " + entity2));



        }else{
            //USED TO DETECT OTHERWAYS OF KILLING SOMETHING?
            //Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_BLUE, "Let's add this as a source..." + killer));
        }
        //String entity = event.getTargetEntity().getType().getName();
        //player.sendMessage(Text.of(entity));

    }
}
