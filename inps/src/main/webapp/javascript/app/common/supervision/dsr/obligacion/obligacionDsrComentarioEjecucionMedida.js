/**
* Resumen		
* Objeto		: obligacionDsrComentarioEjecucionMedida.js
* Descripción		: Mostrar comentario
* Fecha de Creación	: 08/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: Alexander Vilca Narvaez
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*    
*/

//common/supervision/dsr/obligacion/obligacionDsrComentarioEjecucionMedida.js
var coSupeDsrOblOblComEjMed={
	    initForm: function() {
	        $("#btnCerrarComentarioSubsanacion").click(function() {
	            $("#dialogComentarioSubsanacion").dialog("close");
	        });
	    }
}

$(function() {
    boton.closeDialog();
    coSupeDsrOblOblComEjMed.initForm();
});
