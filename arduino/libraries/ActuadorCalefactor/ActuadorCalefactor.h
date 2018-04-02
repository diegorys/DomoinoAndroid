/*
  ActuadorCalefactor.h - Librer�a para el manejo de un calefactor.
  
  Created  2013 nov 7
  Version 1.0
*/

// ensure this library description is only included once
#ifndef ACTUADOR_CALEFACTOR_H
#define ACTUADOR_CALEFACTOR_H
#define LIBRARY_VERSION	 1.0

// include core Wiring API and now Arduino
#if ARDUINO >= 100
  #include "Arduino.h"
#else
  #include "WProgram.h"
#endif

// Clase ActuadorCalefactor
class ActuadorCalefactor {
 
	/** Elementos p�blicos de la interfaz. **/
	public:
	
		/** CONSTRUCTORES **/

		/**
		 * Constructor vac�o. Usar s�lo en la declaraci�n.
		 */
		ActuadorCalefactor();

		/**
		 * Constructor principal que inicializa los par�metros.
		 * @param pinLed: pin donde est� ubicado el led.
		 */
		ActuadorCalefactor(int pinLed);
	
		/** M�TODOS **/

		/**
		 * Comienza a calentar.
		 */
		void encender();

		/**
		 * Detiene la marcha.
		 */
		void apagar();

	private:

		/** ATRIBUTOS **/

		//Pin al que est� conectado el led.
		int pin;

};

#endif
