package be.cegeka.alarms.android.client.futureimplementation;

import java.util.ArrayList;


public class Future<T>{
	
	private T value;
	private ArrayList<FutureCallable> futureCallables = new ArrayList<FutureCallable>();
	
	public void setValue(T value, ResultCode code){
		this.value = value;
		notifyFutures(code);
	}
	
	public T getValue(){
		return value;
	}
	
	private void notifyFutures(ResultCode code){
		for(FutureCallable f : futureCallables){
			f.onSucces(getValue(), code);
		}
	}
	
	public void registerFutureCallable(FutureCallable callable){
		futureCallables.add(callable);
	}
}
