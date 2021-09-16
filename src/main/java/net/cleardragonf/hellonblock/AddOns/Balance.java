package net.cleardragonf.hellonblock.AddOns;

import net.cleardragonf.hellonblock.HOB;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class Balance implements CommandExecutor {


    @Override
    public CommandResult execute(CommandContext context) throws CommandException {
        if((context.cause().root() instanceof Player)){
            Player player = (Player)context.cause().root();

            UUID player2 = player.uniqueId();
            BigDecimal balance = HOB.getEcon().findOrCreateAccount(player2).get().balance(HOB.getEcon().defaultCurrency());
            player.sendMessage(Component.text(balance.toString()));
        }
        return CommandResult.success();
    }
}