import java.util.List;
import java.util.ArrayList;

class Grid2D {

    private final int numOfCols;
    private final ImList<Integer> list;


    Grid2D(List<Integer> list, int numOfCols) {
        this.list = new ImList<Integer>(list);      
        this.numOfCols = numOfCols; 
    }
    
    @Override
    public String toString() {
        String string = "{";
        for (int i = 1; i <= this.list.size(); i++) {
            string += list.get(i - 1);
            if (i != this.list.size()) {
                if (i % this.numOfCols == 0) {
                    string += ";";
                } else { 
                    string += ",";
                }
            }
        }
        string += "}";
        return string;
    }

    Grid2D(int numOfCols) {
        this.list = new ImList<Integer>();
        this.numOfCols = numOfCols;
    }

    Grid2D(ImList<Integer> list, int numOfCols) {
        this.list = list;
        this.numOfCols = numOfCols;
    }

    Grid2D add(int elem) {
        return new Grid2D(this.list.add(elem), this.numOfCols);
    }

    int get(int r, int c) {
        return this.list.get(r * this.numOfCols + c);
    }

    Grid2D set(int r, int c, int elem) {
        int index = r * this.numOfCols + c;
        return new Grid2D(this.list.set(index, elem), this.numOfCols);
    }

    int size() {
        return this.list.size();
    }
}


   





