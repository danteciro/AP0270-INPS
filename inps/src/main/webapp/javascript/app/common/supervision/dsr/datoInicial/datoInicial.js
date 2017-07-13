/**
 * Resumen
 * Objeto	: datoInicial.js
 * Descripción	: Registro de datos Iniciales para la Generacion de una Supervision
 * Fecha de Creación	: 17/08/2016
 * PR de Creación	: OSINE_SFS-791
 * Autor	: GMD
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * =====================================================================================================================================================================
 *OSINE791-RSIS05 |  17/08/2016   |   Zosimo Chaupis Santur      |     Crear la funcionalidad para registrar los datos iniciales de una supervisión de orden de supervisión DSR-CRITICIDAD de la casuística SUPERVISIÓN REALIZADA                           |
 * REQF-0022      |  22/08/2016   |   Jose Herrera Pajuelo       |     Crear la funcionalidad para consultar una supervisión realizada cuando se debe aprobar una orden de supervisión DSR-CRITICIDAD.
 *OSINE791-RSIS25 |08/09/2016     |	Alexander Vilca Narvaez  | Crear la funcionalidad para consultar el comentario de ejecución de medida ingresado para las infracciones identificadas DSR-CRITICIDAD
 *OSINE791-RSIS25 |09/09/2016     |	Alexander Vilca Narvaez  | Modificar la funcionalidad para el modo registro y consulta en los Tabs
 *OSINE791-RSIS04 | 05/10/2016    |   Zosimo Chaupis Santur      |     CREAR LA FUNCIONALIDAD REGISTRAR SUPERVISION DE UNA ORDEN DE SUPERVISION DSR-CRITICIDAD DE LA CASUISTICA SUPERVISION NO INICIADA                      |
 * OSINE_SFS-791  |  06/10/2016   |   Luis García Reyna          | Registrar Supervisión No Iniciada
 * OSINE791-RSIS36|  11/10/2016   |	Alexander Vilca Narvaez  | Modificar la funcionalidad para registrar los datos iniciales de supervisión de órdenes de levantamiento
 * OSINE791-RSIS44|  17/10/2016   |	Cristopher Paucar Torre  | Deshabilitar Pestaña de Datos Iniciales y Recepción Visita.
 **/

