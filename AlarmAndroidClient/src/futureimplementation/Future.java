package futureimplementation;

import java.util.ArrayList;


public class Future<T>{
	
	private T value;
	private ArrayList<FutureCallable> futureCallables = new ArrayList<FutureCallable>();
	
	public void setValue(T value){
		this.value = value;
		notifyFutures();
	}
	
	public T getValue(){
		return value;
	}
	
	private void notifyFutures(){
		for(FutureCallable f : futureCallables){
			f.apply(getValue());
		}
	}
	
	public void registerFutureCallable(FutureCallable callable){
		futureCallables.add(callable);
	}
}
