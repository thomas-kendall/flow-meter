package flowmeter;

import java.util.ArrayList;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;

public class FlowMeter {

	private List<IFlowMeterListener> listeners = new ArrayList<IFlowMeterListener>();

	public FlowMeter(GpioController gpio, Pin inputPin, String name, int inactivityMillisecondsForStop) {
		GpioPinDigitalInput flowMeterPin = gpio.provisionDigitalInputPin(inputPin, name, PinPullResistance.PULL_UP);
		FlowMeterPinListener pinListener = new FlowMeterPinListener(inactivityMillisecondsForStop);
		flowMeterPin.addListener(pinListener);
		Thread watcherThread = new Thread(new FlowMeterWatcherRunnable(this, pinListener));
		watcherThread.start();
	}

	public void addListener(IFlowMeterListener listener) {
		listeners.add(listener);
	}

	public void onFlowStarted() {
		for (IFlowMeterListener listener : listeners) {
			listener.onFlowStarted();
		}
	}

	public void onFlowStopped(long pulses) {
		double litersPoured = pulsesToLiters(pulses);
		for (IFlowMeterListener listener : listeners) {
			listener.onFlowStopped(litersPoured);
		}
	}

	private double pulsesToLiters(long pulses) {
		// 450 pulses per liter
		double liters = ((double) pulses) / 450;
		return liters;
	}
}
