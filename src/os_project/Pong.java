package os_project;

public class Pong extends UserlandProcess {

    @Override
    public void run() {
        String className = getClass().getSimpleName();
        byte[] data = "pong".getBytes();
        int targetProcessPid = OS.getPidByName("Ping");

        System.out.println("I am" + className + ", Pong pid: " + targetProcessPid);
        for (int i = 0; i < 3; i++) {

            KernelMessage receivedMessage = OS.waitForMessage();
            System.out.printf("\n%s: from %d to %d: what: $d\n",
            className, OS.getPidByName(className), targetProcessPid, receivedMessage.getWhat());
            receivedMessage.setTargetPid(targetProcessPid);
            receivedMessage.setData(data);
            OS.sendMessage(receivedMessage);
        }
    }
}
