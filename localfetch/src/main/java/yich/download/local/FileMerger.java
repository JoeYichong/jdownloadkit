package yich.download.local;

import yich.base.dbc.Require;
import yich.base.logging.JUL;
import yich.base.preserver.ByteArrayPreserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileMerger implements Callable<Path> {
    final public static Logger logger = JUL.getLogger(FileMerger.class);
    private Path src;
    private Path dst;

    private String tag;

    private String suffix;

    private boolean delSrc = false;

    private FileAssembler fileAssembler;

    public FileMerger(Path src, Path dst) {
        setSrc(src);
        setDst(dst);
        this.tag = "";
        this.suffix = "ts";

        fileAssembler = new SimpleFileAssembler();
    }

    public Path getSrc() {
        return src;
    }

    public FileMerger setSrc(Path src) {
        Require.argumentWCM(Files.isDirectory(src), "Parameter 'src' value '" +
                src.toString() +"' isn't a directory path.");
        this.src = src;
        return this;
    }

    public FileMerger setSrc(String src) {
        if (src != null) {
            setSrc(Paths.get(src));
        }
        return this;
    }

    public Path getDst() {
        return dst;
    }

    public FileMerger setDst(Path dst) {
        Require.argumentWCM(Files.isDirectory(dst), "Parameter 'dst' value '" +
                dst.toString() +"' isn't a directory path.");
        this.dst = dst;
        return this;
    }

    public FileMerger setDst(String dst) {
        if (dst != null) {
            setDst(Paths.get(dst));
        }
        return this;
    }

    public boolean isDelSrc() {
        return delSrc;
    }

    public FileMerger setDelSrc(boolean delSrc) {
        this.delSrc = delSrc;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public FileMerger setTag(String tag) {
        if (tag != null) {
            this.tag = tag;
        }
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public FileMerger setSuffix(String suffix) {
        if (suffix != null) {
            this.suffix = suffix;
        }
        return this;
    }

    public FileAssembler getFileAssembler() {
        return fileAssembler;
    }

    public FileMerger setFileAssembler(FileAssembler fileAssembler) {
        Require.argumentNotNull(fileAssembler);
        this.fileAssembler = fileAssembler;
        return this;
    }

    public String merge(boolean del) {
        String filePath = null;
        try (Stream<Path> paths = Files.walk(src)) {
            List<byte[]> list =
                    paths
                        .filter(Files::isRegularFile)
                        .map(path -> {
                             try {
                                 ByteArrayOutputStream temp = new ByteArrayOutputStream((int) Files.size(path));
                                 Files.copy(path, temp);
                                 return temp.toByteArray();
                             } catch (IOException e) {
                                 //logger.info(e.getMessage());
                                 throw new RuntimeException(e);
                             }
                        })
                        .collect(Collectors.toList());
                        //.forEach(System.out::println);
            if (list.size() > 0 && list.get(0).length > 0) {
                filePath = (String) new ByteArrayPreserver()
                                           .setBasePath(this.dst.toString() + File.separator)
                                           .setFormat(this.suffix)
                                           .appendTag(this.tag)
                                           .apply(fileAssembler.assemble(list));
                System.out.println("** Data has been Saved to '" + filePath + "'");
            } else {
                System.out.println("** No Data to Save");
                logger.info("** No Data to Save");
            }

        } catch (IOException e) {
            logger.info(e.getMessage());
            return filePath;
        }

        if (del || delSrc) {
            FileCleaner cleaner = FileCleaner.getInstance();
            cleaner.addPredicate(Files::isRegularFile);
            int num = cleaner.clean(src);
            System.out.println("** Merger: " + num + " Source Files has been Cleaned.");
            logger.info("** Merger: " + num + " Source Files has been Cleaned.");
        }
        return filePath;
    }

    public String merge() {
        return merge(false);
    }

    @Override
    public Path call() throws Exception {
        return Paths.get(merge());
    }
}
