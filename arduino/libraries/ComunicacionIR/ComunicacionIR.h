/****************************************/
/************ COMUNICACIÓN IR ***********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#ifndef _COMUNICACION_IR_H_
#define _COMUNICACION_IR_H_

#include <stdlib.h>
#include <Arduino.h>
#include <IRLib.h>
#include <IRLibMatch.h>
#include <IRLibTimer.h>
#include "ComunicacionBase.h";

#define NUM_PROTOCOLOS 10

/** CLASE  **/

/**
 * Gestiona la comunicación por infrarrojos.
 */
class ComunicacionIR: public ComunicacionBase{
  
  public:

	/** ATRIBUTOS PÚBLICOS **/
	  
	//Código actual.
	unsigned long codigo;

	//Protocolo actual.
	IRTYPES protocolo;

    /** CONSTRUCTORES **/
    
    /**
     * Constructor
     */     
    ComunicacionIR(int rx);
    
    /** FUNCIONES **/
    
    /**
     * Actualiza la información recibida.
     */
    virtual String recibirDatos();
    
    /**
     * Envía datos por IR.
     */
    virtual void enviarDatos(String datos);

	/**
	 * Envía datos por IR.
	 */
	void enviar(IRTYPES protocolo, unsigned long codigo);

  protected:

	/** ATRIBUTOS **/

    //Emite por IR.
    IRsend _emisor;
    
    //Recibe IR.
    IRrecv _receptor;

	/** MÉTODOS **/
	
	/**
	 * Obtiene el protocolo en función del nombre.
	 */
	IRTYPES PIRTypes(String protocolo);
  
};

/** FIN DE CLASE COMUNICACIÓN IR **/

#endif
