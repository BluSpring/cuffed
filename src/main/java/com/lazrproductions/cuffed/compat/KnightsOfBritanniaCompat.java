package com.lazrproductions.cuffed.compat;
import org.jetbrains.annotations.NotNull;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class KnightsOfBritanniaCompat {
        public static void load() {
    }

    public static void DrainMana(@NotNull ServerPlayer player, int amount) {
        MinecraftServer server = player.getServer();
        if(server == null)
            return;

        var scoreboard = server.getScoreboard();
        if(scoreboard == null)
            return;

        String scoreboardName = player.getScoreboardName();
        if(scoreboardName == null)
            return;

        var currentManaObjective = scoreboard.getOrCreateObjective("kob.mana");
        if(currentManaObjective == null)
            return;

        var myManaScore = scoreboard.getOrCreatePlayerScore(scoreboardName, currentManaObjective);
        
        int currentMana = myManaScore.getScore();

        if(currentMana <= 0)
            return;

        myManaScore.add(-(int)amount);
    }

    public static void DrainMana(@NotNull ServerPlayer player, double amountPercentage) {
        
        MinecraftServer server = player.getServer();
        if(server == null)
            return;

        var scoreboard = server.getScoreboard();
        if(scoreboard == null)
            return;

        String scoreboardName = player.getScoreboardName();
        if(scoreboardName == null)
            return;

        var currentManaObjective = scoreboard.getOrCreateObjective("kob.mana");
        if(currentManaObjective == null)
            return;
        var maxManaObjective = scoreboard.getOrCreateObjective("kob.mana.max");
        if(maxManaObjective == null)
            return;

        var myManaScore = scoreboard.getOrCreatePlayerScore(scoreboardName, currentManaObjective);
        var maxManaScore = scoreboard.getOrCreatePlayerScore(scoreboardName, maxManaObjective);
        
        int currentMana = myManaScore.getScore();

        if(currentMana <= 0)
            return;

        float amount = (float)maxManaScore.getScore() * (float)amountPercentage;
        myManaScore.add(-(int)amount);
    }
}
