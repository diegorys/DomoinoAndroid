/****************************************/
/************** CONTROLADOR *************/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#ifndef _CONTROLADOR_H_
#define _CONTROLADOR_H_

#include <Arduino.h>
#include "Configuracion.h"
#include "Helpers.h"
#include <ComunicacionBase.h>
#include <ComunicacionSerial.h>
#include <ComunicacionBluetooth.h>
#include <ComunicacionIR.h>

/** CLASE CONTROLADOR **/

/**
 * Define la clase que controla el funcionamiento de la aplicación.
 */
class Controlador{
  
  public:

    /** CONSTRUCTORES **/
    
    /**
     * Constructor
     */     
    Controlador();
    
    /** FUNCIONES **/
    
    /**
     * Activa las comunicaciones.
     */
    void activar();

    /**
     * Actualiza los canales de comunicación.
     */
    void actualizar();
    
    /**
     * Ejecuta la orden recibida.
     */
    void ejecutar(String datos);

    /**
     * Parsea el mensaje recibido por IR y lo decodifica,
     * según el protocolo de comunicación establecido.
     */
    void parsear(unsigned long codigo);
    
    /**
     * Muestra por pantalla el mensaje decodificado.
     */
    void mostrar(char idesclavo, char tipo, char params[]);
    
    /**
     * Obtiene el nombre del nodo emisor del mensaje.
     */
    String getNodo(char idnodo);
    
    /**
     * Obtiene el tipo de mensaje emitido por el
     * nodo esclavo.
     */
    String getMensaje(char tipo, char params[]);
    
    /**
     * Para los mensajes tipo estado, obtiene el estado
     * emitido por el nodo esclavo.
     */
    String getEstado(char estado);
    
    /**
     * Para los mensajes tipo batería, obtiene el porcentaje
     * de batería emitido por el nodo esclavo.
     */
    String getBateria(char bateria[]);
    
    /**
     * Para los mensajes tipo batería, obtiene el porcentaje
     * de batería emitido por el nodo esclavo.
     */
    String getTemperaturaInterna(char temperatura[]);

  protected:
     /** ATRIBUTOS DE COMUNICACIÓN **/
     ComunicacionBluetooth _bluetooth;
     ComunicacionSerial    _serial;
     ComunicacionIR        _ir; 
};

/** FIN DE CLASE CONTROLADOR   **/

#endif
