package ru.pravvich.util;

import lombok.NonNull;

/**
 * @author Pavel Ravvich.
 */
public class QueryValFormatter {

    public enum LikeStrategy {
        LEFT, RIGHT, ANY
    }

    public static String toLike(@NonNull String value, @NonNull LikeStrategy... likeStrategy) {
        String expression;
        if (likeStrategy.length == 0 || likeStrategy[0] == LikeStrategy.ANY) {
            expression = String.format("%s%s%s", "%", value, "%");
        } else if (likeStrategy[0] == LikeStrategy.LEFT) {
            expression = String.format("%s%s", "%", value);
        } else if (likeStrategy[0] == LikeStrategy.RIGHT) {
            expression = String.format("%s%s", value, "%");
        } else {
            throw new RuntimeException("Unknown LikeStrategy. LikeStrategy must be LEFT or RIGHT or ANY");
        }
        return expression;
    }
}
