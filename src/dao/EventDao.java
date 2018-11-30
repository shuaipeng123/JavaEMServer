package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Event;
import enums.EventStatus;


import utils.Dbconn;

public class EventDao {

private Dbconn db = null;
	
	public EventDao(){
		db = new Dbconn();
	}
	
	/** 
	* @Title: getDatesHavingEvents 
	* @Description: get timestamp of dates which have >= 1 events given status
	* @param @param user_id
	* @param @param status
	* @param @return
	* @param @throws SQLException    
	* @return List<Long>    
	* @throws 
	*/
	public List<Long> getDatesHavingEvents(int user_id, String status) throws SQLException{
		List<Long> dates = new ArrayList<Long>();
		
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_dates_having_events(?,?)}");
			c.setInt(1, user_id);
			c.setString(2, status);
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				dates.add(rs.getTimestamp("eventDate").getTime());
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return dates;
	}
	
	/** 
	* @Title: getEventsOfOneDate 
	* @Description: get Event List of one user in one date  
	* @param @param user_id
	* @param @param date
	* @param @return
	* @param @throws SQLException    
	* @return List<Event>    
	* @throws 
	*/
	public List<Event> getEventsOfOneDate(int user_id, Date date) throws SQLException{
		List<Event> events = new ArrayList<Event>();
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_event_by_date(?,?)}");
			c.setInt(1, user_id);
			c.setDate(2, new java.sql.Date(date.getTime()));
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				Event event = new Event();
				event.setAssignedBy(rs.getInt("assignedBy"));
				event.setAssignedTo(rs.getInt("assignedTo"));
				event.setGroupId(rs.getInt("group_id"));
				event.setEventID(rs.getInt("id"));
				event.setEventTitle(rs.getString("eventName"));
				event.setDescription(rs.getString("eventDescription"));
				event.setDeadLine(rs.getTimestamp("eventDeadLine").getTime());
				event.setEventStatus(rs.getString("eventStatus"));
				event.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				event.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				event.setAssignByName(rs.getString("assignByName"));
				events.add(event);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return events;
		
	}
	
	
	/** 
	* @Title: updateStatusOfEvent 
	* @Description: update status of one event given id 
	* @param @param eventId
	* @param @throws SQLException    
	* @return void    
	* @throws 
	*/
	public void updateStatusOfEvent(int eventId,String status) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call update_status_of_event(?,?)}");
			c.setInt(1, eventId);
			c.setString(2, status);
			c.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
	}
	
	/** 
	* @Title: deleteEvent 
	* @Description: delete one event given id 
	* @param @param eventId
	* @param @throws SQLException    
	* @return void    
	* @throws 
	*/
	public void deleteEvent(int eventId) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call delete_event(?)}");
			c.setInt(1, eventId);
			c.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
	}
	
	
	/** 
	* @Title: assignEvent 
	* @Description: assign an event 
	* @param @param event
	* @param @return
	* @param @throws SQLException    
	* @return int    0 - assign failed    >0 - id of this event
	* @throws 
	*/
	public Event assignEvent(Event event) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		Event res = null;
		try {
			c = conn.prepareCall("{call assign_event(?,?,?,?,?,?,?)}");
			c.setLong(1, event.getGroupId());
			c.setString(2, event.getEventTitle());
			c.setString(3, event.getDescription());
			c.setTimestamp(4, new java.sql.Timestamp(event.getDeadLine()));
			c.setInt(5, event.getAssignedBy());
			c.setString(6, EventStatus.started.toString());
			c.setInt(7, event.getAssignedTo());
			
			ResultSet rs = c.executeQuery();
			
			if(rs.next()){
				res = new Event();
				res.setAssignedBy(rs.getInt("assignedBy"));
				res.setAssignedTo(rs.getInt("assignedTo"));
				res.setGroupId(rs.getInt("group_id"));
				res.setEventID(rs.getInt("id"));
				res.setEventTitle(rs.getString("eventName"));
				res.setDescription(rs.getString("eventDescription"));
				res.setDeadLine(rs.getTimestamp("eventDeadLine").getTime());
				res.setEventStatus(rs.getString("eventStatus"));
				res.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				res.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				event.setAssignByName(rs.getString("assignByName"));
				res.setGroupName(rs.getString("groupName"));
			}
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		
		return res;
	}
	
	/** 
	* @Title: getEventById 
	* @Description: get and event object given event_id 
	* @param @param eventId
	* @param @return
	* @param @throws SQLException    
	* @return Event    
	* @throws 
	*/
	public Event getEventById(int eventId) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		
		try {
			c = conn.prepareCall("{call get_event_by_id(?)}");
			c.setInt(1, eventId);
			
			ResultSet rs = c.executeQuery();
			if(rs.next()){
				Event event = new Event();
				event.setAssignedBy(rs.getInt("assignedBy"));
				event.setAssignedTo(rs.getInt("assignedTo"));
				event.setGroupId(rs.getInt("group_id"));
				event.setEventID(rs.getInt("id"));
				event.setEventTitle(rs.getString("eventName"));
				event.setDescription(rs.getString("eventDescription"));
				event.setDeadLine(rs.getTimestamp("eventDeadLine").getTime());
				event.setEventStatus(rs.getString("eventStatus"));
				event.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				event.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				event.setAssignByName(rs.getString("assignByName"));
				event.setGroupName(rs.getString("groupName"));
				
				return event;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return null;
	}
	
	/** 
	* @Title: getEventByGroup 
	* @Description: get event list of one user given group_id 
	* @param @param user_id
	* @param @param group_id
	* @param @return
	* @param @throws SQLException    
	* @return List<Event>    
	* @throws 
	*/
	public List<Event> getEventByGroup(int user_id, int group_id) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		List<Event> events = new ArrayList<Event>();
		
		try {
			c = conn.prepareCall("{call get_event_by_group(?,?)}");
			c.setInt(1, group_id);
			c.setInt(2, user_id);
			
			ResultSet rs = c.executeQuery();
			while(rs.next()){
				Event event = new Event();
				event.setAssignedBy(rs.getInt("assignedBy"));
				event.setAssignedTo(rs.getInt("assignedTo"));
				event.setGroupId(rs.getInt("group_id"));
				event.setEventID(rs.getInt("id"));
				event.setEventTitle(rs.getString("eventName"));
				event.setDescription(rs.getString("eventDescription"));
				event.setDeadLine(rs.getTimestamp("eventDeadLine").getTime());
				event.setEventStatus(rs.getString("eventStatus"));
				event.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				event.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				event.setAssignByName(rs.getString("assignByName"));
				event.setGroupName(rs.getString("groupName"));
				
				events.add(event);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return events;
	}

	public Event updateEvent(int event_id, String discription,String title, int assignTo,long deadline) throws SQLException{
		Connection conn = db.getConnection();
		CallableStatement c = null;
		Event event = null;
		
		try {
			c = conn.prepareCall("{call update_event(?,?,?,?,?)}");
			c.setInt(1, event_id);
			c.setInt(2, assignTo);
			c.setString(3, title);
			c.setString(4, discription);
			c.setTimestamp(5, new java.sql.Timestamp(deadline));
			ResultSet rs = c.executeQuery();
			if(rs.next()){
				event = new Event();
				event.setAssignedBy(rs.getInt("assignedBy"));
				event.setAssignedTo(rs.getInt("assignedTo"));
				event.setGroupId(rs.getInt("group_id"));
				event.setEventID(rs.getInt("id"));
				event.setEventTitle(rs.getString("eventName"));
				event.setDescription(rs.getString("eventDescription"));
				event.setDeadLine(rs.getTimestamp("eventDeadLine").getTime());
				event.setEventStatus(rs.getString("eventStatus"));
				event.setCreatedAt(rs.getTimestamp("createdAt").getTime());
				event.setUpdatedAt(rs.getTimestamp("updatedAt").getTime());
				event.setAssignByName(rs.getString("assignByName"));
				event.setGroupName(rs.getString("groupName"));
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			c.close();
			db.dispose();
		}
		return event;
	}
}
