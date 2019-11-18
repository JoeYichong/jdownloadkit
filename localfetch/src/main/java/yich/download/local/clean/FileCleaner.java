package yich.download.local.clean;

import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.predicate.PredicateNode;
import yich.base.util.StrUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileCleaner extends PredicateNode<Path> {
    final public static Logger logger = JUL.getLogger(FileCleaner.class);

    private List<Path> queue;

    private FileCleaner() {
        super(FileCleaner.class.getName() + "@" +StrUtil.randomAlphaNumeric(5));
        this.setCombinerAnd();
        this.addPredicate(Files::isRegularFile);
        this.queue = new ArrayList<>();
    }

    public static FileCleaner getInstance() {
        return new FileCleaner();
    }

    public int clean(Path dir) {
        if (dir == null) {
            return 0;
        }
        Require.argumentWCM(Files.isDirectory(dir), "Parameter 'dir' value '" +
                dir.toString() +"' isn't a valid directory path.");

        int[] count = {0};
        synchronized (FileCleaner.class) {
            try {
//                if (Files.isRegularFile(dir)) {
//                    Files.deleteIfExists(dir);
//                }

                try (Stream<Path> paths = Files.walk(dir)) {
                    paths
                            .filter(this)
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                    count[0]++;
                                } catch (IOException e) {
//                              System.out.println("** Fail to delete: " + e.getMessage());
//                              logger.info("** Fail to delete: " + e.getMessage());
                                }
                            });
                }

            } catch (IOException e) {
                System.out.println("** IOException: " + e.getMessage());
                logger.info(e.getMessage());
            }

        }

        return count[0];
    }

    public FileCleaner addPath(String path) {
        this.queue.add(Paths.get(path));
        return this;
    }

    public FileCleaner addPath(Path path) {
        Require.argumentNotNull(path);
        this.queue.add(path);
        return this;
    }

    public void clearPaths() {
        this.queue.clear();
    }

    public void removePath(Path path) {
        this.queue.removeIf(p -> p.toString().equals(path.toString()));
    }

    public int start() {
        int[] num = {0}, acc = {0};
        queue.forEach(path -> {
            num[0] = clean(path);
            acc[0] += num[0];
            System.out.println("** Cleaner: " + num[0] + " Cached Files has been Cleaned.");
            logger.info("** Cleaner: " + num[0] + " Cached Files has been Cleaned.");
        });
        return acc[0];
    }

    @Override
    protected String getHint(Path path) {
        return path.toString();
    }

}
