package net.cleardragonf.hellonblock.Commands;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.MinecraftDayTime;

public class CommandManager implements CommandExecutor {

    @Override
    public CommandResult execute(CommandContext args) throws CommandException {
        if (!(args.cause().root() instanceof Player)) {
            // This command is intended for players only
            return CommandResult.success();
        }

        Player player = (Player) args.cause().root();
        MinecraftDayTime time = player.world().properties().dayTime();
        int day = MinecraftDayTime.minecraftEpoch().day();

        player.sendMessage(Component.text("Time on the Server is: " + time + ", on the " + day + " day of the month"));
        return CommandResult.success();
    }
}
