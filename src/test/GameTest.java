package test;

import game.Game;
import game.Cell;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        this.game = new Game();
    }

    @Test
    public void aSingleCellShouldHaveZeroNeighbors() {
        Cell cell = new Cell(0, 0);
        game.addCell(cell);

        Set<Cell> neighbors = game.getAliveNeighbors(cell);

        assertEquals(0, neighbors.size());
    }

    @Test
    public void twoCellsNextToEachOtherShouldBeNeighbors() {
        Cell firstCell = new Cell(0, 0);
        Cell secondCell = new Cell(0, 1);

        game.addCell(firstCell);
        game.addCell(secondCell);

        Set<Cell> neighborsOfFirstCell = game.getAliveNeighbors(firstCell);
        Set<Cell> neighborsOfSecondCell = game.getAliveNeighbors(secondCell);

        assertEquals(1, neighborsOfFirstCell.size());
        assertTrue(neighborsOfFirstCell.contains(secondCell));
        assertEquals(1, neighborsOfSecondCell.size());
        assertTrue(neighborsOfSecondCell.contains(firstCell));
    }

    @Test
    public void twoSingleCellFarFromEachOtherShouldHaveZeroNeighbors() {
        Cell firstCell = new Cell(0, 0);
        Cell secondCell = new Cell(0, 6);
        game.addCell(firstCell);
        game.addCell(secondCell);

        Set<Cell> neighborsOfFirstCell = game.getAliveNeighbors(firstCell);
        Set<Cell> neighborsOfSecondCell = game.getAliveNeighbors(secondCell);

        assertEquals(0, neighborsOfFirstCell.size());
        assertEquals(0, neighborsOfSecondCell.size());
    }

    @Test
    public void aSurroundedCellShouldHaveEightNeighbors() {
        List<Cell> addedCells = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Cell cell = new Cell(x, y);
                game.addCell(cell);
                addedCells.add(cell);
            }
        }

        Cell centerCell = new Cell(1, 1);
        addedCells.remove(centerCell);
        Set<Cell> neighborsOfCenterCell = game.getAliveNeighbors(centerCell);

        assertEquals(8, neighborsOfCenterCell.size());
        assertTrue(addedCells.containsAll(neighborsOfCenterCell));
    }


    @Test
    public void toStringTest() {
        Cell cell = new Cell(0, -1);
        game.addCell(cell);
        assertEquals("  ()\n    \n", game.toString(2));
    }
}
