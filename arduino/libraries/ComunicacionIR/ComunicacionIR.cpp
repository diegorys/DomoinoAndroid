/****************************************/
/************* COMUNICACIÓN IR **********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include "ComunicacionIR.h";

/** CONSTRUCTORES **/

/**
 * Constructor
 */     
ComunicacionIR::ComunicacionIR(int rx): _receptor(rx){
	_receptor.enableIRIn(); // Start the receiver
}
    
/** FUNCIONES **/

/**
 * Actualiza la información recibida.
 */
String ComunicacionIR::recibirDatos(){
  String dato = "";
  IRdecode decodificador;
  if (_receptor.GetResults(&decodificador)) {
    decodificador.decode();
    if(decodificador.decode_type != UNKNOWN && decodificador.value != 0xFFFFFFFF){
      codigo    = decodificador.value;
	  protocolo = decodificador.decode_type;
	  dato = String(codigo, HEX);
    }
    _receptor.resume();
  }  

  return dato;
}

/**
 * Envía datos por IR.
 */
void ComunicacionIR::enviarDatos(String datos){
	int separador = datos.indexOf('#');
	if(separador != -1){
		IRTYPES protocolo = PIRTypes(datos.substring(0,separador));
		String codStr = "";
		codStr.concat(datos.substring(separador+1, datos.length()));
		unsigned long codigo = strtoul(codStr.c_str(), NULL, 16);
		enviar(protocolo, codigo);
		_receptor.resume();
	}
}

/**
 * Envía datos por IR.
 */
void ComunicacionIR::enviar(IRTYPES protocolo, unsigned long codigo){
	Serial.print("Emito: ");
	Serial.print(Pnames(protocolo));
	Serial.print(" - ");
	Serial.println(String(codigo, HEX));
	_emisor.send(protocolo, codigo, 32);
}

/**
 * Obtiene el protocolo en función del nombre.
 */
IRTYPES ComunicacionIR::PIRTypes(String protocolo){
	IRTYPES p;
	
	if(protocolo == "Unknown"){
		p = UNKNOWN;
	}else if(protocolo == "NEC"){
		p = NEC;
	}else if(protocolo == "Sony"){
		p = SONY;
	}else if(protocolo == "RC5"){
		p = RC5;
	}else if(protocolo == "RC6"){
		p = RC6;
	}else if(protocolo == "Panasonic old"){
		p = PANASONIC_OLD;
	}else if(protocolo == "JVC"){
		p = JVC;
	}else if(protocolo == "NECx"){
		p = NECX;
	}else if(protocolo == "Hash Code"){
		p = HASH_CODE;
	}

	return p;
}
