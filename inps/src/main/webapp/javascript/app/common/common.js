/**
* Resumen		
* Objeto		: common.js
* Descripción		: JavaScript donde se centraliza las acciones de los diferentes formularios del Inps.
* Fecha de Creación	: 25/03/2016
* PR de Creación	: OSINE_SFS-480
* Autor			: GMD
* =====================================================================================================================================================================
* Modificaciones |
* Motivo         |  Fecha        |   Nombre                     |     Descripción
* =====================================================================================================================================================================
* OSINE_SFS-480  |  10/05/2016   |   Mario Dioses Fernandez     |     Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica)
* OSINE_SFS-480  |  11/05/2016   |   Hernán Torres Saenz        |     Mostrar campos Nro. Documento y Firmado (Si/No) de archivos
* OSINE_SFS-480  |  11/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección asignaciones,evaluación y notificación/archivado 
* OSINE_SFS-480  |  11/05/2016   |   Hernán Torres Saenz        |     Cargar Lista de Obligaciones luego de Registrar la Fecha-Hora de Inicio del "Registro de Supervisión"
* OSINE_SFS-480  |  12/05/2016   |   Hernan Torres Saenz        |     Agregar criterios al filtro de búsqueda en la sección supevisión de la bandeja del supervisor.Implementar los criterios de búsqueda agregados en la sección supevisión de la bandeja del especialista
* OSINE_SFS-480  |  17/05/2016   |   Hernan Torres Saenz        |     Crear la interfaz para filtros de búsqueda en la funcionalidad "Generar orden de servicio"
* OSINE_SFS-480  |  19/05/2016   |   Hernán Torres Saenz        |     Adecuar  interfaz para la nueva forma de generación de órdenes de servicio (masivo).
* OSINE_SFS-480  |  20/05/2016   |   Hernán Torres Saenz        |     Crear la opción "Anular orden de servicio" en la pestaña asigaciones  de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* OSINE_SFS-480  |  20/05/2016   |   Hernán Torres Saenz        |     Crear la interfaz "Anular orden de servicio" de acuerdo a especificaciones.
* OSINE_SFS-480  |  25/05/2016   |   Mario Dioses Fernandez     |     Crear la opción "Editar" en pestaña asignaciones de la bandeja del especialista el cual direccionará a la interfaz "Anular orden de servicio"
* OSINE_SFS-480  |  27/05/2016   |   Luis García Reyna          |     Funcionalidad de Datos de Mensajeria: Datos de Envio, Datos del Cargo
* OSINE791       |  17/08/2016   |   Yadira Piskulich           |     Abrir Supervision DSR
* OSINE_791-RSIS8|  31/08/2016   |   Cristopher Paucar Torre    |     Registrar Medio Probatorio.
* OSINE_SFS-791  |  06/10/2016   |   Luis García Reyna          |     Registrar Supervisión No Iniciada
* OSINE_791-RSIS34| 10/10/2016   |   Cristopher Paucar Torre    |     Editar Orden de Servicio "Cambiar tipo de Asignación".
*/
 
