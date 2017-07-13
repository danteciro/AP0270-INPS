/**
* Resumen		
* Objeto		: consultaMensajeriaExpediente.js
* Descripción		: consultaMensajeriaExpediente
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |   27/05/2016  |    Luis García Reyna         |     Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
*                |               |                              |
*                |               |                              |
*  
*/

//common/expediente/consultaMensajeriaExpediente.js
var coExCoMeEx={
	procesarGridMensajeria:function() {
        var nombres = ['idMensajeria','tipoDocumento','nroDocumento','departamento','provincia','distrito','direcciondestinatario','N&UacuteMERO EXPEDIENTE','DOCUMENTO','FECHA','ESTADO','DESTINATARIO','UNIDAD REMITENTE'];
        var columnas = [
            {name: "idMensajeria", sortable: false, hidden:true},
            {name: "tipoDocumento", sortable: false, hidden:true},
            {name: "nroDocumento", sortable: false, hidden:true},
            {name: "departamento", sortable: false, hidden:true},
            {name: "provincia", sortable: false, hidden:true},
            {name: "distrito", sortable: false, hidden:true},
            {name: "direcciondestinatario", sortable: false, hidden:true},
            {name: "expediente", width: 100, sortable: false, align: "center"},
            {name: "documento", width: 220, sortable: false, align: "left",formatter:"documento"},
            {name: "fechaDerivacion", width: 65, hidden:false,formatter:fecha, align: "center"},
            {name: "accion", width: 90, sortable: false, hidden:false, align: "center"},
            {name: "destinatario", width: 220, sortable: false, hidden:false, align: "left"},
            {name: "unidadremitente", width: 220, sortable: false, hidden:false, align: "center"}
        ];
        $("#gridContenedorFilesMensajeriaDocumentos").html("");
        var grid = $("<table>", {
            "id": "gridMensajeria"
        });
        var pager = $("<div>", {
            "id": "paginacionMensajeria"
        });
        $("#gridContenedorFilesMensajeriaDocumentos").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/expediente/findMensajeria",
            datatype: "json",
            mtype : "POST",
            postData: {              
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionMensajeria",
            emptyrecords: "No se encontraron resultados",
            recordtext: "{0} - {1}",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idMensajeria"
            },      
            loadComplete: function(data) {
                
            },
            onSelectRow: function(rowid, status) {
            	var row = grid.getRowData(rowid);
            	// Pestaña Datos de Envío
            	coExCoMeEx.limpiarDatosEnvio();
            	if(row["tipoDocumento"]!=null && row["tipoDocumento"]!='' && row["tipoDocumento"]!=undefined){
            		$('#tipoDocumento').val(row["tipoDocumento"]);
            	}
            	if(row["nroDocumento"]!=null && row["nroDocumento"]!='' && row["nroDocumento"]!=undefined){
            		$('#numeroDocumento').val(row["nroDocumento"]);
            	}
            	if(row["fechaDerivacion"]!=null && row["fechaDerivacion"]!='' && row["fechaDerivacion"]!=undefined){
            		$('#fechaEnvio').val(row["fechaDerivacion"]);
            	}
            	if(row["destinatario"]!=null && row["destinatario"]!='' && row["destinatario"]!=undefined){
            		$('#destinatario').val(row["destinatario"]);
            	}
            	if(row["departamento"]!=null && row["departamento"]!='' && row["departamento"]!=undefined &&
            	   row["provincia"]!=null && row["provincia"]!='' && row["provincia"]!=undefined &&
            	   row["distrito"]!=null && row["distrito"]!='' && row["distrito"]!=undefined){
            		$('#ubigeo').val(row["departamento"]+" - "+row["provincia"]+" - "+row["distrito"]);
            	}
            	if(row["direcciondestinatario"]!=null && row["direcciondestinatario"]!='' && row["direcciondestinatario"]!=undefined){
            		$('#direccion').val(row["direcciondestinatario"]);
            	}
            	
            	// Pestaña Datos de Cargo
            	coExCoMeEx.limpiarDatosCargo();
            	coExCoMeEx.procesarDatosCargo(row["idMensajeria"],"FilesOrdenServicioMensajeria");
            	
            	// Pestaña Documentos
            	coExCoMeEx.procesarGridFilesExpedienteMensajeria(row["idMensajeria"],"FilesOrdenServicioMensajeria"); 
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    procesarGridFilesExpedienteMensajeria:function(idMensajeria,nombreGrid){
        var nombres = ['idArchivo','DOCUMENTO','ARCHIVO','ASUNTO','DESCARGAR'];
        var columnas = [
            {name: "idArchivo",index:'id',hidden:true},
            {name: "documento", width: 300, sortable: false, align: "left"},
            {name: "nombreArchivo", width: 350, sortable: false, align: "left"},
            {name: "asunto", width: 300, sortable: false, align: "left"},
            {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpedienteMensajeria"}
            ];
       	
        $("#gridContenedor"+nombreGrid).html("");
        var grid = $("<table>", {
            "id": "grid"+nombreGrid
        });
        var pager = $("<div>", {
            "id": "paginacion"+nombreGrid
        });
        $("#gridContenedor"+nombreGrid).append(grid).append(pager);
        
        grid.jqGrid({
            url: baseURL + "pages/archivo/findArchivoConsultaMensajeria",
            datatype: "json",
            postData: {
                idMensajeria: idMensajeria
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacion"+nombreGrid,
            emptyrecords: "No se encontraron resultados",
            recordtext: "{0} - {1}",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            viewrecords: true,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "idDestinatario"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
                },
    	});	
     },
     limpiarDatosEnvio:function(){
    	 $('#tipoDocumento').val('');
    	 $('#numeroDocumento').val('');
    	 $('#fechaEnvio').val('');
    	 $('#destinatario').val('');
    	 $('#ubigeo').val('');
    	 $('#direccion').val('');
     },
     limpiarDatosCargo:function(){
    	 $('#idEstado').val('');
    	 // Devuelto
    	 $('#idMotivoDevolucion').val('');
    	 
    	 // NotificadoDevuelto
    	 $('#idFechaDevolucionCargo').val('');
    	 $('#idNumeroRemito').val('');
    	 $('#idPrimeraVisita').val('');
    	 
    	 // Robado/ Extraviado
    	 $('#idFechaDenunciaPolicial').val('');
    	 
    	 // Notificado
    	 $('#idSegundaVisita').val('');
    	 $('#idNotificacionPorActa').val('');
    	 $('#idMotivoNotificacionConActa').val('');
    	 $('#idFechaEntregaDestinatario').val('');
    	 $('#idHoraEntregaDestinatario').val('');
    	 $('#idFechaDevolucionArea').val('');
    	 
    	 // Receptor
    	 $('#idRecepcionadoPorOtro').val('');
    	 $('#idNombreReceptor').val('');
    	 $('#idApellidoPaterno').val('');
    	 $('#idApellidoMaterno').val('');
    	 $('#idDocumentoIdentidad').val('');
    	 $('#idRelacionConDestinatario').val('');
     },
     procesarDatosCargo:function(idMensajeria){
 		loading.open();
         $.ajax({
             url:baseURL + "pages/expediente/cargaDatosCargo",
             type:'get',
             async:false,
             data:{
                 idMensajeria: idMensajeria,
             },
             beforeSend:loading.open,
             success:function(data){
                 loading.close();
                 // Carga Pestaña Datos de Cargo.
                 if(data.registro!=null){
                	 $('#idFechaDenunciaPolicial').css('display','inline-block');
                	 
                	 $('#idFechaDenunciaPolicial').val(fechaDenuncia(data.registro.fechaDenunciaPolicial));
                	 
                	 $('#idMensaje').css('display','none');
                     $('#idEstadoMensajeria').css('display','inline-block');
                     $('#idFechaDevolucionAreaMensajeria').css('display','inline-block');
                     
                	 $('#tipoAccionOS').val(data.tipo);
                     $('#idFechaDevolucionArea').val(data.registro.fechaDevolucionuf);
                     $('#idEstado').val(data.registro.nombreEstadoCargoMensajeria);
                	 if(data.registro.nombreEstadoCargoMensajeria.trim()==constant.estadoMensajeria.notificado){// Notificado
                    	 $('#idNotificadoDevuelto').css('display','block');
                    	 $('#idNotificado').css('display','block');
                    	 $('#idReceptor').css('display','block');
                    	 
                    	 $('#idDevuelto').css('display','none');
                    	 $('#idRobado').css('display','none');
                    	 
                    	 //idNotificadoDevuelto
                    	 $('#idFechaDevolucionCargo').val(data.registro.fechaDevolucionCargo);
                    	 $('#idNumeroRemito').val(data.registro.numeroEtiqueta);
                    	 $('#idPrimeraVisita').val(data.registro.fechaVisita1);
                    	 
                    	 //idNotificado
                    	 $('#idSegundaVisita').val(data.registro.fechaVisita2);
                    	 $('#idNotificacionPorActa').val(data.registro.notificacionActa);
                    	 $('#idMotivoNotificacionConActa').val(data.registro.nombreMotivoNotificacionActa);
                    	 $('#idFechaEntregaDestinatario').val(data.registro.fechaEntregaDestinatario);
						
                    	 $('#idHoraEntregaDestinatario').val(data.registro.horaEntregaDestinatario);
                    	 
                    	 //idReceptor
                    	 if(data.registro.recibidoPorDestinatario=='S'){
                    		 $('#idRecepcionadoPorOtro').val('SI');
                    	 }else{
                    		 $('#idRecepcionadoPorOtro').val('NO');
                    	 }
                    	 $('#idNombreReceptor').val(data.registro.nombreReceptor);
                    	 $('#idApellidoPaterno').val(data.registro.apellidoPaternoReceptor);
                    	 $('#idApellidoMaterno').val(data.registro.apellidoMaternoReceptor);
                    	 $('#idDocumentoIdentidad').val(data.registro.numeroIdentificacionReceptor);
                    	 $('#idRelacionConDestinatario').val(data.registro.descripcionRelacion);
                     }
                     
                     if(data.registro.nombreEstadoCargoMensajeria.trim()==constant.estadoMensajeria.devuelto){// Devuelto
                    	 $('#idNotificadoDevuelto').css('display','block');
                    	 $('#idDevuelto').css('display','block');
                    	 
                    	 $('#idReceptor').css('display','none');
                    	 $('#idNotificado').css('display','none');
                    	 $('#idRobado').css('display','none');
                    	 
                    	//idNotificadoDevuelto
                    	 $('#idFechaDevolucionCargo').val(data.registro.fechaDevolucionCargo);
                    	 $('#idNumeroRemito').val(data.registro.numeroEtiqueta); // Falta
                    	 $('#idPrimeraVisita').val(data.registro.fechaVisita1);
                    	 
                    	 //idDevuelto
                    	 $('#idMotivoDevolucion').val(data.registro.nombre);
                    	 
                     }
                     
                     if(data.registro.nombreEstadoCargoMensajeria.trim()==constant.estadoMensajeria.robado){// Robado
                    	 $('#idRobado').css('display','block');
                    	 
                    	 $('#idReceptor').css('display','none');
                    	 $('#idNotificado').css('display','none');
                    	 $('#idDevuelto').css('display','none');
                    	 $('#idNotificadoDevuelto').css('display','none');
                     }
                     
                     
                 }else{
                	 $('#idMensaje').css('display','inline-block');
                	 $('#idEstadoMensajeria').css('display','none');
                	 $('#idFechaDevolucionAreaMensajeria').css('display','none');
                	 
                	 $('#idRobado').css('display','none');
                	 
                	 $('#idReceptor').css('display','none');
                	 $('#idNotificado').css('display','none');
                	 $('#idDevuelto').css('display','none');
                	 $('#idNotificadoDevuelto').css('display','none');
                	 $('#idFechaDenunciaPolicial').css('display','none');
                 }
             },
             error:errorAjax
         });
 	}
};

jQuery.extend($.fn.fmatter, {
    documento: function(cellvalue, options, rowdata) {
        var tipo=$.trim(rowdata.tipoDocumento);
        var numero=$.trim(rowdata.nroDocumento);
        var html = '';
        if (tipo != null && tipo != '' && tipo != undefined &&
        		numero != null && numero != '' && numero != undefined){       
            html=tipo+' '+numero;
        }
        return html;
    }
});

jQuery.extend($.fn.fmatter, {
	descargarFileExpedienteMensajeria: function(cellvalue, options, rowdata) {
        var html = '';
        var nombre=rowdata.nombreArchivo;
        var idArchivo=rowdata.idArchivo;
        if (nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null){       
            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
              '<img class="vam" width="17" height="18" src="'+baseURL+'images/stickers.png">'+
            '</a>';
        }
        
        return html;
    }
});

$(function() {    
    
    $('#tabsConsMensajeriaExpediente').tabs();
    boton.closeDialog();
    coExCoMeEx.procesarGridMensajeria();
});

function fechaDenuncia(data){
    if(data == null){
          return "&nbsp;";
    }else{
          if(data == '-62135751600000'){
                 return '01-01-0001';
          }else{
                 if(typeof data == "string"){
                        return data;
                 }else{
                        return $.datepicker.formatDate('dd-mm-yy',new Date(data));
                 }
          }
    }
}


