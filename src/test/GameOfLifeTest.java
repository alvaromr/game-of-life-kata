package test;

import game.Game;
import game.Cell;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class GameOfLifeTest {
    private Game game;


    @Before
    public void setUp() {
        this.game = new Game();
    }

    @Test
    public void aSingleCellShouldDieInNextTick() {
        Cell cell = new Cell(0, 0);
        game.addCells(Collections.singletonList(cell));

        Game nextGeneration = game.nextGeneration();

        assertFalse(nextGeneration.isAlive(cell));
    }

    @Test
    public void aCellWithOnlyOneNeighborShouldDieInNextTick() {
        Cell firstCell = new Cell(0, 0);
        Cell secondCell = new Cell(0, 1);
        game.addCells(Arrays.asList(firstCell, secondCell));

        Game nextGeneration = game.nextGeneration();

        assertFalse(nextGeneration.isAlive(firstCell));
        assertFalse(nextGeneration.isAlive(secondCell));
    }

    @Test
    public void cellWithTwoNeighborsShouldLiveInNextTick() {
        Cell middleCell = new Cell(0, 0);
        Cell upperCell = new Cell(0, 1);
        Cell bottomCell = new Cell(0, -1);
        game.addCells(Arrays.asList(upperCell, middleCell, bottomCell));

        Game nextGeneration = game.nextGeneration();

        assertTrue(nextGeneration.isAlive(middleCell));
    }

    @Test
    public void cellWithThreeNeighborsShouldLiveInNextTick() {
        Cell middleCell = new Cell(0, 0);
        Cell upperCell = new Cell(0, 1);
        Cell rightCell = new Cell(1, 0);
        Cell bottomCell = new Cell(0, -1);
        game.addCells(Arrays.asList(upperCell, middleCell, bottomCell, rightCell));

        Game nextGeneration = game.nextGeneration();

        assertTrue(nextGeneration.isAlive(middleCell));
    }

    @Test
    public void cellWithMoreThanThreeNeighborsShouldDieInNextTick() {
        Cell middleCell = new Cell(0, 0);
        Cell upperCell = new Cell(0, 1);
        Cell rightCell = new Cell(1, 0);
        Cell leftCell = new Cell(1, -1);
        Cell bottomCell = new Cell(0, -1);
        game.addCells(Arrays.asList(upperCell, middleCell, bottomCell, rightCell, leftCell));

        Game nextGeneration = game.nextGeneration();

        assertFalse(nextGeneration.isAlive(middleCell));
    }

    @Test
    public void aDeadCellWithThreeNeighborsShouldBeRebornInNextTick() {
        Cell middleCell = new Cell(0, 0);
        Cell upperCell = new Cell(0, 1);
        Cell rightCell = new Cell(1, 0);
        Cell bottomCell = new Cell(0, -1);

        game.addCells(Arrays.asList(upperCell, bottomCell, rightCell));

        Game nextGeneration = game.nextGeneration();

        assertTrue(nextGeneration.isAlive(middleCell));
    }

    @Test
    public void shouldProcessMultipleGenerations() {
        Cell middleCell = new Cell(0, 0);
        Cell upperCell = new Cell(0, 1);
        Cell rightCell = new Cell(1, 0);
        Cell leftCell = new Cell(-1, 0);
        Cell bottomCell = new Cell(0, -1);
        game.addCells(Arrays.asList(upperCell, middleCell, bottomCell, rightCell, leftCell));

        for (int i = 0; i < 10; i++) {
            System.out.println(game.toString(15));
            Game nextGeneration = game.nextGeneration();
            assertNotSame(nextGeneration, game);
            game = nextGeneration;
        }
    }
}
