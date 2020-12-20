package Server;

public class StartServer {
    public static final int PORT_WORK = 1234;

    public static void main(String[] args) {
        MultyThreadedServer server = new MultyThreadedServer(PORT_WORK);
        new Thread(server).start();
    }
}
