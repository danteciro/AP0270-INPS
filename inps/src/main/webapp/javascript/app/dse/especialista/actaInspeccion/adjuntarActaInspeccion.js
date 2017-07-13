/**
* Resumen		
* Objeto			: adjuntarActaInspeccion.js
* Descripción		: JavaScript donde se verifica la subida del Acta de Inspeccion.
* Fecha de Creación	: 16/09/2016.
* PR de Creación	: OSINE_SFS-1063.
* Autor				: Hernan Torres Saenz.
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
*/

var adjuntar={
	validaSubidaActa:function(){
		if($('#formAdjuntarActaInspeccion').validateAllForm("#divMensajeValidaFormAdjuntarActaInspeccion")){
			confirmer.open("¿Confirma que desea adjuntar el Acta?","adjuntar.validaGuardarSubiActaInspeccion()");  
		}
    },
    validaGuardarSubiActaInspeccion:function(){
    	$("#formAdjuntarActaInspeccion").submit();
    }
};

$(function() {
    boton.closeDialog();
    $('#btnGuardarAdjuntarInspeccion').click(function(){adjuntar.validaSubidaActa();});
    $("#fileActa").change(function(){      
        $("#file_name_acta").val(quitaSlashDir($("#fileActa").val())); 
    });
    //formulario
    $("#formAdjuntarActaInspeccion").ajaxForm({
    	beforeSubmit: function () {loading.open();},
        dataType: 'json',
        resetForm: true,
        success: function(data) {
            if (data != null && !(data.error)) {
            	if($('#rolSesion').val()=='ESPECIALISTA'){
            		especialistaActaInspeccion.procesarGridEmpresasZona('EmpresasZona','');
            	}else if($('#rolSesion').val()=='SUPERVISOR'){
            		supervisorActaInspeccion.procesarGridEmpresasZona('EmpresasZonaSupervisor','');
            	}
            	mensajeGrowl("success", data.mensaje,"");
            	$("#dialogAdjuntarActa").dialog('close');
            	
            }else{
            	mensajeGrowl("error", data.mensaje, "");
            }
            loading.close();
        },
        error: errorAjax
    });
    
    $("#fileActa").change(function(){      
	    $("#file_name_acta").val(quitaSlashDir($("#fileActa").val())); 
	});
});