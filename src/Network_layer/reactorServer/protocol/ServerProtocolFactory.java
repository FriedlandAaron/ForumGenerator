package Network_layer.reactorServer.protocol;

public interface ServerProtocolFactory<T> {
	/**
	 * @param the ServerMember that the function crate to him  AsyncProtocol.
	 * return Protocol to member.
	 */
   AsyncServerProtocol<T> create();
}
