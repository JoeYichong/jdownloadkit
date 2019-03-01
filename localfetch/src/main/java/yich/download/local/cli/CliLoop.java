package yich.download.local.cli;

import picocli.CommandLine;
import yich.download.local.FileCollector;

import java.util.Arrays;
import java.util.Scanner;

public class CliLoop {
    private static String shellName = "V-Ex";

    private static String[] getOpt(String[] cline) {
        if (cline.length <= 1)
            return new String[0];
        return Arrays.copyOfRange(cline, 1, cline.length);
    }

    private static String shellName() {
        return shellName + "> ";
    }

    public static void main(String[] args) {
        FileCollector collector = null;
        // FileMerger fileMerger = null;

        Scanner scanner = new Scanner(System.in);
        String line;
        System.out.print(shellName());
        while(!"exit".equals(line = scanner.nextLine())) {
            if (line.length() == 0) {
                if (collector != null && collector.isRunning()) {
                    collector.close();
                    collector = null;
                }
                System.out.print(shellName());
                continue;
            }

            String[] strs = line.split(" ");

            if ("collect".equals(strs[0])) {
                collector = CommandLine.call(new CollectorCommand(), getOpt(strs));
                continue;
            }

            if ("merge".equals(strs[0])) {
                CommandLine.call(new MergerCommand(), getOpt(strs));
                continue;
            }

            System.out.println("** Error: Command '" + strs[0] + "' doesn't exist!");
        }
    }
}
