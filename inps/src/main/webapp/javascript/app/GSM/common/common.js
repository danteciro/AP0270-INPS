/**
* Resumen		
* Objeto			: common.js
* Descripción		: JavaScript donde se centraliza las acciones de los diferentes formularios del Inps, gerencia GSM.
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-1344
* Autor				: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
*
*/
 
//GSM/common/common.js
var common={
	/* OSINE_SFS-1344 - Inicio */	
    procesarGridFilesExpediente:function(numeroExpediente,idOrdenServicio,nombreGrid,tipoAccion) {
        var nombres=[];
        var columnas=[];
        var url="";
        var flgCargaTemporales="0";
        switch (tipoAccion){
            case "atender":
            	nombres = ['idArchivo','idDocumentoAdjunto','idTipoDocumento','DOCUMENTO','NRO. DOCUMENTO','FIRMADO','NOMBRE','FECHA CARGA','DESCARGAR','ESTADO'];
                columnas = [
                    {name: "idArchivo", hidden:true},//siged
                    {name: "idDocumento",hidden:true},
                    {name: "idTipoDocumento.codigo",hidden:true},
                    {name: "idTipoDocumento.descripcion", width: 240, sortable: false, align: "left"},
                    {name: "nroDocumento", width: 70, sortable: false, align: "left"},
                    {name: "firmado", width: 50, sortable: false, align: "left",formatter: "descripcionFirmado"},
                    {name: "nombreArchivo", width: 240, sortable: false, align: "left"},
                    {name: "fechaCarga", width: 120, sortable: false, align: "left",formatter:fecha_hora},
                    {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpediente"},
                    {name: "estadoOrigen",width: 60,sortable: false, align: "center",hidden:true}
                ];
                url=baseURL + "pages/archivo/findArchivoExpediente";
                flgCargaTemporales="1";
                break;
            default :
            	nombres = ['idArchivo','idDocumentoAdjunto','idTipoDocumento','TIPO DOCUMENTO','NRO. DOCUMENTO','FIRMADO','NOMBRE','FECHA CARGA','DESCARGAR'];
                columnas = [
                    {name: "idArchivo", hidden:true},//siged
                    {name: "idDocumento",hidden:true},
                    {name: "idTipoDocumento.codigo",hidden:true},
                    {name: "idTipoDocumento.descripcion", width: 240, sortable: false, align: "left"},
                    {name: "nroDocumento", width: 70, sortable: false, align: "left"},
                    {name: "firmado", width: 50, sortable: false, align: "left",formatter: "descripcionFirmado"},
                    {name: "nombreArchivo", width: 240, sortable: false, align: "left"},
                    {name: "fechaCarga", width: 120, sortable: false, align: "left",formatter:fecha_hora},
                    {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpediente"}
                ];
                url=baseURL + "pages/archivo/findArchivoExpediente";
                break;
        }
        ////////////////////////////////////
        $("#gridContenedor"+nombreGrid).html("");
        var grid = $("<table>", {
            "id": "grid"+nombreGrid
        });
        var pager = $("<div>", {
            "id": "paginacion"+nombreGrid
        });
        $("#gridContenedor"+nombreGrid).append(grid).append(pager);

        grid.jqGrid({
            url: url,
            datatype: "json",
            postData: {  
                numeroExpediente:numeroExpediente,
                idOrdenServicio:idOrdenServicio,
                flgCargaTemporales:flgCargaTemporales
            },
            hidegrid: false,
            rowNum: constant.rowNum,
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
                id: "idArchivo"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {            	
                var row=$('#grid'+nombreGrid).getRowData(rowid);
                $('#linkCargarArchivo').css('display','none');
                $('#linkVersionarArchivo').css('display','none');
                $('#linkConsultarVersionesArchivo').css('display','none');
                $('#linkEnumerarDocumento').css('display','none');
                $('#linkFirmarEnumerarDocumento').css('display','none');
                $('#linkAnularArchivo').css('display','none');
                if($('#rolSesion').val()=='SUPERVISOR'){
                	$('#contextMenu'+nombreGrid).parent().css('opacity','1');
                    $('#linkVersionarArchivo').css('display','');
                    $('#linkAnularArchivo').css('display','');
                    if(row['idTipoDocumento.descripcion']==''){
                    	$('#linkEnumerarDocumento').css('display','none');
                        $('#linkFirmarEnumerarDocumento').css('display','none');
                        $('#linkCargarArchivo').css('display','none');
                        $('#linkConsultarVersionesArchivo').css('display','none');
                    }else{
                    	$('#linkEnumerarDocumento').css('display','');
                        $('#linkFirmarEnumerarDocumento').css('display','');
                        $('#linkCargarArchivo').css('display','');
                        $('#linkConsultarVersionesArchivo').css('display','');
                    }
	            }else{
	                $('#contextMenu'+nombreGrid).parent().css('opacity','0');
	            }

                    $('#linkAnularArchivo').attr('onClick', "common.fxGestionarArchivo.anularArchivo('" + rowid +"','"+nombreGrid + "','"+numeroExpediente+"')");
                    $('#linkEnumerarDocumento').attr('onClick', "common.fxGestionarArchivo.validarEnumerarDocumento('" + rowid +"','"+nombreGrid + "','"+tipoAccion+"','"+numeroExpediente+"','"+idOrdenServicio+"')");
                    $('#linkFirmarEnumerarDocumento').attr('onClick', "common.fxGestionarArchivo.validarFirmarEnumerarDocumento('" + rowid +"','"+nombreGrid + "','"+tipoAccion+"','"+numeroExpediente+"','"+idOrdenServicio+"')");
                    $('#linkCargarArchivo').attr('onClick', "common.fxGestionarArchivo.cargarArchivo('"+ rowid +"','"+nombreGrid+"')");
                    $('#linkVersionarArchivo').attr('onClick', "common.fxGestionarArchivo.versionarArchivo('" + rowid  +"','"+nombreGrid+ "')");
                    $('#linkConsultarVersionesArchivo').attr('onClick', 'common.fxGestionarArchivo.consultarVersionesArchivo(' + numeroExpediente +','+row['idDocumento'] + ')');
            },
            loadComplete: function(data){
            	
            	$('#contextMenu'+nombreGrid).parent().remove();
                $('#divContextMenu'+nombreGrid).html("<ul id='contextMenu"+nombreGrid+"'>"
                        + "<li> <a id='linkAnularArchivo' data-icon='ui-icon-trash' title='Anular'>Anular</a></li>"
                        + "<li> <a id='linkCargarArchivo' data-icon='ui-icon-document' title='Cargar Archivo al Documento'>Cargar Archivo al Documento</a></li>"
                        + "<li> <a id='linkVersionarArchivo' data-icon='ui-icon-pencil' title='Versionar'>Versionar</a></li>"
                        + "<li> <a id='linkConsultarVersionesArchivo' data-icon='ui-icon-search' title='Consultar Versiones'>Consultar Versiones</a></li>"
                        + "<li> <a id='linkEnumerarDocumento' data-icon='ui-icon-squaresmall-plus' title='Enumerar'>Enumerar</a></li>"
                        + "<li> <a id='linkFirmarEnumerarDocumento' data-icon='ui-icon-key' title='Fimar/Enumerar'>Fimar/Enumerar</a></li>"
                    + "</ul>");
                $('#contextMenu'+nombreGrid).puicontextmenu({
                    target: $('#grid'+nombreGrid)
                });
            }
        });
    },
    /* OSINE_SFS-1344 - Fin */	
    
    fxGestionarArchivo:{
    	cargarArchivo:function(rowid,nombreGrid){
    		title="CARGAR ARCHIVO AL DOCUMENTO";
    		tipo="cargar";
    		
    		var row=$('#grid'+nombreGrid).getRowData(rowid); 
    		$.ajax({
                url:baseURL + "pages/archivo/cargarArchivo",
                type:'post',
                async:false,
                data:{
                    tipo:tipo,
                    idArchivo: row['idArchivo'],
                    idDocumento: row['idDocumento'],
                    tipoDocumento: row['idTipoDocumento.descripcion'],
                    nombreArchivo: row['nombreArchivo']
    				
                },
                
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogGestionarDocumento").html(data);
                    $("#dialogGestionarDocumento").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                    $('#divCargarVersionarArchivo').css('display','inline-block');
                },
                error:errorAjax
            });
            
        },
        
        versionarArchivo:function(rowid,nombreGrid){
        	title="VERSIONAR ARCHIVO";
        	tipo="versionar";
        	
    		var row=$('#grid'+nombreGrid).getRowData(rowid); 
    		$.ajax({
                url:baseURL + "pages/archivo/cargarArchivo",
                type:'post',
                async:false,
                data:{
                    tipo:tipo,
                    idArchivo: row['idArchivo'],
                    idDocumento: row['idDocumento'],
                    tipoDocumento: row['idTipoDocumento.descripcion'],
                    nombreArchivo: row['nombreArchivo']
    				
                },
                
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogGestionarDocumento").html(data);
                    $("#dialogGestionarDocumento").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                    $('#divCargarVersionarArchivo').css('display','inline-block');
                },
                error:errorAjax
            });
        	
        },
     // htorress - RSIS 9 - Fin	
     // htorress - RSIS 13 - Inicio	
        consultarVersionesArchivo:function(numeroExpediente, idDocumento){    		

        	$('#divVersionesDocumento').dialog('open');  
    		
            function grillaGenerarResultados(){
            	    var nombreGrid="VersionesDocumentos";
            	    
            	    nombres = ['idArchivo','AUTOR','NOMBRE','VERSION','FECHA','DESCARGA'];
            	  
                    columnas = [
                        {name: "idArchivo", hidden:true},//siged
                        {name: "autor", width: 300, sortable: false, align: "left"},
                        {name: "nombreArchivo", width: 300, sortable: false, align: "left"},
                        {name: "version", width: 120, sortable: false, align: "left"},
                        {name: "fechaCarga", width: 120, sortable: false, align: "left",formatter:fecha_hora},
                        {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpediente"}
                      
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
                        url: baseURL + "pages/archivo/findVersionesArchivosSIGED",
                        datatype: "json",
                        postData: {
                        	numeroExpediente:numeroExpediente,
                        	idDocumento:idDocumento
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
                            id: "idHistoricoEstado"
                        },
                        onSelectRow: function(rowid, status) {
                            grid.resetSelection();
                        }            
                    });	
                 }

                grillaGenerarResultados();	
        },
     // htorress - RSIS 13 - Fin     
     // htorress - RSIS 15 - Inicio 
    	anularArchivo:function(rowid,nombreGrid,numeroExpediente){
    		title="ANULAR ARCHIVO";
    		tipo="anular";
    		
    		var row=$('#grid'+nombreGrid).getRowData(rowid); 
    		$.ajax({
	        	url:baseURL + "pages/archivo/cargarArchivo",
	            type:'post',
	            async:false,
	            data:{
	            	tipo:tipo,
	            	idArchivo: row['idArchivo'],
                    idDocumento: row['idDocumento'],
                    tipoDocumento: row['idTipoDocumento.descripcion'],
                    nombreArchivo: row['nombreArchivo'],
                    numeroExpediente: numeroExpediente
	            },
	            
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                $("#dialogGestionarDocumento").html(data);
	                $("#dialogGestionarDocumento").dialog({
	                	resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
	                });
	                $('#divAnularArchivo').css('display','inline-block');
	            },
	            error:errorAjax
	        });
	    },
	    validarEnumerarDocumento:function(rowid, nombreGrid,tipoAccion,numeroExpediente,idOrdenServicio){
    		confirmer.open("¿Ud. Está seguro de enumerar el archivo?","common.fxGestionarArchivo.enumerarDocumento('"+rowid+"','"+nombreGrid+"','"+tipoAccion+"','"+numeroExpediente+"','"+idOrdenServicio+"')");
        },
        enumerarDocumento:function(rowid,nombreGrid,tipoAccion,numeroExpediente,idOrdenServicio){
   		var row=$('#grid'+nombreGrid).getRowData(rowid); 
        	$.ajax({
                url:baseURL + "pages/archivo/enumerarDocumento",
                type:'post',
                async:false,
                data:{
                    idArchivo: row['idArchivo'],
                    idDocumento: row['idDocumento'],
                    idTipoDocumento: row['idTipoDocumento.codigo'],
                    tipoDocumento: row['idTipoDocumento.descripcion'],
                    nombreArchivo: row['nombreArchivo'],
                    numeroExpediente: numeroExpediente
                },
                
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    common.procesarGridFilesExpediente(numeroExpediente,idOrdenServicio,nombreGrid,tipoAccion);
                    if(data.resultado=='0'){
                		mensajeGrowl("success", data.mensaje);
    	            } else {
    	            	mensajeGrowl("error", data.mensaje);
    	            }
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
        },
        validarFirmarEnumerarDocumento:function(rowid, nombreGrid,tipoAccion,numeroExpediente,idOrdenServicio){
    		confirmer.open("¿Ud. Está seguro de firmar y enumerar el archivo?","common.fxGestionarArchivo.firmarEnumerarDocumento('"+ rowid +"','"+ nombreGrid +"','"+tipoAccion+"','"+numeroExpediente+"','"+idOrdenServicio+"')");
        },
        firmarEnumerarDocumento:function(rowid,nombreGrid,tipoAccion,numeroExpediente,idOrdenServicio){
    		var row=$('#grid'+nombreGrid).getRowData(rowid); 
        	$.ajax({
                url:baseURL + "pages/archivo/firmarEnumerarDocumento",
                type:'post',
                async:false,
                data:{
                	idArchivo: row['idArchivo'],
                    idDocumento: row['idDocumento'],
                    idTipoDocumento: row['idTipoDocumento.codigo'],
                    tipoDocumento: row['idTipoDocumento.descripcion'],
                    nombreArchivo: row['nombreArchivo'],
                    numeroExpediente: numeroExpediente
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    common.procesarGridFilesExpediente(numeroExpediente,idOrdenServicio,nombreGrid,tipoAccion);
                    if(data.resultado=='0'){
                		mensajeGrowl("success", data.mensaje);
    	            } else {
    	            	mensajeGrowl("error", data.mensaje);
    	            }
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
        },
    	
    }, 
    // htorress - RSIS 15 - Fin
    fxEliminarDocumento:{
    	validaEliminarDocumentoAdjunto:function(id,nombreGrid,tipoAccion,numeroExpediente,idOrdenServicio){
    		confirmer.open("¿Confirma que desea eliminar el Documento Adjunto?","common.fxEliminarDocumento.procesaEliminarDocumentoAdjunto("+ id +",'"+nombreGrid+"','"+tipoAccion+"','"+numeroExpediente+"','"+idOrdenServicio+"')");  
        },
        procesaEliminarDocumentoAdjunto:function(id,nombreGrid,tipoAccion,numeroExpediente,idOrdenServicio){
        	$.ajax({
                url:baseURL + "pages/archivo/eliminarPghDocumentoAdjunto",
                type:'post',
                dataType:'json',
                async:false,
                data:{idDocumentoAdjunto:id},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    common.procesarGridFilesExpediente(numeroExpediente,idOrdenServicio.val(),nombreGrid,tipoAccion);
                    mensajeGrowl("success", data.mensaje, "");
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            }); 
        },
    	
    },    
    procesarGridTrazaOrdeServ:function(nombreGrid) {
        var nombres = ['idHistoricoEstado','NRO. ORDEN SERVICIO','FECHA ENV&Iacute;O','','','DE','','','PARA', 'ACCI&Oacute;N','PETICI&Oacute;N','MOTIVO'];
        var columnas = [
            {name: "idHistoricoEstado",hidden:true},
            {name: "ordenServicio.numeroOrdenServicio",hidden:true},
            {name: "fechaCreacion", width: 100, sortable: false, align: "left",formatter:fecha_hora},
            {name: "personalOri.apellidoPaterno", hidden:true},
            {name: "personalOri.apellidoMaterno", hidden:true},
            {name: "personalOri.nombre", width: 160, sortable: false, align: "left",formatter:"nombreCompletoPersOriTraza"},
            {name: "personalDest.apellidoPaterno", hidden:true},
            {name: "personalDest.apellidoMaterno", hidden:true},
            {name: "personalDest.nombre", width: 160, sortable: false, align: "left",formatter:"nombreCompletoPersDestTraza"},
            {name: "estadoProceso.nombreEstado",width: 80,sortable: false, align: "center"},
            {name: "peticion.descripcion",width: 60,sortable: false, align: "center"},
            {name: "motivo.descripcion",width: 180,sortable: false, align: "center"}
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
            url: baseURL + "pages/ordenServicio/cargarTrazabilidad",
            datatype: "json",
            postData: {
                idExpediente:$('#idExpedienteTrazaOS').val(),
                idOrdenServicio:$('#idOrdenServicioTrazaOS').val()
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
                id: "idHistoricoEstado"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            }            
        });
    },
    /*OSINE_SFS-1344 - Inicio */
    procesarGridOrdeServSupe:function(nombreGrid) {
        var nombres = ['','C&Oacute;DIGO OSINERGMIN','RAZ&Oacute;N SOCIAL','FECHA ORDEN SERVICIO','N&Uacute;MERO ORDEN DE SERVICIO','N&UacuteMERO EXPEDIENTE','FECHA ESTADO','ESTADO'];
        var columnas = [
            {name: "idOrdenServicio",hidden:true},
            {name: "expediente.unidadSupervisada.codigoOsinergmin", width: 95, sortable: false, align: "left"},
            {name: "expediente.empresaSupervisada.razonSocial", width: 200, sortable: false, align: "left"},
            {name: "fechaCreacion", width: 95, sortable: false, align: "left",formatter:fecha},
            {name: "numeroOrdenServicio", width: 100, sortable: false, align: "left"},
            {name: "expediente.numeroExpediente", width: 100, sortable: false, align: "left"},
            {name: "fechaEstadoProceso", width: 110, sortable: false, align: "left",formatter:fecha_hora},
            {name: "estadoProceso.nombreEstado", width: 100, sortable: false, align: "left"}
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
            url: baseURL + "pages/ordenServicioGSM/findOrdenServicio",
            datatype: "json",
            mtype : "POST",
            postData: {
                idPersonal:$('#idPersonalSesion').val(),
                idEstadoProceso:$('#slctIdEstaProcOSSup').val(),
                fechaInicioEstadoProceso:$('#txtFechaIniOSSup').val(),
                fechaFinEstadoProceso:$('#txtFechaFinOSSup').val(),
                numeroExpediente:$('#txtNumeroExpedienteOSSup').val(),
                numeroOrdenServicio:$('#txtNumeroOrdenServicioOSSup').val(),
                codigoOsinergmin:$('#txtCodOsinergOSSup').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
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
                id: "idOrdenServicio"
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
    },
    /*OSINE_SFS-1344 - Fin */
    abrirOrdenServicio:function(tipo,rowid,idGrid,flgOrigenExpediente){
        switch (tipo){
            case constant.accionOS.consultar :
                title="CONSULTAR ORDEN DE SERVICIO";break;
            case constant.accionOS.revisar :
                title="REVISAR ORDEN DE SERVICIO";break;
            case constant.accionOS.aprobar :
                title="APROBAR ORDEN DE SERVICIO";break;
            case constant.accionOS.notificar :
                title="NOTIFICAR ORDEN DE SERVICIO";break;
            case constant.accionOS.concluir :
                title="CONCLUIR ORDEN DE SERVICIO";break;
            case constant.accionOS.asignar :
                title="ASIGNAR ORDEN DE SERVICIO";break;
            case constant.accionOS.documentos :
                title="DOCUMENTOS EXPEDIENTE";break;
            case constant.accionOS.generar :
                title="GENERAR ORDEN DE SERVICIO";break;
            case constant.accionOS.confirmardescargo :
                title="CONFIRMAR DESCARGO";break;
            case constant.accionOS.anular :
                title="ANULAR ORDEN DE SERVICIO";break;
            case constant.accionOS.editar :
                title="EDITAR ORDEN DE SERVICIO";break;
            default :
                title="";break;
        }
        
        var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
            flgOrigenExpediente=row.flagOrigen;
        }    
        $.ajax({
            url:baseURL + "pages/bandeja/abrirOrdenServicio",
            type:'POST',
            async:false,
            data:{
                tipo:tipo,
                rolSesion:$('#rolSesion').val(),
                idExpediente:row.idExpediente,
                iteracionExpediente:row.iteracionExpediente,
                flagOrigen:flgOrigenExpediente,
                numeroExpediente:row.numeroExpediente,
                asuntoSiged:row.asuntoSiged,
                flagMuestral:row.flagMuestral,
                'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                'tramite.idTramite':row['tramite.idTramite'],
                'tramite.idEtapa':row['tramite.idEtapa'],
                'tramite.idProceso':row['tramite.idProceso'],
                'proceso.idProceso':row['proceso.idProceso'],
                'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                'flagEvaluaTipoAsignacion':row['flagEvaluaTipoAsignacion'],
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogOrdenServicio").html(data);
                $("#dialogOrdenServicio").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar",
                    close: function() {
                            $("#dialogOrdenServicio").dialog("destroy");
                        }
                });
            },
            error:errorAjax
        });
    },
    
    abrirOrdenServicioMasivo:function(tipo,rowid,idGrid,flgOrigenExpediente){
        switch (tipo){
            case constant.accionOS.atender :
                title="ATENDER ORDEN DE SERVICIO";break;
            case constant.accionOS.consultar :
                title="CONSULTAR ORDEN DE SERVICIO";break;
            case constant.accionOS.revisar :
                title="REVISAR ORDEN DE SERVICIO";break;
            case constant.accionOS.aprobar :
                title="APROBAR ORDEN DE SERVICIO";break;
            case constant.accionOS.notificar :
                title="NOTIFICAR ORDEN DE SERVICIO";break;
            case constant.accionOS.concluir :
                title="CONCLUIR ORDEN DE SERVICIO";break;
            case constant.accionOS.asignar :
                title="ASIGNAR ORDEN DE SERVICIO";break;
            case constant.accionOS.documentos :
                title="DOCUMENTOS EXPEDIENTE";break;
            case constant.accionOS.generar :
                title="GENERAR ORDEN DE SERVICIO";break;
            case constant.accionOS.confirmardescargo :
                title="CONFIRMAR DESCARGO";break;
            default :
                title="";break;
        }
        
        var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
            flgOrigenExpediente=row.flagOrigen;
        }
        $.ajax({
            url:baseURL + "pages/ordenServicioGSM/abrirOrdenServicioMasivo",
            type:'get',
            async:false,
            data:{
                tipo:tipo,
                rolSesion:$('#rolSesion').val(),
                idPersonal:$('#idPersonalSesion').val(),
                idExpediente:row.idExpediente,
                flagOrigen:flgOrigenExpediente,
                numeroExpediente:row.numeroExpediente,
                asuntoSiged:row.asuntoSiged,
                'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                'tramite.idTramite':row['tramite.idTramite'],
                'tramite.idEtapa':row['tramite.idEtapa'],
                'tramite.idProceso':row['tramite.idProceso'],
                'proceso.idProceso':row['proceso.idProceso'],
                'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogOrdenServicioMasivo").html(data);
                $("#dialogOrdenServicioMasivo").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    
    abrirDocumentoExpediente:function(rowid,idGrid){
        var title="DOCUMENTOS EXPEDIENTE";
        var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        
        $.ajax({
            url:baseURL + "pages/bandeja/abrirDocumentoExpediente",
            type:'get',
            async:false,
            data:{
                numeroExpediente:row.numeroExpediente
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogDocumentoExpediente").html(data);
                $("#dialogDocumentoExpediente").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    abrirTrazabilidadOrdeServ:function(rowid,idGrid){
        var title="TRAZABILIDAD ORDEN SERVICIO";
        var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        
        $.ajax({
            url:baseURL + "pages/bandeja/abrirTrazabilidadOrdeServ",
            type:'get',
            async:false,
            data:{
                idExpediente:row.idExpediente,
                numeroExpediente:row.numeroExpediente,
                'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio']
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogTrazabilidadOrdeServ").html(data);
                $("#dialogTrazabilidadOrdeServ").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
//    abrirMantUnidadOperativa:function(tipo,ruc,rs,idEmpresaSupervisada){
//    	var title="";
//        if(tipo=="new"){
//            title="CREAR UNIDAD OPERATIVA";
//        }
//        $.ajax({
//            url:baseURL + "pages/bandeja/abrirMantUnidadOperativa",
//            type:'get',
//            async:false,
//            data:{
//                tipo:tipo,
//                'empresaSupervisada.ruc':ruc,
//                'empresaSupervisada.razonSocial':rs,
//                idEmpresaSupervisada:idEmpresaSupervisada
//            },
//            beforeSend:loading.open,
//            success:function(data){
//                loading.close();
//                $("#dialogMantUnidadOperativa").html(data);
//                $("#dialogMantUnidadOperativa").dialog({
//                    resizable: false,
//                    draggable: true,
//                    autoOpen: true,
//                    height:"auto",
//                    width: "auto",
//                    modal: true,
//                    dialogClass: 'dialog',
//                    title: title,
//                    closeText: "Cerrar"
//                });
//            },
//            error:errorAjax
//        });
//    },
    abrirUnidadOperativa:function(rowid,idGrid){
        var title="UNIDAD OPERATIVA";
        var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        $.ajax({
            url:baseURL + "pages/bandeja/abrirUnidadOperativa",
            type:'get',
            async:false,
            data:{
                idExpediente:row.idExpediente,
                flagOrigen:row.flagOrigen,
                asuntoSiged:row.asuntoSiged,
                'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion']
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogUnidadOperativa").html(data);
                $("#dialogUnidadOperativa").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    
    abrirSubirDocumento:function(numeroExpediente,idOrdenServicio, idSupervisoraEmpresa, idExpediente,asuntoSiged,codigoSiged){
	
        var title="SUBIR DOCUMENTO";
        
        $.ajax({
            url:baseURL + "pages/bandeja/abrirSubirDocumento",
            type:'get',
            async:false,
            data:{
                numeroExpediente:numeroExpediente,
                idOrdenServicio:idOrdenServicio,
                idSupervisoraEmpresa:idSupervisoraEmpresa,
            	idExpediente:idExpediente,
            	asuntoSiged:asuntoSiged,
            	codigoSiged:codigoSiged
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogSubirDocumento").html(data);
                $("#dialogSubirDocumento").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    
    abrirSupervision:function(tipo,idOrdenServicio,rol,tipoEmpresa,empresaSupervisora,flagPopup,idExpediente,idObligacionTipo,idProceso,idActividad,iteracion){
    	var title='';
    	if(tipo==constant.modoSupervision.registro){title="REGISTRAR SUPERVISIÓN";}
    	else if(tipo==constant.modoSupervision.consulta){title="CONSULTAR SUPERVISIÓN";}
        $("#dialogSupervision").html('');
        $.ajax({
            url:baseURL + "pages/bandeja/abrirSupervision",
            	type:'post',
    		async:false,
    		data:{
    			idOrdenServicio:idOrdenServicio,
    			rol:rol,
    			tipoEmpresa:tipoEmpresa,
    			empresaSupervisora:empresaSupervisora,
                        iteracion:iteracion,
                        'expediente.idExpediente':idExpediente,
                        'expediente.obligacionTipo.idObligacionTipo':idObligacionTipo,
                        'expediente.proceso.idProceso':idProceso,
                        'expediente.unidadSupervisada.actividad.idActividad':idActividad
    		},
    		beforeSend:loading.open,
    		success:function(data){
    			loading.close();
    			if(flagPopup==constant.estado.activo){
    				$("#dialogSupervision").html(data);
    				if($('#idSupervision').val() == null || $('#idSupervision').val() == ''){
    					$('#fldstDatosSuper .pui-fieldset-content').show();
    				}
    				$("#dialogSupervision").dialog({
    					resizable: false,
    					draggable: true,
    					position: 'top',
    					autoOpen: true,
    					height:"auto",
    					width: "auto",
    					modal: true,
    					dialogClass: 'dialog',
    					title: title,
    					closeText: "Cerrar"
    				});
    			}
    		},
    		error:errorAjax
    	});
    },
    abrirDescripcionHallazgo:function(tipoRespaldo,tipoCumple,tipo,divProbatorio,rowid,idGrid){
    	var title="COMENTARIO DEL HALLAZGO ";
    	var row=$('#'+idGrid).jqGrid("getRowData",rowid);
    	title+=row['obligacion.codigoObligacion']; //mdiosesf - RSIS6    	
    	$.ajax({
            url:baseURL + "pages/bandeja/abrirDescripcionHallazgo",
            type:'get',
            async:false,
            data:{
            	tipoCumple:tipoCumple,
                tipo:tipo,
                tipoRespaldo:tipoRespaldo,
                divProbatorio:divProbatorio, //mdiosesf - RSIS6
                idDetalleSupervision:row.idDetalleSupervision,
                flagResultado:row.flagResultado,
                descripcionResultado:row.descripcionResultado,
                idDetalleSupervisionAnt:row.idDetalleSupervisionAnt,
                'obligacion.idObligacion':row['obligacion.idObligacion'],
                'supervision.idSupervision':row['supervision.idSupervision'],
                'tipificacion.idTipificacion':row['tipificacion.idTipificacion'],
                'criterio.idCriterio':row['criterio.idCriterio']
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogHallazgo").html('');
                $("#dialogHallazgo").html(data);
                $("#dialogHallazgo").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "850",
                    position:['top',51],
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar",
                    close:function( event, ui ) {
                    	var pagina=fxGrilla.obtenerPage(idGrid);
                    	fxGrilla.recargarGrillaPagina(idGrid,pagina);
                    }
                });
            },
            error:errorAjax
        });
    },
    abrirEvaluacionDescargo:function(tipoSancion,tipo,divProbatorio,rowid,idGrid){
    	var title="EVALUACIÓN DE DESCARGO ";
    	var row=$('#'+idGrid).jqGrid("getRowData",rowid);
    	title+=row['obligacion.codigoObligacion']; //mdiosesf - RSIS6
    	$.ajax({
            url:baseURL + "pages/bandeja/abrirEvaluacionDescargo",
            type:'get',
            async:false,
            data:{
            	tipoSancion:tipoSancion,
                tipo:tipo,
                divProbatorio:divProbatorio, //mdiosesf - RSIS6
                idDetalleSupervision:row.idDetalleSupervision,
                flagResultado:row.flagResultado,
                idDetalleSupervisionAnt:row.idDetalleSupervisionAnt,
                descripcionResultado:row.descripcionResultado,
                'obligacion.idObligacion':row['obligacion.idObligacion'],
                'obligacion.codigoObligacion':row['obligacion.codigoObligacion'], //mdiosesf - RSIS6
                'tipificacion.idTipificacion':row['tipificacion.idTipificacion'],
                'criterio.idCriterio':row['criterio.idCriterio']
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogDescargo").html('');
                $("#dialogDescargo").html(data);
                $("#dialogDescargo").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    position: ['top',47],
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar",
                    close:function( event, ui ) {
                    	var pagina=fxGrilla.obtenerPage(idGrid);
                    	fxGrilla.recargarGrillaPagina(idGrid,pagina);
                    }
                });
            },
            error:errorAjax
        });
    },
    abrirAdjuntoSupe:function(tipo,idSupervision){
    	var title="ADJUNTOS DE LA SUPERVISIÓN";
    	$.ajax({
            url:baseURL + "pages/bandeja/abrirAdjuntoSupervision",
            type:'get',
            async:false,
            data:{
            	tipo:tipo,
            	idSupervision:idSupervision
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogAdjuntoSupervision").html('');
                $("#dialogAdjuntoSupervision").html(data);
                $("#dialogAdjuntoSupervision").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    abrirReasignarExpe:function(rowid,idGrid){
        var title="REASIGNAR ESPECIALISTA";
        var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        
        $.ajax({
            url:baseURL + "pages/bandeja/abrirReasignarExpediente",
            type:'get',
            async:false,
            data:{
                idExpediente:row.idExpediente,
                numeroExpediente:row.numeroExpediente
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogReasignarExpediente").html(data);
                $("#dialogReasignarExpediente").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    abrirPopupSeleccActividad:function (tipo_seleccion,sufijo) {
        $.ajax({
            url:baseURL + "pages/bandeja/abrirPopupSeleccActividad", 
            type:'get',
            async:false,
            data:{
                seleccion:tipo_seleccion,
                sufijo:sufijo
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogSeleccActividad").html(data);
                $("#dialogSeleccActividad").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: "Seleccione Rubro"
                });
                $('#arbolActividadesEspe').html(data.arbolActividades);
            },
            error:errorAjax
        });
    },
    
    abrirEnviarMensajeria:function (rowid, idGrid, flagNotificar) {
    	var row=$('#'+idGrid).jqGrid("getRowData",rowid);
        $.ajax({
            url:baseURL + "pages/bandeja/abrirEnviarMensajeria", 
            type:'get',
            async:false,
            data:{
            	idExpediente:row.idExpediente,
                numeroExpediente:row.numeroExpediente,
                'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                flagNotificar:flagNotificar
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogEnviarMensajeria").html(data);
                $("#dialogEnviarMensajeria").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: "ENVÍO DE MENSAJERIA",
                    close:function(){
        				$("#dlgCliente").dialog("destroy");
        			}
                });
                coOrSeOrSeEnMe.autoComplete();
            },
            error:errorAjax
        });
    }, 
    
    serie:{
        cargarEtapa:function(idSlctOrig,idSlctDest){
            $.ajax({
                url:baseURL + "pages/serie/cargarEtapa",
                type:'get',
                dataType:'json',
                async:false,
                data:{idProceso:$(idSlctOrig).val()},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    fill.combo(data,"idEtapa","descripcion",idSlctDest);
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });            
        },
        cargarTramite:function(idSlctOrig,idSlctDest){
            $.ajax({
                url:baseURL + "pages/serie/cargarTramite",
                type:'get',
                dataType:'json',
                async:false,
                data:{idEtapa:$(idSlctOrig).val()},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    fill.combo(data,"idTramite","descripcion",idSlctDest);
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
        }
    },
    bandeja:{ //mdiosesf 
    	cargarEstadoProceso:function(idSlctDest){
    		$.ajax({
                url:baseURL + "pages/bandeja/cargarEstadoProceso",
                type:'get',
                dataType:'json',
                async:false,
                data:{},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();     
                    for (i=0; i<data.length;i++) { 
                    	if(data[i].identificadorEstado==constant.identificadorEstado.osRegistro || data[i].identificadorEstado==constant.identificadorEstado.osOficiado || data[i].identificadorEstado==constant.identificadorEstado.osConcluido) {                    	
                    		data.splice(i,1);  
                    		i--;
                    	}
                    }   
                    fill.comboValorId(data,"identificadorEstado","nombreEstado",idSlctDest,"-1");
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
    	}
    }, //mdiosesf 
    empresaSupervisora:{
    	cargarEntidad:function(idSlctDest){
    		$.ajax({
                url:baseURL + "pages/empresaSupervisora/cargarEntidad",
                type:'get',
                dataType:'json',
                async:false,
                data:{},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    fill.comboValorId(data,"codigo","descripcion",idSlctDest,"1");
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
    	},
    	cargarTipoEmpresaSupervisora:function(idSlctDest){
            $.ajax({
                url:baseURL + "pages/empresaSupervisora/cargarTipoEmpresaSupervisora",
                type:'get',
                dataType:'json',
                async:false,
                data:{},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    var html='<option value="">--Seleccione--</option>';
                    if (data != null) {
                        $.each(data,function(k,v){
                            html+='<option value="'+v['idMaestroColumna']+'" codigo="'+v['codigo']+'">'+v['descripcion']+'</option>';
                        });
                    }
                    $(idSlctDest).html(html);
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
        },
        
        cargarLocador:function(idSlctDest, idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito){ 
            $.ajax({
                url:baseURL + "pages/empresaSupervisora/cargarLocador",
                type:'get',
                dataType:'json',
                async:false,
                data:{
                    idObligacionTipo:idObligacionTipo, 
                    idProceso:idProceso,
                    idRubro:idRubro,
                    idDepartamento:idDepartamento,
                    idProvincia:idProvincia,
                    idDistrito:idDistrito
                },
                //data:{idEntidad:idEntidad},
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    fill.combo(data,"idLocador","nombreCompletoArmado",idSlctDest);
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
        },
        
        cargarSupervisoraEmpresa:function(idSlctDest,idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito){ 
            $.ajax({
                url:baseURL + "pages/empresaSupervisora/cargarSupervisoraEmpresa",
                type:'get',
                dataType:'json',
                async:false,
                data:{
                	idObligacionTipo:idObligacionTipo,
                	idProceso:idProceso,
                	idRubro:idRubro,
                	idDepartamento:idDepartamento,
                	idProvincia:idProvincia,
                	idDistrito:idDistrito
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    var html='<option value="">--Seleccione--</option>';
                    var descripcion = "";
                    if (data != null) {
                        $.each(data,function(k,v){
                        	if(v['razonSocial']!=null){descripcion=v['razonSocial'];}
                        	else if(v['nombreConsorcio']!=null){descripcion=v['nombreConsorcio'];}
                            html+='<option value="'+v['idSupervisoraEmpresa']+'">'+descripcion+'</option>';
                        });
                    }
                    $(idSlctDest).html(html);
                },
                error:function(jqXHR){errorAjax(jqXHR);}
            });
        },
        /* OSINE_SFS-1344 - Inicio */
        cargaDataUnidadOperativa : function(cadIdUnidadSupervisada,fill) {
            $.ajax({
                url : baseURL + "pages/ordenServicioGSM/cargaDataUnidadOperativa",
                type : 'get',
                async : false,
                dataType:'json',
                data : {
                    cadIdUnidadSupervisada : cadIdUnidadSupervisada
                },
                before:function(){loading.open();},
                success : function(data) {
                    loading.close();
                    fill(data);
                },
                error : function(jqXHR) {
                        errorAjax(jqXHR);
                }
            });
        }
        /* OSINE_SFS-1344 - Fin */
    },
    
    abrirBusquedaUnidadOperativa:function(prueba){
        $('#dialogBusquedaUnidadOperativa').dialog('open');
    	coBusUnidad.cargarDepartamento('#slctDepartamentoUnidad');
    	coBusUnidad.cargarProvincia("#slctProvinciaUnidad",$('#slctDepartamentoUnidad').val());
        coBusUnidad.cargarDistrito("#slctDistritoUnidad",$('#slctDepartamentoUnidad').val(), $('#slctProvinciaUnidad').val());
        
        $('#btnBusqUnidad').unbind();
        $('#btnBusqUnidad').bind('click',function(){
        	
            $('#dialogBusquedaUnidadOperativa').dialog('close');
        });   
        $('#btnCerrarBusqUnidad').bind('click',function(){
        	 $('#frmBusqUnidad').find('input').val("");
        });
    },
    /* OSINE_SFS-1344 - Inicio */
    abrirBusquedaExpediente:function(callback,bandeja){
        $('#dialogBusquedaExpediente').dialog('open');
        $('#btnBusqExpe').unbind();
        $('#btnBusqExpe').bind('click',function(){
            callback();
            $('#dialogBusquedaExpediente').dialog('close');
        });   
        $('#btnCerrarBusqExpe').bind('click',function(){
        	 $('#frmBusqExpe').find('input').val("");
        });
    },
    /* OSINE_SFS-1344 - Fin */
    abrirConsultaMensajeriaExpediente: function (){
        var title="CONSULTAR MENSAJERIA EXPEDIENTE";
        $.ajax({
            url:baseURL + "pages/bandeja/abrirConsMensajeriaExpediente",
            type:'get',
            async:false,
            data:{},
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogConsMensajeriaExpediente").html(data);
                $("#dialogConsMensajeriaExpediente").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });        
    },
    
    obligacionTipo:{
        obtenerObligacionSubTipo : function(idTipoSupervision,idSlctDest) {
            $.ajax({
                    url : baseURL + "pages/serie/listarObligacionSubTipo",
                    type : 'get',
                    async : false,
                    dataType:'json',
                    data : {
                        idObligacionTipo: idTipoSupervision
                    },
                    beforeSend:function(){loading.open();},
                    success : function(data) {
                        loading.close();
                        var html = '<option value="">--Seleccione--</option>';
                        var len = data.length;
                        for (var i = 0; i < len; i++) {
                                html += '<option value="' + data[i].idObligacionSubTipo + '"  codigo="'+data[i].identificadorSeleccion+'" >'
                                + data[i].nombre + '</option>';
                        }
                        $(idSlctDest).html(html);
                    },
                    error : function(jqXHR) {
                            errorAjax(jqXHR);
                    }
            });
        }
    },
    
    ordenServicio:{
    	/* OSINE_SFS-1344 - Inicio */ 
        abrirAtender:function(rowid,idGrid,flgOrigenExpediente){
            var title="ATENDER ORDEN DE SERVICIO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/ordenServicioGSM/abrirOrdenServicioAtender",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:flgOrigenExpediente,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'unidadSupervisada.actividad.idActividad':row['actividad.idActividad'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                    /* OSINE_SFS-1344 Inicio */
                    'unidadSupervisadaGSM.idDmUnidadSupervisada':row['unidadSupervisadaGSM.idDmUnidadSupervisada'],
                    'unidadSupervisadaGSM.codigoUnidadSupervisada':row['unidadSupervisadaGSM.codigoUnidadSupervisada'],
                    'unidadSupervisadaGSM.nombreUnidadSupervisada':row['unidadSupervisadaGSM.nombreUnidadSupervisada'],
                    'unidadSupervisadaGSM.idEstrato.descripcion':row['unidadSupervisadaGSM.idEstrato.descripcion'],
                    'titularMinero.idTitularMinero':row['titularMinero.idTitularMinero'],
                    'titularMinero.codigoTitularMinero':row['titularMinero.codigoTitularMinero'],
                    'titularMinero.nombreTitularMinero':row['titularMinero.nombreTitularMinero']                    
            		/* OSINE_SFS-1344 Fin */
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicio").html(data);
                    $("#dialogOrdenServicio").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                },
                error:errorAjax
            });
        },
        /* OSINE_SFS-1344 - Fin */ 
        abrirConsultar:function(rowid,idGrid,flgOrigenExpediente){
            var title="CONSULTAR ORDEN DE SERVICIO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioConsultar",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:flgOrigenExpediente,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicio").html(data);
                    $("#dialogOrdenServicio").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                },
                error:errorAjax
            });
        },
        abrirRevisar:function(rowid,idGrid,flgOrigenExpediente){
            var title="REVISAR ORDEN DE SERVICIO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioRevisar",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:flgOrigenExpediente,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],                    
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicio").html(data);
                    $("#dialogOrdenServicio").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                },
                error:errorAjax
            });
        },
        abrirAprobar:function(rowid,idGrid,flgOrigenExpediente){
            var title="APROBAR ORDEN DE SERVICIO";                
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/ordenServicioGSM/abrirOrdenServicioAprobar",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:flgOrigenExpediente,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicio").html(data);
                    $("#dialogOrdenServicio").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                },
                error:errorAjax
            });
        },
        abrirNotificar:function(rowid,idGrid,flgOrigenExpediente){
            var title="NOTIFICAR ORDEN DE SERVICIO";                
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioNotificar",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:flgOrigenExpediente,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicio").html(data);
                    $("#dialogOrdenServicio").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                },
                error:errorAjax
            });
        },
        abrirConcluir:function(rowid,idGrid,flgOrigenExpediente){
            var title="CONCLUIR ORDEN DE SERVICIO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioConcluir",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:flgOrigenExpediente,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicio").html(data);
                    $("#dialogOrdenServicio").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar"
                    });
                },
                error:errorAjax
            });
        },        
        
        abrirVerificarLevantamiento:function(rowid,idGrid){
            var title="VERIFICAR LEVANTAMIENTO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioLevantamiento",
                type:'POST',
                async:false,
                data:{
                    rolSesion:$('#rolSesion').val(),
                    idExpediente:row.idExpediente,
                    iteracionExpediente:row.iteracionExpediente,
                    flagOrigen:row.flagOrigen,
                    numeroExpediente:row.numeroExpediente,
                    asuntoSiged:row.asuntoSiged,
                    flagMuestral:row.flagMuestral,
                    'obligacionTipo.idObligacionTipo':row['obligacionTipo.idObligacionTipo'],
                    'unidadSupervisada.idUnidadSupervisada':row['unidadSupervisada.idUnidadSupervisada'],
                    'unidadSupervisada.codigoOsinergmin':row['unidadSupervisada.codigoOsinergmin'],
                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
                    'flujoSiged.idFlujoSiged':row['flujoSiged.idFlujoSiged'],
                    'ordenServicio.idOrdenServicio':row['ordenServicio.idOrdenServicio'],
                    'ordenServicio.numeroOrdenServicio':row['ordenServicio.numeroOrdenServicio'],
                    'ordenServicio.idTipoAsignacion':row['ordenServicio.idTipoAsignacion'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],                    
                    'tramite.idTramite':row['tramite.idTramite'],
                    'tramite.idEtapa':row['tramite.idEtapa'],
                    'tramite.idProceso':row['tramite.idProceso'],
                    'proceso.idProceso':row['proceso.idProceso'],
                    'ordenServicio.locador.idLocador':row['ordenServicio.locador.idLocador'],
                    'ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa':row['ordenServicio.supervisoraEmpresa.idSupervisoraEmpresa'],
                    'ordenServicio.iteracion':row['ordenServicio.iteracion'],
                    'ordenServicio.fechaCreacionOS':row['ordenServicio.fechaCreacionOS'],
                    'ordenServicio.fechaHoraCreacionOS':row['ordenServicio.fechaHoraCreacionOS'],
                    'ordenServicio.fechaHoraAnalogicaCreacionOS':row['ordenServicio.fechaHoraAnalogicaCreacionOS'],
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                    'ordenServicio.flagConfirmaTipoAsignacion':row['ordenServicio.flagConfirmaTipoAsignacion'],
                    'flagEvaluaTipoAsignacion':row['flagEvaluaTipoAsignacion']
                },
                beforeSend:loading.open,
                success:function(data){
                    loading.close();
                    $("#dialogOrdenServicioLevantamiento").html(data);
                    $("#dialogOrdenServicioLevantamiento").dialog({
                        resizable: false,
                        draggable: true,
                        autoOpen: true,
                        height:"auto",
                        width: "auto",
                        modal: true,
                        position:"top",
                        dialogClass: 'dialog',
                        title: title,
                        closeText: "Cerrar",
                        close: function() {
                            $("#dialogOrdenServicioLevantamiento").dialog("destroy");
                        }
                    });
                },
                error:errorAjax
            });
        }      
    },
          
    abrirRegistroMedioProbatorios:function(idDetalleSupervision){
    	var title="AGREGAR MEDIO PROBATORIO";
    	$.ajax({
            url:baseURL + "pages/bandeja/abrirRegistroMedioProbatorios",
            type:'get',
            async:false,
            data:{
            	idDetalleSupervision:idDetalleSupervision
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogMediosProbatorios").html('');
                $("#dialogMediosProbatorios").html(data);
                $("#dialogMediosProbatorios").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    },
    
    abrirRegistroArchivosAdjuntos:function(idSupervision){
    	var title="AGREGAR ADJUNTO";
    	$.ajax({
            url:baseURL + "pages/bandeja/abrirAdjuntarArchivosSupervision",
            type:'get',
            async:false,
            data:{
            	idSupervision:idSupervision
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogAgregarAdjunto").html('');
                $("#dialogAgregarAdjunto").html(data);
                $("#dialogAgregarAdjunto").dialog({
                    resizable: false,
                    draggable: true,
                    autoOpen: true,
                    height:"auto",
                    width: "auto",
                    modal: true,
                    dialogClass: 'dialog',
                    title: title,
                    closeText: "Cerrar"
                });
            },
            error:errorAjax
        });
    }
        
};

//init
$(function(){
    $("#dialogBusquedaExpediente").dialog({
        resizable: false,
        autoOpen: false,
        height:"auto",
        width: "auto",
        modal: true,
        title: "BÚSQUEDA EXPEDIENTES",
        close:function(){
            $('#frmBusqExpe').find('input').val("");
            $('#slctEmprSupeOSBusqExpe,#slctTipoEmprSupeBusqExpe').val("");
            $('#divNumeOSBusqExpe').css('display','');
            $('#divEmprSupeBusqExpe').css('display','');
            $('#divNumeroRH').css('display','none');
            $('#divUbigeo').css('display','none');
            $('#slctDepartamento,#slctProvincia,#slctDistrito').val("");
            $('#divPeticion').css('display','none');
            $('#slctPeticion').val("");
        }
    });
    // htorress - RSIS 13 - Inicio
    $("#divVersionesDocumento").dialog({
        resizable: false,
        draggable: true,
        autoOpen: false,
        height:"auto",
        width: "auto",
        modal: true,
        dialogClass: 'dialog',
        title: "CONSULTAR VERSIONES DOCUMENTOS",
        closeText: "Cerrar"
    });
    
    $("#dialogBusquedaUnidadOperativa").dialog({
        resizable: false,
        autoOpen: false,
        height:"auto",
        width: "auto",
        modal: true,
        title: "BUSQUEDA DE UNIDAD OPERATIVA",
        close:function(){
            $('#frmBusqUnidad').find('input').val("");
	        $('#slctDepartamentoUnidad,#slctProvinciaUnidad,#slctDistritoUnidad').val("");
        }
    });
});

jQuery.extend($.fn.fmatter, {
	descargarFileExpediente: function(cellvalue, options, rowdata) {
        var estadoOrigen=rowdata.estadoOrigen;
        var html = '';
        var nombre=rowdata.nombreArchivo;
        if(estadoOrigen==constant.estadoOrigen.archivoSiged){
            var idArchivo=rowdata.idArchivo;
            if (nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null){       
                html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
                  '<img class="vam" width="17" height="18" src="'+baseURL+'images/stickers.png">'+
                '</a>';
            }
        }else if(estadoOrigen==constant.estadoOrigen.archivoInps){
            var idDocumentoAdjunto=rowdata.idDocumentoAdjunto;
            if (nombre != null && nombre != '' && idDocumentoAdjunto!='' && idDocumentoAdjunto!=null){       
                html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto='+idDocumentoAdjunto+'">'+
                  '<img class="vam" width="17" height="18" src="'+baseURL+'images/stickers.png">'+
                '</a>';
            }
        }
        
        return html;
    }
});

jQuery.extend($.fn.fmatter, {
    nombreCompletoPersOriTraza: function(cellvalue, options, rowdata) {
        var retorno=rowdata.personalOri.nombre==null?"":rowdata.personalOri.nombre+" ";
        retorno+=rowdata.personalOri.apellidoPaterno==null?"":rowdata.personalOri.apellidoPaterno+" ";
        retorno+=rowdata.personalOri.apellidoMaterno==null?"":rowdata.personalOri.apellidoMaterno;
	return retorno;
    }
});
jQuery.extend($.fn.fmatter, {
    nombreCompletoPersDestTraza: function(cellvalue, options, rowdata) {
        var retorno=rowdata.personalDest.nombre==null?"":rowdata.personalDest.nombre+" ";
        retorno+=rowdata.personalDest.apellidoPaterno==null?"":rowdata.personalDest.apellidoPaterno+" ";
        retorno+=rowdata.personalDest.apellidoMaterno==null?"":rowdata.personalDest.apellidoMaterno;
	return retorno;
    }
});

jQuery.extend($.fn.fmatter, {
	descripcionFirmado: function(cellvalue, options, rowdata) {
        var retorno="";
        if(rowdata.firmado=='S'){
        	retorno='SI';
        }else if(rowdata.firmado=='N'){
        	retorno='NO';
        }
	return retorno;
    }
});
