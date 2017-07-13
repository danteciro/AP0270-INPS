/*
/**
* Resumen		
* Objeto			: levatamientoDetalle.js
* Descripción		: js levatamientoDetalle
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     28/10/2016      Paul Moscoso                Crear una funcionalidad que permita Agregar Medios Provatorios del Levantamiento
*/ 

var modDetLevan = {		
	 inicioModDetLevantamiento : function(){
		 $(".btn-back").attr("href",  $('#urlSeccion').val());
		 $(".btnSinEscenario").on("click", function (e) {
			 modDetLevan.comentarioIncumplimientoLev("",$('#idOblDsrDetIdDetalleSupervisionLev').val(),$('#idInfraccionOblDetSupLev').val());
		 });
		 $("#btnGuardarLevantamiento").click(function() {
			 $('#descripDetLevantamiento').val($('#descripDetLevantamiento').val().trim());
			 if($('#descripDetLevantamiento').val()!=''){
				 if($('#descripDetLevantamiento').val().length < 8) {
					 navSlide.confirmer('Advertencia', 'La descripci&oacute;n del levantamiento debe tener una longitud mayor o igual a 8 caracteres, corregir.');
				 } else { 
					 modDetLevan.actualizarLevantamiento();
				 }
			 } else {
				 navSlide.confirmer('Advertencia', 'Debe ingresar la descripci&oacute;n de levantamiento.');
			 }
		 });
		 $("#btnCerrarLevantamiento").click(function() {
			 $('#descripDetLevantamiento').val($('#descripDetLevantamiento').val().trim());
			 if($('#descripDetLevantamiento').val()!='' && $('#flagDescDetLev').val()!='1'){
				 navSlide.confirmer('Advertencia', '¿Desea regresar a la bandeja de obligaciones incumplidas del expediente? Se perder&aacute;n los cambios.', 'modDetLevan.regresarBandeja()');
			 } else {
				 navSlide.navAjx($('#urlSeccion').val());
			 } 
		 });
         $("#formMedioAprobatorioDsr").ajaxForm({
	            dataType: 'json',
	            resetForm: true,
	            success: function(data) {	                
	                if (data != null && data.error != null) {
	                    modDetLevan.validaCargaArchivo(data);}
	                }
         });
         $('#btnAgregarMedioProbatorioLevDsr').click(function() {
             modDetLevan.validarAgregarMedioProbatorio();
         });         
	 },
	 regresarBandeja : function(){
		 navSlide.navAjx($('#urlSeccion').val());
	 },
	 actualizarLevantamiento: function(){
		 $.ajax({
            url: "/inps/pages/modLevantamientos/actualizarLevantamiento",
            type: 'post',
            async: false,
            data: {
            	idDetalleLevantamiento:$('#idOblDsrDetIdDetalleLevantamiento').val(),
            	descripcion:$('#descripDetLevantamiento').val(),
            	idExpediente: $('#idExpedienteLevDet').val()
            },
            beforeSend:navSlide.loadingOpen(),
            success: function(data) {
            	navSlide.loadingClose();
            	if (data.resultado == '0') {
            		$('#btnAgregarMPLev').removeAttr("disabled");            		
            		$(".solar-gap").css('display', 'inline');
            		$('#flagDescDetLev').val('1');
            		$('.active-infraccion .estado').html(data.expediente.estadoLevantamiento.descripcion)
            		navSlide.confirmer('Confirmaci&oacute;n', data.mensaje);
            	} else {
            		navSlide.confirmer('Error', data.mensaje);
            	}                 
            }            
        });	 
	 }, 
     comentarioIncumplimientoLev: function(idEsceIncumplimiento,idDetalleSupervision,idInfraccion) {
        $.ajax({
            url: "/inps/pages/modLevantamientos/comentarioIncumplimiento",
            type: 'post',
            async: false,
            data: {
                idEsceIncumplimiento:idEsceIncumplimiento,
                idDetalleSupervision:idDetalleSupervision,
                idInfraccion:idInfraccion
            },
            beforeSend:navSlide.loadingOpen(),
            success: function(data) {
            	navSlide.loadingClose();
            	if(data.esceInc.idEsceIncumplimiento==null && data.infraccion.idInfraccion!=null){
            		$(".comentario-levantamiento-escenario").css('display', 'none');
                    $(".comentario-levantamiento-sin-escenario").css('display', 'inline');
                    $("#descSinEscInfLev").val(data.infraccion.descripcionInfraccion);
                    $('#descSinEscInfLev').prop('readonly', true);
            	} else if(data.esceInc.idEsceIncumplimiento!=null){
            		$(".comentario-levantamiento-escenario").css('display', 'inline');
                    $(".comentario-levantamiento-sin-escenario").css('display', 'none');
                    $("#descEscIncLev").val(data.esceInc.idEsceIncuMaestro.descripcion);
                    $('#descEscIncLev').prop('readonly', true);
            	}   
            }            
        });
        modDetLevan.procesarGridComentarioIncumplimiento(idEsceIncumplimiento,idDetalleSupervision,idInfraccion);
    },    
    procesarGridComentarioIncumplimiento: function(idEsceIncumplimiento,idDetalleSupervision,idInfraccion) {	
    	$.ajax({
            url: "/inps/pages/modLevantamientos/findComentarioIncumplimiento",
            type: 'post',
            async: false,
            data: {
            	idEsceIncumplimiento: idEsceIncumplimiento,
                idDetalleSupervision: idDetalleSupervision,
                idInfraccion: idInfraccion,
                flagBuscaComentDetSup:'1'
            },
            beforeSend:navSlide.loadingOpen(),
            success: function(data) {
            	navSlide.loadingClose();
            	var html='';
            	if(data.comentarioIncumplimientoList!=null){
	            	$.each(data.comentarioIncumplimientoList, function(idx, obj) {
	            		if(obj!=null){
		            		html+="<tr>";
		            		html+="<td style='display:none;' class='text-left blue-label'>"+obj.flagComentDetSupEnEsceIncdo+"</td>"; 
		            		html+="<td style='display:none;' class='text-justify blue-label'>"+obj.idComentarioDetSupervision+"</td>"; 
		            		html+="<td style='display:none;' class='text-justify blue-label'>"+obj.idComentarioIncumplimiento+"</td>"; 
		            		html+="<td class='text-justify blue-label'>"+obj.descripcion+"</td>";      
		            		html+="</tr>";
	            		}
	            	});
            	}
            	if(html==''){
            		html+="<tr>";
            		html+="<td class='text-center' colspan='4'>No se encontraron resultados</td>";
            		html+="</tr>";
            	}
            	$('#gridComentIncuLev tbody').html(html);
            }            
        });
    },
    validarAgregarMedioProbatorio: function () {
        var mensajeValidacion = '';
        if($('#txtDescripcionMPDsr').val().trim() == ''){
        	mensajeValidacion = 'Ingresar correctamente descripci&oacute;n de medio probatorio';
        }
        if (mensajeValidacion == '') {            
            mensajeValidacion = modDetLevan.validarArchivoPermitido($('#fileArchivoSuperDsr').val());
            if (mensajeValidacion == '') {               
                mensajeValidacion = modDetLevan.validarMedioProbatorio($('#txtDescripcionMPDsr').val(), $('#fileArchivoSuperDsr').val());
                if (mensajeValidacion == '') {
                    modDetLevan.registrarMedioProbatorio();
                }
            }
        }        
        if(mensajeValidacion!=''){
            navSlide.confirmer('Error', mensajeValidacion);
        }
    },
    registrarMedioProbatorio:function(){
       $("#formMedioAprobatorioDsr").submit();
    },
    validaCargaArchivo:function(data) {
        if (data.error){
           navSlide.confirmer('Error',data.mensaje);}
        else{
            $('#agregar-medio').modal('toggle');
            navSlide.confirmer('Confirmaci&oacute;n', "Se cargo el archivo correctamente.");
            var url=$('#urlDetSeccion').val();
            navSlide.navAjx(url);
        }
    }, 
    validarArchivoPermitido:function(nombreArchivo){
        var mensajeValidacion = "";
        var extensionPermitida=false;
        var extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."),nombreArchivo.length).toUpperCase();
        var url = "/inps/pages/supervision/findTiposArchivo";
            $.ajax({
                url: url,
                type: 'post',
                async: false,
                data: {},
                beforeSend:navSlide.loadingOpen(),
                success: function(data) {
                	navSlide.loadingClose();
                    if(data.resultado=='0'){
                        if(data.listaTipoArchivo!=undefined && data.listaTipoArchivo.length>0){
                            for(var i=0;i<data.listaTipoArchivo.length;i++){
                                var archivo = data.listaTipoArchivo[i];
                                if(archivo.codigo.toUpperCase()==extensionArchivo){extensionPermitida=true;}
                            }
                            if(!extensionPermitida){mensajeValidacion = "El formato de archivo indicado no esta permitido cargar en el sistema, corregir. <br>";}
                        }
                    } else{
                        navSlide.confirmer('Error',data.mensaje);
                        return null;
                    }
                }
        });
		return mensajeValidacion;
    },
    validarMedioProbatorio:function(descripcion,nombreArchivo){
        var mensajeValidacion = "";
        var url =  "/inps/pages/archivo/listaPghDocumentoAdjunto";
        var archivoPermitido = false;
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {
            	idDetalleLevantamiento : $('#idOblDsrDetIdDetalleLevantamiento').val(),
                nombreDocumento : nombreArchivo.toUpperCase(),
            },
            beforeSend:navSlide.loadingOpen(),
            success: function(data) {
            	navSlide.loadingClose();
            	if(data.resultado=='0'){   
                      if(data.listaDocumentoAdjunto!=undefined && data.listaDocumentoAdjunto.length>0){
                    	  for(var i=0;i<data.listaDocumentoAdjunto.length;i++){
                    		  var archivo = data.listaDocumentoAdjunto[i];
                    		  if(archivo.nombreArchivo==nombreArchivo) {
                                     archivoPermitido=true;
                              }
                    	  }
                          if(!archivoPermitido){mensajeValidacion = "No se puede agregar el mismo archivo a la lista, corregir. <br>";}
    	              }                    
                } else {
                    navSlide.confirmer('Error',data.mensaje);
                    return null;
                }
            }
        });
        return mensajeValidacion;
    }
}
$(function(){	
	modDetLevan.inicioModDetLevantamiento();          
});