var mantenimientoDocumento= {
		procesarGridDocumento : function(nroExpediente) {
			var nombres = ['Fecha','Tipo Documento','Nombre Documento','Documento', 'idArchivo'];
	        var columnas = [
	            {name: "fechaCarga", width: 130, sortable: false, align: "left",formatter:fecha},
	            {name: "idTipoDocumento.descripcion", width: 130, sortable: false, align: "left"},
	            {name: "nombreArchivo", width: 130, sortable: false, align: "left"},
	            {name: "", width: 130, sortable: false, align: "center", formatter: "descargarArchivo"},
	            {name: "idArchivo", hidden:true}
	            ];
	        
	        var url = baseURL + "pages/informeSupervision/listarDocumentos" ;
			var nombreGrid = "VerDocumentos";
			$("#gridContenedor"+nombreGrid).html("");
	        var grid = $("<table>", {
	            "id": "grid"+nombreGrid
	        });
	        var pager = $("<div>", {
	            "id": "paginacion"+nombreGrid
	        });
	        
	        //var x = document.getElementById('nroExpedienteDoc').value;
	        //console.log("PINTA NRO EXP!!!!!!!!!!!!!!1111" + nroExpediente);
	        
	        $("#gridContenedor"+nombreGrid).append(grid).append(pager);
	        grid.jqGrid({
				    url: url,
				    datatype: "json",
				    mtype:'POST',
		            postData: { 
		            	numeroExpediente : nroExpediente
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
		                repeatitems: false
		            },
		            loadComplete: function(data){
		             },
		            loadError: function(jqXHR) {
		                errorAjax(jqXHR);
		            }
		    });
		}
};

jQuery.extend($.fn.fmatter, {
	descargarArchivo : function(cellvalue, options, rowdata) {
		var html = '';
		 var nombre=rowdata.nombreArchivo;
		 var idArchivo=rowdata.idArchivo;
		 console.log("descargarArchivo "+nombre+" idActivo "+idArchivo);
 
			 if (nombre != null && nombre != '' && idArchivo!='' && idArchivo!=null && nombre != undefined){
		            html = '<a class="link" href="'+baseURL + 'pages/archivo/descargaArchivoSiged?idArchivo='+idArchivo+'&nombreArchivo='+nombre+'">'+
		              '<img class="vam" width="17" height="18" title="Descargar" src="'+baseURL+'images/stickers.png">'+
		            '</a>';
		        }
 
		return html;
	}
});


$(function(){
	var nroExpediente = $('#nroExpedienteDoc').val();
	//console.log( 'DATOOOOOOOOOOOOOOOOOO' + x);
	mantenimientoDocumento.procesarGridDocumento(nroExpediente);
	boton.closeDialog();
});