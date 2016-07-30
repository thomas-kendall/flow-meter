package flowmeter;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class FlowMeterPinListener implements GpioPinListenerDigital {

	private long inactivityNanosecondsForStop;
	private volatile long lastPulseNano = 0;
	private volatile long numberOfPulses = 0;

	public FlowMeterPinListener(int inactivityMillisecondsForStop) {
		this.inactivityNanosecondsForStop = inactivityMillisecondsForStop * 1000000;
	}

	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent e) {

		if (e.getState().isHigh()) {
			numberOfPulses++;
			lastPulseNano = System.nanoTime();
			// System.out.println("Pulse " + numberOfPulses + " at " +
			// lastPulseNano);
		}

	}

	public boolean isFlowing() {
		boolean flowing = false;

		if (lastPulseNano > 0 && nanoSecondsSinceLastPulse() < inactivityNanosecondsForStop) {
			flowing = true;
		}

		return flowing;
	}

	private long nanoSecondsSinceLastPulse() {
		// Return 0 if we have not received a pulse yet
		if (lastPulseNano == 0) {
			return 0;
		}
		return System.nanoTime() - lastPulseNano;
	}

	public long takePulses() {
		long pulses = numberOfPulses;
		numberOfPulses = 0;
		return pulses;
	}
}
