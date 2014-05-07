package Network_layer.reactorServer.tokenizer;

public interface TokenizerFactory<T> {
   MessageTokenizer<T> create();
}
