package cs5220stu08.hw2.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.Nullable;

@Entity
@Table(name = "resources")
public class Resource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    private Integer id;
	
	@Column(name = "name")
	private String nameFile;
	
	@Nullable
	private double version;
	
	private double size;
	
	private String type;
	
	private boolean isPublic;
	
	@Column(name = "create_date")
	private Date creationDate;
	
	@Column(name = "modify_date")
	private Date modificationdate;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fileId", referencedColumnName = "id")
    private Resource parentFile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationdate() {
		return modificationdate;
	}

	public void setModificationdate(Date modificationdate) {
		this.modificationdate = modificationdate;
	}

	public Resource getParentFile() {
		return parentFile;
	}

	public void setParentFile(Resource parentFile) {
		this.parentFile = parentFile;
	}

	
}