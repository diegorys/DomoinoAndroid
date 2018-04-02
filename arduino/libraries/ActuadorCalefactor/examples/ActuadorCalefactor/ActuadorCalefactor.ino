/****************************************/
/********** ACTUADOR CALEFACTOR *********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include "ActuadorCalefactor.h"

/** VARIABLES **/

//Pin al que está conectado el servo.
int pin;

//Actuador.
ActuadorCalefactor actuador;

/** FUNCIONES ARDUINO **/

/**
 * Se ejecuta al arrancar el programa.
 */
void setup() {
  Serial.begin(9600);
  pin    	= 11;
  actuador	= ActuadorCalefactor(pin);
  Serial.println("Actuador calefactor");
}

/**
 * Bucle infinito de ejecución.
 */
void loop() {
	actuador.encender();
	delay(500);
	actuador.apagar();
	delay(500);
}