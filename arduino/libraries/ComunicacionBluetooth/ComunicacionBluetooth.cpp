/****************************************/
/********* COMUNICACIÓN BLUETOOTH *******/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include "ComunicacionBluetooth.h";

/** CONSTRUCTORES **/

/**
 * Constructor
 */     
ComunicacionBluetooth::ComunicacionBluetooth(int tx, int rx): bluetooth(tx, rx){
  bluetooth.begin(9600);
  buffer = "";
}
    
/** FUNCIONES **/

/**
 * Actualiza la información recibida.
 */
String ComunicacionBluetooth::recibirDatos(){
  String datos = "";
  if (bluetooth.available()) {
    char data = (char)bluetooth.read();
    if(data == '\n'){
		datos = buffer;
		buffer = "";
	}else{
		buffer += data;
    }
  }
  
  return datos;
}

/**
 * Ejecuta una acción.
 */
void ComunicacionBluetooth::enviarDatos(String datos){
  Serial.print("Envío");
  Serial.println(datos);
}
