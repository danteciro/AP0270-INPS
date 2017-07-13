/**
* Resumen.
* Objeto            : busquedaUnidadOperativa.js
* Descripción       : JavaScript con las acciónes de carga de combos para los filtros de búsqueda.
* Fecha de Creación : 17/05/2016.
* Autor             : Hernán Torres.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          		Descripción
* --------------------------------------------------------------------------------------------------------------
* 
*/

//common/unidadOperativa/busquedaUnidadOperativa.js
var coBusUnidad={
		cargarDepartamento:function(idSlctDest){
			$.ajax({
	            url:baseURL + "pages/bandeja/cargarDepartamento",
	            type:'get',
	            dataType:'json',
	            async:false,
	            data:{},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.comboValorId(data,"idDepartamento","nombre",idSlctDest,"-1");
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarProvincia:function(combo, idDepartamento){
			$.ajax({
	            url:baseURL + "pages/ubigeo/buscarProvincias",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{idDepartamento: idDepartamento},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.combo(data,"idProvincia","nombre",combo);
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},
		cargarDistrito:function(combo, idDepartamento, idProvincia){
			$.ajax({
	            url:baseURL + "pages/ubigeo/buscarDistritos",
	            type:'post',
	            dataType:'json',
	            async:false,
	            data:{idDepartamento: idDepartamento, idProvincia: idProvincia},
	            beforeSend:loading.open,
	            success:function(data){
	                loading.close();
	                fill.combo(data,"idDistrito","nombre",combo);
	            },
	            error:function(jqXHR){errorAjax(jqXHR);}
	        });
		},

};
$(function() {
	  
    $('#slctDepartamentoUnidad').change(function() {
    	fill.clean("#slctProvinciaUnidad");
    	fill.clean("#slctDistritoUnidad");
    	if($('#slctDepartamentoUnidad').val()!=''){
    		coBusUnidad.cargarProvincia("#slctProvinciaUnidad",$('#slctDepartamentoUnidad').val());
    		coBusUnidad.cargarDistrito("#slctDistritoUnidad",$('#slctDepartamentoUnidad').val(), $('#slctProvinciaUnidad').val());
    	}
	});
	$('#slctProvinciaUnidad').change(function() {
		fill.clean("#slctDistritoUnidad");
		if($('#slctProvinciaUnidad').val()!=''){
			coBusUnidad.cargarDistrito("#slctDistritoUnidad",$('#slctDepartamentoUnidad').val(), $('#slctProvinciaUnidad').val());
		}	
	});
	
	permiteCopyPasteInput('#txtRazoSociBusqUnidad,#txtCodigoOsiBusqUnidad,#txtNumeroRHUnidad');
});