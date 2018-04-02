/****************************************/
/*************** BLUETOOTH **************/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include <ComunicacionBase.h>
#include <ComunicacionBluetooth.h>
#include <SoftwareSerial.h>

/** VARIABLES **/

//Emite y recibe por Bluetooth.
ComunicacionBluetooth bt(2, 5);

/** FUNCIONES ARDUINO **/

/**
 * Se ejecuta al arrancar el programa.
 */
void setup(){
  Serial.begin(9600);
  Serial.println("ESPERANDO");
}

/**
 * Bucle infinito de ejecución.
 */
void loop(){
  String data = bt.recibirDatos();
  
  if(data != ""){
    Serial.print("Recibido: ");
    Serial.println(data);
  }
}