import java.util.*;
import java.util.stream.Collectors;

public class MainStreamAPI {
    public static void main(String[] args) {
        int[] testArray = new int[]{2, 3, 4, 4, 5, 5, 1, 1, 2, 0, 7};
        System.out.println(minValue(testArray));
        System.out.println(oddOrEven(Arrays.asList(2, 3, 4, 5, 6, 7, 13)));
    }

    public static int minValue(int[] value) {
        return Arrays.stream(value)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> 10 * x + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0, Collectors.toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
