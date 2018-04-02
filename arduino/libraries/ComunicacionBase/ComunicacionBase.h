/****************************************/
/********** COMUNICACIÓN BASE *********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#ifndef _COMUNICACION_BASE_H_
#define _COMUNICACION_BASE_H_

#include <Arduino.h>

/** CLASE  **/

/**
 * Gestiona la comunicación.
 */
class ComunicacionBase{
  
  public:

    /** CONSTRUCTORES **/
    
    /**
     * Constructor
     */     
    ComunicacionBase(){}
    
    /** FUNCIONES **/
    
    /**
     * Actualiza la información recibida.
     */
    virtual String recibirDatos(){}
    
    /**
     * Ejecuta una acción.
     */
    virtual void enviarDatos(String datos){}
  
};

/** FIN DE CLASE COMUNICACIÓN BASE   **/

#endif
