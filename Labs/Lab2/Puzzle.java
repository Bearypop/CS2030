class Puzzle {
    private final int n;
    private final Grid2D grid;

    Puzzle(int n) { 
        this.n = n;
        ImList<Integer> list = new ImList<Integer>();
        for (int i = 1; i < n * n; i++) {
            list = list.add(i);
        }
        list = list.add(0);
        this.grid = new Grid2D(list, n);
    }

    Puzzle(Grid2D grid) {
        this.grid = grid;
        this.n = (int)Math.sqrt(this.grid.size()); 
    }

    @Override
    public String toString() {
        String string = "\n";
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.grid.get(i, j) == 0) {
                    string += "   .";
                } else {
                    string += String.format("%4d", this.grid.get(i, j));
                }
            }
            if (i != n - 1) {
                string += "\n";
            }
        }
        return string;
    }

    Puzzle move(int num) {
        Grid2D tmpGrid = new Grid2D(this.n);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                tmpGrid = tmpGrid.add(this.grid.get(i,j));
            }
        }
        int row = 0;
        int col = 0;
        boolean found = false;
        for (row = 0; row < this.n; row++) {
            for (col = 0; col < this.n; col++) {
                if (this.grid.get(row, col) == num) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (row != 0 && this.grid.get(row - 1, col) == 0) {
            tmpGrid = this.grid.set(row - 1, col, num);
            tmpGrid = tmpGrid.set(row, col, 0);
        } else if (row != this.n - 1 && this.grid.get(row + 1, col) == 0) {
            tmpGrid = this.grid.set(row + 1, col, num);
            tmpGrid = tmpGrid.set(row, col, 0);
        } else if (col != 0 && this.grid.get(row, col - 1) == 0) {
            tmpGrid = this.grid.set(row, col - 1, num);
            tmpGrid = tmpGrid.set(row, col, 0);
        } else if (col != this.n - 1 && this.grid.get(row, col + 1) == 0) {
            tmpGrid = this.grid.set(row, col + 1, num);
            tmpGrid = tmpGrid.set(row, col, 0);
        }
        return new Puzzle(tmpGrid);
    }

    boolean isSolved() {
        return this.toString().equals(new Puzzle(n).toString());
    }
}







