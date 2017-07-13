/**
* Resumen		
* Objeto		: ejecucionMedida.js
* Descripción		: JavaScript donde se maneja las acciones de la pestaña ejecucionMedida.
* Fecha de Creación	: 29/08/2016
* PR de Creación	: OSINE_SFS-791
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-791  |  29/08/2016   |   Luis García Reyna          |     Registrar Ejecucion Medida
* OSINE791-RSIS25|  09/09/2016   |	Alexander Vilca Narvaez 	| Modificar la funcionalidad para el modo registro y consulta en los Tabs
* OSINE_MAN_DSR_0022|16/06/2017  |   Carlos Quijano Chavez::ADAPTER      |     Cambiar el tipo de Cierre automatico a manual al generar supervision

*    
*/

//common/supervision/dsr/ejecucionMedida/ejecucionMedida.js

var coSuDsrEmEm= {
    
    inhabilitarDatFinSup: function() {
        /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
        $("#seccCartaVisita").css('display','none');
        /* OSINE_SFS-791 - RSIS 16 - Fin */ 
    },
    /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
    registrarDatosFinales: function(){
        coSuDsrEmEm.habilitarDatFinSup();
//        if($("#fechaTerminoRS").val().toUpperCase().trim() == '' || $("#horaTerminoRS").val().toUpperCase().trim() == ''){
//            coSuDsrEmEm.fechayHoraSistema();
//        }
    },
    
    habilitarDatFinSup: function() {
        $("#seccCartaVisita").css('display','');
    },
    
    ValidarFechaTerminoSupervision: function() { 
        $("#fechaTerminoRS").datepicker("option", "minDate", $('#fechaInicioSP').val());
        $("#fechaTerminoRS").datepicker("option", "maxDate", new Date());
    },

    fxRegistraEM:{
        
//        validaRegistroEM:function(){ 

//                var mensajeValidacion = '';
//                if( $('#flagInfracciones').val()==1){
//                    if(!$('#fechaTerminoRS').val()=='' && !$('#horaTerminoRS').val()==''){
////                        mensajeValidacion = coSuDsrEmEm.fxRegistraEM.validarDatosFinalesSupervision();
//                        if (mensajeValidacion == '') {
//                            confirmer.open("¿Confirma que desea registrar los Datos Finales de la Supervisión?", "coSuDsrEmEm.fxRegistraEM.procesaRegistroEmOC()");            
//                        }else {
//                            mensajeGrowl('warn', mensajeValidacion);
//                        }
//                    }else{
//                        $('#fechaTerminoRS').addClass('error');
//                        $('#horaTerminoRS').addClass('error');
//                        mensajeGrowl("warn", "Se debe completar los campos obligatorios, corregir", "");
//                    }
////                }else if($('#frmEM').validateAllForm("#divMensajeValidaFrmEM") && $('#flagInfracciones').val()==0){
//                }else if($('#flagInfracciones').val()==0){
////                    mensajeValidacion = coSuDsrEmEm.fxRegistraEM.validarDatosFinalesSupervision();
//                    if (mensajeValidacion == '') {
//                        confirmer.open("¿Confirma que desea registrar los Datos Finales de la Supervisión?", "coSuDsrEmEm.fxRegistraEM.procesaRegistroEmOC()");            
//                    }else {
//                        mensajeGrowl('warn', mensajeValidacion);
//                    }
//                }      
//        },
        
//        validarDatosFinalesSupervision: function() {
//            coSuDsrEmEm.obtenerHoraSistema();
//            $('#fechaTerminoRS,#horaTerminoRS').removeClass('error');
//            var mensajeValidacion = "";
//            /* Validar Formato Fecha y Hora */
//            var fechaFinSupervision = $("#fechaTerminoRS").val();
//            if (!esFecha(fechaFinSupervision)) {
//                $('#fechaTerminoRS').addClass("error");
//                return mensajeValidacion = "La fecha debe tener formato correcto, corregir";
//            }
//            if (!esHora($('#horaTerminoRS').val())) {
//                $('#horaTerminoRS').addClass("error");
//                return mensajeValidacion = "La hora debe tener formato correcto, corregir";
//            }
//            
//            /* Validamos Fecha Inicio y la Fecha Fin -- Hora Inicio y Fin */
//            var fechaInicioSupervision = $("#fechaInicioSP").val();
//            var fechaFinSupervision = $("#fechaTerminoRS").val();
//            var timeInicioSupervision = $('#fechaInicioSP').val() + " " + $('#horaInicioSP').val();
//            var horaInicioSupervision = new Date(timeInicioSupervision)
//            var horaFinSupervision = $("#horaTerminoRS").timepicker('getTimeAsDate');
//            var horaActualSistema = $("#horaActualSistema").timepicker('getTimeAsDate');
//            
//            var resultadoFechaInicioFin = comparaFecha(fechaInicioSupervision, fechaFinSupervision);
//            if (resultadoFechaInicioFin == 1) {
//            $('#fechaTerminoRS').addClass("error");
//                mensajeValidacion = "La fecha de termino debe ser mayor que la fecha de inicio, corregir <br>";
//                return mensajeValidacion;
//            } else if (resultadoFechaInicioFin == 0) {
//                var resultadoHoras = comparaHora(horaInicioSupervision, horaFinSupervision);
//                if (resultadoHoras > 0) {
//                    $('#horaTerminoRS').addClass("error");
//                    mensajeValidacion = "La hora de fin debe ser mayor que la hora de inicio, corregir <br>";
//                    return mensajeValidacion;
//                }               
//            }
//            
//            /* Validamos Fecha Termino y la Fecha Actual -- Hora Termino y la Hora Actual */
//            var resultadoFechaTerminoHoy = comparaFecha(fechaFinSupervision, obtenerFechaFormateada(new Date()));
//            if (resultadoFechaTerminoHoy == 1) {
//                $('#fechaTerminoRS').addClass("error");
//                mensajeValidacion = "La fecha de termino no debe ser mayor que la fecha actual del Sistema, corregir <br>";
//                return mensajeValidacion;
//            } else if(resultadoFechaTerminoHoy == 0){
//                var resultadoHoraTerminoHoy = comparaHora(horaFinSupervision, horaActualSistema);
//                if (resultadoHoraTerminoHoy > 0) {
//                    $('#horaTerminoRS').addClass("error");
//                    mensajeValidacion = "La hora de termino debe ser menor que la hora actual del sistema, corregir <br>";
//                    return mensajeValidacion;
//                }
//            }
//            
//        return mensajeValidacion;    
//            
//        },
        
        procesaRegistroFlagEjecucionMedidaSi: function(){
            loading.open();
            $.getJSON( baseURL + "pages/supervision/dsr/registroEjecucionMedidaSupervision", 
            {              
                idSupervision : $('#idSupervision').val(),
                flagEjecucionMedida : $('input[name=radioEjecutaMedida]:checked').val()
            })
            .done(function(data) {
                loading.close();              
                if(data.resultado=='0'){
                    mensajeGrowl("success", constant.confirm.save, "");
                    coSuDsrEmEm.procesarGridFilesObligacionesIncumplidas($('#idSupervision').val(),'FilesObligacionIncumplidas');
//                    if($("#fechaTerminoRS").val().toUpperCase().trim() == '' || $("#horaTerminoRS").val().toUpperCase().trim() == ''){
//                        coSuDsrEmEm.fechayHoraSistema();
//                    }
                }else{
                    mensajeGrowl("error", data.mensaje, "");
                }      
            })
            .fail(errorAjax);
            
        },
        
        procesaRegistroFlagEjecucionMedidaNo:function(){
            var parameters = "_=p";
            parameters += "&idSupervision="+$('#idSupervision').val(); 
            parameters += "&flagEjecucionMedida="+$('input[name=radioEjecutaMedida]:checked').val(); 
            var lista = jQuery("#gridFilesObligacionIncumplidas").getDataIDs();
            for(i=0;i<lista.length;i++){
                rowData=jQuery("#gridFilesObligacionIncumplidas").getRowData(lista[i]); 
                parameters += "&listaDetalleSupervision["+i+"].idDetalleSupervision="+rowData.idDetalleSupervision;
                parameters += "&listaDetalleSupervision["+i+"].resultadoSupervision.idResultadosupervision="+rowData['resultadoSupervision.idResultadosupervision'];
                parameters += "&listaDetalleSupervision["+i+"].countEscIncumplido="+rowData.countEscIncumplido;           
            }
            
            $.ajax({
                url:baseURL + "pages/ejecucionMedida/eliminarComentarioEjecucionMedida",
                type:'post',
                async:false,
                data: parameters,
                beforeSend:loading.open,
                success:function(data){           
                    loading.close();
                    if(data.resultado=="0"){
                        mensajeGrowl("success", constant.confirm.save, "");
                        coSuDsrEmEm.procesarGridFilesObligacionesIncumplidas($('#idSupervision').val(),'FilesObligacionIncumplidas');
//                        if($("#fechaTerminoRS").val().toUpperCase().trim() == '' || $("#horaTerminoRS").val().toUpperCase().trim() == ''){
//                            coSuDsrEmEm.fechayHoraSistema();
//                        }
                    }else{
                        mensajeGrowl('error', data.mensaje, '');
                    }
                },
                error:errorAjax
            });
        },
            
        procesaRegistroEmOC:function(){
            loading.open();

            var fechaFinSupervision = $('#fechaTerminoRS').val() + " " + $('#horaTerminoRS').val();
            
            var cartaVisitaSupervision
            if ($("#cartaVisita").val() =='' || $("#cartaVisita").val() != null || $("#cartaVisita").val() != undefined){          
                cartaVisitaSupervision = $("#cartaVisita").val();
            }else {
                cartaVisitaSupervision = '';
            }
            
            $.getJSON( baseURL + "pages/supervision/dsr/registroEjecucionMedidaSupervision", 
            {              
                idSupervision : $('#idSupervision').val(),
                fechaFin: fechaFinSupervision,
                cartaVisita: cartaVisitaSupervision,
            })
            
            .done(function(data) {
                loading.close();              
                if(data.resultado=='0'){
                    mensajeGrowl("success", constant.confirm.save, "");
                    coSupeDsrSupe.MostrarTab(3,4); 
                }else{
                    mensajeGrowl("error", data.mensaje, "");
                }      
                if($('#cartaVisita').val() != undefined){
                    $("#datoCartaVisita").val($('#cartaVisita').val().toUpperCase()); 
                }
                $("#datoFechaTerminoRS").val($('#fechaTerminoRS').val().toUpperCase()); 
                $("#datoHoraTerminoRS").val($('#horaTerminoRS').val().toUpperCase()); 
            })
            .fail(errorAjax);
        }       
    },
    /* OSINE_SFS-791 - RSIS 16 - Fin */  
    /* OSINE791 - RSIS17 - Inicio */
    fxGenerarResultado:{
        generarResultado: function() {      
            var nroInfracciones = $("#flagInfracciones").val();
            if (nroInfracciones == 0) {                
                if ($('#frmEM').validateAllForm("#divMensajeValidaFrmEM")) {
                    coSuDsrEmEm.fxGenerarResultado.generarResultadosInicio();
                }
            } else {
                coSuDsrEmEm.fxGenerarResultado.generarResultadosInicio();
            }
            
        },
        generarResultadosInicio: function() {
            //var mensajeValidacion = coSuDsrEmEm.fxRegistraEM.validarDatosFinalesSupervision();
            //var mensajeValidacion = '';
            //if (mensajeValidacion == '') {
                var idEstado = $("#EstadoResultadoSupervision").val();
                var modo = $("#modoSupervision").val();
                if (idEstado == '' && modo == constant.modoSupervision.registro) {
                    confirmer.open("¿Confirma que desea generar los resultados de la Supervisión?", "coSuDsrEmEm.fxGenerarResultado.generarResultadosDocumento()");
                } else {
                    coSuDsrEmEm.fxGenerarResultado.generarResultadosDocumento();
                }

           // } else {
            //    mensajeGrowl('warn', mensajeValidacion);
            //}
        },
        
        generarResultadosDocumento:function(){
            var nrocarta;
            if ($("#cartaVisita").val() == '' || $("#cartaVisita").val() != null || $("#cartaVisita").val() != undefined) {
                nrocarta = $("#cartaVisita").val();
            } else {
                nrocarta = '';
                
            }
            
            var idntificardorjcucionmdida = "carta";
            if (($("#radioSiEjecMed").is(':checked'))) {
                idntificardorjcucionmdida = "siEjecMed";
            } else {
                if ($("#radioNoEjecMed").is(':checked')) {
                    idntificardorjcucionmdida = "NoEjecMed";
                }
            }
            var fechafin = $("#fechaTerminoRS").val();
            var horafin = $("#horaTerminoRS").val();
			/* OSINE_MAN_DSR_0022 - Inicio */
            var tipoCierre=$("#comboCierre").val();
            /* OSINE_MAN_DSR_0022 - Fin */
            console.log("fecha fin es : |"+fechafin+"| y hora |"+ horafin+"|");
            loading.open();
            $.ajax({
                url: baseURL + "pages/supervision/dsr/generarResultadosDocumento",
                type: 'post',
                async: false,
                data: {
                    idSupervision : $('#idSupervision').val(),
                    flagInfracciones: $('#flagInfracciones').val(),
                    flagObstaculizados: $('#datoFlagObstaculizados').val(),
                    nroCarta : nrocarta,
                    fechaFin : fechafin,
                    horaFin : horafin,
					/* OSINE_MAN_DSR_0022 - Inicio */
                    tipoCierre : tipoCierre,
                    /* OSINE_MAN_DSR_0022 - Fin */
                    direccionOperativa : $('#DireccionOperativaObliDsrSup').val(),
                    idDepartamento :  $('#idDepartamentoObliDsrSup').val(),
                    idProvincia : $('#idProvinciaObliDsrSup').val(),
                    idDistrito :  $('#idDistritoObliDsrSup').val(),
                    identificadorEjecucionMedi : idntificardorjcucionmdida
                },
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                         if(data.registroDocumentoEjecucionMedida!=null){
                            
                            var idSupervision = data.registroDocumentoEjecucionMedida.supervision.idSupervision;
                            var idTipoDocumento = data.registroDocumentoEjecucionMedida.idTipoDocumento.idMaestroColumna;
                            coSuDsrEmEm.fxGenerarResultado.grillaGenerarResultados(idSupervision, idTipoDocumento);
                            $('#divGenerarResultadosDsr').dialog('open');
                            
                            $("#fechaTerminoRSEM").val(data.supervision.fechaFin);            
                            //$("#datoFechaTerminoRS").val(data.supervision.fechaFin);
                           // if ($("#datoFechaTerminoRS").val() =='' || $("#datoFechaTerminoRS").val() != null ){     
                               // if(data.supervision.fechaFin != null){
                              //      $("#datoFechaTerminoRS").val(data.supervision.fechaFin);                                    
                              //      $("#fechaTerminoRS").val(data.supervision.fechaFin);                                    
                              //  }                                
                           // }
                            $("#horaTerminoRSEM").val(data.supervision.horaFin);
                            //$("#datoHoraTerminoRS").val(data.supervision.horaFin);
                            //if ($("#datoHoraTerminoRS").val() =='' || $("#datoHoraTerminoRS").val() != null ){     
                                //if(data.supervision.horaFin != null){
                                   // $("#datoHoraTerminoRS").val(data.supervision.horaFin);
                                   // $("#horaTerminoRS").val(data.supervision.horaFin);
                                    
                               // }                                
                            //}
                            if ($("#datoCartaVisita").val() =='' || $("#datoCartaVisita").val() != null){     
                                if(data.supervision.cartaVisita != null){
                                    $("#datoCartaVisita").val(data.supervision.cartaVisita);
                                }                                
                            }
                            if ($("#EstadoResultadoSupervision").val() =='' || $("#EstadoResultadoSupervision").val() != null ){  
                                if(data.supervision.resultadoSupervisionDTO.idResultadosupervision != null){
                                    $("#EstadoResultadoSupervision").val(data.supervision.resultadoSupervisionDTO.idResultadosupervision);
                                }
                                if(data.supervision.resultadoSupervisionDTO.codigo != null){
                                    $("#EstadoResultadoSupervision").attr("codigo",data.supervision.resultadoSupervisionDTO.codigo);
                                }
                            }
                            coSupeDsrSupe.ComprobarResultadoSupervisionDsr();
                        }
                        /* OSINE_SFS-791 - RSIS 24 - Inicio */ 
                    	coSuDsrEmEm.cargarFlagInfracciones();
                        coSuDsrEmEm.cargarFlagObstaculizados();
                        /* OSINE_SFS-791 - RSIS 24 - Fin */ 
                    } else {
                        //mensajeGrowl("error", data.mensaje, "");
                        mensajeGrowl("error", "Ocurrió un error al Generar Resultados, intente en otro momento.", "");
                    }
                },
                error: errorAjax
            });
        },
        
        grillaGenerarResultados:function(idSupervision, idTipoDocumento){
            var nombres = ['RESULTADO', 'DESCARGAR'];
            var columnas = [
               {name: "descripcionDocumento", width: 210, sortable: false, align: "center"},
               {name: "idDocumentoAdjunto", width: 200, sortable: false, align: "center", formatter: coSuDsrEmEm.fxGenerarResultado.descargarGenerarResultadosIMG}
            ];
           $("#gridContenedorGenerarResultadosDsr").html("");
           var grid = $("<table>", {
               "id": "gridGenerarResultadosDsr"
           });
           var pager = $("<div>", {
               "id": "paginacionGenerarResultados"
           });
           $("#gridContenedorGenerarResultadosDsr").append(grid).append(pager);

           grid.jqGrid({
               url: baseURL + "pages/supervision/dsr/generarResultadosDsr",
               datatype: "json",
               postData: {
            	   idSupervision: idSupervision,
            	   idTipoDocumento: idTipoDocumento
               },
               hidegrid: false,
               rowNum: constant.rowNumPrinc,
               pager: "#paginacionGenerarResultados",
               emptyrecords: "No se encontraron resultados",
               loadtext: "Cargando",
               colNames: nombres,
               colModel: columnas,
               height: "auto",
               width: "auto",
               viewrecords: true,
               caption: "",
               jsonReader: {
                   root: "filas",
                   page: "pagina",
                   total: "total",
                   records: "registros",
                   repeatitems: false,
                   id: "idDocumentoAdjunto"
               },
               onSelectRow: function(rowid, status) {
                   grid.resetSelection();
               },
               onRightClickRow: function(rowid) {
               },
               loadComplete: function(data) {
               },
               loadError: function(jqXHR) {
                   errorAjax(jqXHR);
               }
           });
        },
        
        descargarGenerarResultadosIMG: function(idDocumentoAdjunto){
            var editar = "";
            if (idDocumentoAdjunto != undefined && idDocumentoAdjunto != '') {
                editar = '<a class="link" href="' + baseURL + 'pages/archivo/descargaResultadoGeneradoDsr?idDocumentoAdjunto=' + idDocumentoAdjunto + '" target="_blank" >' +
                        '<img class="vam" width="17" height="18" src="' + baseURL + 'images/descarga.png">' +
                        '</a>';
            }
            return editar;
        },
        validarRadiosObligatoriosEjecucionMedida: function() {
            var mensajeValidacio = "";
            if (!($("#radioSiEjecMed").is(':checked')) && !($("#radioNoEjecMed").is(':checked'))) {
                mensajeValidacio = "Se debe completar los campos obligatorios, corregir<br>";
                return mensajeValidacio;
            }
			/* OSINE_MAN_DSR_0022 - Inicio */
            if ($("#comboCierre").val()=='') {
                mensajeValidacio = "Se debe seleccionar un Tipo de Cierre, corregir<br>";
                return mensajeValidacio;
            }
            /* OSINE_MAN_DSR_0022 - Fin */
            return mensajeValidacio;
        },
        validarComentarioEjecucionMedida:function(){
            mensaje="";
            if (($("#radioSiEjecMed").is(':checked'))) {
                $.ajax({
                    url:baseURL + "pages/detalleSupervision/validarComentarioEjecucionMedida",
                    type:'post',
                    async:false,
                    data:{  
                        idSupervision : $('#idSupervision').val(),
                        codigoResultadoSupervision: constant.codigoResultadoSupervision.incumple
                    },
                    beforeSend:loading.open,
                    success:function(data){
                        loading.close();
                        if (data.resultado == '1') {
                            mensaje=data.mensaje
                        }
                    },
                    error:errorAjax
                });
            }
            return mensaje;
        }
    },
    /* OSINE791 - RSIS17 - Fin */
    
    inicializaObjetosEventos:function(){
        /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
        $('#btnAnteriorOI').click(function(){
            
//            if ($("#cartaVisita").val() =='' || $("#cartaVisita").val() != null ){          
//                var cv = $("#cartaVisita").val().toUpperCase().trim();
//            }else if($("#cartaVisita").val() == undefined){
//                var cv = '';
//            }
//
//            var dcv = $('#datoCartaVisita').val().toUpperCase().trim();
//            var ft = $("#fechaTerminoRS").val().toUpperCase().trim();
//            var dft = $('#datoFechaTerminoRS').val().toUpperCase().trim();
//            var ht = $("#horaTerminoRS").val().toUpperCase().trim();
//            var dht = $('#datoHoraTerminoRS').val().toUpperCase().trim();
            
//            if((cv == '' && dcv == '' || dcv == cv) && (ft == '' && dft =='' || dft == ft) && (ht == '' && dht =='' || dht == ht)){
                coSupeDsrSupe.MostrarTab(3, 4);
//            }else if((cv != '' && dcv=='') || (ft != '' && dft=='') || (ht != '' && dht=='')){
//                coSuDsrEmEm.fxRegistraEM.validaRegistroEM();
//            }else if ((dcv != cv || cv == '') || (dft != ft || ft == '') || (dht != ht || ht == '')){
//                coSuDsrEmEm.fxRegistraEM.validaRegistroEM();
//            }     
            
        });
        /* OSINE_SFS-791 - RSIS 16 - Fin */ 
        /* OSINE791 - RSIS17 - Inicio */
        $('#btnGenerarResultados').click(function(){
            if($('#idDepartamentoObliDsrSup').val()=='' || $('#idProvinciaObliDsrSup').val()=='' || $('#idDistritoObliDsrSup').val()==''){
                mensajeGrowl("error", "No se puede Generar Resultados.", "Unidad Supervisada no tiene datos de Ubigeo.");
                return false;
            }
            
            if($("#EstadoResultadoSupervision").val() == null || $("#EstadoResultadoSupervision").val() == ''){
                coSuDsrEmEm.fechayHoraSistema();
            }
            var nroinfraccion = $("#flagInfracciones").val();
            if (nroinfraccion > 0) {
                var mensajeValidacion = '';
                mensajeValidacion = coSuDsrEmEm.fxGenerarResultado.validarRadiosObligatoriosEjecucionMedida();
                if (mensajeValidacion == '') {
                    //VALIDA COMENTARIO EJECUCION MEDIDA
                    var mensajeComentEjecMed = coSuDsrEmEm.fxGenerarResultado.validarComentarioEjecucionMedida();
                    if (mensajeComentEjecMed == '') {
                        coSuDsrEmEm.fxGenerarResultado.generarResultado();
                    } else {
                        mensajeGrowl('warn', mensajeComentEjecMed);
                    }
                } else {
                    mensajeGrowl('warn', mensajeValidacion);
                }
            } else {
                if (nroinfraccion == 0) {
                    coSuDsrEmEm.fxGenerarResultado.generarResultado();
                }
            }
        });
        $("#divGenerarResultadosDsr").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar"
        });
        /* OSINE791 - RSIS17 - Fin */
    },
    
    procesarGridEjeMedCons: function () {
        var nombres = ['IDDETALLESUPERVISION', 'PRIORIDAD', 'DESCRIPCIÓN DE LA INFRACCIÓN', 'CUMPLIMIENTO'];
        var columnas = [
            {name: "idDetalleSupervision", hidden: true},
            {name: "prioridad", hidden: false, align: "center", width: 15, },
            {name: "descripcionInfraccion", hidden: false},
            {name: "clasecss", width: 20, align: "center", formatter: 'VerImagen'}
        ];
        $("#gridContenedorEjecucionMedidaCons").html("");
        var grid = $("<table>", {
            "id": "gridEjecucionMedidaCons"
        });
        var pager = $("<div>", {
            "id": "paginacionEjecucionMedidaCons"
        });
        $("#gridContenedorEjecucionMedidaCons").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/supervision/dsr/findObligacionesListado",
            datatype: "json",
            mtype: "POST",
            postData: {
                idSupervision: $('#idSupervision').val(),
            },
            hidegrid: false,
            rowNum: constant.rowNumPrinc,
            pager: "#paginacionEjecucionMedidaCons",
            emptyrecords: "No se encontraron resultados",
            loadtext: "Cargando",
            colNames: nombres,
            colModel: columnas,
            height: "auto",
            viewrecords: true,
            width: 810,
            caption: "",
            jsonReader: {
                root: "filas",
                page: "pagina",
                total: "total",
                records: "registros",
                repeatitems: false,
                id: "prdd"
            },
            onSelectRow: function (rowid, status) {
                grid.resetSelection();
            },
            loadError: function (jqXHR) {
                errorAjax(jqXHR);
            }
        });

    },   
    /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
    procesarGridFilesObligacionesIncumplidas:function(idSupervision,nombreGrid){
        if(idSupervision!=null && idSupervision!=''){
            var ocultarEjecucionMedida = false;
            var flagEjecucionMedida = $('#radioSiEjecMed').is(":checked");
            if (!flagEjecucionMedida) {
                ocultarEjecucionMedida = true;
            }
            
            var nombres = ['IDDETALLESUPERVISION','IDINFRACCION','IDRESULTADODETSUPERVISION','PRIORIDAD', 'DESCRIPCIÓN DE LA INFRACCIÓN', 'COMENTARIO', 'EJEC. MEDIDA','countEscIncumplido'];
            var columnas = [

                {name: "idDetalleSupervision",index:'id',hidden:true},
                {name: "infraccion.idInfraccion",index:'idInfraccion',hidden:true},
                {name: "resultadoSupervision.idResultadosupervision",index:'idResultadosupervision',hidden:true},
                {name: "prioridad", width: 60, sortable: false, align: "center"},
                {name: "infraccion.descripcionInfraccion", width: 500, sortable: false, align: "left"},
                {name: "comentario", width: 80, sortable: false, align: "center", formatter: coSuDsrEmEm.comentarioInfraccionFormatter},
                {name: "comentario", width: 80, sortable: false, hidden:ocultarEjecucionMedida, align: "center", formatter: coSuDsrEmEm.comentarioDetalleSupervisionFormatter},
                {name: "countEscIncumplido", width: 10, sortable: false, hidden:true, align: "center"}
            ];

            $("#gridContenedor"+nombreGrid).html("");
            var grid = $("<table>", {"id": "grid"+nombreGrid});
            var pager = $("<div>", {"id": "paginacion"+nombreGrid});
            $("#gridContenedor"+nombreGrid).append(grid).append(pager);
            var codigo= constant.codigoResultadoSupervision.incumple;

            grid.jqGrid({
                url: baseURL + "pages/supervision/dsr/listarDetalleSupervision",
                datatype: "json",
                postData: {
                    idSupervision: idSupervision,
                    codigoResultadoSupervision: codigo
                },
                hidegrid: false,
                rowNum: constant.rowNumPrinc,
                pager: "#paginacion"+nombreGrid,
                emptyrecords: "No se encontraron resultados",
                loadtext: "Cargando",
                colNames: nombres,
                colModel: columnas,
                height: "auto",
                viewrecords: true,
                caption: "Descripción de la Infracción",
                jsonReader: {root: "filas",page: "pagina",total: "total",records: "registros",repeatitems: false,id: "idSupervision"},
                subGrid: true,
                onSelectRow: function(rowid, status) {
                    jQuery("#grid"+nombreGrid).resetSelection();
                },
                afterInsertRow: function(rowid, aData, rowelem) {
                    var rowData = grid.getRowData(rowid);
                    if (rowData["countEscIncumplido"] < 1) {
                        $('tr#' + rowid, grid)
                                .children("td.sgcollapsed")
                                .html("")
                                .removeClass('ui-sgcollapsed sgcollapsed');
                    }
                },

                subGridOptions: {"plusicon": "ui-icon-circle-plus","minusicon": "ui-icon-circle-minus","openicon": "ui-icon-arrowreturn-1-e","reloadOnExpand": false,"selectOnExpand": false},
                subGridRowExpanded: function(subgrid_id, row_id) { 

                    var dataDetalleSupervision = grid.getRowData(row_id);
                    var subgrid_table_id, pager_id;  

                    subgrid_table_id = subgrid_id + "_t";
                    pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
                    var nombres = ['IDESCENARIOINCUMPLIDO','IDDETALLESUPERVISION','IDESCENARIOINCUMPLIMIENTO','IDMAESCEINCUMAESTRO','ESCENARIO INCUMPLIMIENTO','COMENTARIO','EJEC. MEDIDA'];
                    var columnas = [ 
                        {name: "idEscenarioIncumplido",index:'idEido',hidden:true},
                        {name: "idDetalleSupervision",index:'idDs',hidden:true},
                        {name: "escenarioIncumplimientoDTO.idEsceIncumplimiento",index:'idEito',hidden:true},
                        {name: "escenarioIncumplimientoDTO.idEsceIncuMaestro.idMaestroColumna",index:'idEim',hidden:true},
                        {name: "escenarioIncumplimientoDTO.idEsceIncuMaestro.descripcion", width: 200, sortable: false, align: "left"}, 
                        {name: "comentario", width: 80, sortable: false, align: "center", formatter: coSuDsrEmEm.comentarioEscenarioIncumplimientoFormatter},
                        {name: "comentario", width: 80, hidden:ocultarEjecucionMedida, sortable: false, align: "center", formatter: coSuDsrEmEm.comentarioEscenarioIncumplidoFormatter}
                    ];

                    jQuery("#" + subgrid_table_id).jqGrid({

                        url : baseURL + "pages/supervision/dsr/findEscenarioIncumplido",		
                        datatype: "json",
                        postData: {
                            idDetalleSupervision:dataDetalleSupervision.idDetalleSupervision
                        },
                        hidegrid: false,
                        rowNum: constant.rowNumPrinc,
                        pager: pager_id,
                        emptyrecords: "No se encontraron resultados",
                        loadtext: "Cargando",
                        colNames: nombres,
                        colModel: columnas,
                        height: "auto",
                        viewrecords: true,
                        caption: "Escenario Incumplimiento",
                        autowidth: true,
                        jsonReader: {root: "filas", page: "pagina", total: "total", records: "registros",id:"idEscenarioIncumplido"},
                        onSelectRow: function(rowid, status) {
                            jQuery("#" + subgrid_table_id).resetSelection();
                        }
                    });             
                }  
            });
        }
    },
    
    comentarioDetalleSupervisionFormatter: function(cellvalue, options, rowdata) {
        var comentarioDetalleSupervision = rowdata["countEscIncumplido"];
        var html = '',icon='ui-icon-note';
        if (comentarioDetalleSupervision < 1 ){       
            html='<div class="ilb cp btnGrid" onclick="coSuDsrEmEm.abrirPopUpComentarioDetalleSupervision('+rowdata['idDetalleSupervision']+');">';
            if($("#EstadoResultadoSupervision").val() != '' && $("#modoSupervision").val() == constant.modoSupervision.registro){    
                var icon='ui-icon-search'; 
                html+='<span class="ui-icon '+icon+'"></span></div>'
            }else{
                html+='<span class="ui-icon '+icon+'"></span></div>'
            }            
        }
        return html;
    },
    
    abrirPopUpComentarioDetalleSupervision: function (idDetalleSupervision){
        var title="EJECUCIÓN MEDIDA";
        $.ajax({
            url:baseURL + "pages/ejecucionMedida/abrirComentarioDetalleSupervision",
            type:'get',
            async:false,
            data:{
                idDetalleSupervision:idDetalleSupervision
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogEjecucionMedidaComentario").html(data);
                $("#dialogEjecucionMedidaComentario").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    position: ['center', 'top+50'],
                    closeText: "Cerrar",
                    close:function(){
                        $("#dialogEjecucionMedidaComentario").dialog("destroy");
                    }
                });
                
            },
            error:errorAjax
        });        
    },
    
    comentarioEscenarioIncumplidoFormatter: function(cellvalue, options, rowdata) {
        var html = '',icon='ui-icon-note';      
            html='<div class="ilb cp btnGrid" onclick="coSuDsrEmEm.abrirPopUpComentarioEscenarioIncumplido('+rowdata['idEscenarioIncumplido']+');">';

        if($("#EstadoResultadoSupervision").val() != '' && $("#modoSupervision").val() == constant.modoSupervision.registro){    
            var icon='ui-icon-search'; 
            html+='<span class="ui-icon '+icon+'"></span></div>'
        }else{
            html+='<span class="ui-icon '+icon+'"></span></div>'
        }
        
        return html;
    },
    
    abrirPopUpComentarioEscenarioIncumplido: function (idEscenarioIncumplido){
        var title="EJECUCIÓN MEDIDA";
        $.ajax({
            url:baseURL + "pages/ejecucionMedida/abrirComentarioDetalleSupervision",
            type:'get',
            async:false,
            data:{
                idEscenarioIncumplido:idEscenarioIncumplido
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogEjecucionMedidaComentario").html(data);
                $("#dialogEjecucionMedidaComentario").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    position: ['center', 'top+50'],
                    closeText: "Cerrar",
                    close:function(){
                        $("#dialogEjecucionMedidaComentario").dialog("destroy");
                    }
                });
                
            },
            error:errorAjax
        });        
    },
    
    comentarioInfraccionFormatter: function(cellvalue, options, rowdata) {
        var comentarioInfraccion = rowdata["countEscIncumplido"];
        var icon='ui-icon-search',html = '';
        if (comentarioInfraccion < 1 ){       
            html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDet.abrirPopUpComentarioIncumplimiento(\'\','+rowdata.idDetalleSupervision+','+rowdata.infraccion.idInfraccion+',\''+constant.modoSupervision.consulta+'\');">';
            html+='<span class="ui-icon '+icon+'"></span></div>'
        }
        return html;
    },
    
    comentarioEscenarioIncumplimientoFormatter: function(cellvalue, options, rowdata) {
        var icon='ui-icon-search',html = '';
        html='<div class="ilb cp btnGrid" onclick="coSupeDsrOblOblDet.abrirPopUpComentarioIncumplimiento('+rowdata.escenarioIncumplimientoDTO.idEsceIncumplimiento+','+rowdata.idDetalleSupervision+',\'\',\''+constant.modoSupervision.consulta+'\');">';
        html+='<span class="ui-icon '+icon+'"></span></div>'
        return html;
    },
    
    /* OSINE_SFS-791 - RSIS 16 - Fin */ 
    
    verDetalle:function(valor) {
        coSupeDsrOblOblDet.mostrarDetalleObligacion(valor);
    },
    
    fechayHoraSistema: function() {
        $.getJSON(baseURL + "pages/supervision/dsr/obtenerFechayHoraSistema", {
            ajax: 'true'
        }, function(actual) {
            $("#fechaTerminoRS").val(actual.fecha);
            $("#horaTerminoRS").val(actual.hora);
            $("#horaActualSistema").val(actual.hora);
        });
    },
    
    obtenerHoraSistema: function() {
        $.getJSON(baseURL + "pages/supervision/dsr/obtenerFechayHoraSistema", {
            ajax: 'true'
        }, function(actual) {
            $("#horaActualSistema").val(actual.hora);
        });
    },
    
    cargarFlagInfracciones : function (){
        $('#flagInfracciones').val("");
	$.ajax({
            url:baseURL + "pages/detalleSupervision/findDetalleSupervision",
            type:'post',
            async:false,
            data:{  
                idOrdenServicio: $('#idOrdenServicioRS').val(),  
                codigoResultadoSupervision: constant.codigoResultadoSupervision.incumple
            },
            success:function(data){
                if(data!=null && data!=undefined){
                    if(data.length>0){
                        $('#flagInfracciones').val(constant.estado.activo);
                    }else{
                        $('#flagInfracciones').val(constant.estado.inactivo);
                    }
                }
                coSuDsrEmEm.evaluaFlagInfracciones();
            },
            error:errorAjax
	});
    },
    cargarFlagObstaculizados : function (){
        $('#datoFlagObstaculizados').val("");
	$.ajax({
            url:baseURL + "pages/detalleSupervision/findResultadoSupervision",
            type:'post',
            async:false,
            data:{  
                idSupervision : $('#idSupervision').val(),  
                codigoResultadoSupervision: constant.codigoResultadoSupervision.obstaculizado
            },
            success:function(data){
                if(data!=null){
                    $('#datoFlagObstaculizados').val(data.CantidadResultadoSupervision);
                }
            },
            error:errorAjax
	});
    },
    evaluaFlagInfracciones:function(){
        $('#seccChkEM,#seccGridDetaSupIncum,#seccMsjNoInfrac').css('display','none');
        
        if($('#flagInfracciones').val()==constant.estado.activo || $('#modoSupervision').val()==constant.modoSupervision.consulta){
            $('#seccChkEM').css('display','');
        }
        if($('#flagInfracciones').val()==constant.estado.activo && $('#modoSupervision').val()==constant.modoSupervision.registro){
                $('#seccGridDetaSupIncum').css('display','');
        }
        
        if($('#flagInfracciones').val()==constant.estado.inactivo){
                $('#seccMsjNoInfrac,#seccCartaVisita').css('display','');
        }
        /* OSINE_SFS-791 - RSIS 16 - Inicio */ 
        if($("#flagInfracciones").val()==1){
            coSuDsrEmEm.inhabilitarDatFinSup();  
        }else if($("#flagInfracciones").val()==0){
            coSuDsrEmEm.habilitarDatFinSup();            
        }
        /* OSINE_SFS-791 - RSIS 16 - Fin */
        if($('#modoSupervision').val()==constant.modoSupervision.registro){
//            if($("#fechaTerminoRS").val().toUpperCase().trim() == '' || $("#horaTerminoRS").val().toUpperCase().trim() == ''){
//                coSuDsrEmEm.fechayHoraSistema();
//            }
            coSuDsrEmEm.procesarGridFilesObligacionesIncumplidas($('#idSupervision').val(),'FilesObligacionIncumplidas');
        }
    }
};
    
