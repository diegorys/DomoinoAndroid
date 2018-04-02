/****************************************/
/*********** FUNCIONES HELPERS **********/
/****************************************/
/* Autor: Diego de los Reyes Rodríguez. */
/****************************************/

#include "Helpers.h"

/**
 * Convierte a número entero los parámetros.
 */
int castParams(char params[]){
  int num = (int)strtol(params, NULL, 16);
  return num;
}