//common/supervision/dsr/datoInicial/datoInicial.js
//  OSINE791 - RSIS05 - Inicio -->
var iteracion;
var modo;
var coDatoInicial = {
    ValidarFechaSupervision: function() {
        var fechaSup = "";
        var HoraSup = "";
        fechaSup = $("#FechaInicioSup").val();
        HoraSup = $("#HoraInicioSup").val();
        fechaSup = fechaSup.trim();
        HoraSup = HoraSup.trim();
        if (typeof(fechaSup) != 'undefined' && fechaSup != null && typeof(HoraSup) != 'undefined' && HoraSup != null)
        {
            if (fechaSup.length > 1 && HoraSup.length > 1) {
                //$("#radioSiSupervision").attr('checked', 'checked');
                coDatoInicial.HabilitarFechas();
                $("#fechaInicioSP").val(fechaSup);
                $("#horaInicioSP").val(HoraSup);
                $('#fechaInicioSP,#horaInicioSP').attr('disabled', 'disabled');
                $("#validarTabs").val(1);
                /* OSINE791 – RSIS36 - Inicio */
                $("#btnObtenerFechaHoraSistema").css("display","none");
                /* OSINE791 – RSIS36 - Fin */
            } else {
                coSupeDsrSupe.OcultarTabs();
                coSupeDsrSupe.OcultarDivTabs();
                coDatoInicial.InhabilitarFechas();
                $("#validarTabs").val(0);
            }

        } else {
            coSupeDsrSupe.OcultarTabs();
            coSupeDsrSupe.OcultarDivTabs();
            coDatoInicial.InhabilitarFechas();
            $("#validarTabs").val(0);
        }
    },
    //  OSINE791 - RSIS04 - Inicio -->
    VerRadioSupervisionInicial: function() {
        //var codradioEstadoInicial = $("#EstadoResultadoSupervisionInicial").attr("codigo");
        var idradioEstadoInicial = $("#EstadoResultadoSupervisionInicial").val();
        var codradioEstadoInicial = $("#EstadoResultadoSupervisionInicial").attr("codigo");
        var modo = $("#modoSupervision").val();
        
        $('input[value=' + idradioEstadoInicial + ']').attr('checked', true);
        if(idradioEstadoInicial === ""){
            coDatoInicial.OcultarBotonSiguiente();
            coDatoInicial.OcultarBotonGuardarPorVerificar();            
        }
        if(codradioEstadoInicial == constant.codigoResultadoSupervision.supervisionInicialSi && modo == constant.modoSupervision.registro){
            $('#fechaInicioSP,#horaInicioSP').attr('disabled', 'disabled');
            $('#radioSiSupervision,#radioNoSupervision,#radioPorVerificar').attr('disabled', 'disabled');
            coDatoInicial.MostrarBotonSiguiente();
            coDatoInicial.OcultarBotonGuardarPorVerificar();    
        }
        
        if(codradioEstadoInicial == constant.codigoResultadoSupervision.supervisionInicialPorVerificar){
            $('#fechaInicioSP,#horaInicioSP').attr('disabled', 'disabled');
            //$('#radioPorVerificar').attr('disabled', 'disabled');    
            coDatoInicial.OcultarBotonSiguiente();
            coDatoInicial.OcultarBotonGuardarPorVerificar();            
            $('#DivbtnGuardarSupervisionDsrPorVerificar').attr('disabled', 'disabled');            
            $('#radioSiSupervision,#radioNoSupervision').removeAttr('disabled'); 
            coSupeDsrSupe.OcultarTabs();
            coSupeDsrSupe.OcultarDivTabs();
            $("#validarTabs").val(0);
            if(modo == constant.modoSupervision.consulta){
                $('#radioSiSupervision,#radioNoSupervision,#radioPorVerificar').attr('disabled','disabled');            
            }
        }
        
         if(codradioEstadoInicial == constant.codigoResultadoSupervision.supervisionInicialObstaculizada){
            $("#divInicioPrincipal").css("display",'none');            
            $("#DivradioObstaculizado").css("display",'inline');
            $('#fechaInicioSP,#horaInicioSP').attr('disabled', 'disabled');
            coDatoInicial.InhabilitarFechas();
            coDatoInicial.OcultarBotonSiguiente();
            coDatoInicial.OcultarBotonGuardarPorVerificar();  
            coDatoInicial.fxRegistraDatosInicialesObstaculizado.MostrarDatosFinalesSupervisionObstaculizado();
            coDatoInicial.fxRegistraDatosInicialesObstaculizado.ValidarBtnGenerarResultados();  
            coDatoInicial.fxRegistraDatosInicialesObstaculizado.ComprobarResultadoSupervisionInicialDsr();
            $('#fechaInicioSupObstaculizado,#horaInicioSupObstaculizado').attr('disabled', 'disabled');
            $('#DivbtnGuardarSupervisionDsrPorVerificar').attr('disabled', 'disabled');
            coSupeDsrSupe.OcultarTabs();
            coSupeDsrSupe.OcultarDivTabs();
            $("#validarTabs").val(0);
        }
        if(modo == constant.modoSupervision.consulta){
             $('#radioSiSupervision,#radioNoSupervision').attr('disabled','disabled');
        }
        
    },
    //  OSINE791 - RSIS04 - Fin -->
    //  OSINE791 - RSIS04 - Inicio -->
    HabilitarFechas: function() {
        $("#obligatorio").html("(*) Campos obligatorios");
        $("#divFrmDatosInicialSuper").show();
    },
    inHabilitarFechas: function() {
        $("#obligatorio").html("");
        $("#divFrmDatosInicialSuper").css("display","none");
    },
    OcultarRadioObstaculizado: function() {
        $("#radioObstaculizacionSupervision").css('display', 'none');
    },
    inhabilitarObstaculizado: function() {        
        $('#radioObstaculizacionSupervision').attr('disabled', 'disabled');
    },
    MostrarRadioObstaculizado: function() {
        $("#radioObstaculizacionSupervision").css('display', 'block');
    },
    habilitarObstaculizado: function() {
        $('#radioObstaculizacionSupervision').removeAttr('disabled');
    },
    //  OSINE791 - RSIS04 - Fin -->
    RegistrarFechaSistema: function() {
        coDatoInicial.HabilitarFechas();
        coDatoInicial.FechayHoraSistema();
    },
    RegistrarFechaSistemaSi: function() {
        coDatoInicial.RegistrarFechaSistema();            
        coDatoInicial.MostrarBotonSiguiente();
        coDatoInicial.OcultarBotonGuardarPorVerificar();
        /*OSINE_SFS-791 - RSIS 03 - Inicio */
        $("#fldstDatosFinalesSupervision").css('display','none');
        $("#fldstAdjuntosSupervision").css('display','none');
        $("#btnGenerarResultadosNo").css('display','none');
        /*OSINE_SFS-791 - RSIS 03 - Inicio */
        $("#radioPorVerificar").attr('checked', false);
        $("#radioSiSupervision").attr('checked', true);
    },
    //  OSINE791 - RSIS04 - Inicio -->
    RegistrarFechaSistemaPorVerificar: function() {
       // var idInicial = $("#idResultSuperInicial").val();
        //if (idInicial == '') {
        
            $("#fldstDatosFinalesSupervision").css('display','none');
            $("#fldstAdjuntosSupervision").css('display','none');
            $("#btnGenerarResultadosNo").css('display','none');
            coDatoInicial.RegistrarFechaSistema();
            coDatoInicial.OcultarBotonSiguiente();
            coDatoInicial.MostrarBotonGuardarPorVerificar();
            /*OSINE_SFS-791 - RSIS 03 - Inicio */
            /*OSINE_SFS-791 - RSIS 03 - Inicio */
       // }//else{
           // coDatoInicial.OcultarBotonSiguiente();
           // $("#fldstDatosFinalesSupervision").css('display', 'none');
            //$("#fechaInicioSP").css("display","block");
       // }
    },
    //  OSINE791 - RSIS04 - Fin -->
    
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    
    RegistrarFechaSistemaNo: function() {  
            $("#fldstDatosFinalesSupervision").css('display','');
            $('#fechaInicioSup,#horaInicioSup').attr('disabled', 'disabled');
            $('#fechaFinSup,#horaFinSup').attr('disabled', 'disabled');
            coDatoInicial.FechayHoraSistema();
            $("#btnGuardarDatosInicialSupervision").css('display', 'none');
            $("#DivbtnGuardarSupervisionDsrPorVerificar").css("display", "none")
            $("#divFrmDatosInicialSuper").css('display', 'none');
    },
    
    inicializaObjetosEventos: function () {
        $('#fldstDatosFinalesSupervision').puifieldset({toggleable: true, collapsed: true});
        $('#fldstAdjuntosSupervision').puifieldset({toggleable: true, collapsed: true});
        $('#btnGuardarDatosFinalesSupervision').click(
            function(){coDatoInicial.fxRegistraDatosIniciales.validaRegistroDI();}
        );

        $("#btnGenerarResultadosNo").css('display','none');

        $('#btnAbrirRegistrarArchivosAdjuntos').click(function() {
            common.abrirRegistroArchivosAdjuntos($('#idSupervision').val());
        });
                
        $("#fldstDatosFinalesSupervision").css('display','none');
        $("#fldstAdjuntosSupervision").css('display','none');
        $('#btnGenerarResultadosNo').click(
            function(){coDatoInicial.fxRegistraDatosIniciales.procesaGeneraResultadoSupervisionNo();}
        ); 
        
        if($('input[name=radioSupervision]:checked').attr('codigo')==constant.codigoResultadoSupervision.supervisionInicialNo){
            $("#fldstDatosFinalesSupervision").css('display','');
            $('#fechaInicioSup,#horaInicioSup').attr('disabled', 'disabled');
            $('#radioSiSupervision,#radioNoSupervision,#radioPorVerificar').attr('disabled', 'disabled'); 
            $('#fechaFinSup,#horaFinSup').attr('disabled', 'disabled');            
            $('#btnGuardarDatosInicialSupervision').css('display', 'none');
            $('#DivbtnGuardarSupervisionDsrPorVerificar').css("display", "none")
            $('#divFrmDatosInicialSuper').css('display', 'none');
            $("#tabRecepcionVisitaVer").css('display', 'none');
            $("#tabObligacionesVer").css('display', 'none');
            $("#tabOtrosIncumplientosVer").css('display', 'none');
            $("#tabEjecucionMedidaVer").css('display', 'none');
            $("#tabterminarSupervisionVer").css('display', 'none');
                        
            if($('input[name=radioSupervision]:checked').val()!=null && $('input[name=radioSupervision]:checked').val()!='' && $('#codidoResultadoSupervision').val()==constant.codigoResultadoSupervision.supervisionInicialNo){
                $("#fldstAdjuntosSupervision").css('display','');
                $("#btnGenerarResultadosNo").css('display','');
                coDatoInicial.procesarGridAdjuntarArchivosSupervision();
                
                $("#divGenerarResultadosDsrInicial").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: false,
                    height: "auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    closeText: "Cerrar"
                });
        
            }
            
        };
        
        if($('#codidoIdResultadoSupervision').val()==constant.codigoResultadoSupervision.supervisionNo){
            $('#slctMotivoNoSupervision,#txtCartaVisitaSupervision,#txtEspecificarSupervision,#txtComentariosSupervision').attr('disabled', 'disabled');
            $('#btnGuardarDatosFinalesSupervision').css("display", "none");
        };
        
        if($('#idResultadoSupervision').val()=='' && $('#modoSupervision').val()==constant.modoSupervision.consulta){
            $('#btnGenerarResultadosNo').css("display", "none");
        }else if($('#codidoIdResultadoSupervision').val()==constant.codigoResultadoSupervision.supervisionNo){
            $("#btnGenerarResultadosNo").val('Consultar Resultados');
        };
        
        $('#slctMotivoNoSupervision').change(function() {
            $('#txtEspecificarSupervision').val('');
            if ($("#slctMotivoNoSupervision option:selected").attr('especifica') == constant.motivoSupervision.especifica) {
                $('#divEspecificar').css('display', 'inline');
                $('#txtEspecificarSupervision').attr("validate", "[O]");
            }
            else {
                $('#divEspecificar').css('display', 'none');
                $('#txtEspecificarSupervision').removeAttr("validate");
            }
        });

                
    },
    
    fxRegistraDatosIniciales:{
        
        validaRegistroDI:function(){   
            if($('#divFrmDatosFinalesSupervision').validateAllForm("#divMensajeValidaFrmSupervision")){
                    var mensajeValidacion = '';
                    mensajeValidacion = coDatoInicial.validarDatosFinalesSupervision();
                    if (mensajeValidacion == '') {
                        confirmer.open("¿Confirma que desea registrar los datos finales de la supervisión?","coDatoInicial.fxRegistraDatosIniciales.procesaRegistroSupervisionNo()");          
                    }else {
                        mensajeGrowl('warn', mensajeValidacion);
                    }
            }else{
                mensajeGrowl("warn", "Se debe completar los campos obligatorios, corregir", "");
            }
        },
        
        procesaRegistroSupervisionNo: function(){
            loading.open();
            var fechaInicioSupervision = $('#fechaInicioSup').val() + " " + $('#horaInicioSup').val();
            $('#txtCartaVisitaSupervision').val($('#txtCartaVisitaSupervision').val().toUpperCase().trim());
            $('#txtComentariosSupervision').val($('#txtComentariosSupervision').val().toUpperCase().trim());
            $.ajax({
                url: baseURL + "pages/supervision/dsr/guardarDatosInicialesSupervision",
                type: 'post',
                async: false,
                data: {
                    idSupervision: $('#idSupervision').val(),
                    fechaInicio: fechaInicioSupervision,
                    cartaVisita: $('#txtCartaVisitaSupervision').val().toUpperCase(),
                    'motivoNoSupervision.idMotivoNoSupervision': $('#slctMotivoNoSupervision').val().toUpperCase(),
                    descripcionMtvoNoSuprvsn: $('#txtEspecificarSupervision').val().toUpperCase(),
                    observacion: $('#txtComentariosSupervision').val().toUpperCase(),
                    'resultadoSupervisionInicialDTO.idResultadosupervision': $('input[name=radioSupervision]:checked').val()
                },
                 success: function(data) {
                    loading.close();              
                    if(data.resultado=='0'){
                        mensajeGrowl("success", constant.confirm.save, "");
                        $('#fechaInicioSup,#horaInicioSup').attr('disabled', 'disabled');
                        $('#radioSiSupervision,#radioNoSupervision,#radioPorVerificar').attr('disabled', 'disabled');
                        $('input[name=radioSupervision]:checked').attr('checked',true);
                        $("#fldstAdjuntosSupervision").css('display','');
                        $("#btnGenerarResultadosNo").css('display','');
                        coDatoInicial.procesarGridAdjuntarArchivosSupervision();
                    }else{
                        mensajeGrowl("error", data.mensaje, "");
                    }    
                },
                error: errorAjax
            });
        },
        
        procesaGeneraResultadoSupervisionNo: function(){
            if($('#divFrmDatosFinalesSupervision').validateAllForm("#divMensajeValidaFrmSupervision")){
                if($('#txtComentariosSupervision').val().trim().length > 7){
                    if($("#idResultadoSupervision").val()==''){
                    	confirmer.open("¿Confirma que desea generar los resultados de la Supervisión?","coDatoInicial.fxRegistraDatosIniciales.generarResultadosSupervisionNo()");          
                    }else{
                        coDatoInicial.fxRegistraDatosIniciales.generarResultadosSupervisionNo();
                    }
                }else{
                    $('#txtComentariosSupervision').addClass('error');
                    mensajeGrowl("warn", "El comentario de la supervisión debe tener una longitud mayor o igual a 8 caracteres, corregir", "");
                }
            }else{
                mensajeGrowl("warn", "Se debe completar los campos obligatorios, corregir", "");
            }
        },
        
        generarResultadosSupervisionNo: function (){
            var flagSupervisionInicial = constant.codigoFlagSupervisionInicial.SupervisionNo;
            loading.open();
            $.ajax({
                url: baseURL + "pages/supervision/dsr/generarResultadoSupervision",
                type: 'post',
                async: false,
                data: {
                    idSupervision: $('#idSupervision').val(),
                    nroCarta: $('#txtCartaVisitaSupervision').val().toUpperCase(),
                    'motivoNoSupervision.idMotivoNoSupervision': $('#slctMotivoNoSupervision').val().toUpperCase(),
                    descripcionMtvoNoSuprvsn: $('#txtEspecificarSupervision').val().toUpperCase(),
                    observacion: $('#txtComentariosSupervision').val().toUpperCase(),
                    'resultadoSupervisionInicialDTO.idResultadosupervision': $('input[name=radioSupervision]:checked').val(),
                    'resultadoSupervisionInicialDTO.codigo' :$("input:radio[name='radioSupervision']:checked").attr("codigo"),
                    direccionOperativa : $('#DireccionOperativaObliDsrSup').val(),
                    idProvincia : $('#idProvinciaObliDsrSup').val(),
                    idDepartamento :  $('#idDepartamentoObliDsrSup').val(),
                    idDistrito :  $('#idDistritoObliDsrSup').val(),
                    flagSupervisionInicial: flagSupervisionInicial
                },
                 success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
//                        mensajeGrowl("success", constant.confirm.save, "");
                        var idSupervision = data.registroDocumentoSupervision.supervision.idSupervision;
                        var idTipoDocumento = data.registroDocumentoSupervision.idTipoDocumento.idMaestroColumna;
                        coDatoInicial.fxRegistraDatosIniciales.grillaGenerarResultadosInicial(idSupervision, idTipoDocumento);
                        $('#slctMotivoNoSupervision,#txtCartaVisitaSupervision,#txtEspecificarSupervision,#txtComentariosSupervision').attr('disabled', 'disabled');
                        $('#btnGuardarDatosFinalesSupervision').css("display", "none");
                        $('#divGenerarResultadosDsrInicial').dialog('open');
                        
                        if ($("#fechaTerminoRS").val() =='' || $("#fechaTerminoRS").val() != null ){     
                            if(data.supervision.fechaFin != null){
                                $("#fechaFinSup").val(data.supervision.fechaFin);
                            }                                
                        }
                        if ($("#horaTerminoRS").val() =='' || $("#horaTerminoRS").val() != null ){     
                            if(data.supervision.horaFin != null){
                                $("#horaFinSup").val(data.supervision.horaFin);
                            }                                
                        }                        
                        $("#idResultadoSupervision").val(data.supervision.resultadoSupervisionDTO.idResultadosupervision);
                        $("#btnGenerarResultadosNo").val('Consultar Resultados');
                        
                    } else {
                        mensajeGrowl("error", "Ocurrió un error al Generar Resultados, intente en otro momento.", "");
                    }
                },
                error: errorAjax
            });
        },
        
        grillaGenerarResultadosInicial:function(idSupervision, idTipoDocumento){
            var nombres = ['RESULTADO', 'DESCARGAR'];
            var columnas = [
               {name: "descripcionDocumento", width: 210, sortable: false, align: "center"},
               {name: "idDocumentoAdjunto", width: 200, sortable: false, align: "center", formatter: coDatoInicial.fxRegistraDatosInicialesObstaculizado.descargarGenerarResultadosIMGInicial}
            ];
           $("#gridContenedorGenerarResultadosDsrInicial").html("");
           var grid = $("<table>", {
               "id": "gridGenerarResultadosDsr"
           });
           var pager = $("<div>", {
               "id": "paginacionGenerarResultados"
           });
           $("#gridContenedorGenerarResultadosDsrInicial").append(grid).append(pager);

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
        }
        
    },
        
    validarDatosFinalesSupervision: function() {
        var mensajeValidacion = '';
        
        if($('#txtComentariosSupervision').val().trim() != '' && $('#txtComentariosSupervision').val().trim().length < 8 ){
            $('#txtComentariosSupervision').addClass('error');
            mensajeValidacion = "El comentario de la supervisión debe tener una longitud mayor o igual a 8 caracteres, corregir <br>";
            return mensajeValidacion;
            
        }
        
        if ($('#txtEspecificarSupervision').val().trim() != '' && $('#txtEspecificarSupervision').val().trim().length < 8 ) {
            $('#txtEspecificarSupervision').addClass('error');
            mensajeValidacion = "El dato especificar motivo no supervisi&oacute;n debe tener una longitud mayor o igual a 8 caracteres, corregir <br>";
            return mensajeValidacion;
            
        }
        
        return mensajeValidacion;
        
    },

    procesarGridAdjuntarArchivosSupervision:function() {
        
        var nombres = ['idDocumentoAdjunto','DESCRIPCI&Oacute;N','ARCHIVO','DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto",hidden: true},
//            {name: "idSupervision",hidden: false},
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "idDocumentoAdjunto",width: 60,sortable: false,align: "center",formatter: 'descargarDocumentoAdjunto'},
        ];
        
        $("#gridContenedorAdjuntarArchivosSupervision").html("");
        var grid = $("<table>", {
            "id": "gridAdjuntarArchivosSupervision"
        });
        var pager = $("<div>", {
            "id": "paginacionAdjuntarArchivosSupervision"
        });
        $("#gridContenedorAdjuntarArchivosSupervision").append(grid).append(pager);

        grid.jqGrid({
            url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
            datatype: "json",
            postData: {
            idSupervision:$('#idSupervision').val(),
            flagSoloAdjuntos : constant.estado.activo
            /*OSINE_SFS-791 - RSIS 03 - Inicio */
            /*codigoTipoDocumento: constant.codigoTipoDocumento.codigoTipoDocumentoGeneraResultado*/
            /*OSINE_SFS-791 - RSIS 03 - Fin */
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionAdjuntarArchivosSupervision",
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
                    $('#linkEliminarArcAdjSup').attr('onClick', 'coDatoInicial.eliminarDocumentoAdjuntoConf('+rowid+')');
            },
            loadComplete: function(data) {
                $('#contextMenuAdjuntarArchivosSupervision').parent().remove();
                $('#divContextMenuAdjuntarArchivosSupervision').html("<ul id='contextMenuAdjuntarArchivosSupervision'>"
                        + "<li> <a id='linkEliminarArcAdjSup' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
                        + "</ul>");
                $('#contextMenuAdjuntarArchivosSupervision').puicontextmenu({
                    target: $('#gridAdjuntarArchivosSupervision')
                });
                $('#contextMenuAdjuntarArchivosSupervision').parent().css('width','65px');
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
           
        });
    },
    
    eliminarDocumentoAdjuntoConf: function (idDocumentoAdjunto){
        confirmer.open('¿Confirma que desea eliminar el archivo?', 'coDatoInicial.suprimirDocumentoAdjunto("'+idDocumentoAdjunto+'")');
    },
    
    suprimirDocumentoAdjunto: function(idDocumentoAdjunto) {
        loading.open();
        var url = baseURL + "pages/archivo/eliminarPghDocumentoAdjunto";
        $.post(url, {
            idDocumentoAdjunto: idDocumentoAdjunto
        }, function(data) {
            loading.close();
            if (data.resultado == '0') {
                mensajeGrowl("success", constant.confirm.remove);
                coDatoInicial.procesarGridAdjuntarArchivosSupervision();
            }
            else if (data.resultado == '1') {
                mensajeGrowl('error', data.mensaje);
            }
        });
    },
             
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    
    //  OSINE791 - RSIS04 - Inicio -->
    OcultarDatosFinalesSupervisionObstaculizado: function() {
        $("#fldstDatosFinalesSupervisionObstaculizado").hide();
    },
    //  OSINE791 - RSIS04 - Fin -->
    MostrarDatosFinalesSupervision: function(){
        $("#fldstDatosFinalesSupervision").show();
        $('#fechaInicioSup,#horaInicioSup').attr('disabled', 'disabled');
        $('#fechaFinSup,#horaFinSup').attr('disabled', 'disabled');
        coDatoInicial.FechayHoraSistema();

    }, 
    
    //  OSINE791 - RSIS04 - Inicio -->
    inicializaObjetosEventosObstaculizado: function () {
        $('#fldstDatosFinalesSupervisionObstaculizado').puifieldset({toggleable: true, collapsed: true});
        $('#btnGuardarDatosFinalesSupervisionObstaculizado').click(
                function () {
                    coDatoInicial.fxRegistraDatosInicialesObstaculizado.validaRegistroSupervisionObstaculizada();
                }
        );
        $('#btnGenerarResultadoSupervisionObstaculizado').click(
                function () {
                    if ($('#idDepartamentoObliDsrSup').val() == '' || $('#idProvinciaObliDsrSup').val() == '' || $('#idDistritoObliDsrSup').val() == '') {
                        mensajeGrowl("error", "No se puede Generar Resultados.", "Unidad Supervisada no tiene datos de Ubigeo.");
                        return false;
                    } else {
                        coDatoInicial.fxRegistraDatosInicialesObstaculizado.SupInicialgenerarResultadosInicio();
                    }
                }
        );
        /* OSINE791 - RSIS04 - Inicio */
        $("#divGenerarResultadosDsrInicial").dialog({
            resizable: false,
            draggable: true,
            autoOpen: false,
            height: "auto",
            width: "auto",
            modal: true,
            dialogClass: 'dialog',
            closeText: "Cerrar"
        });
        /* OSINE791 - RSIS04 - Fin */


    },
    //  OSINE791 - RSIS04 - Fin -->
    
    //  OSINE791 - RSIS04 - Inicio -->
    fxRegistraDatosInicialesObstaculizado:{   
        SupInicialgenerarResultadosInicio: function () {
            var idEstado = $("#EstadoResultadoSupervision").val();
            var modo = $("#modoSupervision").val();
            if (idEstado == '' && modo == constant.modoSupervision.registro) {
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.validaRegistroSupervisionGenerarResultadosObstaculizado();
            } else {
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.generarResultadosSupervisionInicial();
            }
        },      
        
        RegistrarSupervisionPorVerificar: function () {
            var mensajeValidacion = '';
            var mensajeradio = coDatoInicial.validarRadiosObligatoriosSPInicial();
            if (mensajeradio == '') {
                if ($('#divFrmDatosInicialSuper').validateAllForm('#divMensajeValidaFrmInicialSupDsr')) {
                    mensajeValidacion = coDatoInicial.validarDatosInicialSupervision();
                    if (mensajeValidacion == '') {
                        confirmer.open('¿Confirma que desea registrar los datos Iniciales de la supervisi&oacute;n?', 'coDatoInicial.registrarDatosSupervisionDsr()');
                    } else {
                        mensajeGrowl('warn', mensajeValidacion);
                    }
                } else {
                    var mensajeObligatorio = "Se debe completar los campos obligatorios, corregir<br>";
                    mensajeGrowl('warn', mensajeObligatorio);
                }
            } else {
                mensajeGrowl('warn', mensajeradio);
            }
        },
        
        validaRegistroSupervisionGenerarResultadosObstaculizado: function () {
            if ($('#divFrmDatosFinalesSupervisionObstaculizado').validateAllForm("#divMensajeValidaFrmSupervision")) {
                if ($('#txtComentariosSupervisionObstaculizado').val().trim().length > 7) {
                    confirmer.open("¿Confirma que desea generar los resultados de la Supervisión?", "coDatoInicial.fxRegistraDatosInicialesObstaculizado.generarResultadosSupervisionInicial()");
                } else {
                    $('#txtComentariosSupervisionObstaculizado').addClass('error');
                    mensajeGrowl("warn", "El comentario de la obstaculización de la supervisión debe tener una longitud mayor o igual a 8 caracteres, corregir", "");
                }
            } else {
               // mensajeGrowl("warn", "Se debe completar los campos obligatorios, corregir", "");
            }
        },
        generarResultadosSupervisionInicial:function(){ 
            var nrocarta = $("#txtCartaVisitaSupervisionObstaculizado").val();
            var observaciones = $("#txtComentariosSupervisionObstaculizado").val();
            var idradioEstadoInicial = $("input:radio[name='radioSupervision']:checked").val();
            var codradioEstadoInicial = $("input:radio[name='radioSupervision']:checked").attr("codigo");
            var flagSupervisionInicial = constant.codigoFlagSupervisionInicial.SupervisionObstaculizado;
            loading.open();
            $.ajax({
                url: baseURL + "pages/supervision/dsr/generarResultadosDocSupervisionInicial",
                type: 'post',
                async: false,
                data: {
                    idSupervision : $('#idSupervision').val(),
                    nroCarta : nrocarta,
                    'resultadoSupervisionInicialDTO.idResultadosupervision' :idradioEstadoInicial,
                    'resultadoSupervisionInicialDTO.codigo' :codradioEstadoInicial,
                    observacion : observaciones,
                    direccionOperativa : $('#DireccionOperativaObliDsrSup').val(),
                    idProvincia : $('#idProvinciaObliDsrSup').val(),
                    idDepartamento :  $('#idDepartamentoObliDsrSup').val(),
                    idDistrito :  $('#idDistritoObliDsrSup').val(),
                    flagSupervisionInicial : flagSupervisionInicial
                },
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                         if(data.registroDocumentoEjecucionMedida!=null){
                            var idSupervision = data.registroDocumentoEjecucionMedida.supervision.idSupervision;
                            var idTipoDocumento = data.registroDocumentoEjecucionMedida.idTipoDocumento.idMaestroColumna;
                            coDatoInicial.fxRegistraDatosInicialesObstaculizado.grillaGenerarResultadosInicial(idSupervision, idTipoDocumento);
                            $('#divGenerarResultadosDsrInicial').dialog('open');
                            if ($("#fechaFinSupObstaculizado").val() =='' || $("#fechaFinSupObstaculizado").val() != null ){     
                                if(data.supervision.fechaFin != null){
                                    $("#fechaFinSupObstaculizado").val(data.supervision.fechaFin);
                                }                                
                            }
                            if ($("#horaFinSupObstaculizado").val() =='' || $("#horaFinSupObstaculizado").val() != null ){     
                                if(data.supervision.horaFin != null){
                                    $("#horaFinSupObstaculizado").val(data.supervision.horaFin);
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
                            coDatoInicial.fxRegistraDatosInicialesObstaculizado.ComprobarResultadoSupervisionInicialDsr();
                         }
                    } else {
                    	/*if(data.errorNroCarta!=null && data.errorNroCarta == constant.estado.activo)
                    		mensajeGrowl("error", data.mensaje, "");
                    	else */
                    		mensajeGrowl("error", "Ocurrió un error al Generar Resultados, intente en otro momento.", "");                    	
                    }
                },
                error: errorAjax
            });            
        },
        ComprobarResultadoSupervisionInicialDsr: function () {
            var idEstado = $("#EstadoResultadoSupervision").val();
            var modo = $("#modoSupervision").val();
            if (idEstado != '' && modo == constant.modoSupervision.registro) {
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.OcultarbtnGuardarObstaculizado();
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.bloquearDatosInicialesObstaculizacion();
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.ModificarPosicionBtnGenerarResultadosObstaculizadoInicial();
                $("#btnGenerarResultadoSupervisionObstaculizado").val('Consultar Resultados');
            }else{
                if (idEstado != '' && modo == constant.modoSupervision.consulta) {
                    $("#divbtnGenerarResultadosSupObstaculiza").css("display", "block");
                    coDatoInicial.fxRegistraDatosInicialesObstaculizado.OcultarbtnGuardarObstaculizado();
                    coDatoInicial.fxRegistraDatosInicialesObstaculizado.ModificarPosicionBtnGenerarResultadosObstaculizadoInicial();
                    $("#btnGenerarResultadoSupervisionObstaculizado").val('Consultar Resultados');
                }else{
                    if (idEstado == '' && modo == constant.modoSupervision.consulta) {
                        coDatoInicial.fxRegistraDatosInicialesObstaculizado.OcultarBtnGenerarResultadosObstaculizado();
                        coDatoInicial.fxRegistraDatosInicialesObstaculizado.OcultarbtnGuardarObstaculizado();
                    }
                }
            }
        },
        
        ModificarPosicionBtnGenerarResultadosObstaculizadoInicial: function () {
            $("#divbtnGenerarResultadosSupObstaculiza").css("margin-left", "0px");
            $("#divbtnGenerarResultadosSupObstaculiza").css("margin-top", "20px");
        },
        bloquearDatosInicialesObstaculizacion: function () {
            $("#txtCartaVisitaSupervisionObstaculizado").attr("disabled","disabled");
            $("#txtComentariosSupervisionObstaculizado").attr("disabled","disabled");
        },
        OcultarbtnGuardarObstaculizado: function () {
            $("#divbtnGuardarSupObstaculiza").css("display","none");
        },
        grillaGenerarResultadosInicial:function(idSupervision, idTipoDocumento){
            var nombres = ['RESULTADO', 'DESCARGAR'];
            var columnas = [
               {name: "descripcionDocumento", width: 210, sortable: false, align: "center"},
               {name: "idDocumentoAdjunto", width: 200, sortable: false, align: "center", formatter: coDatoInicial.fxRegistraDatosInicialesObstaculizado.descargarGenerarResultadosIMGInicial}
            ];
           $("#gridContenedorGenerarResultadosDsrInicial").html("");
           var grid = $("<table>", {
               "id": "gridGenerarResultadosDsr"
           });
           var pager = $("<div>", {
               "id": "paginacionGenerarResultados"
           });
           $("#gridContenedorGenerarResultadosDsrInicial").append(grid).append(pager);

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
        
        descargarGenerarResultadosIMGInicial: function(idDocumentoAdjunto){
            var editar = "";
            if (idDocumentoAdjunto != undefined && idDocumentoAdjunto != '') {
                editar = '<a class="link" href="' + baseURL + 'pages/archivo/descargaResultadoGeneradoDsr?idDocumentoAdjunto=' + idDocumentoAdjunto + '" target="_blank" >' +
                        '<img class="vam" width="17" height="18" src="' + baseURL + 'images/descarga.png">' +
                        '</a>';
            }
            return editar;
        },
        
        validaRegistroSupervisionObstaculizada:function(){   
            if($('#divFrmDatosFinalesSupervisionObstaculizado').validateAllForm("#divMensajeValidaFrmSupervision")){
                if($('#txtComentariosSupervisionObstaculizado').val().trim().length > 7){
                        confirmer.open("¿Confirma que desea registrar los datos de obstaculización de la supervisión?","coDatoInicial.fxRegistraDatosInicialesObstaculizado.procesaRegistroSupervisionObstaculizada()");          
                }else{
                    $('#txtComentariosSupervisionObstaculizado').addClass('error');
                    mensajeGrowl("warn", "El comentario de la obstaculización de la supervisión debe tener una longitud mayor o igual a 8 caracteres, corregir", "");
                }
            }else{
                //mensajeGrowl("warn", "Se debe completar los campos obligatorios, corregir", "");
            }
        },
        
        procesaRegistroSupervisionObstaculizada: function(){
            var motivoNoSuprvsn = '-1';
            var descripcionMtvoNoSuprvsn = '';            
            var fechaInicioSupervision = $('#fechaInicioSupObstaculizado').val() + " " + $('#horaInicioSupObstaculizado').val();
            var idradioEstadoInicial = $("input:radio[name='radioSupervision']:checked").val();
            var codradioEstadoInicial = $("input:radio[name='radioSupervision']:checked").attr("codigo");
            $('#txtComentariosSupervisionObstaculizado').val($('#txtComentariosSupervisionObstaculizado').val().toUpperCase().trim());
            $('#txtCartaVisitaSupervisionObstaculizado').val($('#txtCartaVisitaSupervisionObstaculizado').val().toUpperCase().trim());
            loading.open();
            $.ajax({
                url: baseURL + "pages/supervision/dsr/guardarDatosInicialSupervisionDsrObstaculizada",
                type: 'post',
                async: false,
                data: {
                    idSupervision: $('#idSupervision').val(),
                    fechaInicio: fechaInicioSupervision,
                    cartaVisita: $('#txtCartaVisitaSupervisionObstaculizado').val().toUpperCase(),
                    'motivoNoSupervision.idMotivoNoSupervision': motivoNoSuprvsn,
                    descripcionMtvoNoSuprvsn: descripcionMtvoNoSuprvsn,
                    observacion: $('#txtComentariosSupervisionObstaculizado').val().toUpperCase(),
                    'ordenServicioDTO.idOrdenServicio': $('#idOrdenServicioRS').val(),
                    'resultadoSupervisionInicialDTO.idResultadosupervision': idradioEstadoInicial,
                    'resultadoSupervisionInicialDTO.codigo': codradioEstadoInicial
                },
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        mensajeGrowl("success", constant.confirm.save, "");
                        coDatoInicial.fxRegistraDatosInicialesObstaculizado.ValidarBtnGenerarResultados();
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: errorAjax
            });
    
    
        },
        
        
        ValidarBtnGenerarResultados: function () {
            if (!$("#txtCartaVisitaSupervisionObstaculizado").val() == '' && !$("#txtComentariosSupervisionObstaculizado").val() == '') {
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.MostrarBtnGenerarResultados();
            } else {
                coDatoInicial.fxRegistraDatosInicialesObstaculizado.OcultarBtnGenerarResultadosObstaculizado();
            }
        },

        MostrarBtnGenerarResultados: function () {
            $("#divbtnGenerarResultadosSupObstaculiza").css("display", "block");
            coDatoInicial.fxRegistraDatosInicialesObstaculizado.ModificarPosicionBtnGuardarObstaculizado();
            coDatoInicial.fxRegistraDatosInicialesObstaculizado.ModificarPosicionBtnGenerarResultadosObstaculizado();
        },
        
        OcultarBtnGenerarResultadosObstaculizado: function () {
            $("#divbtnGenerarResultadosSupObstaculiza").css("display", "none");
        },

        ModificarPosicionBtnGuardarObstaculizado: function () {
            $("#divbtnGuardarSupObstaculiza").css("margin-left", "-130px");
        },
        ModificarPosicionBtnGenerarResultadosObstaculizado: function () {
            $("#divbtnGenerarResultadosSupObstaculiza").css("margin-left", "170px");
        },
        
        //  OSINE791 - RSIS04 - Inicio -->
        MostrarDatosFinalesSupervisionObstaculizado: function () {
            $("#fldstDatosFinalesSupervisionObstaculizado").show();
            $('#fechaInicioSup,#horaInicioSup').attr('disabled', 'disabled');
            $('#fechaFinSup,#horaFinSup').attr('disabled', 'disabled');
            //coDatoInicial.FechayHoraSistema();
        },
    //  OSINE791 - RSIS04 - Fin -->   
        
    },
    //  OSINE791 - RSIS04 - Fin -->
    //  OSINE791 - RSIS04 - Inicio -->
    OcultarBotonSiguiente: function() {
    //	OSINE791 - RSIS36 - Inicio -->
        var fechaSup = "";
        var HoraSup = "";
        fechaSup = $("#FechaInicioSup").val();
        HoraSup = $("#HoraInicioSup").val();
        fechaSup = fechaSup.trim();
        HoraSup = HoraSup.trim();
    	if ( $("#idIteracionOS").val() > constant.iteracion.primera &&   fechaSup.length > 1 && HoraSup.length > 1) {
    		   $("#btnGuardarDatosInicialSupervision").css('display', '');
    	}
    	else {
    		$("#btnGuardarDatosInicialSupervision").css('display', 'none');
    	}
    //	OSINE791 - RSIS36 - Fin -->
    },
    OcultarRadioSi: function() {
        $("#radioSiSupervision").css('display', 'none');
    },
    OcultarRadioNo: function() {
        $("#radioNoSupervision").css('display', 'none');
    },
    OcultarRadioPorVerificar: function() {
        $("#radioPorVerificar").css('display', 'none');
    },
    MostrarBotonSiguiente: function() {
    			$("#btnGuardarDatosInicialSupervision").css("display", "block")
    },
    MostrarBotonGuardarPorVerificar: function() {
        $("#DivbtnGuardarSupervisionDsrPorVerificar").css("display", "block")
    },
    OcultarBotonGuardarPorVerificar: function() {
        $("#DivbtnGuardarSupervisionDsrPorVerificar").css("display", "none")
    },       
    InhabilitarFechas: function() {
        $("#divFrmDatosInicialSuper").hide();
        $("#obligatorio").html();
    },
    //  OSINE791 - RSIS04 - Fin -->
    GenerarEspacios: function(cantidad) {
        var espacio = "";
        for (var i = 0; i < cantidad; i++) {
            espacio += "&nbsp;";
        }
        return espacio;
    },
    guardarDatosInicialSupervision: function() {
        var iteracion= $("#idIteracionOS").val();
    	if (iteracion==constant.iteracion.primera){
	        var mensajeValidacion = '';
	        var mensajeradio = coDatoInicial.validarRadiosObligatoriosSPInicial();
	        if (mensajeradio == '') {
	        	      if ($('#divFrmDatosInicialSuper').validateAllForm('#divMensajeValidaFrmInicialSupDsr')) {
	                      mensajeValidacion = coDatoInicial.validarDatosInicialSupervision();
	                      if (mensajeValidacion == '') {
	                          confirmer.open('¿Confirma que desea registrar los datos Iniciales de la supervisi&oacute;n?', 'coDatoInicial.registrarDatosSupervisionDsr()');
	                      }
	                      else {
	                          mensajeGrowl('warn', mensajeValidacion);
	                      }
	                  } else {
	                      var mensajeObligatorio = "Se debe completar los campos obligatorios, corregir<br>";
	                      mensajeGrowl('warn', mensajeObligatorio);
	                  }	            
	        } else {
	            mensajeGrowl('warn', mensajeradio);
	        }
    	}
	  	else if (iteracion>constant.iteracion.primera){
    		mensajeValidacion = coDatoInicial.validarDatosInicialSupervision();
                if (mensajeValidacion=='' ) {
                    confirmer.open('¿Confirma que desea registrar los datos Iniciales de la supervisi&oacute;n?', 'coDatoInicial.registrarDatosIteracionDsr()');
                } else {
                    var mensajeObligatorio = "Se debe completar los campos obligatorios, corregir<br>";
                    mensajeGrowl('warn', mensajeObligatorio); 
                }
    	}
    
    	
    },
    
    registrarDatosIteracionDsr: function() {
    	loading.open();
        var motivoNoSuprvsn = '-1';
        var fechaInicioSupervision = $('#fechaInicioSP').val() + " " + $('#horaInicioSP').val();
    	var descripcionMtvoNoSuprvsn = '';
           $.getJSON(baseURL + "pages/supervision/dsr/guardarDatosInicialSupervisionDsr",
            {
                idSupervision: $('#idSupervision').val(),
                fechaInicio: fechaInicioSupervision,
                'motivoNoSupervision.idMotivoNoSupervision': motivoNoSuprvsn,
                descripcionMtvoNoSuprvsn: descripcionMtvoNoSuprvsn,
                'ordenServicioDTO.idOrdenServicio': $('#idOrdenServicioRS').val()
            })
    	.done(function(data) {
    	     loading.close();
    		 if (data.resultado == '0') {  
    			 mensajeGrowl("success", constant.confirm.save);
    			 $("#validarTabs").val(1);
                 coSupeDsrSupe.VerTabsIteracion();
                 coSupeDsrSupe.MostrarTab(1, 0);
                 $("#btnObtenerFechaHoraSistema").css('display','none');
    		 } else {
    			 mensajeGrowl("error", data.mensaje, "");
    		 }    		
    	  })
          .fail(errorAjax);
    },    
    
    registrarDatosSupervisionDsr: function() {
        loading.open();
        var motivoNoSuprvsn = '-1';
        var descripcionMtvoNoSuprvsn = '';
        var fechaInicioSupervision = $('#fechaInicioSP').val() + " " + $('#horaInicioSP').val();
        var idradioEstadoInicial = $("input:radio[name='radioSupervision']:checked").val();
        var codradioEstadoInicial = $("input:radio[name='radioSupervision']:checked").attr("codigo");
        $.getJSON(baseURL + "pages/supervision/dsr/guardarDatosInicialSupervisionDsr",
            {
                idSupervision: $('#idSupervision').val(),
                fechaInicio: fechaInicioSupervision,
                'motivoNoSupervision.idMotivoNoSupervision': motivoNoSuprvsn,
                descripcionMtvoNoSuprvsn: descripcionMtvoNoSuprvsn,
                'ordenServicioDTO.idOrdenServicio': $('#idOrdenServicioRS').val(),
                'resultadoSupervisionInicialDTO.idResultadosupervision': idradioEstadoInicial,
                'resultadoSupervisionInicialDTO.codigo': codradioEstadoInicial
            })
            .done(function(data) {
                loading.close();
//            /* OSINE_SFS-480 - RSIS 13 - Inicio*/
//            if ($('#idSupervision').val() == null || $('#idSupervision').val() == '') {
//                $('#idSupervision').val(data.sup.idSupervision);
//                $('#idIteracionOS').val(data.sup.ordenServicioDTO.iteracion);
//                $('#idTipoAsignacion').val(data.sup.ordenServicioDTO.idTipoAsignacion);
//                $('#idOrdenServicioRS').val(data.sup.ordenServicioDTO.idOrdenServicio);
//                $('#fechaCreacionOS').val(data.sup.ordenServicioDTO.fechaHoraCreacionOS);
//
//                $('#flagSupervision').val(data.sup.flagSupervision);
//                $('#txtNumeroOrdenServicio').val(data.sup.ordenServicioDTO.numeroOrdenServicio);
//
//            }
//            /* OSINE_SFS-480 - RSIS 13 - Fin */
	            if (data.resultado == '0') {
	                mensajeGrowl("success", constant.confirm.save);
	                $("#EstadoResultadoSupervisionInicial").attr("codigo", codradioEstadoInicial);
	                $("#EstadoResultadoSupervisionInicial").val(idradioEstadoInicial);
	                $('input[value=' + idradioEstadoInicial + ']').attr('checked', true);                
	                if (codradioEstadoInicial == constant.codigoResultadoSupervision.supervisionInicialSi) {
	                    $('#fechaInicioSP,#horaInicioSP,#radioSiSupervision,#radioNoSupervision,#radioPorVerificar').attr('disabled', 'disabled');
	                    $("#radioPorVerificar").attr('checked', false);
	                    $("#radioNoSupervision").attr('checked', false);
	                    $("#radioSiSupervision").attr('checked', true);
	                    coSupeDsrSupe.VerTabs();
	                    coSupeDsrSupe.VerDivTabs()
	                    coSupeDsrOblOblLst.procesarGridOblLst();
	                    $("#validarTabs").val(1);
	                    $("#fldstPersonaAtiende").removeAttr("disabled");
	                    coSupeDsrSupe.MostrarTab(1, 0);
	                }
	                if (codradioEstadoInicial == constant.codigoResultadoSupervision.supervisionInicialPorVerificar) {
	                    $('#fechaInicioSP,#horaInicioSP').attr('disabled', 'disabled');
	                    $('#DivbtnGuardarSupervisionDsrPorVerificar').css('display', 'none');
	                    //$('#radioPorVerificar').attr('disabled', 'disabled');
	                }
	
	             } else {
		   			 mensajeGrowl("error", data.mensaje, "");
		   		 }    
            })
            .fail(errorAjax);

    },
    FechayHoraSistema: function() {
        coDatoInicial.cargarFechaSistema();
    },
    cargarFechaSistema: function() {
        $.getJSON(baseURL + "pages/supervision/dsr/obtenerFechayHoraSistema", {
            ajax: 'true'
        }, function(actual) {
            $("#fechaInicioSP").val(actual.fecha);
            //$("#horaInicioSP").val(actual.hora);
            $('#horaInicioSP').timepicker('setTime', actual.hora);
            /*OSINE_SFS-791 - RSIS 03 - Inicio */
            $("#fechaInicioSup").val(actual.fecha);
            //$("#horaInicioSP").val(actual.hora);
            $('#horaInicioSup').timepicker('setTime', actual.hora);
            /*OSINE_SFS-791 - RSIS 03 - Fin */
            
        });
    },
    //  OSINE791 - RSIS04 - Inicio -->
    validarRadiosObligatoriosSPInicial: function() {
        var mensajeValidacio = "";
        if (!($("#radioSiSupervision").is(':checked')) && !($("#radioNoSupervision").is(':checked')) && !($("#radioPorVerificar").is(':checked'))) {
            mensajeValidacio = "Se debe completar los campos obligatorios, corregir<br>";
            return mensajeValidacio;
        }
        return mensajeValidacio;
    },
    //  OSINE791 - RSIS04 - Fin -->
    convertTo12Hour: function(fecha) {
        var hours = fecha.getHours() == 0 ? "12" : fecha.getHours() > 12 ? fecha.getHours() - 12 : fecha.getHours();
        hours = (hours < 10 ? "0" : "") + hours;
        var minutes = (fecha.getMinutes() < 10 ? "0" : "") + fecha.getMinutes();
        var ampm = fecha.getHours() < 12 ? "AM" : "PM";
        var formattedTime = hours + ":" + minutes + " " + ampm;
        return formattedTime;
    },
    
    validarDatosInicialSupervision: function() {
        $('#fechaInicioSP,#horaInicioSP').removeClass('error');
        var mensajeValidacion = "";
        var time = $('#fechaCreacionOS').val().replace(' ', 'T');
        var fechaCreaOS = new Date(time);
        fechaCreaOS.setDate(fechaCreaOS.getDate());
        var fechaInicioSP = $("#fechaInicioSP").val();
        var horaInicioSP = $('#horaInicioSP').timepicker('getTimeAsDate');
        if (!esFecha(fechaInicioSP)) {
            $('#fechaInicioSP').addClass("error");
            return mensajeValidacion = "La fecha debe tener formato correcto, corregir";
        }
        if (!esHora($('#horaInicioSP').val())) {
            $('#horaInicioSP').addClass("error");
            return mensajeValidacion = "La hora debe tener formato correcto, corregir";
        }
        //validamos fecha creacion y la fecha de inicio
        var resultadoFechaCreaIni = comparaFecha(parseDate(fechaCreaOS), fechaInicioSP);
        if (resultadoFechaCreaIni == 1) {
            $('#fechaInicioSP').addClass("error");
            mensajeValidacion = "La fecha de inicio debe ser mayor que la fecha de creaci&oacute;n de orden de servicio, corregir <br>";
            return mensajeValidacion;
        } else if (resultadoFechaCreaIni == 0) {
            var resultadoHorasCreaIni = comparaHora(fechaCreaOS, horaInicioSP);
            if (resultadoHorasCreaIni > 0) {
                $('#horaInicioSP').addClass("error");
                mensajeValidacion = "La hora de inicio debe ser mayor que la hora de creaci&oacute;n de orden de servicio, corregir <br>";
                return mensajeValidacion;
            }
        }
        var resultadoFechaCreaHoy = comparaFecha(fechaInicioSP, obtenerFechaFormateada(new Date()));
        if (resultadoFechaCreaHoy == 1) {
            $('#fechaInicioSP').addClass("error");
            mensajeValidacion = "La fecha de inicio no debe ser mayor que la fecha del Sistema, corregir <br>";
            return mensajeValidacion;
        }
        return mensajeValidacion;

    },
            ValidarFechaInicialSupervision: function() { 
        var time = $('#fechaCreacionOS').val().replace(' ', 'T');
        var fechaCreaOS = new Date(time);
        fechaCreaOS.setDate(fechaCreaOS.getDate());
        $("#fechaInicioSP").datepicker("option", "minDate", fechaCreaOS);
    },
    /* OSINE791 - RSIS25 - Inicio */
    desactivaTab: function(){
        $('#tabsSupervision').tabs('disable', 1);//
        $('#tabsSupervision').tabs('disable', 2);//
        $('#tabsSupervision').tabs('disable', 3);//
        $('#tabsSupervision').tabs('disable', 4);//
        $('#tabsSupervision').tabs('disable', 5);//
    },
    activaTab: function(){
        $('#tabsSupervision').tabs('enable', 1);//
        $('#tabsSupervision').tabs('enable', 2);//
        $('#tabsSupervision').tabs('enable', 3);//
        $('#tabsSupervision').tabs('enable', 4);//
        $('#tabsSupervision').tabs('enable', 5);//
    },
    /* OSINE791 - RSIS25 - Fin */
    
    RegistrarFechaIteracion: function() {
        $.getJSON(baseURL + "pages/supervision/dsr/obtenerFechayHoraSistema", {
            ajax: 'true'
        }, function(actual) {
            $("#fechaInicioIteracion").val(actual.fecha);
            $("#horaInicioIteracion").val(actual.hora);
            //$('#horaInicioIteracion').timepicker('setTime', actual.hora);
        });
    }
}

