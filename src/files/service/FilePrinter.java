package files.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FilePrinter implements Printer {
    private Writer writer;

    public FilePrinter(String name) {
        super();
        try {
            this.writer = new FileWriter(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void print(String msg) {
        try {
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
