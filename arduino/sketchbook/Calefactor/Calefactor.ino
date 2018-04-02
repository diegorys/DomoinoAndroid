
/****************************************/
/************** CALEFACTOR **************/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include <ActuadorCalefactor.h>
#include <IRLib.h>
#include <IRLibMatch.h>
#include <IRLibTimer.h>
#include <ComunicacionBase.h>;
#include <ComunicacionIR.h>

#include "Configuracion.h"

/** VARIABLES **/

//Comunicación IR.
ComunicacionIR _ir(RxIR);

//Calefactor.
ActuadorCalefactor _calefactor;

//Estado del calefactor.
boolean _estado;

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
  Serial.println("Bienvenido al panel de control del climatizador");
  _reposo = 100;
  _tiempo = millis();
  _ir = ComunicacionIR(RxIR);
  _calefactor = ActuadorCalefactor(CALEFACTOR_PIN);
  _estado = false;
}

/**
 * Bucle infinito de ejecución.
 */
void loop(){
  unsigned long tiempoActual = millis();
  
  //Dejamos un tiempo entre cada ejecución.
  if(tiempoActual - _tiempo > _reposo){
    String datoIr = _ir.recibirDatos();
    if(datoIr != "" && _ir.protocolo == NEC){
      //ADA - Emitido por el maestro, con destino calefactor, encender/apagar
      if(_ir.codigo == 0xada030){
        _estado?_calefactor.apagar():_calefactor.encender();
        _estado = !_estado;
      }else if(datoIr != ""){
        Serial.print("Orden desconocida: ");
        Serial.println(datoIr);
      }
    }
    
    _tiempo = millis();
  }
}
