$(function() {
	boton.closeDialog();
	actaInspecion.listarEmpresaZona();
});

var actaInspeccion = {
		listarEmpresaZona: function() {
			var nombres = ['Zona','Acta'];
	        var columnas = [
	            {name: "zona", width: 130, sortable: false, align: "left"},
	            {name: "acta", width: 130, sortable: false, align: "left"},
	           ];
	        var url = baseURL + "pages/informeSupervision/listarZonasEmpresas" ;
			var nombreGrid = "ZonasEmpresa";
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
		            },
		            hidegrid: false,
		            rowNum: 20,
		            mtype:'GET',
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
		            },
		            loadComplete: function(data){
		            },
		            loadError: function(jqXHR) {
		                errorAjax(jqXHR);
		            }
		    });
		}
}