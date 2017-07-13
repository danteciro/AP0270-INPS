//common/supervision/gestionArchivo.js
var coExSuDo={
	validaGestion:function(){
		var tipo = $('#tipoAccion').val();
		if(tipo != 'anular'){
			confirmer.open("¿Confirma que desea "+tipo+" el archivo seleccionado?","coExSuDo.validaGestionArchivo()");  
		}else{
			confirmer.open("¿Ud. Está seguro de eliminar el archivo?","coExSuDo.validaAnularArchivo()");  
		}
	
    },
	validaGestionArchivo:function(){
		
		if($('#formCargarDocuOS').validateAllForm("#divMensajeValidaFormSubirDocuOS")){
			$("#formCargarDocuOS").submit();
        }
	},
	validaAnularArchivo:function(){
		if($('#formAnularArchOS').validateAllForm("#divMensajeValidaFormAnularArchOS")){
        	$("#formAnularArchOS").submit();
        }
	},
	
    verificaRptaSubiArchOS:function(data){
        $("#dialogGestionarDocumento").dialog('close');
        $('[id^="gridFilesOrdenServicio"]').trigger('reloadGrid');
        if (data.error) {
            mensajeGrowl("error", "Advertencia:", data.mensaje);
        }else {
            mensajeGrowl("success", data.mensaje);
        }
    }
};

$(function() {
    boton.closeDialog();
    $('#btnGuardarGestionDocumentos').click(function(){coExSuDo.validaGestion();});
    $("#fileArchivoCargar").change(function(){      
        $("#file_name_cargar").val(quitaSlashDir($("#fileArchivoCargar").val())); 
    });
    //formulario
    $("#formCargarDocuOS").ajaxForm({
    	beforeSubmit: function () {loading.open();},
        dataType: 'json',
        resetForm: true,
        success: function(data) {
            if (data != null && data.error != null) {
            	loading.close();
                coExSuDo.verificaRptaSubiArchOS(data);
            }
        },
        error: errorAjax
    });
    
    $('#btnAnularArchivo').click(function(){coExSuDo.validaGestion();});
    
    $("#formAnularArchOS").ajaxForm({
    	beforeSubmit: function () {loading.open();},
        dataType: 'json',
        resetForm: true,
        success: function(data) {
            if (data != null && data.error != null) {
            	loading.close();
                coExSuDo.verificaRptaSubiArchOS(data);
            }
        },
        error: errorAjax
    });
        
    
    $('#txtMotivo').alphanum(constant.valida.alphanum.comentario);
    
});