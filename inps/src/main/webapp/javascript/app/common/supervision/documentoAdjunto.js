//variables globales
var modoDA;
var coSupeDocuAdju=(function() {
	function constructor() {
		modoDA = $('#modoDASuper').val();
		comportamiento();
		grillaAdjuntosSupervision();
	}
	
	function comportamiento(){
		$("#fileDASuper").change(function(){      
	        $("#file_nameDASuper").val(quitaSlashDir($("#fileDASuper").val())); 
	    });
		$('#btnAgregarDASuper').click(function() {
			validarAgregarDocumentoAdjunto();
        });
		//Formularios
		$("#formDocumentoAdjuntoSuper").ajaxForm({
			beforeSubmit: function () {loading.open();},
            dataType: 'json',
            resetForm: true,
            success: function(data) {
            	loading.close();
                if (data != null && data.error != null) {enviarDatosArchivoBL(data);}
            },
            error: errorAjax
        });
	}
	function grillaAdjuntosSupervision(){
		var nombres = ['idDocumentoAdjunto','DESCRIPCI&Oacute;N','ARCHIVO','DESCARGAR'];
        var columnas = [
            {name: "idDocumentoAdjunto",hidden: true},
            {name: "descripcionDocumento", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "nombreArchivo", hidden: false, width: 200, sortable: false, align: "center"},
            {name: "idDocumentoAdjunto",width: 60,sortable: false,align: "center",formatter: descargarDocumentoAdjunto},
            ];
        $("#gridContenedorDASuper").html("");
        var grid = $("<table>", {
            "id": "gridAdjuntoSupervision"
        });
        var pager = $("<div>", {
            "id": "paginacionAdjuntoSupervision"
        });
        $("#gridContenedorDASuper").append(grid).append(pager);
        grid.jqGrid({
            url: baseURL + "pages/archivo/findPghDocumentoAdjunto",
            datatype: "json",
            postData: {
            	idSupervision:$('#idSupervisionDA').val()
            },
            hidegrid: false,
            rowNum: constant.rowNum,
            pager: "#paginacionAdjuntoSupervision",
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
                    $('#linkEliminarDASuper').attr('onClick', 'coSupeDocuAdju.eliminarDocumentoAdjuntoConf('+rowid+')');
            },
            loadComplete: function(data) {
            	if(modoDA==constant.modoSupervision.registro){
	                $('#contextMenuDASuper').parent().remove();
	                $('#divContextMenuDASuper').html("<ul id='contextMenuDASuper'>"
	                        + "<li> <a id='linkEliminarDASuper' data-icon='ui-icon-trash' title='Eliminar'>Eliminar</a></li>"
	                        + "</ul>");
	                $('#contextMenuDASuper').puicontextmenu({
	                    target: $('#gridAdjuntoSupervision')
	                });
	                $('#contextMenuDASuper').parent().css('width','65px');
	                cambioColorTrGrid($('#gridAdjuntoSupervision'));
	                if(data.total == 0){
	                	$("#btnVerAdjuntoSupervision").hide();
	                }else{
	                	$("#btnVerAdjuntoSupervision").show();
	                }
            	}
            },
            loadError: function(jqXHR) {
                errorAjax(jqXHR);
            }
        });
	}
	
	function eliminarDocumentoAdjuntoConf(idDocumentoAdjunto){
		confirmer.open('¿Confirma que desea eliminar el archivo?', 'coSupeDocuAdju.suprimirDocumentoAdjunto("'+idDocumentoAdjunto+'")');
	}
	
	//CRUD
	function suprimirDocumentoAdjunto(idDocumentoAdjunto){
		loading.open();
		var url = baseURL + "pages/archivo/eliminarPghDocumentoAdjunto";
		$.post(url, {
			idDocumentoAdjunto : idDocumentoAdjunto
		}, function(data) {
			loading.close();
			if (data.resultado == '0') {grillaAdjuntosSupervision();mensajeGrowl("success", constant.confirm.remove);}
			else if (data.resultado == '1') {mensajeGrowl('error', data.mensaje);}
		});
	}
	function registrarDocumentoAdjunto(){
		$("#formDocumentoAdjuntoSuper").submit();
	}
	
	function validarAgregarDocumentoAdjunto(){
		$('#txtDescripcionDASuper,#file_nameDASuper').removeClass('error');
		var mensajeValidacion = '';
		if(validarFormularioDocumentoAdjunto()){
			mensajeValidacion = validarArchivoPermitido($('#file_nameDASuper').val());
			if(mensajeValidacion==''){
				mensajeValidacion = validarDocumentoAdjuntoGrilla($('#txtDescripcionDASuper').val(),$('#file_nameDASuper').val());
				if(mensajeValidacion==''){confirmer.open('¿Confirma que desea agregar el archivo?', 'coSupeDocuAdju.registrarDocumentoAdjunto()');}
			}
			if(mensajeValidacion!=''){mensajeGrowl('warn', mensajeValidacion);}
		}
	}
	
	function validarFormularioDocumentoAdjunto(){
		var validarDocumentoAdjunto = true;
		validarDocumentoAdjunto = $('#formDocumentoAdjuntoSuper').validateAllForm('#divMensajeFrmDocumentoAdjuntoSuper');
		return validarDocumentoAdjunto;
	}
	
	function validarDocumentoAdjuntoGrilla(descripcion,nombreArchivo){
            var mensajeValidacion = "";
            loading.open();
            var url = baseURL + "pages/archivo/listaPghDocumentoAdjunto";
            $.ajax({
                url: url,
                type: 'post',
                async: false,
                data: {
                    idSupervision : $('#idSupervisionDA').val(),
                    descripcionDocumento : descripcion,
                    nombreDocumento : nombreArchivo.toUpperCase()
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
	
	function validarTipoArchivo(extension,nombreArchivo){
		var mensajeValidacion = "";
		var extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."),nombreArchivo.length);
		if(extension.toUpperCase()!=extensionArchivo.toUpperCase()){mensajeValidacion='El archivo seleccionado no corresponde con el tipo de archivo, por favor corregir <br>';}
		return mensajeValidacion;
	}
	
	function validarArchivoPermitido(nombreArchivo){
		var mensajeValidacion = "";
		var extensionPermitida=false;
		var extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."),nombreArchivo.length).toUpperCase();
		loading.open();
		var url = baseURL + "pages/supervision/findTiposArchivo";
		$.ajax({
            url: url,
            type: 'post',
            async: false,
            data: {},
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
	}
	
	//Formater
	function descargarDocumentoAdjunto(id,cellvalue, options, rowdata) {
        return "<img src=\"" + baseURL + "/../images/stickers.png\" width='17' height='18' onclick=\"coSupeDocuAdju.descargaDocumentosAdjunto('" + id + "')\" style=\"cursor: pointer;\" alt=\"Descargar Medio Probatorio\"/>";
	}
	function eliminarDocumentoAdjunto(cellvalue, options, rowdata) {
		return editar = "<img src=\"" + baseURL + "/../images/delete_16.png\" style=\"cursor: pointer;\" alt=\"Eliminar Adjunto\" onclick=\"coSupeDocuAdju.eliminarDocumentoAdjuntoConf('"+rowdata.idDocumentoAdjunto+"');\" />";
	}
	function descargaDocumentosAdjunto(idDocumentAdjunto){
        document.location.href = baseURL + 'pages/archivo/descargaPghArchivoBD?idDocumentoAdjunto=' + idDocumentAdjunto;
	}
	function enviarDatosArchivoBL(data) {
        if (data.error) {mensajeGrowl("warn", "Advertencia:", data.mensaje);}
        else {mensajeGrowl("success", constant.confirm.save);grillaAdjuntosSupervision();}
    }
	return{
		constructor:constructor,
		registrarDocumentoAdjunto:registrarDocumentoAdjunto,
		eliminarDocumentoAdjuntoConf:eliminarDocumentoAdjuntoConf,
		suprimirDocumentoAdjunto:suprimirDocumentoAdjunto,
		descargaDocumentosAdjunto:descargaDocumentosAdjunto
	};
})();
$(function() {
	boton.closeDialog();
	coSupeDocuAdju.constructor();
});