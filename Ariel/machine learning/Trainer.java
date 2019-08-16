public class Trainer {
	BasicNetwork[] bn;
	int[] shape;
	
	public Trainer(int num,int[] net) {
		bn = new BasicNetwork[num];
		shape = net;
		for(int i=0 ; i<num ; i++)
			bn[i] = new BasicNetwork(net);
	}
	
	public void train (float[] scores) {
		int n = scores.length; 
		
		if(n != bn.length)
			return;
		
        for (int i = 1; i < n; ++i) { 
            float key = scores[i];
            BasicNetwork key2 = bn[i];
            int j = i - 1; 
            
            while (j >= 0 && scores[j] > key) { 
            	scores[j + 1] = scores[j]; 
                bn[j + 1] = bn[j];
                j = j - 1; 
            } 
            scores[j + 1] = key;
            bn[j + 1] = key2;
        } 
        
        for(int i=0 ; i< bn.length/2 ; i++)
        	if(Math.random() < 0.95)
        		bn[i] = new BasicNetwork(bn[bn.length-i-1]);
        	else
        		bn[i] = new BasicNetwork(shape);
       	}
}