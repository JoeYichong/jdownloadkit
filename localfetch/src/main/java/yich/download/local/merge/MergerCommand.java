package yich.download.local.merge;

import picocli.CommandLine;

import yich.base.logging.JUL;
import yich.download.local.picocli.MyCommand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MergerCommand extends MyCommand<String> {
    final public static Logger logger = JUL.getLogger(MergerCommand.class);

    @CommandLine.Option(names = {"-c", "--clean"}, description = "Clean input files")
    Boolean delSrc = false;

    @CommandLine.Option(names = {"-t", "--tag"}, description = "File name tag")
    String tag = null;

    @CommandLine.Option(names = {"-s", "--suffix"}, description = "File suffix")
    String suffix = null;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Directory")
    String output = null;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input Directory")
    String input = null;


    @Override
    public String call() {
        try {
            return FileMergers.newInstance(parameters())
                              .merge();
//            return FileMergers.newInstance()
//                              .setTag(tag)
//                              .setSuffix(suffix)
//                              .setSrc(input)
//                              .setDst(output)
//                              .merge(delSrc);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("** Error: " + e.getMessage());
            logger.log(Level.SEVERE, "** Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected Class getClassType() {
        return MergerCommand.class;
    }

//    public static void main(String[] args) throws IllegalAccessException {
//        new MergerCommand().parameters().forEach((k, v) -> System.out.println(k + ":" + v));
//    }

}
