package org.daniel;

import org.daniel.chapter1.Echo;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Takes chapter number as only parameter");
            System.exit(1);
        }

        int chapter = Integer.parseInt(args[0]);

        switch (chapter) {
            case 1:
                Echo echo = new Echo();
                echo.run();
                break;
        }
    }
}
