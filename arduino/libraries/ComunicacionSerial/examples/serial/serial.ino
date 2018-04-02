/****************************************/
/**************** SERIAL ****************/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include <ComunicacionBase.h>
#include <ComunicacionSerial.h>

/** VARIABLES **/

//Emite y recibe por Serial.
ComunicacionIR _serial;

/** FUNCIONES ARDUINO **/

/**
 * Se ejecuta al arrancar el programa.
 */
void setup()
{
  Serial.begin(9600) ;
  Serial.println("INNOVAIA: Emisor y receptor Serial.") ;
}

/**
 * Bucle infinito de ejecución.
 */
void loop()
{
  String datos = _serial.recibirDatos();
  if(datos != ""){
	Serial.print("Recibido: ");
	Serial.println(datos);
	_serial.enviarDatos(datos);
  }
}