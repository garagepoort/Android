package be.cegeka.android.ShouldrTap;

import java.util.HashSet;
import java.util.Set;

// AbstractTapper<H<T>,T>
public class Tapper  {
	
	private Set<Shoulder<?>> shoulders = new HashSet<Shoulder<?>>();
	
	public void addShoulder(Shoulder<?> shoulder){
		shoulders.add(shoulder);
	}

	public void removeShoulder(Shoulder<?> shoulder){
		shoulders.remove(shoulder);
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void tapShoulders(AbstractEvent data){
		for(Shoulder shoulder : shoulders){
			if(shoulder.getDesiredClass() == data.getClass()){
				shoulder.notifyShoulder(data);
			}
		}
	}
	
}

