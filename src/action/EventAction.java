package action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.Event;
import enums.EventStatus;
import enums.ExecResult;

import service.EventService;
import utils.StrutsUtil;

public class EventAction {

	String jsonEvent;
	
	int user_id;
	
	String status;
	
	int group_id;
	
	long timestamp;
	

	String arrEvents;
	
	public String getArrEvents() {
		return arrEvents;
	}
	public void setArrEvents(String arrEvents) {
		this.arrEvents = arrEvents;
	}

	/*
	 * for update
	 */
	int event_id;
	int assignTo;
	String title;
	String description;
	long deadline;
	
	
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public int getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(int assignTo) {
		this.assignTo = assignTo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getDeadline() {
		return deadline;
	}
	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getJsonEvent() {
		return jsonEvent;
	}
	public void setJsonEvent(String jsonEvent) {
		this.jsonEvent = jsonEvent;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void updateEventBatches(){
		HttpServletResponse response = ServletActionContext.getResponse();
		
		if(arrEvents == null || status == null || 
				(!status.equals(EventStatus.started.toString() )
						&& !status.equals(EventStatus.finished.toString())
						&& !status.equals(EventStatus.dropped.toString()))){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		ArrayList<Integer> arr = new Gson().fromJson(arrEvents, new TypeToken<ArrayList<Integer>>(){}.getType());
		System.out.println(arr);
		if(arr == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		boolean res = new EventService().updateStatusOfEventBatches(arr,status);
		
		if(res){
			try {
				StrutsUtil.write(response,ExecResult.success.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}else{
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	
	
	public void assignEvent(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(jsonEvent == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		Event event = null;
		try{
			event = new Gson().fromJson(jsonEvent, Event.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(event == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}else{
			if(event.getAssignedBy() == 0 ||
					event.getAssignedTo() == 0||
					event.getDeadLine() == 0||
					event.getDescription() == null||
					event.getTitle() == null||
					event.getGroupId() == 0){
				
				try {
					StrutsUtil.write(response,ExecResult.failed.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		Event ultimate = null;
		try {
			ultimate = new EventService().assignEvent(event);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(ultimate == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,new Gson().toJson(ultimate));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void getDatesHavingEvents(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(user_id == 0 || status == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		List<Long> dates = new EventService().getDatesHavingEvents(user_id, status);
		
		try {
			StrutsUtil.write(response,new Gson().toJson(dates));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getEventsByGroup(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(user_id == 0 || group_id == 0 ){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		List<Event> events = new EventService().getEventByGroup(user_id, group_id);
		
		try {
			StrutsUtil.write(response,new Gson().toJson(events));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void getEventsByDate(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(user_id == 0 || timestamp == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		List<Event> events = new EventService().getEventsOfOneDate(user_id, timestamp);
		
		try {
			StrutsUtil.write(response,new Gson().toJson(events));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEvent(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(event_id == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		boolean res = new EventService().deleteEvent(event_id);
		
		if(!res){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,ExecResult.success.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void updateStatusOfEvent(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(event_id == 0 || status == null || (!status.equals(EventStatus.started.toString() )
				&& !status.equals(EventStatus.finished.toString())
				&& !status.equals(EventStatus.dropped.toString()))){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		boolean res = new EventService().updateStatusOfEvent(event_id, status);
		
		if(!res){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,ExecResult.success.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateEvent(){
		HttpServletResponse response = ServletActionContext.getResponse();
		if(event_id == 0 || assignTo == 0 || title == null || description == null || deadline == 0){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
			
		}
		
		Event event = new EventService().updateEvent(event_id, description, title, assignTo, deadline);
		
		if(event == null){
			try {
				StrutsUtil.write(response,ExecResult.failed.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				StrutsUtil.write(response,new Gson().toJson(event));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
