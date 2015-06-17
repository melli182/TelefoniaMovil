package uy.edu.um.telmovil.commons.components;

import java.util.List;


/**
 * Esta clase represeta el Radio Network Controller 
 * 
 * Funciones:      
 * </p>- Establecer la conexion de radio
 * </p>- Seleccion de propiedades de dicha conexion (QoS, AB, tipo:voz,datos)
 * </p>- Control de handovers
 * </p>- Conecta el MSC con la radioBase (nodeB)
 */
public class RNC{

	private long id;
	List<NodeB> nodesB; // nuestras celdas
	private MSC msc;
	private List<QoSParameters> qosParams;

}
