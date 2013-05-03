package futureimplementation.futurecallables;

import synchronisation.remote.LoginUserTask;
import be.cegeka.android.alarms.transferobjects.UserTO;
import futureimplementation.Future;
import futureimplementation.FutureCallable;
import futureimplementation.ResultCode;

public class AuthenticateUserFutureCallable implements FutureCallable<Boolean>{

		private String email;
		private String password;
		private Future<UserTO> future;
		
		public AuthenticateUserFutureCallable(String email, String password, Future<UserTO> future){
			this.email=email;
			this.password=password;
			this.future=future;
		}
		
		@Override
		public void apply(Boolean result, ResultCode resultCode)
		{
			if(resultCode == ResultCode.SUCCESS){
				LoginUserTask loginUserTask = new LoginUserTask(future);
				loginUserTask.execute(email, password);
			}else{
				future.setValue(null, resultCode);
			}
		}
		
	}