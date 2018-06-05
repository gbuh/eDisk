package files.service;

public class HelloServiceImpl implements HelloService {
    private Printer printer;

    public HelloServiceImpl() {
        super();
    }

    public HelloServiceImpl(Printer printer) {
        super();
        this.printer = printer;
    }

    @Override
    public void print() {
        printer.print("Hello!!!");
    }

}
