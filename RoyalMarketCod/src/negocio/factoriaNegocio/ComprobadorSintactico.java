package negocio.factoriaNegocio;

public class ComprobadorSintactico {
	
	private static final int LENGTH = 9;

	public static boolean isNIF(String nif) {
		if (nif.length() != LENGTH || nif.isEmpty()) return false;
		boolean valid = true;
		int i = 0;
		while (i < LENGTH - 1 && valid) {
			valid = Character.isDigit(nif.charAt(i));
			i++;
		}
		return valid && Character.isAlphabetic(nif.charAt(LENGTH - 1));
	}

	public static boolean isNombre(String nombre) {
		if (nombre.isEmpty()) return false;
		boolean valid = true;
		int i = 0, spaces = 0;
		while (i < nombre.length() && valid) {
			char c = nombre.charAt(i);
			if (c != ' ') valid = !Character.isDigit(c);
			else spaces++;
			i++;
		}
		return spaces == nombre.length() ? false : valid;
	}

	public static boolean isDireccion(String direccion) {
		if (direccion.isEmpty()) return false;
		boolean valid = true;
		int i = 0, spaces = 0;
		while (i < direccion.length() && valid) {
			char c = direccion.charAt(i);
			if (c != ' ') valid = !Character.isDigit(c);
			else spaces++;
			i++;
		}
		return spaces == direccion.length() ? false : valid;
	}
	public static boolean isTlf(String tlf) {
		if (tlf.length() != LENGTH || tlf.isEmpty()) return false;
		boolean found = false;
		int i = 0;
		if (tlf.length() < 9)
			found = true;
		while (!found && i < tlf.length()) {
			if(!(tlf.charAt(i) < '0' || tlf.charAt(i) > '9' || tlf.charAt(i) != '\b'))
				found = true;
			i++;
		}
		return !found;
	}
	
}