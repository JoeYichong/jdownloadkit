package yich.download.local.collect;

import yich.base.predicate.TimePredicate;
import yich.base.time.TimeInflater;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileCreationTimePredicate extends TimePredicate<Path> {
    public FileCreationTimePredicate(String name, TimeInflater timeInflater) {
        super(name, timeInflater);
    }

    @Override
    protected Object getTargetTime(Path path) {
        try {
            long long_time = Files.readAttributes(path, BasicFileAttributes.class)
                    .creationTime().toMillis();
            LocalDateTime time =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(long_time), ZoneId.systemDefault());
            return time;
//            return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return path.toString();
    }
}
