package yich.download.local.collect;

import yich.base.predicate.PredicateNode;

import java.util.function.Predicate;

abstract public class FileDetector<T> extends PredicateNode<T> {
    public FileDetector(String name) {
        super(name);
    }

    abstract public void alt();

    public static <T> FileDetector of(Predicate<T> predicate){
        if (predicate == null) {
            return null;
        }
        return new FileDetector<T>(null) {
            @Override
            protected String getHint(T t) {
                return null;
            }

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
