package org.ual.aas.controllers;
import org.ual.aas.models.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.ual.aas.models.Task;
import org.ual.aas.models.TaskList;

import javax.persistence.Query;

public class TaskListController {
	private SessionFactory sessionFactory;
	
	public TaskListController() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("resources/hibernate.cfg.xml")
				.enableAutoClose().build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void close() {
		this.sessionFactory.close();
	}

	private List<TaskList> readTaskLists() {
		// TODO
		// Open Hibernate connection
		// Build all TaskList objects
		// Close connection		
		return null;
	}
	
	private TaskList readTaskList(String taskListId) {
		// TODO
		/*
		 * Session session = sessionFactory.openSession(); // Open Hibernate connection
		 * session.beginTransaction(); // Build TaskList object
		 * 
		 * String id = "from tasklist where id = "+ taskListId + ";"; Query query =
		 * session.createQuery(id); String result = query.getResultList().toString();
		 * 
		 * // Close connection 
		 * session.close(); 
		 * return result;
		 */
		return null;
	}
	
	public void writeTaskList(TaskList taskList) {
		// TODO: change object id
        Session session = sessionFactory.openSession();
		// Open Hibernate session
        session.beginTransaction();
        // Save TaskList object
        session.save(taskList);
        session.getTransaction().commit();
        // Close session
        session.close();
	}
	public void writeTask(Task task) {
		// TODO: change object id
        Session session = sessionFactory.openSession();
		// Open Hibernate session
        session.beginTransaction();
        // Save TaskList object
        session.save(task);
        session.getTransaction().commit();
        // Close session
        session.close();
	}
	
	public List<TaskList> getTaskLists() {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 session.beginTransaction();

		 List<TaskList> lists = session.createQuery("from TaskList", TaskList.class).list();
		 
		 session.getTransaction().commit();
		 
		 return lists;
	}
	
	public TaskList getTaskList(int id) {
		// TODO Auto-generated method stub
		 Session session = sessionFactory.openSession();
		 session.beginTransaction();

		 TaskList list = session.get(TaskList.class, id ); //Integer.parseInt(id));

		 session.getTransaction().commit();

		 return list;
	}
	
	public Task getTask(int taskId) { // TODO : validar metodo - nao retorna valores por nao ser lista. 
		
		 Session session = sessionFactory.openSession();
		 session.beginTransaction();
		 
//		 int tasksId = session.createQuery("SELECT tasks_id FROM TASKLIST_TASK where tasklist_id IN :ids").setParameter("ids", taskListId).getFirstResult();

		 Task t = session.get(Task.class, taskId);
		 
		 session.getTransaction().commit();

	 	 return t;
//		 List<TaskList> lists = session.createQuery("from TaskList", TaskList.class).list();
		 
//		 Query query = session.createQuery("from Task");
//
//	       List<TaskList> lists = query.getResultList();
//		
		
//		return null;
	}
	
	public boolean hasTasksLists() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		
		 
//		 List<TaskList> lists = session.createQuery("from TaskList", TaskList.class).list();
		 
		 Query query = session.createQuery("from TaskList");

	       List<TaskList> lists = query.getResultList();
		 
		 session.beginTransaction();
		 session.close();
		 if (lists.size() >0) {
			 return true;
		 }else {
			 return false;
		 }
		 
	}

	public boolean hasTaskList(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public String createTaskList(String name) {
		TaskList taskList = new TaskList(name);
//		taskList.setName("Lista tarefa");
		this.writeTaskList(taskList);
		return ""+taskList.getId();
	}
	

//	public String createTaskListWithValue() {
//		TaskList taskList = new TaskList();
//		Task task = new Task();
//		task.setDescription("lista criada");
//		task.setState("em curso");
//		taskList.addTask(task);
//		this.writeTaskList(taskList);
//		return ""+taskList.getId();
//	}

	public String createTask(String taskId, String description, String state) {
		// TODO Auto-generated method stub
		Task task = new Task();
		task.setState(state);
		task.setDescription(description);
		task.setId(Integer.valueOf(taskId));
		
		this.writeTask(task);
		return ""+task.getId();
	}

	
	public void deleteTaskList(String taskListId) { // elimina a lista // TODO: alterar esta query
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		TaskList taskList = new TaskList();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "DELETE from tasklist tl, task t, tasklist_task tt where tt.tasklist_id = " + taskListId + 
        	"and tl.id = " + taskListId + "t.id = (select task_id )" ;
        Query querys  = session.createQuery(query);
        querys.executeUpdate();
        session.getTransaction().commit();
        // Close session
        session.close();

	}
	
	public String deleteTask(String Id) { // elimina tarefas 
		Session session = sessionFactory.openSession();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "DELETE from task where id = " + Id ; 
        Query querys = session.createQuery(query); 
        querys.executeUpdate();									// TODO: validar query  
        session.getTransaction().commit();
        // Close session
        session.close();
        return "ID eliminado" + Id;

	}
	
	public boolean hasTask(String taskListId, String taskId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void changeTaskDescription(String taskId, String taskDescription) {

		Session session = sessionFactory.openSession();
		TaskList taskList = new TaskList();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "UPDATE task set description = " + taskDescription + "where id = " + taskId ;
        Query querys  = session.createQuery(query);
        querys.executeUpdate();
        session.getTransaction().commit();
        // Close session
        session.close();

	}

	public void changeTaskStatus(String taskId, String status) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		TaskList taskList = new TaskList();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "UPDATE task set state = " + status + "where id = " + taskId ;
        Query querys  = session.createQuery(query);
        querys.executeUpdate();
        session.getTransaction().commit();
        // Close session
        session.close();

	}
	
	
	public void changeTaskListName(String id, String newName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		TaskList taskList = new TaskList();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "UPDATE tasklist set name = " + newName + "where id = " + id ;
        Query querys  = session.createQuery(query);
        querys.executeUpdate();
        session.getTransaction().commit();
        // Close session
        session.close();
	}


}
