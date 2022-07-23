import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Ramon
 */
public final class ArduinoSerial implements SerialPortPacketListener {
	public SerialPort serialPort = null;
    private BufferedReader arduinoOutput = null;
    private String portName = null;
    private ArrayList<String> lines;

    public ArduinoSerial(String portName){
        this.portName = portName;
        this.lines = new ArrayList<>();
    }

    public BufferedReader getArduinoOutput(){
        return this.arduinoOutput;
    }

    public ArrayList<String> getLines(){
        return this.lines;
    }

    private static SerialPort selectPort(String name, boolean ignoreCase, boolean allowedSubstring){
        SerialPort allPorts[] = SerialPort.getCommPorts();
        for(SerialPort port: allPorts){
            String portDescription = ignoreCase? port.getPortDescription() : port.getPortDescription().toLowerCase();
            String portSystemName = ignoreCase? port.getSystemPortName() : port.getSystemPortName().toLowerCase();

            System.out.println(portDescription + ", " + portSystemName);

            boolean isPort = allowedSubstring?
                    portDescription.contains(name.toLowerCase()) ||
                    portSystemName.contains(name.toLowerCase())
                    :
                    portDescription.equals(name.toLowerCase()) ||
                    portSystemName.equals(name.toLowerCase());

            if(isPort) return port;
        }

        return null;
    }

    private SerialPort selectPort(){
        SerialPort port = selectPort(this.portName, false, false);
        if(port != null) return port;

        port = selectPort(this.portName, false, true);
        if(port != null) return port;

        port = selectPort(this.portName, true, false);
        if(port != null) return port;

        port = selectPort(this.portName, false, false);
        if(port != null) return port;

        throw new RuntimeException("A porta \"" + this.portName + "\" não foi encontrada. As portas disponíveis são " + Arrays.asList(SerialPort.getCommPorts()));
    }

    public void initialize() {
        this.serialPort = selectPort();
        serialPort.openPort();
        serialPort.setBaudRate(9600);
        serialPort.addDataListener(this);   
        serialPort.setComPortParameters(9600, 8, 1, 0);
        arduinoOutput = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        try {
            final String inputLine = arduinoOutput.readLine();
            lines.add(inputLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public int getPacketSize() {
		return 8;
	}
	
	public String getLastLine() {
        if(lines.size() == 0) return "";
		return lines.get(lines.size() - 1);
	}
}