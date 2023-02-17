import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.function.UnaryOperator;

class Main {

    static IntStream twinPrimes(int n) {
        return IntStream.range(2, n + 1)
            .filter(x -> isPrime(x))
            .filter(x -> isPrime(x - 2) || isPrime(x + 2));
    }

    static boolean isPrime(int n) {
        return n > 1 && IntStream.range(2, (int) Math.sqrt(n) + 1)
            .noneMatch(x -> n % x == 0);
    }

    static String reverse(String str) {
        return Stream.<String>of(str.split(""))
            .reduce("", (x,y) -> y + x);
    }

    static long countRepeats(List<Integer> list) {
        long count = IntStream.range(1, list.size() - 1)
            .filter(x -> list.get(x) == list.get(x + 1) && list.get(x) != list.get(x - 1))
            .count();
        if (list.get(0) == list.get(1)) {
            count++;
        }
        return count;
    }

    static UnaryOperator<List<Integer>> generateRule() {
        return x -> {
            return IntStream.range(0, x.size())
                .map(y -> {
                    if (x.get(y) == 1) {
                        return 0;
                    } else if ((y != 0 && y != x.size() - 1) &&
                            (x.get(y + 1) == 1 && x.get(y - 1) == 0 ||
                             x.get(y + 1) == 0 && x.get(y - 1) == 1)) {
                        return 1;
                    }
                    if (y == 0 && x.get(y + 1) == 1) {
                        return 1;
                    }
                    if (y == x.size() - 1 && x.get(y - 1) == 1) {
                        return 1;
                    }
                    return 0;
                })
            .boxed()
                .collect(Collectors.toList());
        };
    }

    static Stream<String> gameOfLife(List<Integer> list, UnaryOperator<List<Integer>> rule, int n) {
        return Stream.<List<Integer>>iterate(list, rule)
            .limit(n)
            .map(x -> {
                return IntStream.range(0, x.size())
                    .mapToObj(y -> x.get(y) == 1 ? "*" : " ")
                    .reduce("", (a,b) -> a + b);
            });
    }
}
