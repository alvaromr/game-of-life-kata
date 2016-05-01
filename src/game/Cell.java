package game;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Cell)) {
            return false;
        }
        Cell c = (Cell) o;
        return this.x == c.x && this.y == c.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public Set<Cell> getNeighborhood() {
        Set<Cell> neighborhood = new HashSet<>();
        IntStream.rangeClosed(-1, 1).forEach(dx ->
                        IntStream.rangeClosed(-1, 1).forEach(dy ->
                                        neighborhood.add(new Cell(this.x + dx, this.y + dy))
                        )
        );
        neighborhood.remove(this);
        return neighborhood;
    }
}
