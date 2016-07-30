package flowmeter;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class Application {

	public static void main(String[] args) {
		try {
			System.out.println("Listening on GPIO pin 2...");
			final GpioController gpio = GpioFactory.getInstance();

			// provision gpio pin #02 as an input pin with its internal pull up
			// resistor enabled
			// (configure pin edge to both rising and falling to get notified
			// for
			// HIGH and LOW state
			// changes)
			GpioPinDigitalInput flowMeter = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "Flow Meter",
					PinPullResistance.PULL_UP);

			flowMeter.addListener(new FlowMeterPinListener());

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
