package flowmeter;

public class FlowMeterWatcherRunnable implements Runnable {

	private FlowMeter flowMeter;
	private FlowMeterPinListener pinListener;

	public FlowMeterWatcherRunnable(FlowMeter flowMeter, FlowMeterPinListener pinListener) {
		this.flowMeter = flowMeter;
		this.pinListener = pinListener;
	}

	public void run() {
		boolean looping = true;
		boolean previouslyFlowing = false;

		while (looping) {
			boolean flowing = pinListener.isFlowing();
			if (previouslyFlowing != flowing) {
				if (flowing) {
					flowMeter.onFlowStarted();
				} else {
					flowMeter.onFlowStopped(pinListener.takePulses());
				}
				previouslyFlowing = flowing;
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				looping = false;
			}
		}
	}

}
