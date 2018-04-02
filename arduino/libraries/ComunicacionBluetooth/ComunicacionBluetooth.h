	/****************************************/
/********* COMUNICACIÓN BLUETOOTH *******/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#ifndef _COMUNICACION_BLUETOOTH_H_
#define _COMUNICACION_BLUETOOTH_H_

#include <Arduino.h>
#include <SoftwareSerial.h>
#include "ComunicacionBase.h";

/** CLASE  **/

/**
 * Gestiona la comunicación por bluetooth.
 */
class ComunicacionBluetooth: public ComunicacionBase{
  
  public:

    /** CONSTRUCTORES **/
    
    /**
     * Constructor
     */     
    ComunicacionBluetooth(int tx, int rx);
    
    /** FUNCIONES **/
    
    /**
     * Actualiza la información recibida.
     */
    virtual String recibirDatos();
    
    /**
     * Ejecuta una acción.
     */
    virtual void enviarDatos(String datos);

  protected:
  
      String buffer;
      
      SoftwareSerial bluetooth;
  
};

/** FIN DE CLASE COMUNICACIÓN BLUETOOTH   **/

#endif
