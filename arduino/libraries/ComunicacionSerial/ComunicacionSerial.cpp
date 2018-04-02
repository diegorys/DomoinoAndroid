/****************************************/
/********** COMUNICACIÓN SERIAL *********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include "ComunicacionSerial.h";

/** CONSTRUCTORES **/

/**
 * Constructor
 */     
ComunicacionSerial::ComunicacionSerial(){
  buffer = "";
}
    
/** FUNCIONES **/

/**
 * Actualiza la información recibida.
 */
String ComunicacionSerial::recibirDatos(){
  String datos = "";
  if (Serial.available()) {
    int ssize = Serial.available();
    char data = (char)Serial.read();
    for(int i=0;i<ssize;i++){
      if(data == '\n'){
        datos = buffer;
        buffer = "";
      }else{
        buffer += data;
      } 
    }
  }
  
  return datos;
}

/**
 * Ejecuta una acción.
 */
void ComunicacionSerial::enviarDatos(String datos){
  Serial.println(datos);
  Serial.print("Enviado: ");
  Serial.println(datos);
}
