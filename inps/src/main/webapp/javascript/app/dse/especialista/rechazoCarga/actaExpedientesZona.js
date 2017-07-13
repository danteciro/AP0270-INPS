/**
 * Resumen		
 * Objeto				: actaExpedientesZona
 * Descripción			: JavaScript donde se maneja las acciones de Acta Expediente Zona
 * Fecha de Creación	: 11/10/2016.
 * PR de Creación		: OSINE_SFS-1063.
 * Autor				: Victoria Ubaldo G.
 * ===============================================================================================
 * Modificaciones |
 * Motivo         |  Fecha        |   Nombre                     |     Descripción
 * ===============================================================================================
 *
 */
var grillaActasExpedientesZona={
		
	    procesarGridActasExpedientesZona: function(nombreGrid, accion) {
	    	var nombres = ['nombreActaDoc', 'idActaDoc', 'Zona','Acta' ];
	    			var columnas = [
	    	            {name: "nombreActaDoc", hidden:true},
	    	            {name: "idActaDoc", hidden:true},
	    	            {name: "nombreZona", width: 130, sortable: false, align: "center"},
	    	            {name: "", width: 130, sortable: false, align: "center" ,formatter: "descargarFileActaZona"}
	    	            ];
	        $("#gridContenedor"+nombreGrid).html("");
	        var grid = $("<table>", {
	            "id": "grid"+nombreGrid
	        });
	        var pager = $("<div>", {
	            "id": "paginacion"+nombreGrid
	        });
	        $("#gridContenedor"+nombreGrid).append(grid).append(pager);
 
	        var  nroExpediente = document.getElementById("txtNumeroExpediente").value;
	        
	        grid.jqGrid({
	        	 url : baseURL + "pages/empresasVw/listarActaZonaPorExpediente",
	            datatype: "json",
	            mtype : "POST",
	            postData: {
	            	numeroExpediente: nroExpediente
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
	                id: "idActaDoc"
	            },
	            onSelectRow: function(rowid, status) {
	                grid.resetSelection();
	            },
	            loadComplete: function(data) {
	            	
	            },
	            loadError: function(jqXHR) {
	                errorAjax(jqXHR);
	            }
	        });

	    }  
	};



jQuery.extend($.fn.fmatter, {
	descargarFileActaZona : function(cellvalue, options, rowdata) {
		var html = '';
		 var nombre=rowdata.nombreActaDoc;
		 var idArchivo=rowdata.idActaDoc;
 
			 if (nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null && nombre != undefined){
		            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
		              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
		            '</a>';
		        }
 
		return html;
	}
});

 

$(function() {
	
	grillaActasExpedientesZona.procesarGridActasExpedientesZona('ActasExpedientesZona','');
	boton.closeDialog();
});

//@ sourceURL=actaExpedientesZona.js