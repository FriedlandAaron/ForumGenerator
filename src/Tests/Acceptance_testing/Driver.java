package Tests.Acceptance_testing;



public abstract class Driver {

	public static BridgeForum getBridge() {
		BridgeProxy bridge = new BridgeProxy();
		bridge.setRealBridge(); // add real bridge here
		return bridge;
	}	
}