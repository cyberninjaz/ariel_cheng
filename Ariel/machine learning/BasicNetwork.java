public class BasicNetwork {
	Layer[] layer;
	
	public BasicNetwork(int[] entire) {
		layer = new Layer[entire.length - 1];
		for(int i=1; i< entire.length ; i++)
			layer[i - 1] = new Layer(entire[i], entire[i - 1]);
	}
	
	public BasicNetwork(BasicNetwork bn) {
		layer = new Layer[bn.layer.length];
		
		for(int j=0 ; j< layer.length ; j++)
			layer[j] = new Layer(bn.layer[j]);
	}
	public BasicNetwork(String str) {
		String[] s = str.split("\\|");
		layer = new Layer[s.length];
		
		for(int r=0 ; r<s.length ; r++)
			layer[r] = new Layer(s[r]);
	}
	
	public float[] output(float[] input) {
		for(int i=0 ; i< layer.length ; i++) {
			input = layer[i].allActivation(input);
		}
		
		return input;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(int p=0 ; p<layer.length ; p++)
			str.append( layer[p].toString() ).append("|");
		str.deleteCharAt(str.length() - 1);
		
		return str.toString();
	}
}

class Neuron {
	final double MUTATION_RATE = 0.15;
	float[] weight;
	float bias;
	float activation;
	
	public Neuron(int num) {
		weight = new float[num];
		
		for(int i=0;i< weight.length;i++)
			weight[i] = (float) ( 4 * Math.random() - 2 );
		bias = (float) (4 * Math.random() -2 );
	}
	
	public Neuron(Neuron n) {
		weight = new float[n.weight.length];
		
		for(int i=0;i< weight.length;i++) {
			double r = Math.random();
			if( r < MUTATION_RATE / 10 )								//mutation
				weight[i] = (float) ( 4 * Math.random() - 2 );
			else if( r < MUTATION_RATE )
				weight[i] = n.weight[i] + (float) ( 4 * Math.random() - 2 );
			else
				weight[i] = n.weight[i];
		}
		double r = Math.random();
		if( r < MUTATION_RATE / 10 )
			bias = (float) ( 4 * Math.random() - 2 );
		else if( r < MUTATION_RATE )
			bias = n.bias + (float) ( 4 * Math.random() - 2 );
		else
			bias = n.bias;
	}
	
	public Neuron(String str) {					//turn string to neuron
		String[] s = str.split(",");
		weight = new float[s.length-1];
		
		for(int w=0; w<weight.length ; w++)
			weight[w] = Float.parseFloat(s[w]);
		bias = Float.parseFloat(s[s.length-1]);
	}
	
	public float activation(float[] act) {
		activation = 0;
		
		for(int i=0 ; i< weight.length ; i++)
			activation += weight[i] * act[i];
		
		activation += bias;
		activation = sigmoid(activation);
		
		return activation;
	}
	
	private float sigmoid(float x) {
		return x / (1 + Math.abs(x));
	}
	
	@Override
	public String toString() {			//turn neuron to string
		StringBuilder str = new StringBuilder();
		
		for(int p=0 ; p<weight.length ; p++)
			str.append(weight[p]).append(",");
		str.append(bias);
		
		return str.toString();
	}
}

class Layer {
	Neuron[] neuron;
	
	public Layer(int much,int prevneu) {
		neuron = new Neuron[much];
		for(int j=0 ; j< much ; j++)
			neuron[j] = new Neuron(prevneu);
	}
	public Layer(Layer l) {
		neuron = new Neuron[l.neuron.length];

		for(int i=0;i< neuron.length;i++)
			neuron[i] = new Neuron(l.neuron[i]);
	}
	public Layer(String str) {
		String[] s = str.split("/");
		neuron = new Neuron[s.length];
		
		for(int u=0 ; u<s.length ; u++) {
			neuron[u] = new Neuron(s[u]);
		}
	}
	
	public float[] allActivation(float[] actprev) {
		float[] store = new float[neuron.length];
		
		for(int k=0 ; k<neuron.length ; k++)
			store[k]= neuron[k].activation(actprev);
		
		return store;
	}
	
	@Override
	public String toString() {			//turn layer to string
		StringBuilder str = new StringBuilder();
		
		for(int p=0 ; p<neuron.length ; p++)
			str.append( neuron[p].toString() ).append("/");
		str.deleteCharAt(str.length() - 1);
		
		return str.toString();
	}
}