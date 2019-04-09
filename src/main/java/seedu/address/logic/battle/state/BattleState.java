package seedu.address.logic.battle.state;

/**
 * Enum that represents the state the battle is currently in.
 */
public enum BattleState {
    /**
     * The battle has not begun yet.
     */
    PRE_BATTLE("Preparing for battle...", Stage.STAGE_BEFORE),
    /**
     * The human player is currently putting their ships.
     */
    PLAYER_PUT_SHIP("You are currently placing ships...", Stage.STAGE_SHIP),
    /**
     * The enemy player is currently putting their ships.
     */
    ENEMY_PUT_SHIP("Enemy is currently placing ships...", Stage.STAGE_SHIP),
    /**
     * The human player is currently planning their attack.
     */
    PLAYER_ATTACK("You are currently planning attack...", Stage.STAGE_BATTLE),
    /**
     * The human player is currently planning their attack.
     */
    ENEMY_ATTACK("Enemy is currently planning attack...", Stage.STAGE_BATTLE),
    /**
     * The human player has won the game.
     */
    PLAYER_WIN("You have won!", Stage.STAGE_AFTER),
    /**
     * The human player has lost the game..
     */
    ENEMY_WIN("You have lost...", Stage.STAGE_AFTER);

    private static final String STAGE_BEFORE = "game has not started";
    private static final String STAGE_SHIP = "placing ships";
    private static final String STAGE_BATTLE = "battling";
    private static final String STAGE_AFTER = "game has finished";
    /**
     * A human-readable description of the state.
     * Usage: as-is
     */
    private final String description;

    /**
     * A human-readable description of the current stage of battle.
     * <br>
     * Usage: "Cannot (action) while (stage)"
     * e.g. "Cannot put ship while battle is happening"
     * e.g. "Cannot attack while game is finished"
     * e.g. "Cannot attack while game has not started"
     */
    private final Stage stage;

    private BattleState(String description, Stage stage) {
        this.description = description;
        this.stage = stage;
    }

    public String getDescription() {
        return description;
    }

    public String getStage() {
        return stage.toString();
    }

    /**
     * Stores the current stage of the game.
     * <br>
     * Reason for this class is because of this bug:
     * https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6676362
     */
    private enum Stage {
        STAGE_BEFORE("game has not started"),
        STAGE_SHIP("placing ships"),
        STAGE_BATTLE("battling"),
        STAGE_AFTER("game has finished");

        private final String stage;

        Stage(String stage) {
            this.stage = stage;
        }

        @Override
        public String toString() {
            return stage;
        }
    }
}