jQuery.extend($.fn.fmatter, {
    VerImagen: function(cellvalue, options, rowdata) {
        var valor = (rowdata.idDetalleSupervision != null)?rowdata.idDetalleSupervision:"";
        var imgestilo = (rowdata.clasecss != null)?rowdata.clasecss:"";
        var html = '<div title="'+rowdata.descripcionResSup+'" onclick="coSuDsrEmEm.verDetalle(' + valor + ')" class="ilb cp btnGrid"><span class="ui-icon '+imgestilo+'"></span></div>'
        return html;
    }
});

$(function() {
    $('#fechaTerminoRS').datepicker();
    $('#horaTerminoRS').timepicker({
        showPeriod: true,
        showLeadingZero: true,
        hourText: 'Hora',
        minuteText: 'Minuto',
    });
    $('#horaActualSistema').timepicker({
        showPeriod: true,
        showLeadingZero: true,
        hourText: 'Hora',
        minuteText: 'Minuto',
    });
    coSuDsrEmEm.ValidarFechaTerminoSupervision();
    coSuDsrEmEm.inicializaObjetosEventos();
    
    //modo consulta
    if($('#modoSupervision').val()==constant.modoSupervision.consulta){
        coSuDsrEmEm.cargarFlagInfracciones();
        coSuDsrEmEm.cargarFlagObstaculizados();
         
    }
    if($('#modoSupervision').val()==constant.modoSupervision.consulta){
        coSuDsrEmEm.procesarGridEjeMedCons();
        $('#btnAnteriorRecepcionVisitaCons').click(function () {
			/* OSINE791 - RSIS25 - Inicio */
            coSupeDsrSupe.MostrarTab(2, 1);
            /* OSINE791 - RSIS25 - Fin */
            $('#tabsSupervision').tabs('disable', 2);
        }); 
    } 
    boton.closeDialog();
});
