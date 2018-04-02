
/*******************************************/
/******** NODO MAESTRO DE HABITACIÓN *******/
/*******************************************/
/*** Autor: Diego de los Reyes Rodríguez. **/
/*******************************************/

#include <stdlib.h>

#include <SoftwareSerial.h>
#include <IRLib.h>
#include <IRLibMatch.h>
#include <IRLibTimer.h>

#include <ComunicacionBase.h>
#include <ComunicacionSerial.h>
#include <ComunicacionBluetooth.h>
#include <ComunicacionIR.h>

#include "Controlador.h"

/** VARIABLES **/

//Controlador inteligente.
Controlador _controlador;

//Tiempo de ejecución.
unsigned long _tiempo;

//Tiempo reposo.
unsigned long _reposo;

/** FUNCIONES ARDUINO **/

/**
 * Se ejecuta al arrancar el programa.
 */
void setup(){
  Serial.begin(9600);
  Serial.println("CONTROLADOR INTELIGENTE");
  _controlador.activar();
}

/**
 * Bucle infinito de ejecución.
 */
void loop(){
  unsigned long tiempoActual = millis();
  
  //Dejamos un tiempo entre cada actualización.
  if(tiempoActual - _tiempo > _reposo){
    _controlador.actualizar();
    _tiempo = millis();
  }
}
