package net.cleardragonf.hellonblock.AddOns;

import net.cleardragonf.hellonblock.HOB;
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
            Player player = (Player) context.cause().root();
            Optional<UniqueAccount> uOpt = HOB.getEcon().findOrCreateAccount(player.uniqueId());
            if (uOpt.isPresent()) {
                UniqueAccount acc = uOpt.get();
                BigDecimal balance = acc.balance(HOB.getEcon().defaultCurrency());
            }
        }
        return CommandResult.success();
    }
}