package flowmeter;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class FlowMeterPinListener implements GpioPinListenerDigital {

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {

		if (e.getState().isHigh()) {
			long nanoTime = System.nanoTime();
			System.out.println("Pulse " + nanoTime);
		}

	}
}
