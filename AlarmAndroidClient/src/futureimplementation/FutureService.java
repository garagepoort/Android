package futureimplementation;

import java.util.HashMap;

public class FutureService {
	
	private static HashMap<Future<Object>, FutureCallable> futuresHashMap = new HashMap<Future<Object>, FutureCallable>(); 
	
	public static void whenResolved(Future future, FutureCallable futureCallable){
		future.registerFutureCallable(futureCallable);
		futuresHashMap.put(future, futureCallable);
	}

}
