/**
 * 
 */
package presentacion.controlador.command;

import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author jiayu
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public interface Command {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param datos
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Context execute(Object datos);
	public Eventos getId();
}