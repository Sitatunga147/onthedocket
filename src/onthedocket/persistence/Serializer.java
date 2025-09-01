package onthedocket.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import onthedocket.models.Event;
import onthedocket.models.EventCategory;
import onthedocket.utils.Theme;

/**
 * Handles persisting and restoring application data by writing and reading
 * the in-memory model (events, categories, and theme) to a backing file.
 * <p>
 * A Serializer is bound to a specific {@code File} and offers static methods
 * to perform serialization (writing) and deserialization (reading). If the
 * file is not set or accessible, operations will return {@code false}.
 * </p>
 * 
 * @author Sitatunga147 (with moderate AI assistance)
 */
public class Serializer {
	private static File file;

	/**
	 * Constructs a Serializer that will use the provided file for all
	 * subsequent serialization and deserialization operations.
	 *
	 * @param file the file to which serialized data will be written and from
	 *             which data will be read
	 */
	public Serializer(File file) {
		Serializer.file = file;
	}
	
	/**
	 * Serializes the current state of {@link DataManager}—including the list
	 * of events, the list of categories, and the selected theme—to the
	 * configured file.
	 *
	 * @return {@code true} if the serialization succeeded; {@code false}
	 *         if the file was not set or an I/O error occurred
	 */
	public static boolean serialize() {
		if(file == null) return false;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(DataManager.getEvents());
			oos.writeObject(DataManager.getCategories());
			oos.writeObject(DataManager.getTheme());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Deserializes data from the configured file and replaces the in-memory
	 * contents of {@link DataManager} with the persisted events, categories,
	 * and theme.
	 *
	 * @return {@code true} if the deserialization succeeded; {@code false}
	 *         if the file was not set, did not exist, or an I/O or
	 *         class-not-found error occurred
	 */
	@SuppressWarnings("unchecked")
	public static boolean deserialize() {
		if(file == null || !file.exists()) return false;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			ArrayList<Event> savedEvents = (ArrayList<Event>) ois.readObject();
			ArrayList<EventCategory> savedCategories = (ArrayList<EventCategory>) ois.readObject();
			Theme savedTheme = (Theme) ois.readObject();
			
			DataManager.getEvents().clear();
			DataManager.getEvents().addAll(savedEvents);
			
			DataManager.getCategories().clear();
			DataManager.getCategories().addAll(savedCategories);
			
			DataManager.setTheme(savedTheme);
			return true;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
