package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import enums.MsgType;



public class BaseMsg  implements Serializable {
    private static final long serialVersionUID = 1L;
    private MsgType type;
    private String clientId;
    private String groupId;
    long date;
    String avatar;
    private Map<String,Object> params;
    
    public BaseMsg() {
        params = new HashMap<String,Object>();
    }
    
    

	public String getAvatar() {
		return avatar;
	}



	public void setAvator(String avatar) {
		this.avatar = avatar;
	}



	public void setParams(Map<String, Object> params) {
		this.params = params;
	}



	public long getDate() {
		return date;
	}



	public void setDate(long date) {
		this.date = date;
	}



	public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
    
    public Map<String,Object> getParams(){
    	return params;
    }
    
    public void putParams(String key, Object val){
    	params.put(key, val);
    }

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
    
}
