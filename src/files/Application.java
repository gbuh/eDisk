package files;

public class Application {
    private String nameApp;

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.setNameApp("Hi! I'm good app)");
        System.out.println(app.getNameApp());
    }
}
