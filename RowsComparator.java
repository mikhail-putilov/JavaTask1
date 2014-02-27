import java.util.Comparator;

public class RowsComparator implements Comparator<String[]> {
    private final int columnIndex;
    private NullSafeNumericalFirstComparator comparator;

    public RowsComparator(final int columnIndex) {
        this.columnIndex = columnIndex;
        comparator = new NullSafeNumericalFirstComparator();
    }

    @Override
    public int compare(final String[] row1, final String[] row2) {
        try {
            String cell_1 = row1[columnIndex];
            String cell_2 = row2[columnIndex];
            return comparator.compare(cell_1, cell_2);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Rows can't be compared by column index: "+ columnIndex);
            throw new IndexOutOfBoundsException(e.getMessage());
        }
    }
}
