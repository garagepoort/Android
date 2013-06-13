package be.cegeka.android.ShouldrTap;

import java.util.ArrayList;
import java.util.List;

public class MainClass {

	public static void main(String[] args){
		new MainClass().doAlles();
	}
	
	public void doAlles(){
		MyEvent myEvent = new MyEvent();
		MyEvent2 myEvent2 = new MyEvent2();
		
		Shoulder<MyEvent> shoulder = new MyShoulder();
		Tapper tapper = new Tapper();
		
		tapper.addShoulder(shoulder);
		List<String> list = new ArrayList<String>();
		list.add("test tap");
		list.add("test tap2");
		
		myEvent.setData(list);
		myEvent2.setData(list);
		tapper.tapShoulders(myEvent);
		tapper.tapShoulders(myEvent);
	}
	
	private class MyShoulder extends Shoulder<MyEvent>{

		public MyShoulder() {
			super(MyEvent.class);
		}

		@Override
		public void update(MyEvent a) {
			for(String s : a.getData()){
				System.out.println(s);
			}
		}
		
	}
	
	private class MyEvent extends AbstractEvent<List<String>>{

		private List<String> strings = new ArrayList<String>();
		
		@Override
		public List<String> getData() {
			return strings;
		}

		@Override
		public void setData(List<String> data) {
			this.strings=data;
		}
		
	}
	
	private class MyEvent2 extends AbstractEvent<List<String>>{

		private List<String> strings = new ArrayList<String>();
		
		@Override
		public List<String> getData() {
			return strings;
		}

		@Override
		public void setData(List<String> data) {
			this.strings=data;
		}
		
	}
}
