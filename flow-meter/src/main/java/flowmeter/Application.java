package flowmeter;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

public class Application {

	public static void main(String[] args) {
		try {
			System.out.println("Listening on GPIO pin 2...");
			final GpioController gpio = GpioFactory.getInstance();

			FlowMeter flowMeter = new FlowMeter(gpio, RaspiPin.GPIO_02, "Flow Meter", 300);
			flowMeter.addListener(new IFlowMeterListener() {

				public void onFlowStarted() {
					System.out.println("Flow started");
				}

				public void onFlowStopped(double litersPoured) {
					System.out.println("Flow stopped, " + litersPoured + " liters poured.");
				}
			});

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
