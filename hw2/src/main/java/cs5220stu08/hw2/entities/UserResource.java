package cs5220stu08.hw2.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_resource")
public class UserResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private  UserResourcePk pk;
	
	@ManyToOne(optional=false)
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false )
	private User user;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="id_resource", referencedColumnName = "id", insertable = false, updatable = false)
	private Resource resource;
	
	private String ownerType;
		
	public UserResourcePk getPk() {
		return pk;
	}

	public void setPk(UserResourcePk pk) {
		this.pk = pk;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	
	
}