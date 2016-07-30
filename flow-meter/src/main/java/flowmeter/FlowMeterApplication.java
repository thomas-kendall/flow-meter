package flowmeter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@SpringBootApplication
public class FlowMeterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FlowMeterApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Initializing...");
		final GpioController gpio = GpioFactory.getInstance();

		// provision gpio pin #02 as an input pin with its internal pull up
		// resistor enabled
		// (configure pin edge to both rising and falling to get notified for
		// HIGH and LOW state
		// changes)
		GpioPinDigitalInput flowMeter = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "Flow Meter",
				PinPullResistance.PULL_UP);

		System.out.println("Listening for state changes on pin GPIO_02...");
		PinState previousState = PinState.HIGH;
		while (true) {
			PinState state = flowMeter.getState();
			if (state.compareTo(previousState) != 0) {
				System.out.println(state.isHigh() ? "High" : state.isLow() ? "Low" : "Unknown");
				previousState = state;
			}
		}
	}
}
