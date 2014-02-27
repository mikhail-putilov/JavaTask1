import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String[]> rows = new ArrayList<String[]>();
        rows.add(new String[]{"1", "123"});
        rows.add(new String[]{"2", "1asd"});
        rows.add(new String[]{"4", "1"});
        rows.add(new String[]{"4", ""});
        rows.add(new String[]{"2", null});
        rows.add(new String[]{"0", "c123"});
        rows.add(new String[]{"7", "dsa"});
        IStringRowsListSorter sorter = Task1Impl.INSTANCE;
        sorter.sort(rows, 1);
        sorter.sort(rows, 0);
        for(String[] a : rows) {
            System.out.println(Arrays.asList(a));
        }
    }
}
