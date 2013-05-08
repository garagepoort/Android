package be.cegeka.alarms.android.client.futureimplementation;


public class FutureService {
	
	
	public static void whenResolved(Future future, FutureCallable futureCallable){
		future.registerFutureCallable(futureCallable);
	}

}
