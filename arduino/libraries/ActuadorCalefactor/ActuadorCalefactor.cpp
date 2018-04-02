/*
  ActuadorCalefactor.h - Librer�a para el manejo de un calefactor.
  
  Created  2013 nov 7
  Version 1.0
*/

// Incluye el archivo de descripci�n.
#include "ActuadorCalefactor.h"

   
/** CONSTRUCTORES **/

/**
 * Constructor vac�o. Usar s�lo en la declaraci�n.
 */
ActuadorCalefactor::ActuadorCalefactor()
{
}

/**
 * Constructor principal que inicializa los par�metros.
 * @param pinServo: pin donde est� ubicado el led.
 */
ActuadorCalefactor::ActuadorCalefactor(int pinLed){
	pin		= pinLed;
	pinMode(pin, OUTPUT);
}

/** M�TODOS P�BLICOS **/

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