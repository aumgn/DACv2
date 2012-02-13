package fr.aumgn.dac.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACUtil;

public enum DACMessage {

	CmdDefineExists         ("command.define.exists"),
	CmdDefineSuccess        ("command.define.success"),
	CmdDeleteUnknown        ("command.delete.unknown"),
	CmdDeleteInGame         ("command.delete.in-game"),
	CmdDeleteSuccess        ("command.delete.success"),
	CmdArenasList           ("command.arenas.list"),
	CmdArenasArena          ("command.arenas.arena"),
	CmdGotoNotInGame        ("command.goto.not-in-game"),
	CmdGotoSuccess          ("command.goto.success"),
	CmdJoinAlreadyPlaying   ("command.join.already-playing"),
	CmdJoinNotInStart       ("command.join.not-in-start"),
	CmdJoinInGame           ("command.join.in-game"),
	CmdJoinMaxReached       ("command.join.max-reached"),
	CmdKickNotInGame        ("command.kick.not-in-game"),
	CmdKickNoPlayerFound    ("command.kick.no-player-found"),
	CmdLivesNotInGame       ("command.lives.not-in-game"),
	CmdLivesUnknownPlayer   ("command.lives.unknown-player"),
	CmdModesUnknown         ("command.modes.unknown"),
	CmdModesList            ("command.modes.list"),
	CmdModesMode            ("command.modes.mode"),
	CmdModesAddSuccess      ("command.modes.add-success"),
	CmdModesRemoveSuccess   ("command.modes.remove-success"),
	CmdOptionsUnknown       ("command.options.unknown"),
	CmdOptionsList          ("command.options.list"),
	CmdOptionsOption        ("command.options.option"),
	CmdOptionsAddSuccess    ("command.options.add-success"),
	CmdOptionsRemoveSuccess ("command.options.remove-success"),
	CmdQuitNotInGame        ("command.quit.not-in-game"),
	CmdReloadSuccess        ("command.reload.success"),
	CmdResetUnknown         ("command.reset.unknown"),
	CmdResetInGame          ("command.reset.in-game"),
	CmdResetSuccess         ("command.reset.success"),
	CmdSelectUnknown        ("command.select.unknown"),
	CmdSelectSuccessPool    ("command.select.success-pool"),
	CmdSelectSuccessStart   ("command.select.success-start"),
	CmdSelectError          ("command.select.error"),
	CmdSetUnknown           ("command.set.unknown"),
	CmdSetWrongWorld        ("command.set.another-world"),
	CmdSetSuccessDiving     ("command.set.success-diving"),
	CmdSetSuccessPool       ("command.set.success-pool"),
	CmdSetSuccessStart      ("command.set.success-start"),
	CmdSetError             ("command.set.error"),
	CmdSetIncompleteRegion  ("command.set.incomplete-selection"),
	CmdStartNotInGame       ("command.start.not-in-game"),
	CmdStartUnknownMode     ("command.start.unknown-mode"),
	CmdStartUnavailableMode ("command.start.unavailable-mode"),
	CmdStartMinNotReached   ("command.start.min-not-reached"),
	CmdStopNoGameToStop     ("command.stop.no-game-to-stop"),

	JoinNewGame             ("join.new-game"),
	JoinNewGame2            ("join.new-game2"),
	JoinCurrentPlayers      ("join.current-players"),
	JoinPlayerList          ("join.player-list"),
	JoinPlayerJoin          ("join.player-join"),
	JoinPlayerQuit          ("join.player-quit"),
	JoinStopped             ("join.stopped"),

	GameStart               ("game.start"),
	GamePlayers             ("game.players"),
	GamePlayerList          ("game.player-list"),
	GameEnjoy               ("game.enjoy"),
	GamePlayerTurn          ("game.player-turn"),
	GamePlayerTurn2         ("game.player-turn2"),
	GameTurnTimedOut        ("game.turn-timed-out"),
	GameConfirmation        ("game.confirmation"),
	GameConfirmation2       ("game.confirmation2"),
	GameDACConfirmation     ("game.dac-confirmation"),
	GameDACConfirmation2    ("game.dac-confirmation2"),
	GameDACConfirmation3    ("game.dac-confirmation3"),
	GameDAC                 ("game.dac"),
	GameDAC2                ("game.dac2"),
	GameLivesAfterDAC       ("game.lives-after-dac"),
	GameLivesAfterDAC2      ("game.lives-after-dac2"),
	GameJumpSuccess         ("game.jump-success"),
	GameJumpSuccess2        ("game.jump-success2"),
	GameJumpFail            ("game.jump-fail"),
	GameJumpFail2           ("game.jump-fail2"),
	GameConfirmationFail    ("game.confirmation-fail"),
	GameConfirmationFail2   ("game.confirmation-fail2"),
	GameLivesAfterFail      ("game.lives-after-fail"),
	GameLivesAfterFail2     ("game.lives-after-fail2"),
	GamePlayerQuit          ("game.player-quit"),
	GameMustConfirmate      ("game.must-confirmate"),
	GameMustConfirmate2     ("game.must-confirmate2"),
	GameFinished            ("game.finished"),
	GameWinner              ("game.winner"),
	GameRank                ("game.rank"),
	GameStopped             ("game.stopped"),
	GameDisplayLives        ("game.display-lives");
	
	private static final String MESSAGES_FILENAME = "messages.yml";

	public static void reload(Plugin plugin) {
		YamlConfiguration newMessages = new YamlConfiguration();
		YamlConfiguration defaultMessages = new YamlConfiguration();
		try {
			newMessages.load(new File(plugin.getDataFolder(), MESSAGES_FILENAME));
			defaultMessages.load(plugin.getResource(MESSAGES_FILENAME));
			load(newMessages, defaultMessages);
		} catch (IOException exc) {
			DAC.getLogger().severe("Unable to read " + MESSAGES_FILENAME + " file.");
			DAC.getLogger().severe(exc.getClass().getSimpleName() + " exception raised");
		} catch (InvalidConfigurationException exc) {
			DAC.getLogger().severe("Unable to load " + MESSAGES_FILENAME + " file.");
			DAC.getLogger().severe(exc.getClass().getSimpleName() + " exception raised");
		}
	}

	private static void load(Configuration config, Configuration defaults) {
		for (DACMessage message : DACMessage.values()) {
			String node = message.getNode();
			if (config.isString(node)) {
				message.setValue(DACUtil.parseColorsMarkup(config.getString(node)));
			} else if (defaults.isString(node)) { 
				message.setValue(DACUtil.parseColorsMarkup(defaults.getString(node)));
			} else {
				message.setValue("");
			}
		}
	}

	private String node;
	private String value;

	private DACMessage(String node) {
		this.node = node;
		this.value = "";
	}

	public String getNode() {
		return node;
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return getValue();
	}

	public String format(Object... args) {
		return String.format(getValue(), args);
	}

}
