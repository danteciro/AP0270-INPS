/*
/**
* Resumen		
* Objeto			: modLevantamientos.jp
* Descripción		: js modLevantamientos
* Fecha de Creación	: 25/10/2016
* PR de Creación	: OSINE_SFS-791
* Autor				: GMD
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------				
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad de bandeja de expedientes con infracciones de supervisión DSR-CRITICIDAD para uso del Agente
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente registrar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
* OSINE_SFS-791     25/10/2016      Mario Dioses Fernandez      Crear una funcionalidad que permita al Agente consultar el levantamiento de infracciones para expedientes de supervisión DSR-CRITICIDAD.
*/ 

var modLevan = {		
	inicioModLevantamiento : function(){
		modLevan.abrirModLevantamiento();
		
		$("#btnCerrarSession").click(function() {
			 open(location, '_self').close();
		 });
	},
	abrirModLevantamiento : function(){
		$.ajax({
			url: "/inps/pages/modLevantamientos",
	        type:'post',
	        async:false,
	        data:{},
	        beforeSend:navSlide.loadingOpen(),
	        success:function(data){navSlide.loadingClose();}
	    }); 		    
	}
}
$(function(){	
	modLevan.inicioModLevantamiento();
});