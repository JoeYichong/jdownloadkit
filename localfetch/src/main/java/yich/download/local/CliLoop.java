package yich.download.local;

import picocli.CommandLine;
import yich.download.local.auto.AutoCommand;
import yich.download.local.clean.CleanerCommand;
import yich.download.local.collect.CollectorCommand;
import yich.download.local.merge.MergerCommand;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Future;

public class CliLoop {
    private static String shellName = ">>";

    private static String[] getOpt(String[] cline) {
        if (cline.length <= 1)
            return new String[0];
        return Arrays.copyOfRange(cline, 1, cline.length);
    }

    private static String shellName() {
        return shellName + "> ";
    }

    public static void main(String[] args) {
        Future colFuture = null;

        Scanner scanner = new Scanner(System.in);
        String line;
        System.out.print(shellName());
        while(!"exit".equals(line = scanner.nextLine())) {
            if (line.length() == 0) {
                if (colFuture != null && !colFuture.isCancelled() && !colFuture.isDone()) {
                    colFuture.cancel(true);
                }
                System.out.print(shellName());
                continue;
            }

            String[] strs = line.split(" ");

            if ("collect".equals(strs[0])) {
                colFuture = CommandLine.call(new CollectorCommand(), getOpt(strs));
                continue;
            }

            if ("merge".equals(strs[0])) {
                CommandLine.call(new MergerCommand(), getOpt(strs));
                continue;
            }

            if ("clean".equals(strs[0])) {
                CommandLine.call(new CleanerCommand(), getOpt(strs));
                continue;
            }

            if ("auto".equals(strs[0])) {
                CommandLine.call(new AutoCommand(), getOpt(strs));
                continue;
            }

            System.out.println("** Error: Command '" + strs[0] + "' doesn't exist!");
        }
    }
}
