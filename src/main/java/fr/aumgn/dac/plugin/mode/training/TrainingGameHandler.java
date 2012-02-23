package fr.aumgn.dac.plugin.mode.training;

import org.bukkit.Location;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.column.GlassyColumn;
import fr.aumgn.dac.api.area.column.UniformColumn;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;

public class TrainingGameHandler extends SimpleGameHandler<TrainingGamePlayer> {

    private Game<TrainingGamePlayer> game;
    private Arena arena;

    public TrainingGameHandler(Game<TrainingGamePlayer> game) {
        this.game = game;
        this.arena = game.getArena();
    }

    @Override
    public void onStart() {
        if (DAC.getConfig().getResetOnStart()) {
            arena.getPool().reset();
        }
        game.send(DACMessage.GameStart);
    }

    @Override
    public void onTurn(TrainingGamePlayer player) {
        player.send(DACMessage.GamePlayerTurn2);
        player.sendToOthers(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
        player.tpToDiving();
    }

    @Override
    public void onSuccess(TrainingGamePlayer player) {
        Location loc = player.getPlayer().getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        
        player.tpAfterJump();
        
        Pool pool = arena.getPool();
        if (pool.isADACPattern(x, z)) {
            player.incrementDACs();
            pool.getColumn(x, z).set(new GlassyColumn(player.getColor()));
            player.send(DACMessage.GameDAC2);
            player.sendToOthers(DACMessage.GameDAC.format(player.getDisplayName()));
        } else {
            player.incrementSuccesses();
            pool.getColumn(x, z).set(new UniformColumn(player.getColor()));
            player.send(DACMessage.GameJumpSuccess2);
            player.sendToOthers(DACMessage.GameJumpSuccess.format(player.getDisplayName()));
        }
        game.nextTurn();
    }

    @Override
    public void onFail(TrainingGamePlayer player) {
        player.tpAfterFail();
        player.incrementFails();
        player.send(DACMessage.GameJumpFail2);
        player.sendToOthers(DACMessage.GameJumpFail.format(player.getDisplayName()));
        game.nextTurn();
    }
    
    private void sendStats(TrainingGamePlayer player) {
        player.send(DACMessage.StatsSuccess.format(player.getSuccesses()));
        player.send(DACMessage.StatsDAC.format(player.getDACs()));
        player.send(DACMessage.StatsFail.format(player.getFails()));
    }

    @Override
    public void onQuit(TrainingGamePlayer player) {
        sendStats(player);
        if (game.getPlayers().size() == 0) {
            game.stop();
        }
    }

    @Override
    public void onStop() {
        for (TrainingGamePlayer player : game.getPlayers()) {
            sendStats(player);
        }
    }

}
