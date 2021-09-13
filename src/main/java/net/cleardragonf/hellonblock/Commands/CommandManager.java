package net.cleardragonf.hellonblock.Commands;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import net.cleardragonf.hellonblock.DayCounter;

import net.cleardragonf.hellonblock.Commands.CommandManager;
import org.spongepowered.api.util.MinecraftDayTime;
import org.w3c.dom.Text;

public class CommandManager implements CommandExecutor {

    @Override
    public CommandResult execute(CommandContext args) throws CommandException {
        MinecraftDayTime time = Sponge.server().worldManager().defaultWorld().properties().dayTime();
        int day = MinecraftDayTime.minecraftEpoch().day();

        if(!(args.cause() instanceof Player)){
            //src.sendMessaGE(Component.text("Time on the Server is: " + time + ", on the " + day + " day of the month"));
            return CommandResult.success();
        }
        Player player = (Player)args;

        player.sendMessage(Component.text("Time on the Server is: " + time + ", on the " + day + " day of the month"));
        return CommandResult.success();

    }


}
