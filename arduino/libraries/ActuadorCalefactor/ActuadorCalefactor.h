/*
  ActuadorCalefactor.h - Librería para el manejo de un calefactor.
  
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
 
	/** Elementos públicos de la interfaz. **/
	public:
	
		/** CONSTRUCTORES **/

		/**
		 * Constructor vacío. Usar sólo en la declaración.
		 */
		ActuadorCalefactor();

		/**
		 * Constructor principal que inicializa los parámetros.
		 * @param pinLed: pin donde está ubicado el led.
		 */
		ActuadorCalefactor(int pinLed);
	
		/** MÉTODOS **/

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

		//Pin al que está conectado el led.
		int pin;

};

#endif
