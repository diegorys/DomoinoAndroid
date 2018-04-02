/****************************************/
/************** CONTROLADOR *************/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include <Arduino.h>
#include "Controlador.h"

/**
 * Constructor
 */
Controlador::Controlador(): 
_bluetooth(TxB, RxB), _ir(RxIR){ 

}

/**
 * Activa las comunicaciones.
 */
void Controlador::activar(){
  _ir = ComunicacionIR(RxIR);
} 

/**
 * Actualiza los canales de comunicación.
 */
void Controlador::actualizar(){
  String datos = "";

  //Establecemos la prioridad de emisores: primero el serial (indica que viene
  //de un dispositivo conectado por USB), después Bluetooth y finalmente, IR
  datos = _serial.recibirDatos();

  if(datos == ""){
    datos = _bluetooth.recibirDatos();
  }

  if(datos == ""){
    datos = _ir.recibirDatos();
    //Por IR puede venir información de un nodo esclavo.
    parsear(_ir.codigo);
  }

  if(datos != ""){
    Serial.print(" -> Recibido: ");
    Serial.println(datos);
    ejecutar(datos);
  }
}

/**
 * Ejecuta la orden recibida.
 */
void Controlador::ejecutar(String datos){
  int separador = datos.indexOf('#');
  //Simplemente, replica los datos que vienen por Bluetooth
  //y los emite por IR.
  if(separador != -1){
    _ir.enviarDatos(datos);
    _serial.enviarDatos(datos);
  }
}

/**
 * Parsea el mensaje recibido por IR y lo decodifica,
 * según el protocolo de comunicación establecido.
 */
void Controlador::parsear(unsigned long codigo){
  String buf = String(codigo, HEX);
  Serial.println(buf);
  //Si los tres primeros caracteres son "ada", el mensaje procede
  //de un nodo esclavo.
  if(buf[0] == 'a' && buf[1] == 'd' && buf[2] == 'a'){
    //El primer caracter identifica al nodo esclavo.
    char idesclavo;
    idesclavo = buf[3];
    //El siguiente, el tipo de mensaje.
    char tipo;
    tipo = buf[4];
    //El resto son parámetros, dependientes del nodo y del tipo de mensaje.
    char params[5];  
    for(int i = 0; i < 5; i++){
      params[i] = buf[i+5];
    }
    mostrar(idesclavo, tipo, params);
  }else{
    Serial.println("Interferencia");
  }
}

/**
 * Muestra por pantalla el mensaje decodificado.
 */
void Controlador::mostrar(char idesclavo, char tipo, char params[]){
  //Mostramos el nodo.
  String nodo    = getNodo(idesclavo);
  String mensaje = getMensaje(tipo, params);
  Serial.print("Nodo: ");
  Serial.println(nodo);
  Serial.println("Mensaje:");
  Serial.println(mensaje);
}

/**
 * Obtiene el nombre del nodo emisor del mensaje.
 */
String Controlador::getNodo(char idnodo){
  String nodo = "";
  
  switch(idnodo){
    case '0':
      nodo = "Maestro de habitación";
      break;
    case '1':
      nodo = "Climatizador";
      break;
    case '2':
      nodo = "Detector de gas";
      break;
    default:
      nodo.concat("Nodo desconocido: ");
      nodo.concat(idnodo);
  }
  
  return nodo;
}

/**
 * Obtiene el tipo de mensaje emitido por el
 * nodo esclavo.
 */
String Controlador::getMensaje(char tipo, char params[]){
  String mensaje = "";
  
  switch(tipo){
    case '0':
      mensaje = getEstado(params[0]);
      break;
   case '1':
      mensaje = getBateria(params);
      break;
   case '2':
      mensaje = getTemperaturaInterna(params);
      break;
   default:
      mensaje.concat("Mensaje desconodido: ");
      mensaje.concat(tipo);
  }
  
  return mensaje;
}

/**
 * Para los mensajes tipo estado, obtiene el estado
 * emitido por el nodo esclavo.
 */
String Controlador::getEstado(char estado){
  String mensaje = "";
  switch(estado){
    case '0':
      mensaje = "Estado: nivel bajo";
      break;
    case '1':
      mensaje = "Estado: nivel medio";
      break;
    case '2':
      mensaje = "Estado: nivel alto";
      break;
    default:
      mensaje.concat("Estado desconocido: ");
      mensaje.concat(estado);
  }
  return mensaje;
}

/**
 * Para los mensajes tipo batería, obtiene el porcentaje
 * de batería emitido por el nodo esclavo.
 */
String Controlador::getBateria(char bateria[]){
  String mensaje = "Bat. ";
  byte porcentaje = castParams(bateria);
  mensaje.concat(porcentaje);
  mensaje.concat("%");
  return mensaje;
}

/**
 * Para los mensajes tipo batería, obtiene el porcentaje
 * de batería emitido por el nodo esclavo.
 */
String Controlador::getTemperaturaInterna(char temperatura[]){
  String mensaje = "Temp. ";
  byte temp = castParams(temperatura);
  mensaje.concat(temp);
  mensaje.concat(" G");
  return mensaje;
}

