import javax.swing.*;

public class Main extends JFrame{

    JLabel label = new JLabel();

    public Main() {
        setDefaultCloseOperation(3);
        setBounds(0, 0, 500, 500);
        add(label);

        ArduinoSerial f = new ArduinoSerial("COM3");

        new Thread(){
            @Override
            public void run(){
                f.initialize();

                while(true){
                    try {
                        setLabel( f.getLastLine() );
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();

    }

    public void setLabel(String temp){
        label.setText("Temperatura: " + temp);
    }

    public static void main(String[] args) {
        new Main().setVisible(true);

//        SerialPort comPort = SerialPort.getCommPorts()[0];
//        System.out.println("Connecting to: " + comPort.getDescriptivePortName());
//        comPort.allowElevatedPermissionsRequest();
//        System.out.println("Opening with return: " + comPort.openPort());

    }
}