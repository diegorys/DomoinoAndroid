/*
  ActuadorCalefactor.h - Librería para el manejo de un calefactor.
  
  Created  2013 nov 7
  Version 1.0
*/

// Incluye el archivo de descripción.
#include "ActuadorCalefactor.h"

   
/** CONSTRUCTORES **/

/**
 * Constructor vacío. Usar sólo en la declaración.
 */
ActuadorCalefactor::ActuadorCalefactor()
{
}

/**
 * Constructor principal que inicializa los parámetros.
 * @param pinServo: pin donde está ubicado el led.
 */
ActuadorCalefactor::ActuadorCalefactor(int pinLed){
	pin		= pinLed;
	pinMode(pin, OUTPUT);
}

/** MÉTODOS PÚBLICOS **/

/**
 * Comienza a calentar.
 */
void ActuadorCalefactor::encender(){
	digitalWrite(pin, HIGH);
}

/**
 * Detiene la marcha.
 */
void ActuadorCalefactor::apagar(){
	digitalWrite(pin, LOW);
}