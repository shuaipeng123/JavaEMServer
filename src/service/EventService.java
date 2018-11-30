package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import dao.EventDao;
import entity.Event;

public class EventService {

	EventDao eventDao = null;
	
	public EventService(){
		eventDao = new EventDao();
	}
	
	public List<Long> getDatesHavingEvents(int user_id,String status){
		try {
			return eventDao.getDatesHavingEvents(user_id, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new ArrayList<Long>();
	}
	
	public Event assignEvent(Event event) throws SQLException{
		return eventDao.assignEvent(event);
		
	}
	
	public List<Event> getEventByGroup(int user_id, int group_id){
		try {
			return eventDao.getEventByGroup(user_id, group_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Event>();
	}
	
	public List<Event> getEventsOfOneDate(int user_id, long timestamp){
		try {
			return eventDao.getEventsOfOneDate(user_id, new Date(timestamp));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Event>();
	}
	
	public boolean deleteEvent(int event_id){
		try {
			eventDao.deleteEvent(event_id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateStatusOfEvent(int event_id,String status){
		try {
			eventDao.updateStatusOfEvent(event_id, status);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateStatusOfEventBatches(List<Integer> eventIds, String status){
		try {
			for(Integer id:eventIds){
				eventDao.updateStatusOfEvent(id, status);
			}
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Event updateEvent(int event_id, String discription,String title, int assignTo, long deadline){
		try {
			return eventDao.updateEvent(event_id, discription, title, assignTo,deadline);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void main(String[] args) throws SQLException {
		Event event = new Event();
		event.setAssignedBy(2);
		event.setAssignedTo(3);
		event.setDeadLine(new Date().getTime());
		event.setDescription("232323");
		event.setEventTitle("title2");
		event.setGroupId(1);
		
		System.out.println(new Gson().toJson(event));
		
		EventService es = new EventService();
		
		//System.out.println(es.updateEvent(1, "new", "new task", 2,new Date().getTime()));
		System.out.println(es.assignEvent(event).getGroupName());
		
		//System.out.println(es.getEventByGroup(2,2).size());
		
		//System.out.println(es.getDatesHavingEvents(1, "started"));
		
//		Date date = new Date();
//		date.setHours(0);
//		date.setMinutes(0);
//		date.setSeconds(0);
//		//1509854400488
//		System.out.println(date.getTime());
//		System.out.println(es.getEventsOfOneDate(2,date.getTime()).size());
	}
}