/*OSINE_SFS-791 - RSIS 03 - Inicio */
jQuery.extend($.fn.fmatter, {
    
    eliminarDocumentoAdjunto:function (cellvalue, options, rowdata) {
        return editar = "<img src=\"" + baseURL + "/../images/delete_16.png\" style=\"cursor: pointer;\" alt=\"Eliminar Adjunto\" onclick=\"coDatoInicial.eliminarDocumentoAdjuntoConf('"+rowdata.idDocumentoAdjunto+"');\" />";
    },
    
    descargarDocumentoAdjunto: function(id, cellvalue, options, rowdata) {
        var html = "<a href='" + baseURL + "pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=" + id + "' target='_blank'>";
        html += "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
        html += "</a>";
        return html;
    }
});
/*OSINE_SFS-791 - RSIS 03 - Fin */

$(function() {
    boton.closeDialog();
    $('#fechaInicioSP').datepicker();
    $('#horaInicioSP').timepicker({
        showPeriod: true,
        showLeadingZero: true,
        hourText: 'Hora',
        minuteText: 'Minuto',
    });
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    $('#fechaInicioSup').datepicker();
    $('#horaInicioSup').timepicker({
        showPeriod: true,
        showLeadingZero: true,
        hourText: 'Hora',
        minuteText: 'Minuto',
    });
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    $('#fechaFinDI').datepicker();
    $('#horaFinDI').timepicker({
        showPeriod: true,
        showLeadingZero: true,
        hourText: 'Hora',
        minuteText: 'Minuto',
    });  
    
    //  OSINE791 - RSIS04 - Inicio -->
    $("#DivradioObstaculizado").css("display",'none');
    $("#fldstDatosFinalesSupervisionObstaculizado").css("display",'none');
    coDatoInicial.ValidarFechaSupervision();
    coDatoInicial.ValidarFechaInicialSupervision();      
    coDatoInicial.VerRadioSupervisionInicial();  
    //  OSINE791 - RSIS04 - Fin -->
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    coDatoInicial.inicializaObjetosEventos();
    coDatoInicial.inicializaObjetosEventosObstaculizado();
            
    /*OSINE_SFS-791 - RSIS 03 - Fin */
    
    /* OSINE791 - RSIS25 - Inicio */
    modo = $('#modoSupervision').val();
    if (modo==constant.modoSupervision.consulta){
    coDatoInicial.activaTab();
    }
    else{
    coDatoInicial.desactivaTab();
    }
    /* OSINE791 - RSIS25 - Inicio */
    $('#btnGuardarDatosInicialSupervision').click(function() {
        var auxiliar = $("#validarTabs").val();
        	if (auxiliar == 1) {
                  coSupeDsrSupe.MostrarTab(1, 0);
            } else {
                  coDatoInicial.guardarDatosInicialSupervision();
            }
    });
    /* OSINE791 - RSIS4 - Inicio */
    $('#btnGuardarDatosInicialSupervisionPorVerificar').click(function() {  
        coDatoInicial.fxRegistraDatosInicialesObstaculizado.RegistrarSupervisionPorVerificar();
    });
    /* OSINE791 – RSIS4 - Fin */
    /* OSINE791 – RSIS22 - Inicio */
    $('#btnSiguienteRecepcionVisita').click(function() {
        coSupeDsrSupe.MostrarTab(1, 0);
    });
    /* OSINE791 – RSIS22 - Fin */

    
    /* OSINE791 – RSIS36 - Inicio */
    //Consideracion 2da Iteracion
    /////////////////////////////
    if($('#idIteracionOS').val()>constant.iteracion.primera){
        $('#divFrmDatosInicialSuper').css('display','');
        coDatoInicial.HabilitarFechas();
        $('#btnObtenerFechaHoraSistema').click(function() {
            coDatoInicial.RegistrarFechaSistema();            
            coDatoInicial.MostrarBotonSiguiente();
        });
        
    }
    
    /* OSINE791 – RSIS36 - Fin */
    /* OSINE791 – RSIS44 - Inicio */
     if(modo == constant.modoSupervision.consulta){
        $("#btnObtenerFechaHoraSistema").css("display",'none');
     }
     /* OSINE791 – RSIS44 - Fin */
    
});

//  OSINE791 - RSIS05 - Fin -->
