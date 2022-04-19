/**
 * Mateo Estrada Ramírez
 * 16/09/2020
 * Interfaz utilizada para el paciente
 */

package co.edu.uniquindio.farmacia.model;

public interface IPaciente extends java.io.Serializable
{

	public final int MAX_DISPENSACIONES=1000;

	public int getMaxDispensaciones();
	
}
