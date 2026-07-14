package com.lazrproductions.cuffed.command.argument;

import com.lazrproductions.cuffed.restraints.base.RestraintType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.StringRepresentableArgument;

public class RestraintTypeArgument extends StringRepresentableArgument<RestraintType> {
    public static RestraintTypeArgument restraintType() {
        return new RestraintTypeArgument();
    }

    public static RestraintType getRestraintType(CommandContext<CommandSourceStack> ctx, String argument) {
        return ctx.getArgument(argument, RestraintType.class);
    }

    protected RestraintTypeArgument() {
        super(RestraintType.CODEC, RestraintType::values);
    }
}
