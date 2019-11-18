package yich.download.local;

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

        while_loop:
        while(!"exit".equals(line = scanner.nextLine())) {
            if (line.length() == 0) { // press "Enter" to exit "collector" thread
                if (colFuture != null && !colFuture.isCancelled() && !colFuture.isDone()) {
                    colFuture.cancel(true);
                }
                System.out.print(shellName());
                continue;
            }

            String[] strs = line.split(" ");

            switch (strs[0]) {
                case "collect": {
                    colFuture = new CollectorCommand().call(getOpt(strs));
                    continue while_loop;
                }

                case "merge": {
                    new MergerCommand().call(getOpt(strs));
                    continue while_loop;
                }

                case "clean": {
                    new CleanerCommand().call(getOpt(strs));
                    continue while_loop;
                }

                case "auto": {
                    new AutoCommand().call(getOpt(strs));
                    continue while_loop;
                }

                default: {
                    System.out.println("** Error: Command '" + strs[0] + "' doesn't exist!");
                }

            }

        }
    }
}
