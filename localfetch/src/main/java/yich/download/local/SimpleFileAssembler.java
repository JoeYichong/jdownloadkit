package yich.download.local;


import yich.base.util.ByteUtil;

import java.util.List;

public class SimpleFileAssembler implements FileAssembler {

    @Override
    public byte[] assemble(List<byte[]> list) {

        return ByteUtil.merge(list);

    }
}
