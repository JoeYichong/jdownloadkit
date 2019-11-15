package yich.download.local.merge;


import yich.base.util.ByteUtil;
import yich.download.local.merge.FileAssembler;

import java.util.List;

public class SimpleFileAssembler implements FileAssembler {

    @Override
    public byte[] assemble(List<byte[]> list) {

        return ByteUtil.merge(list);

    }
}
