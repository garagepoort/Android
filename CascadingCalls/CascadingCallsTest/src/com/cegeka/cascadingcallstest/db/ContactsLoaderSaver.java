package com.cegeka.cascadingcallstest.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.os.Environment;
import android.util.Log;

import com.cegeka.cascadingcallstest.model.Contact;


public class ContactsLoaderSaver {
	
	public final static String SAVE_FOLDER_NAME = "/CascadingEmergencyCalls";
	public final static String SAVE_FILE_NAME = "/CONTACTS.TXT";
	
	public static void saveContacts(List<Contact> contacts){
		
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mExternalStorageAvailable = mExternalStorageWriteable = true;
			
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
			
		} else {
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		if(mExternalStorageAvailable && mExternalStorageWriteable){
			File root = Environment.getExternalStorageDirectory(); 
			File dir = new File (root.getAbsolutePath() + SAVE_FOLDER_NAME);
			dir.mkdirs();
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), SAVE_FOLDER_NAME + SAVE_FILE_NAME);
			try {
				PrintWriter writer = new PrintWriter(file);
				for(Contact c : contacts){
					writer.append(c.toString() + "\n");
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				Log.w("ExternalStorage", "Error writing " + file, e);
			}

		}
	}
	
	public static List<Contact> loadContacts() {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		Scanner scanner = null;
		try {
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), SAVE_FOLDER_NAME + SAVE_FILE_NAME);
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			Log.i("ExternalStorage", "File didn't exist yet.");
			return contacts;
		}
		
		while(scanner.hasNextLine()){
			String[] components = scanner.nextLine().split(";");
			contacts.add(new Contact(components[1], components[0]));
		}
		return contacts;
	}

}
