package game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {

    private final Set<Cell> cells;

    public Game() {
        this.cells = new HashSet<>();
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public void addCells(Collection<Cell> cells) {
        cells.stream().forEach(this::addCell);
    }

    public boolean isAlive(Cell cell) {
        return this.cells.contains(cell);
    }

    public Set<Cell> getAliveNeighbors(Cell cell) {
        Set<Cell> neighborhood = cell.getNeighborhood();
        return neighborhood.stream()
                .filter(this::isAlive)
                .collect(Collectors.toSet());
    }

    public String toString(int size) {
        final StringBuilder txt = new StringBuilder();
        IntStream.range(-size / 2, size / 2).forEach(j -> {
                    IntStream.range(-size / 2, size / 2).forEach(i ->
                                    txt.append(this.isAlive(new Cell(i, j)) ? "()" : "  ")
                    );
                    txt.append("\n");
                }
        );
        return txt.toString();
    }

    public Game nextGeneration() {
        Game nextGeneration = new Game();
        this.addNextGenerationStillAliveCells(nextGeneration);
        this.addNextGenerationRebornCells(nextGeneration);
        return nextGeneration;
    }

    private void addNextGenerationStillAliveCells(Game nextGeneration) {
        this.cells.stream().filter(this::shouldRemainAliveInNextGeneration).forEach(nextGeneration::addCell);
    }

    private void addNextGenerationRebornCells(Game nextGeneration) {
        this.cells.stream()
                .map(Cell::getNeighborhood)
                .forEach(neighborhood -> neighborhood.stream()
                                .filter(this::shouldBeRebornInNextGeneration)
                                .forEach(nextGeneration::addCell)
                );
    }

    private boolean shouldRemainAliveInNextGeneration(Cell cell) {
        return this.getAliveNeighbors(cell).size() == 2 || this.getAliveNeighbors(cell).size() == 3;
    }

    private boolean shouldBeRebornInNextGeneration(Cell cell) {
        return !this.isAlive(cell) && this.getAliveNeighbors(cell).size() == 3;
    }
}
