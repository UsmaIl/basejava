import java.util.*;
import java.util.stream.Collectors;

public class HW12 {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{2, 3, 4, 4, 5, 5, 1, 1, 2, 0, 0}));
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(13);
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] value) {
        return Integer.parseInt(Arrays.stream(value).sorted().distinct().mapToObj(Objects::toString).collect(Collectors.joining()));
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> x % 2 == 0, Collectors.toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
