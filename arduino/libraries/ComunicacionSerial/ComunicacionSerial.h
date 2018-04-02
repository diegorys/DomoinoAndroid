/****************************************/
/********** COMUNICACIÓN SERIAL *********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#ifndef _COMUNICACION_SERIAL_H_
#define _COMUNICACION_SERIAL_H_

#include <Arduino.h>
#include "ComunicacionBase.h";

/** CLASE  **/

/**
 * Gestiona la comunicación serial.
 */
class ComunicacionSerial: public ComunicacionBase{
  
  public:

    /** CONSTRUCTORES **/
    
    /**
     * Constructor
     */     
    ComunicacionSerial();
    
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
  
};

/** FIN DE CLASE COMUNICACIÓN SERIAL   **/

#endif
