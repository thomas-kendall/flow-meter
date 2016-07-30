package flowmeter;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class FlowMeterPinListener implements GpioPinListenerDigital {

	private volatile long lastPulseNano = -1;
	private volatile long numberOfPulses = 0;

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {

		if (e.getState().isHigh()) {
			numberOfPulses++;
			lastPulseNano = System.nanoTime();
			System.out.println("Pulse " + numberOfPulses + " at " + lastPulseNano);
		}

	}

	public long nanoSecondsSinceLastPulse() {
		// Return -1 if we have not received a pulse yet
		if (lastPulseNano < 0) {
			return -1;
		}
		return System.nanoTime() - lastPulseNano;
	}
}