//common/common.js
var common={
    procesarGridFilesExpediente:function(numeroExpediente,idOrdenServicio,nombreGrid,tipoAccion) {
        var nombres=[];
        var columnas=[];
        var url="";
        var flgCargaTemporales="0";
        switch (tipoAccion){
            case "atender":
            	// htorress - RSIS 9 - Inicio
                //nombres = ['idArchivo','idDocumentoAdjunto','TIPO DOCUMENTO','NOMBRE','FECHA CARGA','DESCARGAR','ESTADO'];
            	/* OSINE_SFS-480 - RSIS 08 - Inicio */
            	//nombres = ['idArchivo','idDocumentoAdjunto','idTipoDocumento','DOCUMENTO','NOMBRE','FECHA CARGA','DESCARGAR','ESTADO'];
            	nombres = ['idArchivo','idDocumentoAdjunto','idTipoDocumento','DOCUMENTO','NRO. DOCUMENTO','FIRMADO','NOMBRE','FECHA CARGA','DESCARGAR','ESTADO'];
            	/* OSINE_SFS-480 - RSIS 08 - Fin */
                // htorress - RSIS 9 - Fin
                columnas = [
                    {name: "idArchivo", hidden:true},//siged
                    // htorress - RSIS 9 - Inicio
                    //{name: "idDocumentoAdjunto",hidden:true},//bd
                    {name: "idDocumento",hidden:true},
                    {name: "idTipoDocumento.codigo",hidden:true},
                    // htorress - RSIS 9 - Fin
                    /* OSINE_SFS-480 - RSIS 08 - Inicio */
                    //{name: "idTipoDocumento.descripcion", width: 300, sortable: false, align: "left"},
                    {name: "idTipoDocumento.descripcion", width: 240, sortable: false, align: "left"},
                    {name: "nroDocumento", width: 70, sortable: false, align: "left"},
                    {name: "firmado", width: 50, sortable: false, align: "left",formatter: "descripcionFirmado"},
                    //{name: "nombreArchivo", width: 300, sortable: false, align: "left"},
                    {name: "nombreArchivo", width: 240, sortable: false, align: "left"},
                    /* OSINE_SFS-480 - RSIS 08 - Fin */
                    {name: "fechaCarga", width: 120, sortable: false, align: "left",formatter:fecha_hora},
                    {name: "",width: 60,sortable: false, align: "center",formatter: "descargarFileExpediente"},
                    {name: "estadoOrigen",width: 60,sortable: false, align: "center",hidden:true}
                ];
                url=baseURL + "pages/archivo/findArchivoExpediente";
                flgCargaTemporales="1";
                break;
            default :
            	// htorress - RSIS 9 - Inicio
                //nombres = ['idArchivo','idDocumentoAdjunto','TIPO DOCUMENTO','NOMBRE','FECHA CARGA','DESCARGAR'];
            	/* OSINE_SFS-480 - RSIS 08 - Inicio */
            	//nombres = ['idArchivo','idDocumentoAdjunto','idTipoDocumento','TIPO DOCUMENTO','NOMBRE','FECHA CARGA','DESCARGAR'];
            	nombres = ['idArchivo','idDocumentoAdjunto','idTipoDocumento','TIPO DOCUMENTO','NRO. DOCUMENTO','FIRMADO','NOMBRE','FECHA CARGA','DESCARGAR'];
            	/* OSINE_SFS-480 - RSIS 08 - Fin */
            	// htorress - RSIS 9 - Fin
                columnas = [
                    {name: "idArchivo", hidden:true},//siged
                    // htorress - RSIS 9 - Inicio
                    //{name: "idDocumentoAdjunto",hidden:true},//bd
                    {name: "idDocumento",hidden:true},
                    {name: "idTipoDocumento.codigo",hidden:true},
                    // htorress - RSIS 9 - Fin
                    /* OSINE_SFS-480 - RSIS 08 - Inicio */
                    //{name: "idTipoDocumento.descripcion", width: 300, sortable: false, align: "left"},
                    {name: "idTipoDocumento.descripcion", width: 240, sortable: false, align: "left"},
                    {name: "nroDocumento", width: 70, sortable: false, align: "left"},
                    {name: "firmado", width: 50, sortable: false, align: "left",formatter: "descripcionFirmado"},
                    //{name: "nombreArchivo", width: 300, sortable: false, align: "left"},
                    {name: "nombreArchivo", width: 240, sortable: false, align: "left"},
                    /* OSINE_SFS-480 - RSIS 08 - Fin */
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
                // htorress - RSIS 9 - Inicio
                //id: "idDocumentoAdjunto"
                id: "idArchivo"
                // htorress - RSIS 9 - Fin
            },
            onSelectRow: function(rowid, status) {
                grid.resetSelection();
            },
            onRightClickRow: function(rowid) {            	
                var row=$('#grid'+nombreGrid).getRowData(rowid); 
                // htorress - RSIS 9 - Inicio
                //$('#linkEliminarDocumentoAdjunto').css('display','none');
                $('#linkCargarArchivo').css('display','none');
                $('#linkVersionarArchivo').css('display','none');
                // htorress - RSIS 9 - Fin
                // htorress - RSIS 13 - Inicio
                $('#linkConsultarVersionesArchivo').css('display','none');
                // htorress - RSIS 13 - Fin
                // htorress - RSIS 15 - Inicio
                $('#linkEnumerarDocumento').css('display','none');
                $('#linkFirmarEnumerarDocumento').css('display','none');
                $('#linkAnularArchivo').css('display','none');
                if($('#rolSesion').val()=='SUPERVISOR'){
                //if(row['estadoOrigen']=='TEMPORAL'){
                // htorress - RSIS 15 - Fin
                	$('#contextMenu'+nombreGrid).parent().css('opacity','1');
                	// htorress - RSIS 9 - Inicio
                	//$('#linkEliminarDocumentoAdjunto').css('display','');
                    $('#linkVersionarArchivo').css('display','');
                    // htorress - RSIS 9 - Fin
                    // htorress - RSIS 15 - Inicio
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
                    // htorress - RSIS 15 - Fin
                    // htorress - RSIS 9 - Inicio
                    $('#linkCargarArchivo').attr('onClick', "common.fxGestionarArchivo.cargarArchivo('"+ rowid +"','"+nombreGrid+"')");
                    $('#linkVersionarArchivo').attr('onClick', "common.fxGestionarArchivo.versionarArchivo('" + rowid  +"','"+nombreGrid+ "')");
                    // htorress - RSIS 9 - Fin
                // htorress - RSIS 13 - Inicio
                    $('#linkConsultarVersionesArchivo').attr('onClick', 'common.fxGestionarArchivo.consultarVersionesArchivo(' + numeroExpediente +','+row['idDocumento'] + ')');
                //$('#linkEliminarDocumentoAdjunto').attr('onClick', 'common.fxEliminarDocumento.validaEliminarDocumentoAdjunto(' + rowid + ','"+nombreGrid + "','"+tipoAccion+"','"+numeroExpediente+"','"+idOrdenServicio+"')');
                // htorress - RSIS 13 - Fin
                
            },
            loadComplete: function(data){
            	
            	$('#contextMenu'+nombreGrid).parent().remove();
                $('#divContextMenu'+nombreGrid).html("<ul id='contextMenu"+nombreGrid+"'>"
                		// htorress - RSIS 9 - Inicio
                        //+ "<li> <a id='linkEliminarDocumentoAdjunto' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
                        + "<li> <a id='linkAnularArchivo' data-icon='ui-icon-trash' title='Anular'>Anular</a></li>"
                        + "<li> <a id='linkCargarArchivo' data-icon='ui-icon-document' title='Cargar Archivo al Documento'>Cargar Archivo al Documento</a></li>"
                        + "<li> <a id='linkVersionarArchivo' data-icon='ui-icon-pencil' title='Versionar'>Versionar</a></li>"
                        + "<li> <a id='linkConsultarVersionesArchivo' data-icon='ui-icon-search' title='Consultar Versiones'>Consultar Versiones</a></li>"
                        + "<li> <a id='linkEnumerarDocumento' data-icon='ui-icon-squaresmall-plus' title='Enumerar'>Enumerar</a></li>"
                        + "<li> <a id='linkFirmarEnumerarDocumento' data-icon='ui-icon-key' title='Fimar/Enumerar'>Fimar/Enumerar</a></li>"
                        // htorress - RSIS 9 - Fin
                    + "</ul>");
                $('#contextMenu'+nombreGrid).puicontextmenu({
                    target: $('#grid'+nombreGrid)
                });
            }
        });
    },
    // htorress - RSIS 9 - Inicio
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
            url: baseURL + "pages/ordenServicio/findOrdenServicio",
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
            /* OSINE_SFS-480 - RSIS 41 - Inicio */
            case constant.accionOS.anular :
                title="ANULAR ORDEN DE SERVICIO";break;
            /* OSINE_SFS-480 - RSIS 41 - Fin */
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
                'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                /* OSINE_SFS-480 - RSIS 42 - Inicio */
                'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],               
                /* OSINE_SFS-480 - RSIS 42 - Fin */
                /* OSINE_SFS-791 - RSIS 34 - Inicio */
                'flagEvaluaTipoAsignacion':row['flagEvaluaTipoAsignacion'],
                /* OSINE_SFS-791 - RSIS 34 - Fin */
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
    /* OSINE_SFS-480 - RSIS 27 - Inicio */
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
            url:baseURL + "pages/bandeja/abrirOrdenServicioMasivo",
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
    /* OSINE_SFS-480 - RSIS 27 - Fin */
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
    abrirMantUnidadOperativa:function(tipo,ruc,rs,idEmpresaSupervisada){
    	var title="";
        if(tipo=="new"){
            title="CREAR UNIDAD OPERATIVA";
        }
        $.ajax({
            url:baseURL + "pages/bandeja/abrirMantUnidadOperativa",
            type:'get',
            async:false,
            data:{
                tipo:tipo,
                'empresaSupervisada.ruc':ruc,
                'empresaSupervisada.razonSocial':rs,
                idEmpresaSupervisada:idEmpresaSupervisada
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $("#dialogMantUnidadOperativa").html(data);
                $("#dialogMantUnidadOperativa").dialog({
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
    // htorress - RSIS 8 - Inicio
    abrirSubirDocumento:function(numeroExpediente,idOrdenServicio,idExpediente,asuntoSiged,codigoSiged,idDirccionUnidadSuprvisada,idUnidadSupervisada){
    // htorress - RSIS 8 - Fin
        var title="SUBIR DOCUMENTO";
		
        $.ajax({
            url:baseURL + "pages/bandeja/abrirSubirDocumento",
            type:'get',
            async:false,
            data:{
                numeroExpediente:numeroExpediente,
                idOrdenServicio:idOrdenServicio,
                // htorress - RSIS 8 - Inicio
//            	idEmpresaSupervisada:idEmpresaSupervisada,
            	idExpediente:idExpediente,
            	asuntoSiged:asuntoSiged,
            	codigoSiged:codigoSiged,
            	// htorress - RSIS 8 - Fin
                idDirccionUnidadSuprvisada:idDirccionUnidadSuprvisada,
                idUnidadSupervisada:idUnidadSupervisada
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
    /* OSINE791 - RSIS2 - Inicio */
    //abrirSupervision:function(tipo,idOrdenServicio,rol,tipoEmpresa,empresaSupervisora,flagPopup,idExpediente,idObligacionTipo,idProceso,idActividad){
    abrirSupervision:function(tipo,idOrdenServicio,rol,tipoEmpresa,empresaSupervisora,flagPopup,idExpediente,idObligacionTipo,idProceso,idActividad,iteracion){        
    /* OSINE791 - RSIS2 - Fin */
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
                        /* OSINE_SFS-480 - RSIS 13 - Fin */
                        iteracion:iteracion,
                        'expediente.idExpediente':idExpediente,
                        /* OSINE_SFS-480 - RSIS 13 - Inicio */
                        'expediente.obligacionTipo.idObligacionTipo':idObligacionTipo,
                        'expediente.proceso.idProceso':idProceso,
                        'expediente.unidadSupervisada.actividad.idActividad':idActividad
    		},
    		beforeSend:loading.open,
    		success:function(data){
    			loading.close();
    			if(flagPopup==constant.estado.activo){
    				$("#dialogSupervision").html(data);
    				/* OSINE_SFS-480 - RSIS 13 - Inicio */
    				if($('#idSupervision').val() == null || $('#idSupervision').val() == ''){
    					$('#fldstDatosSuper .pui-fieldset-content').show();
    				}
    				/* OSINE_SFS-480 - RSIS 13 - Fin */
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
    abrirVerAdjuntoSupe:function(tipo,idSupervision){
    	var title="ARCHIVOS ADJUNTOS";
    	$.ajax({
            url:baseURL + "pages/bandeja/verAdjuntoSupervision",
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
    /* OSINE_SFS-480 - RSIS 44 - Inicio */
    abrirEnviarMensajeria:function (rowid, idGrid, flagNotificar) { 
    	//flagNotificar > (1) cuando viene de la bandeja evaluacion, notificar.
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
    /* OSINE_SFS-480 - RSIS 44 - Fin */
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
        /* OSINE_SFS-480 - RSIS 11 - Inicio */
        cargarLocador:function(idSlctDest, idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito){ 
            $.ajax({
                url:baseURL + "pages/empresaSupervisora/cargarLocador",
                type:'get',
                dataType:'json',
                async:false,
                data:{  
                    /* OSINE_SFS-480 - RSIS 11 - Inicio */
                    idObligacionTipo:idObligacionTipo, 
                    idProceso:idProceso,
                    idRubro:idRubro,
                    idDepartamento:idDepartamento,
                    idProvincia:idProvincia,
                    idDistrito:idDistrito 
                    /* OSINE_SFS-480 - RSIS 11 - Fin */
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
        /* OSINE_SFS-480 - RSIS 11 - Fin */
        cargarSupervisoraEmpresa:function(idSlctDest,idObligacionTipo, idProceso, idRubro, idDepartamento, idProvincia, idDistrito){ 
            $.ajax({
                url:baseURL + "pages/empresaSupervisora/cargarSupervisoraEmpresa",
                type:'get',
                dataType:'json',
                async:false,
                data:{
                    /* OSINE_SFS-480 - RSIS 11 - Inicio */
                	idObligacionTipo:idObligacionTipo,
                	idProceso:idProceso,
                	idRubro:idRubro,
                	idDepartamento:idDepartamento,
                	idProvincia:idProvincia,
                	idDistrito:idDistrito 
                    /* OSINE_SFS-480 - RSIS 11 - Fin */
                },
                //data:{idEntidad:idEntidad},
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
        cargaDataUnidadOperativa : function(cadIdUnidadSupervisada,fill) {
            $.ajax({
                url : baseURL + "pages/unidadSupervisada/cargaDataUnidadOperativa",
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
    },
    /* OSINE_SFS-480 - RSIS 33 - Inicio */
    abrirBusquedaUnidadOperativa:function(prueba){
    	//supeAsigAsig.procesarGridBusUnidadOperativa
        $('#dialogBusquedaUnidadOperativa').dialog('open');
    	coBusUnidad.cargarDepartamento('#slctDepartamentoUnidad');
    	coBusUnidad.cargarProvincia("#slctProvinciaUnidad",$('#slctDepartamentoUnidad').val());
        coBusUnidad.cargarDistrito("#slctDistritoUnidad",$('#slctDepartamentoUnidad').val(), $('#slctProvinciaUnidad').val());
        
        $('#btnBusqUnidad').unbind();
        $('#btnBusqUnidad').bind('click',function(){
            //callback();
        	prueba('1');
        	
            $('#dialogBusquedaUnidadOperativa').dialog('close');
        });   
        $('#btnCerrarBusqUnidad').bind('click',function(){
        	 $('#frmBusqUnidad').find('input').val("");
        });
    },
    /* OSINE_SFS-480 - RSIS 33 - Fin */
    abrirBusquedaExpediente:function(callback,bandeja){
    	debugger;
        $('#dialogBusquedaExpediente').dialog('open');
        $("#slctEstadoCumplimiento").val("");
        fill.clean('#slctEmprSupeOSBusqExpe');
        common.empresaSupervisora.cargarEntidad('#slctEntidadOSBusqExpe');
        common.empresaSupervisora.cargarTipoEmpresaSupervisora('#slctTipoEmprSupeBusqExpe');
        common.bandeja.cargarEstadoProceso('#slctEstadoProceso'); //mdiosesf
        /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
	        coExpeBuEx.cargarDepartamento('#slctDepartamento');
	        coExpeBuEx.cargarProvincia("#slctProvincia",$('#slctDepartamento').val());
	        coExpeBuEx.cargarDistrito("#slctDistrito",$('#slctDepartamento').val(), $('#slctProvincia').val());
        	coExpeBuEx.cargarPeticion('#slctPeticion');
        	coExpeBuEx.cargarEstadoOS('#slctEstadoOS');
        /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
        switch (bandeja){
            case constant.identificadorBandeja.espDerivados :
                $('#divNumeOSBusqExpe').css('display','none');
                $('#divEmprSupeBusqExpe').css('display','none');
                $('#divEstadoProceso').css('display','none');
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
                $('#divNumeroRH').css('display','none');
                $('#divUbigeo').css('display','none');
                $('#divPeticion').css('display','none');
                $("#divFechaOrdenServicio").css('display','none');
	            $("#divEstadoOS").css('display','none');
	            $("#divEstadoCumplimiento").css('display','none');
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
                break;
            case constant.identificadorBandeja.resRecepcion :
                $('#divNumeOSBusqExpe').css('display','none');
                $('#divEmprSupeBusqExpe').css('display','none');
                $('#divEstadoProceso').css('display','none');
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
                $('#divNumeroRH').css('display','none');
                $('#divUbigeo').css('display','none');
                $('#divPeticion').css('display','none');
                $("#divFechaOrdenServicio").css('display','none');
	            $("#divEstadoOS").css('display','none');
	            $("#divEstadoCumplimiento").css('display','none');
                /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
                break;
            case constant.identificadorBandeja.supAsignaciones :
                $('#divEmprSupeBusqExpe').css('display','none');  
                $('#divEstadoProceso').css('display','none');
                /* OSINE_SFS-480 - RSIS 32 - Inicio */
                $('#divNumeroRH').css('display','inline');
                $('#divUbigeo').css('display','inline');
                $("#divFechaOrdenServicio").css('display','none');
	            $("#divEstadoOS").css('display','none');
	            $("#divEstadoCumplimiento").css('display','none');
                /* OSINE_SFS-480 - RSIS 32 - Fin */
                break;
            case constant.identificadorBandeja.espEvaluacion :
            	if($('#rolSesion').val()==constant.rol.jefeUnidad || $('#rolSesion').val()==constant.rol.jefeRegional)
                    $('#divEstadoProceso').css('display','inline');
            	 /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
 	            $('#divNumeroRH').css('display','inline');
 	            $('#divUbigeo').css('display','inline');
 	           $("#divFechaOrdenServicio").css('display','none');
	            $("#divEstadoOS").css('display','none');
	            $("#divEstadoCumplimiento").css('display','none');
             	 /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */ 	           
            	 break;
            /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
            case constant.identificadorBandeja.espAsignaciones :
           	 	$('#divEstadoProceso').css('display','none');
	            $('#divNumeroRH').css('display','inline');
	            $('#divUbigeo').css('display','inline');
	            $('#divPeticion').css('display','inline');
	            $("#divFechaOrdenServicio").css('display','none');
	            $("#divEstadoOS").css('display','none');
	            $("#divEstadoCumplimiento").css('display','none');
           	 	break;	
           	/* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
            case constant.identificadorBandeja.espLegal :
           	 	$('#divEstadoProceso').css('display','none');
	            $('#divNumeroRH').css('display','inline');
	            $('#divUbigeo').css('display','inline');
	            $('#divPeticion').css('display','inline');
	            $("#divFechaOrdenServicio").css('display','inline');
	            $("#divEstadoOS").css('display','inline');
	            $("#divEstadoCumplimiento").css('display','inline');
           	 	break;	
            default :
            	$('#divEstadoProceso').css('display','none');
            	/* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
	            $('#divNumeroRH').css('display','inline');
	            $('#divUbigeo').css('display','inline');
	            $("#divFechaOrdenServicio").css('display','none');
	            $("#divEstadoOS").css('display','none');
	            $("#divEstadoCumplimiento").css('display','none');
            	/* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
                break;
        }
        $('#btnBusqExpe').unbind();
        $('#btnBusqExpe').bind('click',function(){
            callback();
            $('#dialogBusquedaExpediente').dialog('close');
        });   
        $('#btnCerrarBusqExpe').bind('click',function(){
        	 $('#frmBusqExpe').find('input').val("");
             $('#slctEmprSupeOSBusqExpe,#slctTipoEmprSupeBusqExpe').val("");
        });
    },
    /* OSINE_SFS-480 - RSIS 06 - Inicio */ 
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
    /* OSINE_SFS-480 - RSIS 06 - Fin */ 
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
        abrirAsignar:function(rowid,idGrid,flgOrigenExpediente){
            console.log("--1");
            var title="ASIGNAR ORDEN DE SERVICIO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }    
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioAsignar",
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
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
        abrirAtender:function(rowid,idGrid,flgOrigenExpediente){
            var title="ATENDER ORDEN DE SERVICIO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioAtender",
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo']                    
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                    /* OSINE_SFS-480 - RSIS 42 - Inicio */
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                    'comentarios':row['comentarios']
                    /* OSINE_SFS-480 - RSIS 42 - Fin */
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                url:baseURL + "pages/bandeja/abrirOrdenServicioAprobar",
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
                    /* OSINE_SFS-480 - RSIS 42 - Inicio */
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                    /* OSINE_SFS-480 - RSIS 42 - Fin */
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
        abrirConfirmarDescargo:function(rowid,idGrid,flgOrigenExpediente){
            var title="CONFIRMAR DESCARGO";
            var row=$('#'+idGrid).jqGrid("getRowData",rowid);
            if(flgOrigenExpediente==undefined || flgOrigenExpediente==''){
                flgOrigenExpediente=row.flagOrigen;
            }
            $.ajax({
                url:baseURL + "pages/bandeja/abrirOrdenServicioConfirmarDescargo",
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
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
                    /* OSINE_SFS-480 - RSIS 42 - Inicio */
                    'ordenServicio.idPeticion':row['ordenServicio.idPeticion'],
                    'ordenServicio.idMotivo':row['ordenServicio.idMotivo'],
                    'ordenServicio.comentarioDevolucion':row['ordenServicio.comentarioDevolucion'],
                    'obligacionSubTipo.idObligacionSubTipo':row['obligacionSubTipo.idObligacionSubTipo'],
                    /* OSINE_SFS-480 - RSIS 42 - Fin */
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
        /* OSINE_SFS-791 - RSIS 33 - Inicio */
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
                    'unidadSupervisada.nombreUnidad':row['unidadSupervisada.nombreUnidad'],
                    'unidadSupervisada.ruc':row['unidadSupervisada.ruc'],
//                    'empresaSupervisada.razonSocial':row['empresaSupervisada.razonSocial'],
//                    'empresaSupervisada.idEmpresaSupervisada':row['empresaSupervisada.idEmpresaSupervisada'],
//                    'empresaSupervisada.ruc':row['empresaSupervisada.ruc'],
//                    'empresaSupervisada.tipoDocumentoIdentidad.descripcion':row['empresaSupervisada.tipoDocumentoIdentidad.descripcion'],
//                    'empresaSupervisada.nroIdentificacion':row['empresaSupervisada.nroIdentificacion'],
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
        /* OSINE_SFS-791 - RSIS 33 - Inicio */       
    },
    /* OSINE_SFS-791 - RSIS 8 - Inicio */       
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
    /*<!--  OSINE791 - RSIS8 - Fin -->*/
    },
    
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
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
    /*OSINE_SFS-791 - RSIS 03 - Inicio */
    
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
            /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Inicio */
            $('#divNumeroRH').css('display','none');
            $('#divUbigeo').css('display','none');
            $('#slctDepartamento,#slctProvincia,#slctDistrito').val("");
            $('#divPeticion').css('display','none');
            $('#slctPeticion').val("");
	    /* OSINE_SFS-480 - RSIS 29, 30 y 31 - Fin */
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
    // htorress - RSIS 13 - Fin
    /* OSINE_SFS-480 - RSIS 33 - Inicio */   
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
    /* OSINE_SFS-480 - RSIS 33 - Fin */
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
/* OSINE_SFS-480 - RSIS 08 - Inicio */
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
/* OSINE_SFS-480 - RSIS 08 - Fin */
