/**
 * Resumen		
 * Objeto			 : ordenServicioMasivo.js
 * Descripción		 : JavaScript donde se centraliza la generación masiva de OS, gerencia GSM.
 * Fecha de Creación : 25/10/2016.
 * PR de Creación	 : OSINE_SFS-1344.
 * Autor			 : Hernán Torres.
 * =====================================================================================================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                         |     Descripción
 * =====================================================================================================================================================================
 * 
 */

var coOrSeOrSeMaGSM = {
    armaValidateFormOS: function() {
        $('#frmOSMAS').find('input, select, textarea').removeAttr('validate');
        switch ($('#tipoAccionOSMAS').val()) {
            case constant.accionOS.generar:
                $(
                        '#slctIdFlujoSigedOSMAS,#txtAsuntoSigedOSMAS,#cmbTipoAsigOSMAS,#slctTipoEmprSupeOSMAS,#cmbTipoSupervisionOSMAS,#slctEmprSupeOSMAS,#nroDocumentoOS,#slctIdProcesoOSMAS')
                        .attr('validate', '[O]');
                break;
            default:
        }
    },
    fxGenerar: {
        validaGenerarOS: function() {
            if ($('#frmOSMAS').validateAllForm("#divMensajeValidaFrmOSMAS")) {
                if ($('#unidOperativaSeleccionadaMAS').html() != "") {
                    confirmer.open(
                            "¿Confirma que desea generar una Orden de Servicio?",
                            "coOrSeOrSeMaGSM.fxGenerar.procesaGenerarOrdenServicio()");
                } else {
                    mensajeGrowl("warn", "Debe seleccionar Unidad Operativa", "");
                }
            }
        },
        procesaGenerarOrdenServicio: function() {
            coOrSeOrSeMaGSM.armaFormOrdenServicioMasiva();
            $.ajax({
                url: baseURL + "pages/expediente/generarExpedienteOrdenServicioMasivo",
                type: 'post',
                async: false,
                dataType: 'json',
                data: $('#frmOSMAS').serialize(),
                beforeSend: loading.open(),
                success: function(data) {
                    loading.close();
                    if (data.resultado == '0') {
                        $("#dialogOrdenServicioMasivo").dialog('close');
                        confirmer.open(""
                                + data.msgOrdSerCreada
                                + data.ordSerCreadaConcatenada
                                + data.msgOrdSerNoCreada
                                + data.msgNoGenExpSiged
                                + data.ordSerNoGenExpSigedConcatenada
                                + data.msgNoReeSiged
                                + data.ordSerNoReeSigedConcatenada
                                + data.msgNoIdPerSiged
                                + data.ordSerNoIdPerSigedConcatenada
                                + "", "", {textAceptar: '', textCancelar: 'Cerrar', title: 'Orden de Servicio'});
                        // recargar grillas involucradas
                        espeAsigAsig.procesarGridAsignacion();
                        espeNotiArchNotiArch.procesarGridArchivadoNotificado();// TEMPORAL// pq// el// expediente// desparece// al// confirmar// descargo,// pero// luego// reaparece// al// asignar// os
                    } else {
                        mensajeGrowl("error", data.mensaje, "");
                    }
                },
                error: errorAjax
            });

        },
        
        validarQuitarUO: function(rowid, nombreGrid) {
            var row = $('#grid' + nombreGrid).getRowData(rowid);
            confirmer.open("¿Ud. Está seguro de quitar la Unidad Operativa?", "coOrSeOrSeMaGSM.fxGenerar.quitarUO('" + rowid + "','" + nombreGrid + "')");
        },
        quitarUO: function(rowid, nombreGrid) {
            var row = $('#grid' + nombreGrid).getRowData(rowid);
            $.ajax({
                url: baseURL + "pages/unidadSupervisada/quitarUO",
                type: 'post',
                async: false,
                data: {
                    idUnidadSupervisada: rowid,
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    coOrSeOrSeMaGSM.fxGenerar.buscarUnidadOperativa('0');
                    $('#idUnidSupSele' + rowid).remove();
                    mensajeGrowl("success", data.mensaje, "");
                    //$('#divAnularArchivo').css('display','inline-block');
                },
                error: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        },
        buscarUnidadOperativa: function(flagBusqueda) {
            var postDataEnvio = {
                registroHidrocarburos: $('#txtNumeroRHUnidad').val() == null || $('#txtNumeroRHUnidad').val().trim() == '' ? '' : $('#txtNumeroRHUnidad').val(),
                cadCodigoOsinerg: $('#txtCadCodigoOsiBusqUnidad').val(),
                razonSocial: $('#txtRazoSociBusqUnidad').val(),
                idDepartamento: $('#slctDepartamentoUnidad').val() == null || $('#slctDepartamentoUnidad').val().trim() == '' ? '' : $('#slctDepartamentoUnidad').val(),
                idProvincia: $('#slctProvinciaUnidad').val() == null || $('#slctProvinciaUnidad').val().trim() == '' ? '' : $('#slctProvinciaUnidad').val(),
                idDistrito: $('#slctDistritoUnidad').val() == null || $('#slctDistritoUnidad').val().trim() == '' ? '' : $('#slctDistritoUnidad').val(),
                flagBusqueda: flagBusqueda,
                flagEsUnidadMuestral: $("#flagEsUnidadMuestral").val(),
                idObligacionSubTipo: $('#cmbSubTipoSupervisionOSMAS').val()
            };

            var nombres = ['idUnidadSupervisada', 'C&Oacute;DIGO OSINERGMIN', 'NRO. REGISTRO HIDROCARBURO', 'RUBRO',
                'DIRECCION', 'ID EMPRESA SUPERVISADA', 'DEPARTAMENTO', 'PROVINCIA', 'DISTRITO', 'ID ACTIVIDAD', 'TIPO - NUMERO DE DOCUMENTO', 'NOMBRE UNIDAD', 'RAZ&Oacute;N SOCIAL'
                        , 'TIPO SELECCION', '', ''];
            var columnas = [
                {name: "idUnidadSupervisada", hidden: true},
                {name: "codigoOsinergmin", width: 70, hidden: false},
                {name: "nroRegistroHidrocarburo", hidden: false},
                {name: "nombreActividad", width: 150},
                {name: "direccion", width: 160},
                {name: "empresaSup.idEmpresaSupervisada", hidden: true},
                {name: "direccionUnidadsupervisada.departamento.idDepartamento", hidden: true},
                {name: "direccionUnidadsupervisada.provincia.idProvincia", hidden: true},
                {name: "direccionUnidadsupervisada.distrito.idDistrito", hidden: true},
                {name: "idActividad", hidden: true},
                {name: "nroDocumentoCompleto", width: 100},
                {name: "nombreUnidad", width: 210, sortable: false, align: "left", hidden: true},
                {name: "empresaSup.razonSocial", width: 210, sortable: false, align: "left"},
                {name: "tipoSeleccion", width: 50, sortable: false, align: "center"},
                {name: 'check', width: 21, sortable: false, align: "center", formatter: "checkboxSelecUnidadSupervisada"},
                {name: 'flagMuestral', hidden: true}
            ];
            $("#gridContenedorUnidadOperativaMAS").html("");
            var grid = $("<table>", {
                "id": "gridUnidadesOperativas"
            });
            var pager = $("<div>", {
                "id": "paginacionUnidadesOperativas"
            });
            $("#gridContenedorUnidadOperativaMAS").append(grid).append(pager);

            grid.jqGrid({
                url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativaMasivaGrid",
                datatype: "json",
                mtype: "POST",
                postData: postDataEnvio,
                hidegrid: false,
                rowNum: constant.rowNumPrinc,
                pager: "#paginacionUnidadesOperativas",
                emptyrecords: "No se encontraron resultados",
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
                    id: "idUnidadSupervisada"
                },
                onSelectRow: function(rowid, status) {
                    grid.resetSelection();
                },
                onRightClickRow: function(rowid) {
                    var row = $('#gridUnidadesOperativas').getRowData(rowid);
                    $('#contextMenuUnidadOperativa').parent().css('opacity', '1');
                    $('#linkQuitar').css('display', '')
                    $('#linkQuitar').attr('onClick', 'coOrSeOrSeMaGSM.fxGenerar.validarQuitarUO("' + rowid + '","gridUnidadesOperativas")');
                },
                loadComplete: function(data) {
                    //agregar check seleccionar todos
                    var html = '<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="coOrSeOrSeMaGSM.selecAllOrdenServicioVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
                    $('#gridUnidadesOperativas_check').html(html);
                    $('#gridUnidadesOperativas_check').css('height', '20px');
                    $('#gridUnidadesOperativas_check').removeClass('ui-jqgrid-sortable');

                    //para marcar los ya seleccionados, casos de navegar entre paginas de grilla
                    $('#unidOperativaSeleccionadaMAS').find('div').map(function() {
                        $('#idUnidSup' + $(this).attr('id').replace('idUnidSupSele', '')).attr('checked', true);
                    });
                    // Para la opción de Quitar UO de la lista de Sesión.
                    $('#contextMenuUnidadOperativa').parent().remove();
                    $('#divContextMenuUnidadOperativaMAS').html("<ul id='contextMenuUnidadOperativa'>"
                            + "<li> <a id='linkQuitar' data-icon='ui-icon-trash' title='Quitar'>Quitar</a></li>"
                            + "</ul>");
                    $('#contextMenuUnidadOperativa').puicontextmenu({
                        target: $('#gridUnidadesOperativas')
                    });
                },
                loadError: function(jqXHR) {
                    errorAjax(jqXHR);
                }
            });
        },
        
        procBuscarTitularMineroOS: function(nombreGrid) {
        	$('#txtCodigoTitularMinero').removeClass("error");
            if ($('#txtCodigoTitularMinero').val() == '') {
                $('#txtCodigoTitularMinero').addClass("error");
                return false;
            } else {
	            var nombres = ['idDmUnidadSupervisada','idTitularMinero', 
	                           'C&Oacute;D. TITULAR MINERO', 'NOMBRE TITULAR MINERO', 
	                           'idEstrato.idMaestroColumna','ESTRATO',
	                           'C&Oacute;D. UNIDAD SUPERVISADA', 'NOMBRE UNIDAD SUPERVISADA', 
	                           'idTipoMinado.idMaestroColumna', 'TIPO MINADO', 
	                           'idActividad.idActividad', 'RUBRO', 'SELECCIONAR'];
	            var columnas = [
	                {name: "idDmUnidadSupervisada", hidden: true},
	                {name: "idTitularMinero.idTitularMinero", hidden: true},
	                
	                {name: "idTitularMinero.codigoTitularMinero", width: 70, align: "center",hidden: false},
	                {name: "idTitularMinero.nombreTitularMinero", width: 250,hidden: false, align: "center"},
	                
	                {name: "idEstrato.idMaestroColumna", hidden: true},
	                {name: "idEstrato.descripcion", align: "center", width: 95, hidden: false},
	                
	                {name: "codigoUnidadSupervisada", width: 80, align: "center"},
	                {name: "nombreUnidadSupervisada", width: 200, align: "center"},
	                
	                {name: "idTipoMinado.idMaestroColumna", hidden: true},
	                {name: "idTipoMinado.descripcion", width: 90, align: "center"},
	                
	                {name: "idActividad.idActividad", hidden: true},
	                {name: "idActividad.nombre", width: 80, align: "center", hidden: true},
	                
	                {name: "", width: 70, sortable: false, align: "center",  formatter: radioSelecUnidadSupervisada}
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
	                url: baseURL + "pages/ordenServicioGSM/cargaUnidadSupervisadaTitularMinero",
	                datatype: "json",
	                mtype: "POST",
	                postData: {
	                	codigoTitularMinero:$('#txtCodigoTitularMinero').val()
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
	                caption: "",
	                jsonReader: {
	                    root: "filas",
	                    page: "pagina",
	                    total: "total",
	                    records: "registros",
	                    repeatitems: false,
	                    id: "idDmUnidadSupervisada"
	                },
	                onSelectRow: function(rowid, status) {
	                    grid.resetSelection();
	                },
	                onRightClickRow: function(rowid) {
	                	/*
	                	var row=$('#grid'+nombreGrid).getRowData(rowid); 
	                    $('#contextMenu'+nombreGrid).parent().css('opacity', '1');
	                    $('#linkQuitar').css('display', '')
	                    $('#linkQuitar').attr('onClick', 'coOrSeOrSeMa.fxGenerar.validarQuitarUO("' + rowid + '","gridUnidadesOperativas")');
	                    */
	                },
	                loadComplete: function(data) {
	                	var html = '';
	                	if(data.resultado!=0){
	                		mensajeGrowl(
                                    "error",
                                    "No se encuentra el C&oacute;digo del Titular Minero ingresado.",
                                    "");
	                		html = '[0] Unidad(es) Supervisada(s) encontrada(s)';
	                	}else{
	                		html = '['+data.registros+'] Unidad(es) Supervisada(s) encontrada(s)';
	                	}	                	
	                	$('#divCantidadRegistrosTitMinMAS').html(html);
	                	/*
	                    //agregar check seleccionar todos
	                    var html = '<input id="chkSeleAllVisibles" type="checkbox" name="" value="" onclick="coOrSeOrSeMa.selecAllOrdenServicioVisibles(this);"><label for="chkSeleAllVisibles" class="checkbox"></label>';
	                    $('#gridUnidadesOperativas_check').html(html);
	                    $('#gridUnidadesOperativas_check').css('height', '20px');
	                    $('#gridUnidadesOperativas_check').removeClass('ui-jqgrid-sortable');
	
	                    //para marcar los ya seleccionados, casos de navegar entre paginas de grilla
	                    $('#unidOperativaSeleccionadaMAS').find('div').map(function() {
	                        $('#idUnidSup' + $(this).attr('id').replace('idUnidSupSele', '')).attr('checked', true);
	                    });
	                    // Para la opción de Quitar UO de la lista de Sesión.
	                    $('#contextMenuUnidadOperativa').parent().remove();
	                    $('#divContextMenuUnidadOperativaMAS').html("<ul id='contextMenuUnidadOperativa'>"
	                            + "<li> <a id='linkQuitar' data-icon='ui-icon-trash' title='Quitar'>Quitar</a></li>"
	                            + "</ul>");
	                    $('#contextMenuUnidadOperativa').puicontextmenu({
	                        target: $('#grid'+nombreGrid)
	                    });
	                    */
	                	
	                },
	                loadError: function(jqXHR) {
	                    errorAjax(jqXHR);
	                }
	            });
            }
        },
	procBuscarUnidadSupervisadaMasivaOS: function() {
            $.ajax({
                url: baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativaMasivaMul",
                type: 'post',
                async: false,
                dataType: 'json',
                data: {
                    idObligacionSubTipo: $('#cmbSubTipoSupervisionOSMAS').val(),
                    flagEsUnidadMuestral: '1'
                },
                beforeSend: loading.open,
                success: function(data) {
                    loading.close();
                    if (data.resultado == "0") {
                        coOrSeOrSeMa.armaValidateFiltros();
                        //coOrSeOrSeMa.activarSlctTipoEmprSupeOS(); 
                        coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
                    } else {
                        mensajeGrowl(
                                "error",
                                "No se encuentra la Unidad Operativa ingresada.",
                                "");
                    }
                },
                error: errorAjax
            });

        }
        
    },
    activarSlctTipoEmprSupeOS: function(difiere, difiereAct, validaMensaje) {
        if (difiere || $('#slctIdProcesoOSMAS').val() == '' || $('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    removeSesionLista: function() {
        $.ajax({
            url: baseURL + "pages/unidadSupervisada/removeSesionLista",
            type: 'get',
            async: false,
            dataType: 'json',
            success: function(data) {
                $('#unidOperativaSeleccionadaMAS').html('');
                $('#txtIdActividadOSMAS').val('');
                $("#txtIdDepartamentoOSMAS").val('');
                $("#txtIdProvinciaOSMAS").val('');
                $("#txtIdDistritoOSMAS").val('');
                coOrSeOrSeMaGSM.armaValidateFiltros();//coOrSeOrSeMaGSM.activarSlctTipoEmprSupeOS();
                loading.close;
            },
            error: errorAjax
        });
    },
    /* OSINE_SFS-480 - RSIS 26 - Inicio*/
    /*obtenerObligacionSubTipo: function(idTipoSupervision) {
        $.getJSON(baseURL + "pages/serie/listarObligacionSubTipo", {
            idObligacionTipo: idTipoSupervision,
            ajax: 'true',
            async: true
        }, function(data) {
            var html = '<option value="">--Seleccione--</option>';
            var len = data.length;
            for (var i = 0; i < len; i++) {
                html += '<option value="' + data[i].idObligacionSubTipo + '"  codigo="' + data[i].identificadorSeleccion + '" >'
                        + data[i].nombre + '</option>';
            }
            $('#cmbSubTipoSupervisionOSMAS').html(html);
        });
    },*/
    
    
    selecUnidSupervisada: function(obj) {
        difiere = false;
        difiereAct = false;
        validaMensaje = false;
        var countDep = 0;
        var countProv = 0;
        var countDist = 0;
        var countAct = 0;
        var depActual = 0;
        var depAnterior = 0;
        var provActual = 0;
        var provAnterior = 0;
        var distActual = 0;
        var distAnterior = 0;
        var actAnterior = 0;
        var actActual = 0;
        if ($(obj).attr('checked')) {
            idUniOpeSele = $(obj).attr('id').replace('idUnidTM', '');
            if ($('#idUnidSupSele' + idUniOpeSele).length == '0') {
                var row = $('#gridUnidadOperativaTitMinMAS').jqGrid("getRowData", idUniOpeSele);
                var html = '<div id="idUnidSupSele' + idUniOpeSele + '">' +
                        '<input type="text" style="width:50px;" name="" value="' + row['idDmUnidadSupervisada'] + '" />' +
                        '<input type="text" style="width:50px;" name="" value="' + row['idTitularMinero.idTitularMinero'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="actividad" name="" value="' + row['idActividad.idActividad'] + '" />' +
//                        '<input type="text" style="width:50px;" codigo="departamento" name="" value="' + row['direccionUnidadsupervisada.departamento.idDepartamento'] + '" />' +
//                        '<input type="text" style="width:50px;" codigo="provincia" name="" value="' + row['direccionUnidadsupervisada.provincia.idProvincia'] + '" />' +
//                        '<input type="text" style="width:50px;" codigo="distrito" name="" value="' + row['direccionUnidadsupervisada.distrito.idDistrito'] + '" />' +
                        '<input type="text" style="width:50px;" codigo="codosi" name="" value="' + row['idTitularMinero.codigoTitularMinero'] + '" />' +
//                        '<input type="text" style="width:50px;" codigo="flagMuestral" name="" value="' + row['flagMuestral'] + '" />' +
                        '</div>';
                $('#unidOperativaSeleccionadaMAS').append(html);
            }
        } else {
            $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).remove();
        }

        var dataDep = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="departamento"]');
        dataDep.map(function() {
            depAnterior = depActual;
            depActual = $(this).val();
            if (countDep == 0) {
                depAnterior = depActual;
            }
            if (depAnterior != depActual) {
                difiere = true;
            }
            countDep++;
        });
        var dataPro = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="provincia"]');
        dataPro.map(function() {
            provAnterior = provActual;
            provActual = $(this).val();
            if (countProv == 0) {
                provAnterior = provActual;
            }
            if (provAnterior != provActual) {
                difiere = true;
            }
            countProv++;
        });
        var dataDis = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="distrito"]');
        dataDis.map(function() {
            distAnterior = distActual;
            distActual = $(this).val();
            if (countDist == 0) {
                distAnterior = distActual;
            }
            if (distAnterior != distActual) {
                difiere = true;
            }
            countDist++;
        });
        var dataAct = $('#unidOperativaSeleccionadaMAS').find('div input[codigo="actividad"]');
        dataAct.map(function() {
            actAnterior = actActual;
            actActual = $(this).val();
            if (countAct == 0) {
                actAnterior = actActual;
            }
            if (actAnterior != actActual) {
                difiereAct = true;
            }
            countAct++;
        });

        //        	var departamento = $('#idUnidSupSele'+$(obj).attr('id').replace('idUnidSup','')).find('input').eq(3).val();
        //        	var provincia = $('#idUnidSupSele'+$(obj).attr('id').replace('idUnidSup','')).find('input').eq(4).val();
        //        	var distrito = $('#idUnidSupSele'+$(obj).attr('id').replace('idUnidSup','')).find('input').eq(5).val();
        //        	if(departamento=='' || provincia=='' || distrito=='' ){
        //            	mensajeGrowl("warn","La Unidad Operativa seleccionada no tiene Dirección Operativa registrada.","");        	
        //            }
        //        }
        //        if($(obj).attr('checked')){
        codosi = $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).find('input[codigo="codosi"]').val();
        if (codosi == '') {
            $(obj).removeAttr('checked');
            $('#idUnidSupSele' + $(obj).attr('id').replace('idUnidSup', '')).remove();
            mensajeGrowl("warn", "La Unidad Operativa seleccionada no tiene Código Osinergmin.", "");
        }
        if ($(obj).attr('checked')) {
        } else {
            validaMensaje = true;
        }
        if (!difiere) {
            $("#txtIdDepartamentoOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(3).val());
            $("#txtIdProvinciaOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(4).val());
            $("#txtIdDistritoOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(5).val());
        } else {
            $("#txtIdDepartamentoOSMAS").val('');
            $("#txtIdProvinciaOSMAS").val('');
            $("#txtIdDistritoOSMAS").val('');
        }
        if (!difiereAct && $('#unidOperativaSeleccionadaMAS').html() != "") {
            $("#txtIdActividadOSMAS").val($('#unidOperativaSeleccionadaMAS div').find('input').eq(2).val());
        } else {
            $("#txtIdActividadOSMAS").val('');
        }

        coOrSeOrSeMaGSM.armaValidateFiltros(difiere, difiereAct, validaMensaje);//coOrSeOrSeMaGSM.activarSlctTipoEmprSupeOS(difiere);


    },
    selecAllOrdenServicioVisibles: function(obj) {
        if ($(obj).attr('checked')) {
            $('#gridUnidadesOperativas').find('input').attr('checked', true);
        } else {
            $('#gridUnidadesOperativas').find('input').attr('checked', false);
        }
        $('#gridUnidadesOperativas').find('input').map(function() {
            coOrSeOrSeMaGSM.selecUnidSupervisada(this);
        });
    },
    armaFormOrdenServicioMasiva: function() {
        cont = 0;
        $('#unidOperativaSeleccionadaMAS div').map(function() {
            $(this).find('input').eq(0).attr('name', 'unidadesSupervisadas[' + cont + '].idUnidadSupervisada');
            $(this).find('input').eq(1).attr('name', 'unidadesSupervisadas[' + cont + '].empresaSupervisada.idEmpresaSupervisada');
            $(this).find('input').eq(2).attr('name', 'unidadesSupervisadas[' + cont + '].actividad.idActividad');
            $(this).find('input').eq(3).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].departamento.idDepartamento');
            $(this).find('input').eq(4).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].provincia.idProvincia');
            $(this).find('input').eq(5).attr('name', 'unidadesSupervisadas[' + cont + '].listaDirecciones[0].distrito.idDistrito');
            $(this).find('input').eq(6).attr('name', 'unidadesSupervisadas[' + cont + '].codigoOsinergmin');
            $(this).find('input').eq(7).attr('name', 'unidadesSupervisadas[' + cont + '].flagMuestral');
            cont++;
        });
    },
    armaValidateFiltros: function(difiere, difiereAct, validaMensaje) {
        str = $('#txtFiltrosMAS').val();
        array = str.split(',');
        contadorXfiltro = 0;
        supervision = 0;
        rubro = 0;
        ubigeo = 0;
        proceso = 0;
        for (i = 0; i < array.length; i++) {
            if (array[i] == "OBLI") {
                contadorXfiltro++;
                supervision = 1;
            }
            if (array[i] == "RUBR") {
                contadorXfiltro++;
                rubro = 1;
            }
            if (array[i] == "UBIG") {
                contadorXfiltro++;
                ubigeo = 1;
            }
            if (array[i] == "PROC") {
                contadorXfiltro++;
                proceso = 1;
            }
        }
        coOrSeOrSeMaGSM.armaCondicionFiltros(contadorXfiltro, supervision, rubro, ubigeo, proceso, difiere, difiereAct, validaMensaje);
    },
    armaCondicionFiltros: function(contadorXfiltro, supervision, rubro, ubigeo, proceso, difiere, difiereAct, validaMensaje) {
        switch (contadorXfiltro) {
            case 0:
                coOrSeOrSeMaGSM.armaCondicionCeroFiltros();
                //console.info("La Unidad Orgánica no contiene ningún filtro");
                break;
            case 1:
                //console.info('valida ['+contadorXfiltro+']condiciones');
                if (supervision == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxUnoTipoSupervision();
                } else if (rubro == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxUnoRubro(difiereAct, validaMensaje);
                } else if (ubigeo == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxUnoUbigeo(difiere, validaMensaje);
                } else if (proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxUnoProceso();
                }
                break;
            case 2:
                //console.info('valida ['+contadorXfiltro+']condiciones');
                if (supervision == 1 && rubro == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxDosSR(difiereAct, validaMensaje);
                } else if (supervision == 1 && ubigeo == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxDosSU(difiere);
                } else if (supervision == 1 && proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxDosSP();
                } else if (rubro == 1 && ubigeo == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxDosRU(difiere, difiereAct, validaMensaje);
                } else if (rubro == 1 && proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxDosRP(difiereAct, validaMensaje);
                } else if (ubigeo == 1 && proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxDosUP(difiere, validaMensaje);
                }
                break;
            case 3:
                if (supervision == 1 && rubro == 1 && ubigeo == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxTresSRU(difiere, difiereAct, validaMensaje);
                } else if (rubro == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxTresRUP(difiere, difiereAct, validaMensaje);
                } else if (supervision == 1 && rubro == 1 && proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxTresSRP(difiereAct, validaMensaje);
                } else if (supervision == 1 && ubigeo == 1 && proceso == 1) {
                    coOrSeOrSeMaGSM.armaCondicionFiltrosxTresSUP(difiere);
                }
                break;
            case 4:
                //console.info('valida ['+contadorXfiltro+']condiciones');
                coOrSeOrSeMaGSM.activarSlctTipoEmprSupeOS(difiere, difiereAct, validaMensaje);
                break;
        }

    },
    armaCondicionCeroFiltros: function() {
        coOrSeOrSeMaGSM.habilitarEmpresa();
    },
    armaCondicionFiltrosxUnoProceso: function() {
        if ($('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
    },
    armaCondicionFiltrosxUnoTipoSupervision: function() {
        if ($('#cmbTipoSupervisionOSMAS').val() == '') {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
    },
    armaCondicionFiltrosxUnoRubro: function(difiereAct, validaMensaje) {
        if ($('#txtIdActividadOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxUnoUbigeo: function(difiere, validaMensaje) {
        if ($('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    armaCondicionFiltrosxDosSR: function(difiereAct, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxDosSU: function(difiere, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    armaCondicionFiltrosxDosSP: function() {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
    },
    armaCondicionFiltrosxDosRU: function(difiere, difiereAct, validaMensaje) {
        if ($('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere || difiereAct) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    armaCondicionFiltrosxDosRP: function(difiereAct, validaMensaje) {
        if ($('#txtIdActividadOSMAS').val() == '' || $('#slctIdProcesoOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxDosUP: function(difiere, validaMensaje) {
        if ($('#slctIdProcesoOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    armaCondicionFiltrosxTresSRU: function(difiere, difiereAct, validaMensaje) {
        if (difiereAct || $('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    armaCondicionFiltrosxTresRUP: function(difiere, difiereAct, validaMensaje) {
        if (difiereAct || $('#txtIdActividadOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje) {
            if (difiere && !difiereAct) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
            }
            if (difiereAct && !difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
            }
            if (difiereAct && difiere) {
                mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro, ni al mismo Ubigeo.", "");
            }
        }
    },
    armaCondicionFiltrosxTresSRP: function(difiereAct, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdActividadOSMAS').val() == '' || $('#slctIdProcesoOSMAS').val() == '' || difiereAct) {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiereAct) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Rubro.", "");
        }
    },
    armaCondicionFiltrosxTresSUP: function(difiere, validaMensaje) {
        if ($('#cmbTipoSupervisionOSMAS').val() == '' || $('#txtIdDepartamentoOSMAS').val() == '' || $('#txtIdProvinciaOSMAS').val() == '' || $('#txtIdDistritoOSMAS').val() == '' || difiere || $('#slctIdProcesoOSMAS').val() == '') {
            coOrSeOrSeMaGSM.deshabilitarEmpresa();
        } else {
            coOrSeOrSeMaGSM.habilitarEmpresa();
        }
        if (!validaMensaje && difiere) {
            mensajeGrowl("warn", "La(s) Unidad(es) Operativa(s) seleccionada(s) no pertenecen al mismo Ubigeo.", "");
        }
    },
    habilitarEmpresa: function() {
        $("#slctTipoEmprSupeOSMAS").val('');
        $("#slctTipoEmprSupeOSMAS").removeAttr('disabled');
        $("#slctEmprSupeOSMAS").removeAttr('disabled');
    },
    deshabilitarEmpresa: function() {
        $("#slctTipoEmprSupeOSMAS").val('');
        $("#slctEmprSupeOSMAS").val('');
        $("#slctTipoEmprSupeOSMAS").attr('disabled', 'disabled');
        $("#slctEmprSupeOSMAS").attr('disabled', 'disabled');
    }
    
};

/* OSINE_SFS-1344 - Inicio */
function radioSelecUnidadSupervisada(cellvalue, options, rowdata) {
        var id = rowdata.idDmUnidadSupervisada;
        var html="";
        if (id != null && id != '' && id != undefined) {
        	html += "<center>";
            html += "<input id='idUnidTM" + id + "' type='radio' name='radioUSTitMin' value='0' onclick=\"coOrSeOrSeMaGSM.selecUnidSupervisada(this);\"";
            html += "/><label for='idUnidTM" + id + "' class='radio'></label>";
            html += "</center>";
        }
        
        return html;
};
/* OSINE_SFS-1344 - Fin */

$(function() {
    coOrSeOrSeMaGSM.removeSesionLista();
    $('#slctIdFlujoSigedOSMAS').change(function() {
        $('#slctCodigoFlujoSigedOSMAS').val($('#slctIdFlujoSigedOSMAS').find(':checked').attr('codigoSiged'));
        $('#slctNombreFlujoSigedOSMAS').val($('#slctIdFlujoSigedOSMAS').find(':checked').text());
    });
    $('#btnCerrarRecepcion').click(function() {
        $("#divOficioFechaFin").dialog("close");
    });
    $('#slctIdProcesoOSMAS').change(function() {
        fill.clean("#slctIdEtapaOS,#slctIdTramiteOS");
        common.serie.cargarEtapa("#slctIdProcesoOSMAS", "#slctIdEtapaOS");
    });
    // change empresa supervisora
    $('#slctEmprSupeOSMAS').change(function() {
        if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
            $('#slctLocadorEmprSupeOSMAS').val($('#slctEmprSupeOSMAS').val());
            $('#slctSupervisoraEmprSupeOSMAS').val('');
        } else if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
            $('#slctSupervisoraEmprSupeOSMAS').val($('#slctEmprSupeOSMAS').val());
            $('#slctLocadorEmprSupeOSMAS').val('');
        }
    });
    $('#slctTipoEmprSupeOSMAS')
            .change(
            function() {
                var idProceso = $("#slctIdProcesoOSMAS").val();
                var idObligacionTipo = $("#cmbTipoSupervisionOSMAS").val();
                var idRubro = $("#txtIdActividadOSMAS").val();
                var idDepartamento = $("#txtIdDepartamentoOSMAS").val();
                var idProvincia = $("#txtIdProvinciaOSMAS").val();
                var idDistrito = $("#txtIdDistritoOSMAS").val();
                fill.clean("#slctEmprSupeOSMAS");
                if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaNatural) {
                    common.empresaSupervisora.cargarLocador('#slctEmprSupeOSMAS', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                } else if ($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo') == constant.empresaSupervisora.personaJuridica) {
                    common.empresaSupervisora.cargarSupervisoraEmpresa('#slctEmprSupeOSMAS', idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito);
                }
                $('#slctCodigoTipoEmprSupeOSMAS').val($('#slctTipoEmprSupeOSMAS').find(':checked').attr('codigo'));
            });

    $('#slctIdProcesoOSMAS').change(function() {
        coOrSeOrSeMaGSM.armaValidateFiltros();//coOrSeOrSeMaGSM.activarSlctTipoEmprSupeOS();
    });
    $('#cmbTipoSupervisionOSMAS').change(function() {
        coOrSeOrSeMaGSM.armaValidateFiltros();//coOrSeOrSeMaGSM.activarSlctTipoEmprSupeOS();
        $('#flagEsUnidadMuestral').val(constant.estado.inactivo);
    });
    // generar
    $('#btnGenerarOSMAS').click(function() {
        coOrSeOrSeMaGSM.fxGenerar.validaGenerarOS();
    });
    // buscar UnidadOperativa por CodigoOsinergmin
    $('#btnBuscarTitularMineroOSMasivo').click(function() {
    	coOrSeOrSeMaGSM.fxGenerar.procBuscarTitularMineroOS('UnidadOperativaTitMinMAS');
    });
    // arma validaciones de campos obligatorios en el formulario
    coOrSeOrSeMaGSM.armaValidateFormOS();
    // cargar files del expediente
    if ($('#tipoAccionOSMAS').val() != constant.accionOS.generar) {
        common.procesarGridFilesExpediente($('#txtNumeroExpedienteOSMAS').val(),
                $('#txtIdOrdenServicioOSMAS').val(), "FilesOrdenServicio", $(
                '#tipoAccionOSMAS').val());
    }
    // validaciones alphanum
    $('#txtAsuntoSigedOSMAS').alphanum(constant.valida.alphanum.descrip);
    boton.closeDialog();
    
//    $('#cmbTipoSupervisionOSMAS').change(function() {
//        if ($("#cmbTipoSupervisionOSMAS").find(':checked').attr('codigo') > 0) {
//            $('#subTipo').css('display', 'inline-block');
//            coOrSeOrSeMaGSM.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSMAS').val());
//        } else {
//            $('#subTipo').css('display', 'none');
//            coOrSeOrSeMaGSM.obtenerObligacionSubTipo($('#cmbTipoSupervisionOSMAS').val());
//        }
//        coOrSeOrSeMaGSM.removeSesionLista();
//        coOrSeOrSeMaGSM.fxGenerar.buscarUnidadOperativa('0');
//    });
/*	$('#cmbSubTipoSupervisionOSMAS').change(function() {
        if ($("#cmbSubTipoSupervisionOSMAS").find(':checked').attr('codigo') == 'S') {
            $('#flagEsUnidadMuestral').val(constant.estado.activo);
            coOrSeOrSeMa.fxGenerar.procBuscarUnidadSupervisadaMasivaOS();
        } else {
            $('#flagEsUnidadMuestral').val(constant.estado.inactivo);
            coOrSeOrSeMa.removeSesionLista();
            coOrSeOrSeMa.fxGenerar.buscarUnidadOperativa('0');
        }

    });*/
    $('#txtCodigoTitularMinero').alphanum(constant.valida.alphanum.codigoTitularMinero);
    $('#txtObligatorioUnidadOrganicaMAS').html(($('#descFiltroConfiguradoMAS').val() != '') ? 'Datos requeridos: ' + $('#descFiltroConfiguradoMAS').val() : '');
});