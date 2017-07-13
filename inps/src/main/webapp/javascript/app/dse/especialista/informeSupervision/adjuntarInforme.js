
var adjuntarInforme = {
		validaSubida:function(){
			if($("#file_nameInforme").val()!=null && $("#file_nameInforme").val()!=""){
				var file_name_array = $("#file_nameInforme").val().split(".");
	        	var file_extension = file_name_array[file_name_array.length - 1];
	        	if(file_extension=='pdf' || file_extension=='PDF'){
			confirmer.open("¿Confirma que desea guardar el documento seleccionado?","adjuntarInforme.validaGuardarSubiDocuOS()");  
			}else{
				mensajeGrowl('warn', "Formato no permitido. ( Extensiones permitidas: .pdf) ");
			} 
			}else{
				errorArchivo();
				
			}
	    },
	    validaGuardarSubiDocuOS:function(){
	        if($('#formSubirDocuOSInforme').validateAllForm("#divMensajeValidaformSubirDocuOSInforme")){
	        	 	$("#formSubirDocuOSInforme").submit();
	        }
	    },
	    
	    verificaRptaSubiDocuOS:function(data){
	        $("#dialogAdjuntarInforme").dialog('close');
	        if (data.error) {
	            mensajeGrowl("error", "Advertencia:", data.mensaje);
	        }else {
	            mensajeGrowl("success", data.mensaje);
	            mantenimientoInformeSupervision.procesarGridInformeSupervision('InformeSupervision');
	        }
	    }
};
function errorArchivo() {
    var msj1 = "Por favor seleccione un archivo.";
    
    loading.close();
    mensajeGrowl('warn', msj1);
}
$(function() {
	boton.closeDialog();
	$("#radioObservacion").change(function() {
		if($('#radioObservacion').is(":checked")){
			 $('#divMotivoObs').attr("style","display:inline");
			 $('#radioObservacion').val("1");
		}else{
			  $('#divMotivoObs').attr("style","display:none");
			  $('#radioObservacion').val("0");
			  $('#txtMotivoObs').val("");
		}		
	});
	

	$('#btnGuardarDocumento').click(function(){
		adjuntarInforme.validaSubida();
 	
	});
	$("#fileArchivoInforme").change(function(){      
	     $("#file_nameInforme").val(quitaSlashDir($("#fileArchivoInforme").val())); 
	 });
	$("#formSubirDocuOSInforme").ajaxForm({
	  	beforeSubmit: function () {loading.open();},
	    dataType: 'json',
	    resetForm: true,
	    success: function(data) {
	    if (data != null && data.error != null) {
	      	loading.close();
	       	adjuntarInforme.verificaRptaSubiDocuOS(data);
	        console.log(data);
	        }
	    },
	        error: errorAjax
	 });
	   


});