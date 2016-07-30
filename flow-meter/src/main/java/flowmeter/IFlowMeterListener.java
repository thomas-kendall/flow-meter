package flowmeter;

public interface IFlowMeterListener {
	void onFlowStarted();

	void onFlowStopped(double litersPoured);
}
