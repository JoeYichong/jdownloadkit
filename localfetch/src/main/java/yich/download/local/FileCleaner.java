package yich.download.local;

import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.predicate.PredicateNode;
import yich.base.util.StrUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileCleaner extends PredicateNode<Path> implements Callable<Integer> {
    final public static Logger logger = JUL.getLogger(FileCollector.class);

    private Path path = null;

    public FileCleaner(String name) {
        super(name);
        this.setCombinerAnd();
    }

    public FileCleaner() {
        super(FileCleaner.class.getName() + "@" +StrUtil.randomAlphaNumeric(5));
    }

    public Path getPath() {
        return path;
    }

    public FileCleaner setPath(Path path) {
        Require.argumentWCM(Files.isDirectory(path), "Parameter 'path' value '" +
                path.toString() +"' isn't a directory path.");
        this.path = path;
        return this;
    }

    public FileCleaner setPath(String path) {
        if (path != null) {
            setPath(Paths.get(path));
        }
        return this;
    }

    public boolean cancel() {
        boolean action = (path != null);
        if (action) {
            this.path = null;
        }
        return action;
    }

    public int clean(Path dir) {
        if (dir == null) {
            return 0;
        }
        int[] count = {0};
        try {
            if (Files.isRegularFile(dir)) {
                Files.deleteIfExists(dir);
            }

            try (Stream<Path> paths = Files.walk(dir)) {
                paths
                     .filter(this)
                     .forEach(path -> {
                         try {
                              Files.deleteIfExists(path);
                              count[0]++;
                         } catch (IOException e) {
                              System.out.println(e.getMessage());
                              logger.info(e.getMessage());
                         }
                      });
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.info(e.getMessage());
        }
        return count[0];
    }

    @Override
    public Integer call() {
        if (this.path != null) {
            int num = clean(this.path);
            this.path = null;
            return num;
        }
        return 0;
    }

    @Override
    protected String getHint(Path path) {
        return path.toString();
    }

}
