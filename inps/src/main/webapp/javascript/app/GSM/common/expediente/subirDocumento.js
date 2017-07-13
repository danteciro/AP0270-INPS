

//common/expdiente/subirDocumento.js
var coExSuDo={
	validaSubida:function(){
		confirmer.open("¿Confirma que desea guardar el documento seleccionado?","coExSuDo.validaGuardarSubiDocuOS()");  
    },
    validaGuardarSubiDocuOS:function(){
        if($('#formSubirDocuOS').validateAllForm("#divMensajeValidaFormSubirDocuOS")){
        	var mensajeValidacion = coExSuDo.validarArchivo($("#file_name").val());
        	if(mensajeValidacion!=''){mensajeGrowl('warn', mensajeValidacion);}
        	else{$("#formSubirDocuOS").submit();}
        }
    },
    verificaRptaSubiDocuOS:function(data){
        $("#dialogSubirDocumento").dialog('close');
        /* OSINE-SFS-791 RSIS1 - INICIO*/
//        $('#gridFilesOrdenServicio').trigger('reloadGrid');
        $( "table[id^='gridFilesOrdenServicio']" ).trigger('reloadGrid');
        /* OSINE-SFS-791 RSIS1 - FIN*/
        if (data.error) {
            mensajeGrowl("error", "Advertencia:", data.mensaje);
        }else {
            mensajeGrowl("success", data.mensaje);
        }
    },
    validarArchivo:function(nombreArchivo){
		var mensajeValidacion = "";
		loading.open();
		var url = baseURL + "pages/archivo/listaPghDocumentoAdjunto";
		$.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
            	idOrdenServicio : $('#idOrdenServicioSubDoc').val(),
            	nombreDocumento : nombreArchivo
    		},
            success: function(data) {
            	loading.close();
            	if(data.resultado=='0'){
            		if(data.listaDocumentoAdjunto!=undefined && data.listaDocumentoAdjunto.length>0){
    					mensajeValidacion = "No se puede agregar el mismo archivo a la lista, corregir <br>";
    				}
	            }else{
	            	mensajeGrowl('error', data.mensaje);
					return null;
	            }
            },
            error: errorAjax
        });
        return mensajeValidacion;
    }
};

$(function() {
    boton.closeDialog();
    $('#btnGuardarSubirDocumentoOS').click(function(){coExSuDo.validaSubida();});
    $("#fileArchivo").change(function(){      
        $("#file_name").val(quitaSlashDir($("#fileArchivo").val())); 
    });
    //formulario
    $("#formSubirDocuOS").ajaxForm({
    	beforeSubmit: function () {loading.open();},
        dataType: 'json',
        resetForm: true,
        success: function(data) {
            if (data != null && data.error != null) {
            	loading.close();
                coExSuDo.verificaRptaSubiDocuOS(data);
            }
        },
        error: errorAjax
    });
    $('#txtDescDocuSubirDocuOS').alphanum(constant.valida.alphanum.descrip);
});