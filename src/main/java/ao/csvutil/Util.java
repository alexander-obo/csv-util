package ao.csvutil;

import java.io.*;

public final class Util {

    static BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fileName));
    }

    static PrintWriter getPrintWriter(String fileName) throws IOException {
        return new PrintWriter(new FileWriter(fileName));
    }

    /**
     * Prevent initialization.
     */
    private Util() {
    }
}
