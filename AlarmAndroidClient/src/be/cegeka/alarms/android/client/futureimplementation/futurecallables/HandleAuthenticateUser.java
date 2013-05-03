package be.cegeka.alarms.android.client.futureimplementation.futurecallables;

import be.cegeka.alarms.android.client.futureimplementation.Future;
import be.cegeka.alarms.android.client.futureimplementation.FutureCallable;
import be.cegeka.alarms.android.client.futureimplementation.ResultCode;
import be.cegeka.alarms.android.client.serverconnection.login.LoginUserTask;
import be.cegeka.android.alarms.transferobjects.UserTO;

public class HandleAuthenticateUser implements FutureCallable<Boolean>{

		private String email;
		private String password;
		private Future<UserTO> future;
		
		public HandleAuthenticateUser(String email, String password, Future<UserTO> future){
			this.email=email;
			this.password=password;
			this.future=future;
		}
		
		@Override
		public void onSucces(Boolean result, ResultCode resultCode)
		{
			if(resultCode == ResultCode.SUCCESS){
				future = new LoginUserTask().executeFuture(email, password);
			}else{
				future.setValue(null, resultCode);
			}
		}

		@Override
		public void onError(Exception e)
		{
			// TODO Auto-generated method stub
			
		}
		
	}