package yich.download.local;

import yich.base.logging.JUL;
import yich.base.predicate.PredicateNode;
import yich.base.util.StrUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileCleaner extends PredicateNode<Path> implements Callable<Integer> {
    final public static Logger logger = JUL.getLogger(FileCollector.class);

    public FileCleaner(String name) {
        super(name);
        this.setCombinerAnd();
    }

    public FileCleaner() {
        super("FileCleaner@" +StrUtil.randomAlphaNumeric(5));
    }

    public int clean(Path dir) {
        int[] count = {0};
        try (Stream<Path> paths = Files.walk(dir)) {
            paths
                 .filter(this)
                 .forEach(path -> {
                      try {
                          Files.deleteIfExists(path);
                          count[0]++;
                      } catch (IOException e) {
                          logger.info(e.getMessage());
                      }
                  });
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.info(e.getMessage());
        }

        return count[0];
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }

    @Override
    protected String getHint(Path path) {
        return path.toString();
    }

}
