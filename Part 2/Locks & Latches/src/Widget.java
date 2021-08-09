public class Widget {
    public synchronized void execute() {

    }
}

class Button extends Widget {
    @Override
    public synchronized void execute() {
        super.execute();
    }
}