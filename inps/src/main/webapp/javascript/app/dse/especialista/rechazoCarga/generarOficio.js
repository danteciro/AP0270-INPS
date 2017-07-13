/**
 * Resumen		
 * Objeto				: generarOficio.js
 * Descripción			: JavaScript donde se maneja las acciones de Generar Oficio
 * Fecha de Creación	: 11/10/2016.
 * PR de Creación		: OSINE_SFS-1063.
 * Autor				: Victoria Ubaldo G.
 * ===============================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * ===============================================================================================
 *
 */

$(function() {
    boton.closeDialog();
    $("#formSubirOficio").ajaxForm({
    	dataType: 'json',
        resetForm : true,
        success : function(data) {
            if (data != null && data.error != null) {
            	if (data.error) {
                    mensajeGrowl("error", "Error", data.mensaje);
                } 
            	loading.close();
            }                                     
        }
    });
   $('#btnGuardarOficio').click(function(){
	   if($("#file_name").val()!=""){
		   var file_name_array = $("#file_name").val().split(".");
       	   var file_extension = file_name_array[file_name_array.length - 1];
       	if(file_extension=='doc' || file_extension=='docx' || file_extension=='DOC' || file_extension=='DOCX'){
		   $("#formSubirOficio").submit();
		   //setTimeout(function(){ 
			   coExSuDo.validaSubida();		 
		   //}, 3000); 
	   }else{
			mensajeGrowl('warn', "Formato no permitido. ( Extensiones permitidas: .doc, .docx) ");
	   }
	   }else{
		   mensajeGrowl('error', "Por favor seleccione un archivo.");
	   }
	
   });
 
    $("#fileArchivo").change(function(){      
        $("#file_name").val(quitaSlashDir($("#fileArchivo").val())); 
    });
    $('#txtFirma').alphanum(constant.valida.alphanum.descrip);
});
var coExSuDo={
	validaSubida:function(){
 
		confirmer.open("¿Confirma que desea guardar el oficio seleccionado?","coExSuDo.generarExpedienteRechazoCarga()");  

},
    validaGuardarSubiDocuOS:function(){
    	
        if($('#formSubirOficio').validateAllForm("#divMensajeValidaFormSubirOficio")){
        	var mensajeValidacion = coExSuDo.validarArchivo($("#file_name").val());
        	if(mensajeValidacion!=''){
        		mensajeGrowl('warn', mensajeValidacion);
        	}
        	else{$("#formSubirOficio").submit();}
        }
    } ,
    generarExpedienteRechazoCarga: function() {
    	 $.ajax({
    	 url: baseURL + "pages/empresasVw/generarExpedienteRechazoCarga",
         type: 'post',
         async: false,
         dataType: 'json',
         data: { 	ruc:  $('#txtRuc').val(),
                	idEmpresa: $('#txtIdEmpresa').val(),
                    anio:$('#txtAnio').val(),
                    empresa: $('#txtDescEmpresa').val()
         },
         beforeSend: loading.open,
         success: function(data) {
             loading.close();
             if (data.resultado == '0') {
                 $("#dialogGenerarOficioEspecialista").dialog('close');
                 mensajeGrowl("success", constant.confirm.save, "");
                 especialistaRechazoCarga.procesarGridEmpresas('Empresas','buscar');
                 //supervisorRechazoCarga.procesarGridEmpresas('Empresas','buscar');
                 } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        
    }
};
