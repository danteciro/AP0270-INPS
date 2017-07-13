/*<%--
* Resumen		
* Objeto		: RegistrarMedioProbatorio.js
* Descripción		: Registro medio Probatorio.
* Fecha de Creación	: 31/08/2016
* PR de Creación	: OSINE_791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_791-RSIS8|  31/08/2016   |   Cristopher Paucar Torre    |     Registrar Medio Probatorio.
*                |               |                              |
*                |               |                              |
*                |               |                              |
*                
--%>*/
/* common/supervision/registrarMedioProbatorio.js  */

var coSuReMePro = {
    validarAgregarMedioProbatorio : function(){
        $('#txtDescripcionMPDar,#file_nameSuperDsr').removeClass('error');
        var mensajeValidacion = '';

        if($('#formMedioAprobatorioDsr').validateAllForm('#divMensajeValidaFrmMDDsr')){
            if(mensajeValidacion==''){
                mensajeValidacion = coSuReMePro.validarArchivoPermitido($('#file_nameSuperDsr').val());
                if(mensajeValidacion==''){
                    mensajeValidacion = coSuReMePro.validarMedioProbatorio($('#txtDescripcionMPDsr').val(),$('#file_nameSuperDsr').val());  
                    if(mensajeValidacion==''){confirmer.open('¿Confirma que desea agregar el archivo?', 'coSuReMePro.registrarMedioProbatorio()');}
                }
            }
            if(mensajeValidacion!=''){mensajeGrowl('warn', mensajeValidacion);}
        }
    },
    validarArchivoPermitido:function(nombreArchivo){
        var mensajeValidacion = "";
        var extensionPermitida=false;
        var extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."),nombreArchivo.length).toUpperCase();
        var url = baseURL + "pages/supervision/findTiposArchivo";
            $.ajax({
                url: url,
                type: 'post',
                async: false,
                data: {},
                beforeSend:loading.open,
                success: function(data) {
                    loading.close();
                    if(data.resultado=='0'){
                        if(data.listaTipoArchivo!=undefined && data.listaTipoArchivo.length>0){
                            for(var i=0;i<data.listaTipoArchivo.length;i++){
                                var archivo = data.listaTipoArchivo[i];
                                if(archivo.codigo.toUpperCase()==extensionArchivo){extensionPermitida=true;}
                            }
                            if(!extensionPermitida){mensajeValidacion = "El formato de archivo indicado no esta permitido cargar en el sistema, corregir <br>";}
                        }
                    }else{
                        mensajeGrowl('error', data.mensaje);
                                        return null;
                    }
                },
                error: errorAjax
        });
		return mensajeValidacion;
    },
    validarMedioProbatorio:function(descripcion,nombreArchivo){
        var mensajeValidacion = "";
        var url = baseURL + "pages/archivo/listaPghDocumentoAdjunto";
        var archivoPermitido = false;
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            beforeSend:loading.open,
            data: {
            	idDetalleSupervision : $('#idDetalleSupervisionMedioProb').val(),
                nombreDocumento : nombreArchivo.toUpperCase(),
            },
          success: function(data) {                
            	loading.close();
            	if(data.resultado=='0'){   
                      if(data.listaDocumentoAdjunto!=undefined && data.listaDocumentoAdjunto.length>0){
                        for(var i=0;i<data.listaDocumentoAdjunto.length;i++){
            		     var archivo = data.listaDocumentoAdjunto[i];
            			 if(archivo.nombreArchivo==nombreArchivo) {
                                     archivoPermitido=true;}
            			}
                                if(!archivoPermitido){mensajeValidacion = "No se puede agregar el mismo archivo a la lista, corregir <br>";}
    	              	   }                    
                    }else{
                    mensajeGrowl('error', data.mensaje);
                    return null;
                }
            },
            error: errorAjax
        });
        return mensajeValidacion;
    },
    registrarMedioProbatorio:function(){
        $("#formMedioAprobatorioDsr").submit();
    },
    validaCargaArchivo:function(data) {
        if (data.error){
            mensajeGrowl("warn", "Advertencia:", data.mensaje);}
        else{
            mensajeGrowl("success", constant.confirm.save);
            $( "[id^='gridArchivosMedioProbatorio']" ).trigger('reloadGrid');
        }
    }
}

$(function() {
    boton.closeDialog();
    $("#fileArchivoSuperDsr").change(function(){      
        $("#file_nameSuperDsr").val(quitaSlashDir($("#fileArchivoSuperDsr").val())); 
    });
    $('#btnAgregarMedioProbatorioDsr').click(function() {
        coSuReMePro.validarAgregarMedioProbatorio();
    });
    //Formularios
    $("#formMedioAprobatorioDsr").ajaxForm({
        beforeSubmit: function () {
            loading.open();
            console.log("beforeSubmit");
        },
        dataType: 'json',
        resetForm: true,
        success: function(data) {
            loading.close();
            $("#dialogMediosProbatorios").dialog('close');
            if (data != null && data.error != null) {
                coSuReMePro.validaCargaArchivo(data);}
            },
        error: errorAjax
    });
});