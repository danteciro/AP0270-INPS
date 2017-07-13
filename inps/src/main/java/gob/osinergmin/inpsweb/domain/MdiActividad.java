/**
* Resumen		
* Objeto		: MdiActividad.java
* Descripción		: Clase del modelo de dominio MdiActividad
* Fecha de Creación	: 
* PR de Creación	: OSINE_SFS-480
* Autor			: Julio Piro Gonzales
* ---------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo            Fecha           Nombre                      Descripción
* ---------------------------------------------------------------------------------------------------
* OSINE_SFS-480     09/05/2016      Mario Dioses Fernandez      Registrar en BD campo FECHA_INICIO_PLAZO y FECHA_FIN_PLAZO luego de CONCLUIR_OS, considerando Plazo_Descargo por Rubro (MDI_ACTIVIDAD)
* OSINE_SFS-480     19/05/2016      Luis García Reyna           Crear la interfaz "devolver asignaciones" de acuerdo a especificaciones
* OSINE791          26/08/2016      Gullet Alvites Pisco        Crear el contructor de la clase para los campos idActividad, nombre
*/ 

package gob.osinergmin.inpsweb.domain;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "MDI_ACTIVIDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MdiActividad.findAll", query = "SELECT m FROM MdiActividad m"),
    @NamedQuery(name = "MdiActividad.findByCodigo", query = "SELECT m FROM MdiActividad m WHERE m.codigo = :codigo"),
    @NamedQuery(name = "MdiActividad.findByIdActividad", query = "SELECT m FROM MdiActividad m WHERE m.idActividad = :idActividad"),
    @NamedQuery(name = "MdiActividad.findByFechaActualizacion", query = "SELECT m FROM MdiActividad m WHERE m.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "MdiActividad.findByFechaCreacion", query = "SELECT m FROM MdiActividad m WHERE m.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "MdiActividad.findByNombre", query = "SELECT m FROM MdiActividad m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MdiActividad.findByUsuarioActualizacion", query = "SELECT m FROM MdiActividad m WHERE m.usuarioActualizacion = :usuarioActualizacion"),
    @NamedQuery(name = "MdiActividad.findByUsuarioCreacion", query = "SELECT m FROM MdiActividad m WHERE m.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "MdiActividad.findByTerminalCreacion", query = "SELECT m FROM MdiActividad m WHERE m.terminalCreacion = :terminalCreacion"),
    @NamedQuery(name = "MdiActividad.findByTerminalActualizacion", query = "SELECT m FROM MdiActividad m WHERE m.terminalActualizacion = :terminalActualizacion"),
    @NamedQuery(name = "MdiActividad.findByNivel", query = "SELECT m FROM MdiActividad m WHERE m.nivel = :nivel"),
    @NamedQuery(name = "MdiActividad.findByEsMayor", query = "SELECT m FROM MdiActividad m WHERE m.esMayor = :esMayor"),
    @NamedQuery(name = "MdiActividad.findByEtapa", query = "SELECT m FROM MdiActividad m WHERE m.etapa = :etapa"),
    @NamedQuery(name = "MdiActividad.findByEstado", query = "SELECT m FROM MdiActividad m WHERE m.estado = :estado"),
    @NamedQuery(name = "MdiActividad.findBySolicitaUsuarioScop", query = "SELECT m FROM MdiActividad m WHERE m.solicitaUsuarioScop = :solicitaUsuarioScop"),
    @NamedQuery(name = "MdiActividad.findByIdTipoDireccion", query = "SELECT m FROM MdiActividad m WHERE m.idTipoDireccion = :idTipoDireccion"),
    @NamedQuery(name = "MdiActividad.findByCodigoSubactividad", query = "SELECT m FROM MdiActividad m WHERE m.codigoSubactividad = :codigoSubactividad"),
    @NamedQuery(name = "MdiActividad.findByNombreCorto", query = "SELECT m FROM MdiActividad m WHERE m.nombreCorto = :nombreCorto"),
    @NamedQuery(name = "MdiActividad.findByFlagGabinete", query = "SELECT m FROM MdiActividad m WHERE m.flagGabinete = :flagGabinete"),
    @NamedQuery(name = "MdiActividad.findByAmbito", query = "SELECT m FROM MdiActividad m WHERE m.ambito = :ambito")})
