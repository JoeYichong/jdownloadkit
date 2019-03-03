package yich.download.local;

import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCollector implements Callable<List<Path>> {
    final public static Logger logger = JUL.getLogger(FileCollector.class);
    private Path src;
    private Path dst;

    private long interval;

    private Thread holder;

    private Predicate<Path> formatDetector;

    private int waiting_count;

    private boolean delSrc;

    public FileCollector(Path src, Path dst) {
        this.interval = 5000;
        this.waiting_count = 0;
        this.holder = null;
        this.delSrc = false;
        setSrc(src);
        setDst(dst);
    }

    public FileCollector(Path src, Path dst, long interval) {
        this(src, dst);
        setInterval(interval);
    }

    public Path getSrc() {
        return src;
    }

    public FileCollector setSrc(Path src) {
        Require.argumentWCM(Files.isDirectory(src), "Parameter 'src' value '" +
                                           src.toString() +"' isn't a directory path.");
        this.src = src;
        return this;
    }

    public Path getDst() {
        return dst;
    }

    public FileCollector setDst(Path dst) {
        Require.argumentWCM(Files.isDirectory(dst), "Parameter 'dst' value '" +
                                           dst.toString() +"' isn't a directory path.");
        this.dst = dst;
        return this;
    }

    public long getInterval() {
        return interval;
    }

    public FileCollector setInterval(long interval) {
        Require.argument(interval > 0, interval, "interval > 0");
        this.interval = interval;
        return this;
    }

    public Predicate<Path> getFormatDetector() {
        return formatDetector;
    }

    public FileCollector setFormatDetector(Predicate<Path> formatDetector) {
        Require.argumentNotNull(formatDetector);
        this.formatDetector = formatDetector;
        return this;
    }

    public boolean isDelSrc() {
        return delSrc;
    }

    public FileCollector setDelSrc(boolean delSrc) {
        this.delSrc = delSrc;
        return this;
    }

    private long creationTime(Path path) {
        try {
            return Files.readAttributes(path, BasicFileAttributes.class)
                    .creationTime().toMillis();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getCopiedFiles() {
        try (Stream<Path> paths = Files.walk(dst)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.toFile().getName())
//                    .map(name -> {
//                        // System.out.println("dst_list: " + name);
//                        return name;
//                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean isFormatRight(Path path) {
        if (formatDetector == null) {
            return false;
        }
        else {
            return formatDetector.test(path);
        }
    }

    private List<Path> collect() {
        List<String> dst_list = getCopiedFiles();
        List<Path> copied_paths = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(src)) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> {
//                        System.out.println("** Check File: '" + path.toString() + "'");
                        return isFormatRight(path);
                    })
                    .filter(path -> !dst_list.contains(creationTime(path) + ""))
                    .forEach(path -> {
                        try {
                            Path d_path = Paths.get(dst.toString() + File.separator + creationTime(path));
                            Files.copy(path, d_path);
                            copied_paths.add(d_path);
                            this.waiting_count = 0;
                            System.out.print("\n** '" + src + File.separator + path.getFileName()
                                                        + "' Copied to '" + d_path.toString() + "'");
                        } catch (IOException e) {
                            logger.info(e.getMessage());
                        }
                    });

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return copied_paths;
    }

    public int cleanSource(boolean all) {
        List<List<String>> dst_list = new ArrayList<>();

        if (!all) {
            dst_list.add(getCopiedFiles());
        }

        FileCleaner cleaner = new FileCleaner();
        cleaner.addPredicate(Files::isRegularFile)
                .addPredicate(path -> isFormatRight(path))
                .addPredicate(path -> all || dst_list.get(0).contains(creationTime(path) + ""));
        return cleaner.clean(src);
    }

    public boolean isRunning() {
        return this.holder != null;
    }

    @Override
    public List<Path> call() {
        List<Path> paths = new ArrayList<>();
        try {
            while (isRunning() && !this.holder.isInterrupted()) {
                paths.addAll(collect());

                Thread.sleep(interval);
                if (waiting_count == 0) {
                    System.out.print("\nWaiting...");
                } else if (waiting_count % 20 == 0) {
                    System.out.print("\n..");
                } else {
                    System.out.print("..");
                }
                waiting_count ++;
            }
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        } finally {
            System.out.println("** Collector has been terminated.");
            logger.info("** Collector has been terminated.");
            //System.out.println("%% delSrc: " + delSrc);
            if (delSrc) {
                int num = cleanSource(false);
                System.out.println("** Collector: " + num + " Copied Files has been Cleaned.");
                logger.info("** Collector: " + num + " Copied Files has been Cleaned.");
            }
        }

        return paths;
    }

    public Future start() {
        FutureTask task = new FutureTask<>(this);
        this.holder = new Thread(task);
        this.holder.start();
        System.out.println("** Collector has started.");
        logger.info("** Collector has started.");
        return task;
    }

    public void close() {
        if (this.holder != null) {
            Thread thread = this.holder;
            this.holder = null;
            thread.interrupt();
        }
    }

}
