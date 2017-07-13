/**
* Resumen.
* Objeto            : MdiLocador.java.
* Descripción       : Domain con los atributos del MdiLocador.
* Fecha de Creación : 21/05/2016
* PR de Creación    : OSINE_SFS-480                   
* Autor             : Julio Piro.
* --------------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha         Nombre          			Descripción
* --------------------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/06/2016    Mario Dioses Fernandez    Listar Empresas Supervisoras según filtros definidos para Gerencia (Unidad Organica).                                                       
*/

package gob.osinergmin.inpsweb.domain;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author jpiro
 */
@Entity
@Table(name = "MDI_LOCADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiLocador.findAll", query = "SELECT m FROM MdiLocador m")
})
public class MdiLocador extends Auditoria {
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_LOCADOR")
    private Long idLocador;
//    @Column(name = "ID_TIPO_DOCUMENTO_IDENTIDAD")
//    private Long idTipoDocumentoIdentidad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_DOCUMENTO_IDENTIDAD")
    private MdiMaestroColumna idTipoDocumentoIdentidad;
    @Size(max = 30)
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Size(max = 15)
    @Column(name = "RUC")
    private String ruc;
    @Size(max = 50)
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Size(max = 50)
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Size(max = 50)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Size(max = 50)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Column(name = "SEXO")
    private Long sexo;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "TIPO_SANGRE")
    private Long tipoSangre;
    @Column(name = "ESTADO_CIVIL")
    private Long estadoCivil;
    @Size(max = 2)
    @Column(name = "HIJOS")
    private String hijos;
    @Column(name = "NUMERO_HIJOS")
    private Short numeroHijos;
    @Column(name = "ID_NACIONALIDAD")
    private Long idNacionalidad;
    @Size(max = 30)
    @Column(name = "TELEFONO_CONTACTO")
    private String telefonoContacto;
    @Column(name = "ID_COLEGIO_PROFESIONAL")
    private Long idColegioProfesional;
    @Size(max = 20)
    @Column(name = "NUMERO_COLEGIATURA")
    private String numeroColegiatura;
    @Size(max = 30)
    @Column(name = "TELEFONO_PERSONAL")
    private String telefonoPersonal;
    @Column(name = "FECHA_FIN_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinVigencia;
    @Size(max = 500)
    @Column(name = "ALERGIA_LOCADOR")
    private String alergiaLocador;
    @Column(name = "FECHA_INICIO_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioVigencia;
    @Size(max = 500)
    @Column(name = "RESTRICCION_MEDICA")
    private String restriccionMedica;
    @Size(max = 50)
    @Column(name = "CORREO_ELECTRONICO_PERSONAL")
    private String correoElectronicoPersonal;
    @Size(max = 50)
    @Column(name = "CORREO_ELECTRONICO_LABORAL")
    private String correoElectronicoLaboral;
    @Column(name = "ID_PROFESION")
    private Long idProfesion;
    @Size(max = 200)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 2)
    @Column(name = "ORIGEN_INFORMACION")
    private String origenInformacion;
    @Size(max = 200)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    @Size(max = 8)
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "COROCO_IDCORRENTISTA")
    private Integer corocoIdcorrentista;
    @OneToMany(mappedBy = "idLocador", fetch = FetchType.LAZY)
    private List<PghPersonal> pghPersonalList;
    @OneToMany(mappedBy = "idLocador", fetch = FetchType.LAZY)
    private List<PghOrdenServicio> pghOrdenServicioList;
    @OneToMany(mappedBy = "idLocador", fetch = FetchType.LAZY)
    private List<PghEmprSupeActiObli> pghEmprSupeActiObliList;
    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @OneToMany(mappedBy = "idLocador", fetch = FetchType.LAZY)
    private List<MdiContratoEmpresaLocador> mdiContratoEmpresaLocadorList;
    /* OSINE_SFS-480 - RSIS 11 - Fin */
    
    @Transient
    private String nombreCompletoArmado;

    public String getNombreCompletoArmado() {
        return nombreCompletoArmado;
    }

    public void setNombreCompletoArmado(String nombreCompletoArmado) {
        this.nombreCompletoArmado = nombreCompletoArmado;
    }

    public MdiLocador() {
    }

    public MdiLocador(Long idLocador) {
        this.idLocador = idLocador;
    }
    
    public MdiLocador(Long idLocador,String nombreCompleto,String auxiliar) {
        this.idLocador = idLocador;
        this.nombreCompleto = nombreCompleto;
    }
    
    public MdiLocador(Long idLocador,String nombreCompletoArmado) {
        this.idLocador = idLocador;
        this.nombreCompletoArmado=nombreCompletoArmado;
    }

    public MdiLocador(Long idLocador, String apellidoPaterno, String apellidoMaterno, String primerNombre, String segundoNombre,
    		String numeroDocumento, String ruc, String telefonoContacto, String telefonoPersonal, String estado, String nombreCompleto,
    		String nombreCompletoArmado, Long idtipoDocumentoIdentidad, String descTipoDocumentoIdentidad, Date fechaNacimiento, Long sexo, 
    		String correoElectronicoPersonal, Long idProfesion) {
		this.idLocador = idLocador;
    	this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.numeroDocumento = numeroDocumento;
		this.ruc = ruc;
		this.telefonoContacto = telefonoContacto;
		this.idTipoDocumentoIdentidad=new MdiMaestroColumna(idtipoDocumentoIdentidad,descTipoDocumentoIdentidad);
		this.telefonoPersonal = telefonoPersonal;
		this.estado = estado;
		this.nombreCompleto = nombreCompleto;
		this.nombreCompletoArmado = nombreCompletoArmado;
		this.fechaNacimiento = fechaNacimiento;		
		this.sexo = sexo;
		this.correoElectronicoPersonal = correoElectronicoPersonal;
		this.idProfesion = idProfesion;
	}

	public Long getIdLocador() {
        return idLocador;
    }

    public void setIdLocador(Long idLocador) {
        this.idLocador = idLocador;
    }

    public MdiMaestroColumna getIdTipoDocumentoIdentidad() {
        return idTipoDocumentoIdentidad;
    }

    public void setIdTipoDocumentoIdentidad(MdiMaestroColumna idTipoDocumentoIdentidad) {
        this.idTipoDocumentoIdentidad = idTipoDocumentoIdentidad;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Long getSexo() {
        return sexo;
    }

    public void setSexo(Long sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(Long tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Long getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Long estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getHijos() {
        return hijos;
    }

    public void setHijos(String hijos) {
        this.hijos = hijos;
    }

    public Short getNumeroHijos() {
        return numeroHijos;
    }

    public void setNumeroHijos(Short numeroHijos) {
        this.numeroHijos = numeroHijos;
    }

    public Long getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(Long idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Long getIdColegioProfesional() {
        return idColegioProfesional;
    }

    public void setIdColegioProfesional(Long idColegioProfesional) {
        this.idColegioProfesional = idColegioProfesional;
    }

    public String getNumeroColegiatura() {
        return numeroColegiatura;
    }

    public void setNumeroColegiatura(String numeroColegiatura) {
        this.numeroColegiatura = numeroColegiatura;
    }

    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }

    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
    }

    public Date getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(Date fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public String getAlergiaLocador() {
        return alergiaLocador;
    }

    public void setAlergiaLocador(String alergiaLocador) {
        this.alergiaLocador = alergiaLocador;
    }

    public Date getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(Date fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public String getRestriccionMedica() {
        return restriccionMedica;
    }

    public void setRestriccionMedica(String restriccionMedica) {
        this.restriccionMedica = restriccionMedica;
    }

    public String getCorreoElectronicoPersonal() {
        return correoElectronicoPersonal;
    }

    public void setCorreoElectronicoPersonal(String correoElectronicoPersonal) {
        this.correoElectronicoPersonal = correoElectronicoPersonal;
    }

    public String getCorreoElectronicoLaboral() {
        return correoElectronicoLaboral;
    }

    public void setCorreoElectronicoLaboral(String correoElectronicoLaboral) {
        this.correoElectronicoLaboral = correoElectronicoLaboral;
    }

    public Long getIdProfesion() {
        return idProfesion;
    }

    public void setIdProfesion(Long idProfesion) {
        this.idProfesion = idProfesion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrigenInformacion() {
        return origenInformacion;
    }

    public void setOrigenInformacion(String origenInformacion) {
        this.origenInformacion = origenInformacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCorocoIdcorrentista() {
        return corocoIdcorrentista;
    }

    public void setCorocoIdcorrentista(Integer corocoIdcorrentista) {
        this.corocoIdcorrentista = corocoIdcorrentista;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghPersonal> getPghPersonalList() {
        return pghPersonalList;
    }

    public void setPghPersonalList(List<PghPersonal> pghPersonalList) {
        this.pghPersonalList = pghPersonalList;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghOrdenServicio> getPghOrdenServicioList() {
        return pghOrdenServicioList;
    }

    public void setPghOrdenServicioList(List<PghOrdenServicio> pghOrdenServicioList) {
        this.pghOrdenServicioList = pghOrdenServicioList;
    }
    
    @XmlTransient
    public List<PghEmprSupeActiObli> getPghEmprSupeActiObliList() {
        return pghEmprSupeActiObliList;
    }

    public void setPghEmprSupeActiObliList(List<PghEmprSupeActiObli> pghEmprSupeActiObliList) {
        this.pghEmprSupeActiObliList = pghEmprSupeActiObliList;
    }

    /* OSINE_SFS-480 - RSIS 11 - Inicio */
    @XmlTransient
    public List<MdiContratoEmpresaLocador> getMdiContratoEmpresaLocadorList() {
        return mdiContratoEmpresaLocadorList;
    }

    public void setMdiContratoEmpresaLocadorList(List<MdiContratoEmpresaLocador> mdiContratoEmpresaLocadorList) {
        this.mdiContratoEmpresaLocadorList = mdiContratoEmpresaLocadorList;
    }
    /* OSINE_SFS-480 - RSIS 11 - Fin */
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocador != null ? idLocador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiLocador)) {
            return false;
        }
        MdiLocador other = (MdiLocador) object;
        if ((this.idLocador == null && other.idLocador != null) || (this.idLocador != null && !this.idLocador.equals(other.idLocador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.MdiLocador[ idLocador=" + idLocador + " ]";
    }
}
