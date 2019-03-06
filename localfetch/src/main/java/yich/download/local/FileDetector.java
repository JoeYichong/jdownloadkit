package yich.download.local;

import java.util.function.Predicate;

abstract public class FileDetector<T> implements Predicate<T> {
    abstract public void alt();

    public static <T> FileDetector of(Predicate<T> predicate){
        if (predicate == null) {
            return null;
        }
        return new FileDetector<T>() {
            @Override
            public boolean test(T t) {
                return predicate.test(t);
            }

            @Override
            public void alt() {
            }
        };

    }
}
