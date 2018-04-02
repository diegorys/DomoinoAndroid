/****************************************/
/************ ECO INFRARROJOS ***********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include <IRLib.h>
#include <IRLibMatch.h>
#include <IRLibTimer.h>
#include <ComunicacionBase.h>
#include <ComunicacionIR.h>

/** VARIABLES **/

//Emite y recibe por IR.
ComunicacionIR _ir(8);

//Decodifica un valor recibido.
IRdecode decodificador;

//Último protocolo recibido.
IRTYPES protocolo = UNKNOWN;

//Último código recibido.
unsigned long codigo;

//Orden recibida por el puerto serial.
int orden = 0;

/** FUNCIONES ARDUINO **/

/**
 * Se ejecuta al arrancar el programa.
 */
void setup()
{
  Serial.begin(9600) ;
  Serial.println("INNOVAIA: Emisor y receptor IR.") ;
  _ir = ComunicacionIR(8);
}

/**
 * Bucle infinito de ejecución.
 */
void loop()
{
  recibir();
  enviar();
  delay(300);
}

/**
 * Envía el último código mediante el último protocolo
 * que se ha recibido.
 */
void enviar(){
  if (Serial.available() > 0) {
    orden = Serial.parseInt();
    if(orden == 1){
      if(protocolo != UNKNOWN){
        Serial.println("ENVIANDO");
        mostrar();
        _ir.enviar(protocolo,codigo);
        Serial.println("OK");
      }else{
        Serial.println("Protocolo desconocido");
      }
    }
  }
}

/**
 * Comprueba si hay datos y, si es así, actualiza el último
 * protocolo y el último código.
 */
void recibir(){
  String datos = _ir.recibirDatos();
  if (datos != "") {	
    codigo = _ir.codigo;
    protocolo = _ir.protocolo;
    Serial.println("RECIBIDO");
    mostrar();
  }  
}

/**
 * Muestra los datos del último protocolo y el
 * último código que se han recibido.
 */
void mostrar(){
  Serial.print("Protocolo: ");
  Serial.println(Pnames(protocolo));
  Serial.print("Valor: ");
  Serial.println(codigo, HEX);
}
