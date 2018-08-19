package net.spehl.jpa.techtalk;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Helpers {
    private Helpers(){}

    public static <T> Supplier<T> chooseOneOf(T ... values) {
        Random random = new Random();
        return () -> values[random.nextInt(values.length)];
    }

    public static <T> Function<Integer[], Supplier<T>> weightedChooseOneOf(T ... values) {
        Random random = new Random();
        return (weights) -> {
            checkArgument(weights.length == values.length);
            int total = sum(weights);
            return () -> {
                int currentSum = 0;
                int choosenElement = random.nextInt(total);
                for (int i = 0; i < values.length; i++) {
                    currentSum += weights[i];
                    if (choosenElement < currentSum) {
                        return values[i];
                    }
                }
                throw new AssertionError("");
            };
        };
    }

    public static int sum(Integer[] ints) {
        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }

}
