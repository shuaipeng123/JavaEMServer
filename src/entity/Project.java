package entity;

import java.util.Date;

/**
 * Created by huqicheng on 2017/10/14.
 */

public class Project {
    long projectId;
    String projectName;
    String projectDescription;
    long projectDeadline;
    long createdAt;
    long updatedAt;


    public long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    



    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

	public long getProjectDeadline() {
		return projectDeadline;
	}

	public void setProjectDeadline(long projectDeadline) {
		this.projectDeadline = projectDeadline;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

    


}
