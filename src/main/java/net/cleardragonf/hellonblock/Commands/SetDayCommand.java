package net.cleardragonf.hellonblock.Commands;

import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import net.cleardragonf.hellonblock.Commands.SetDayCommand;

public class SetDayCommand implements CommandExecutor {


    @Override
    public CommandResult execute(CommandContext context) throws CommandException {
        if(!(context.cause() instanceof  Player)){

        }
        return CommandResult.success();
    }
}
