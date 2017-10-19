package me.dragon.base.core;

/**
 * Created by dragon on 4/1/2017.
 */
public class ExceptionUtils {
    /**
     * 将CheckedException转换为UnCheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e.getMessage(), e);
    }
}