public class MdiActividad extends Auditoria {
    @Size(max = 3)
    @Column(name = "CODIGO")
    private String codigo;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACTIVIDAD")
    private Long idActividad;
    @Size(max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NIVEL")
    private Integer nivel;
    @Size(max = 2)
    @Column(name = "ES_MAYOR")
    private String esMayor;
    @Column(name = "ETAPA")
    private Long etapa;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "SOLICITA_USUARIO_SCOP")
    private Character solicitaUsuarioScop;
    @Column(name = "ID_TIPO_DIRECCION")
    private Long idTipoDireccion;
    @Column(name = "CODIGO_SUBACTIVIDAD")
    private Long codigoSubactividad;
    @Size(max = 10)
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;
    @Column(name = "FLAG_GABINETE")
    private Character flagGabinete;
    @Size(max = 3)
    @Column(name = "AMBITO")
    private String ambito;
    @OneToMany(mappedBy = "idActividad", fetch = FetchType.LAZY)
    private List<MdiUnidadSupervisada> mdiUnidadSupervisadaList;
    @OneToMany(mappedBy = "idActividadPadre", fetch = FetchType.LAZY)
    private List<MdiActividad> mdiActividadList;
    @Column(name = "ID_ACTIVIDAD_PADRE")
    private Long idActividadPadre;
    @OneToMany(mappedBy = "idActividad", fetch = FetchType.LAZY)
    private List<PghProcTramActividad> pghProcTramActividadList;
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    @Column(name = "PLAZO_DESCARGO") 
    private Long plazoDescargo; 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
    @OneToMany(mappedBy = "idActividad", fetch = FetchType.LAZY)
    private List<PghEmprSupeActiObli> pghEmprSupeActiObliList;
    @OneToMany(mappedBy = "idActividad", fetch = FetchType.LAZY)
    private List<DmUnidadSupervisada> dmUnidadSupervisadaList;
    
    public MdiActividad() {
    }

    public MdiActividad(Long idActividad) {
        this.idActividad = idActividad;
    }
     /* OSINE_SFS-480 - RSIS 38 - Inicio */
    public MdiActividad(String nombreActividad) {
        this.nombre=nombreActividad;
    }
     /* OSINE_SFS-480 - RSIS 38 - Fin */
    /* OSINE791 - RSIS 21 - Inicio */ 
    public MdiActividad(Long idActividad, String nombre) {
        this.idActividad = idActividad;
        this.nombre = nombre;
    }
    /* OSINE791 - RSIS 21 - Fin */ 
    public MdiActividad(Long idActividad, String nombre,String codigo) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getEsMayor() {
        return esMayor;
    }

    public void setEsMayor(String esMayor) {
        this.esMayor = esMayor;
    }

    public Long getEtapa() {
        return etapa;
    }

    public void setEtapa(Long etapa) {
        this.etapa = etapa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Character getSolicitaUsuarioScop() {
        return solicitaUsuarioScop;
    }

    public void setSolicitaUsuarioScop(Character solicitaUsuarioScop) {
        this.solicitaUsuarioScop = solicitaUsuarioScop;
    }

    public Long getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(Long idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public Long getCodigoSubactividad() {
        return codigoSubactividad;
    }

    public void setCodigoSubactividad(Long codigoSubactividad) {
        this.codigoSubactividad = codigoSubactividad;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Character getFlagGabinete() {
        return flagGabinete;
    }

    public void setFlagGabinete(Character flagGabinete) {
        this.flagGabinete = flagGabinete;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    @XmlTransient
    @JsonIgnore
    public List<MdiUnidadSupervisada> getMdiUnidadSupervisadaList() {
        return mdiUnidadSupervisadaList;
    }

    public void setMdiUnidadSupervisadaList(List<MdiUnidadSupervisada> mdiUnidadSupervisadaList) {
        this.mdiUnidadSupervisadaList = mdiUnidadSupervisadaList;
    }

    @XmlTransient
    @JsonIgnore
    public List<MdiActividad> getMdiActividadList() {
        return mdiActividadList;
    }

    public void setMdiActividadList(List<MdiActividad> mdiActividadList) {
        this.mdiActividadList = mdiActividadList;
    }

    public Long getIdActividadPadre() {
        return idActividadPadre;
    }

    public void setIdActividadPadre(Long idActividadPadre) {
        this.idActividadPadre = idActividadPadre;
    }

    @XmlTransient
    @JsonIgnore
    public List<PghProcTramActividad> getPghProcTramActividadList() {
        return pghProcTramActividadList;
    }

    public void setPghProcTramActividadList(List<PghProcTramActividad> pghProcTramActividadList) {
        this.pghProcTramActividadList = pghProcTramActividadList;
    }
    
    @XmlTransient
    public List<PghEmprSupeActiObli> getPghEmprSupeActiObliList() {
        return pghEmprSupeActiObliList;
    }

    public void setPghEmprSupeActiObliList(List<PghEmprSupeActiObli> pghEmprSupeActiObliList) {
        this.pghEmprSupeActiObliList = pghEmprSupeActiObliList;
    }
    
    @XmlTransient
    public List<DmUnidadSupervisada> getDmUnidadSupervisadaList() {
        return dmUnidadSupervisadaList;
    }

    public void setDmUnidadSupervisadaList(List<DmUnidadSupervisada> dmUnidadSupervisadaList) {
        this.dmUnidadSupervisadaList = dmUnidadSupervisadaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividad != null ? idActividad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MdiActividad)) {
            return false;
        }
        MdiActividad other = (MdiActividad) object;
        if ((this.idActividad == null && other.idActividad != null) || (this.idActividad != null && !this.idActividad.equals(other.idActividad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.osinergmin.inps.domain.MdiActividad[ idActividad=" + idActividad + " ]";
    }  
    /* OSINE_SFS-480 - RSIS 17 - Inicio */
    public Long getPlazoDescargo() { 
		return plazoDescargo;
	}

	public void setPlazoDescargo(Long plazoDescargo) {
		this.plazoDescargo = plazoDescargo;
	} 
    /* OSINE_SFS-480 - RSIS 17 - Fin */
}
