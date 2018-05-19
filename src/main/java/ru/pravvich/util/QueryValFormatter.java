package ru.pravvich.util;

import lombok.NonNull;

/**
 * @author Pavel Ravvich.
 */
public class QueryValFormatter {

    public enum LikeStrategy {
        LEFT, RIGHT, ANY
    }

    public static String toLike(@NonNull String value, @NonNull LikeStrategy strategy) {
        String expression;
        if (strategy == LikeStrategy.ANY) {
            expression = String.format("%s%s%s", "%", value, "%");
        } else if (strategy == LikeStrategy.LEFT) {
            expression = String.format("%s%s", "%", value);
        } else if (strategy == LikeStrategy.RIGHT) {
            expression = String.format("%s%s", value, "%");
        } else {
            throw new RuntimeException("Unknown LikeStrategy. LikeStrategy must be LEFT or RIGHT or ANY");
        }
        return expression;
    }
}
