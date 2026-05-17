package guiFolder.controllerGUI;

import Pojos.User;

public class Sesion {
	
	private static User currentUser;

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User user) {
		currentUser = user;
	}
	
	public static void clearSession() {
		currentUser = null;
	}

}
