package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.battle.AttackDestroyedShip;
import seedu.address.logic.battle.AttackHit;
import seedu.address.logic.battle.AttackMissed;
import seedu.address.logic.battle.AttackResult;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * The StatsCommandTest will test the methods of the statistics class.
 * Contains integration tests (interaction with Model, Player and Battleship commands)
 */
public class StatsCommandTest {

    @Test
    public void execute_addHit_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(1, p1.addHit());
        assertEquals(2, p1.addHit());
    }

    @Test
    public void execute_addMiss_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(1, p1.addMiss());
        assertEquals(2, p1.addMiss());
    }

    @Test
    public void execute_minusMove_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(9, p1.addMove());
        assertEquals(8, p1.addMove());
    }

    @Test
    public void execute_getMovesLeft_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(10, p1.getMovesMade());
        p1.addMove();
        assertEquals(9, p1.getMovesMade());
    }

    @Test
    public void execute_getHitCount_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, p1.getHitCount());
        p1.addHit();
        assertEquals(1, p1.getHitCount());
    }

    @Test
    public void execute_getMissCount_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, p1.getMissCount());
        p1.addMiss();
        assertEquals(1, p1.getMissCount());
    }

    @Test
    public void execute_getAccuracy_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, (int) p1.getAccuracy());
        p1.addAttack();
        p1.addHit();
        assertEquals(1, (int) p1.getAccuracy());
    }

    @Test
    public void execute_getEnemyShipsDestroyed_success() {
        PlayerStatistics p1 = new PlayerStatistics();
        assertEquals(0, (int) p1.getEnemyShipsDestroyed());
    }

    @Test
    public void execute_addResultToStats_hitSuccess() {
        PlayerStatistics p1 = new PlayerStatistics();
        AttackResult res = new AttackHit(new Player("Alice", 5, 2, 1),
                new Player("Bob", 5, 2, 1), new Coordinates("a2"));
        assertEquals("hit", p1.addResultToStats(res));
    }

    @Test
    public void execute_addResultToStats_missSuccess() {
        PlayerStatistics p1 = new PlayerStatistics();
        AttackResult res = new AttackMissed(new Player("Alice", 5, 2, 1),
                new Player("Bob", 5, 2, 1), new Coordinates("a2"));
        assertEquals("missed", p1.addResultToStats(res));
    }

    @Test
    public void execute_addResultToStats_attackDestroyedSuccess() {
        PlayerStatistics p1 = new PlayerStatistics();
        AttackResult res = new AttackDestroyedShip(new Player("Alice", 5, 2, 1),
                new Player("Bob", 5, 2, 1), new Coordinates("a2"),
                new Battleship(new Name("Placeholder"), 2, 2, new HashSet<>()));
        assertEquals("destroyed", p1.addResultToStats(res));
    }
}
