package fr.aumgn.dac.config;

import org.bukkit.configuration.Configuration;

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
	GameTurnTimedOut        ("game.turn-timed-out"),
	GameConfirmation        ("game.confirmation"),
	GameDACConfirmation     ("game.dac-confirmation"),
	GameDACConfirmation2    ("game.dac-confirmation2"),
	GameDAC                 ("game.dac"),
	GameLivesAfterDAC       ("game.lives-after-dac"),
	GameJumpSuccess         ("game.jump-success"),
	GameJumpFail            ("game.jump-fail"),
	GameConfirmationFail    ("game.confirmation-fail"),
	GameLivesAfterFail      ("game.lives-after-fail"),
	GamePlayerQuit          ("game.player-quit"),
	GameMustConfirmate      ("game.must-confirmate"),
	GameFinished            ("game.finished"),
	GameWinner              ("game.winner"),
	GameRank                ("game.rank"),
	GameStopped             ("game.stopped"),
	GameDisplayLives        ("game.display-lives");

	public static void load(Configuration config, Configuration defaults) {
		for (DACMessage lang : DACMessage.values()) {
			String node = lang.getNode();
			if (config.isString(node)) {
				lang.setValue(DACUtil.parseColorsMarkup(config.getString(node)));
			} else if (defaults.isString(node)) { 
				lang.setValue(DACUtil.parseColorsMarkup(defaults.getString(node)));
			} else {
				lang.setValue("");
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
