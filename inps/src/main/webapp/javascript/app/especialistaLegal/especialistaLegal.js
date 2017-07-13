$(function() {
    $('#tabsEspecialista').tabs();
    confirmer.start();
    espe.cargarUnidadDivision();
});
// especialista.js
var espe={
    cargarUnidadDivision : function (){
	$.ajax({
            url:baseURL + "pages/bandeja/findUnidadDivisionPersonal",
            type:'get',
            async:false,
            data:{  
                idPersonal:$('#idPersonalSesion').val()  
            },
            beforeSend:loading.open,
            success:function(data){
                loading.close();
                $('#divDivision').html(data.division);
                $('#divUnidad').html(data.unidad);
            },
            error:errorAjax
	});
}
};