/* 
    Document   : asignacion
    Created on : 25/03/2016, 09:32:18 AM
    Author     : htorress
*/

$(function() {
    $('#tabsJefe').tabs();
    confirmer.start();
    espe.cargarUnidadDivision();
});
// jefe.js
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