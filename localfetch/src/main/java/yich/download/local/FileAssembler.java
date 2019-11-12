package yich.download.local;

import java.util.List;

public interface FileAssembler {
    byte[] assemble(List<byte[]> list);
}
