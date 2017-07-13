/**
* Resumen
* Objeto		: ObligacionSubTipoServiceNeg.java
* Descripción		: Clase Interfaz de la capa de negocio que contiene la declaración de métodos a implementarse en el ObligacionSubTipoServiceNegImpl
* Fecha de Creación	: 24/05/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: Giancarlo Villanueva Andrade
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                              Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     24/05/2016      Giancarlo Villanueva Andrade        Crear componente de selección de "subtipo de supervisión".Relacionar y adecuar el subtipo de supervisión, el cual deberá depender del tipo de supervisión seleccionado.
* 
*/

package gob.osinergmin.inpsweb.service.business;

import gob.osinergmin.mdicommon.domain.dto.ObligacionSubTipoDTO;
import gob.osinergmin.mdicommon.domain.ui.ObligacionSubTipoFilter;

import java.util.List;

/* OSINE_SFS-480 - RSIS 26 - Inicio */
public interface ObligacionSubTipoServiceNeg {
	List<ObligacionSubTipoDTO> listarObligacionesSubTipo(ObligacionSubTipoFilter filtro);

 }
/* OSINE_SFS-480 - RSIS 26 - Fin */