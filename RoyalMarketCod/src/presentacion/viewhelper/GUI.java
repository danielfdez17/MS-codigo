package presentacion.viewhelper;

import presentacion.factoriaVistas.Context;

public interface GUI {
	public void update(Context context);
	public void clear();
	public String getErrorMsg(int error);
	public boolean areTextFieldsEmpty();
}
