/**
* * Resumen		
* Objeto		: ejecucionMedidaComentario.jsp
* Descripción		: JavaScript donde se maneja las acciones de la pestaña ejecucionMedidaComentario.
* Fecha de Creación	: 07/09/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  07/09/2016   |   Luis García Reyna          |     Registrar Comentario (Detalle Supervision - Escenario Incumplido).
*                |               |                              |
*                |               |                              |
*   
*/

//common/supervision/dsr/ejecucionMedida/ejecucionMedidaComentario.js

/* OSINE_SFS-791 - RSIS 16 - Inicio */ 
var coSuDsrEmEmC = {
    
    fxRegistraEmComentario:{
        
        validaRegistroEmComentario: function(idDetalleSupervision, idEscenarioIncumplido){
            var mensajeValidacion = '';
            if(idDetalleSupervision !='' && idDetalleSupervision !=null && $('#frmOI').validateAllForm("#divMensValidaEmComDetSup")){
                if($('#txtComentarioEjecucionMedida').val().trim().length > 7){
                    confirmer.open("¿Confirma que desea registrar la ejecucion de medida?", "coSuDsrEmEmC.fxRegistraEmComentario.procesaRegistroEmComDetSup()");
                }else{
                    $('#txtComentarioEjecucionMedida').addClass('error');
                    mensajeGrowl("warn", "La ejecución de medida debe tener una longitud mayor o igual a 8 caracteres, corregir", "");
                }
            }else if(idEscenarioIncumplido !='' && idEscenarioIncumplido !=null && $('#frmOI').validateAllForm("#divMensValidaEmComDetSup")){
                if($('#txtComentarioEjecucionMedida').val().trim().length > 7){
                    confirmer.open("¿Confirma que desea registrar la ejecucion de medida?", "coSuDsrEmEmC.fxRegistraEmComentario.procesaRegistroEmComEscIdo()");
                }else{
                    $('#txtComentarioEjecucionMedida').addClass('error');
                    mensajeGrowl("warn", "La ejecución de medida debe tener una longitud mayor o igual a 8 caracteres, corregir", "");
                }
            }
        },
        
        procesaRegistroEmComDetSup: function(){
            loading.open();
            $.ajax({
            	  url: baseURL + "pages/ejecucionMedida/registroComentarioDetalleSupervision",
            	  type: 'post',
            	  async: false,
            	   data: {
                       idDetalleSupervision : $('#idDetalleSupervision').val(),
                       comentario: $('#txtComentarioEjecucionMedida').val()
            	   },
	            success: function(data) {
	                loading.close();              
	                if(data.resultado=='0'){
	                    mensajeGrowl("success", constant.confirm.save, "");
	                    $("#dialogEjecucionMedidaComentario").dialog("destroy");
	                }else{
	                    mensajeGrowl("error", data.mensaje, "");
	                } 
	            },
	            error: errorAjax
            });
        },
        
        procesaRegistroEmComEscIdo: function(){
            loading.open();
            $.ajax({
          	  url: baseURL + "pages/ejecucionMedida/registroComentarioEscenarioIncumplido",
          	  type: 'post',
          	  async: false,
          	   data: {
                   idEscenarioIncumplido : $('#idEscenarioIncumplido').val(),
                   comentarioEjecucion: $('#txtComentarioEjecucionMedida').val()
          	   },
	            success: function(data) {
	                loading.close();              
	                if(data.resultado=='0'){
	                    mensajeGrowl("success", constant.confirm.save, "");
	                    $("#dialogEjecucionMedidaComentario").dialog("destroy");
	                }else{
	                    mensajeGrowl("error", data.mensaje, "");
	                } 
	            },
	            error: errorAjax
          });
        }
        
    },
    
    inicializaObjetosEventos:function(){
        $('#btnGuadarEmComentario').click(function(){
            coSuDsrEmEmC.fxRegistraEmComentario.validaRegistroEmComentario($('#idDetalleSupervision').val(),$('#idEscenarioIncumplido').val());
        });
    }

};

$(function() {
    boton.closeDialog();
    coSuDsrEmEmC.inicializaObjetosEventos();
    coSupeDsrSupe.ComprobarResultadoSupervisionDsr();
});
/* OSINE_SFS-791 - RSIS 16 - Fin */ 