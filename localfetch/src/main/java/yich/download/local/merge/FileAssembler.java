package yich.download.local.merge;

import java.util.List;

public interface FileAssembler {
    byte[] assemble(List<byte[]> list);
}
