package onthedocket.utils;

import java.io.File;

import onthedocket.persistence.DataManager;
import onthedocket.persistence.Serializer;
import onthedocket.views.MainView;

public class Launcher {
	
	public static void main(String[] args) {
		File serFile = new File("onthedocket.ser");
		new Serializer(serFile);
		Serializer.deserialize();
		new MainView(DataManager.getTheme());
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Serializer.serialize();
		}));
	}
}
