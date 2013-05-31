package be.cegeka.memento.domain.utilities;

import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;

public class PropertyReader {

	public static String getProperty(Context context, String fileName, String key) {
		try {
			AssetManager assetManager = context.getAssets();
			Properties properties = new Properties();
			InputStream fileIn = assetManager.open(fileName);
			properties.load(fileIn);
			return properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
