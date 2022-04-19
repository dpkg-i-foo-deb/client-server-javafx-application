/**
 * mateo
 * Nov 23, 2020 7:16:53 PM
 */
package co.edu.uniquindio.servidor.application;

import java.util.Scanner;

import co.edu.uniquindio.farmacia.model.Farmacia;
import co.edu.uniquindio.farmacia.model.Usuario;
import co.edu.uniquindio.farmacia.persistencia.Persistencia;

public class HiloMenu extends Thread
{
	private boolean ejecutando=true;
	private static Farmacia farmacia;
	private int seleccion;
	private Scanner scanner;
	
	public HiloMenu()
	{
		farmacia=App.getFarmacia();
		scanner=new Scanner(System.in);
	}
	
	@Override
	public void run()
	{
		while(ejecutando)
		{
			System.out.print("Menú del servidor\n");
			System.out.print("[1] Agregar usuario\n");
			System.out.print("[2] Restablecer modelo sin eliminar usuarios\n");
			System.out.print("[3] Restablecer todo el modelo");
			
			System.out.print("\n\nElija una opción\n");
			seleccion=scanner.nextInt();
			switch(seleccion)
			{
				case 1: agregarUsuario();
						break;
				case 2: restablecerModelo();
						break;
				case 3: reiniciarModelo();
			}
		}
	}
	
	private void agregarUsuario()
	{
		String nombre="", pass="";
		boolean esAdmin=false;
		System.out.print("\nUsuarios actuales:\n");
		for(int contador=0; contador<farmacia.getUsuarios().size();contador++)
		{
			System.out.print(farmacia.getUsuarios().get(contador).getUid()+" "+farmacia.getUsuarios().get(contador).getPass());
			System.out.print("\n");
		}
		
		System.out.print("Nombre del nuevo usuario:\n");
		scanner.nextLine();
		nombre=scanner.nextLine();
		System.out.print("Contraseña:\n");
		pass=scanner.nextLine();
		System.out.print("¿Es administrador? true=sí, false=no\n");
		esAdmin= scanner.nextBoolean();
		
		farmacia.getUsuarios().add(new Usuario(nombre, pass,esAdmin));
		System.out.print("Se añadió el usuario\n\n");
		Persistencia.guardarModeloXML(farmacia);
	}
	
	private void restablecerModelo()
	{
		farmacia.getMedicamentos().clear();
		farmacia.getPacientes().clear();
		farmacia.getDispensaciones().clear();
		Persistencia.guardarModeloXML(farmacia);
	}
	
	private void reiniciarModelo()
	{
		farmacia= new Farmacia();
		Persistencia.guardarModeloXML(farmacia);
	}
}
