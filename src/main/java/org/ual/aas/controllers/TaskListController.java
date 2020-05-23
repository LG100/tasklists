package org.ual.aas.controllers;

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
		Session session = sessionFactory.openSession();
		// Open Hibernate connection
		session.beginTransaction();
		// Build TaskList object

		String id = "from tasklist where id = "+ taskListId + ";";
		Query query = session.createQuery(id);
		List result = query.getResultList();

		// Close connection
		session.close();
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
	
	public List<TaskList> getTaskLists() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasTasksLists() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasTaskList(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public String createTaskList(String name) {
		TaskList taskList = new TaskList(name);
		this.writeTaskList(taskList);
		return ""+taskList.getId();
	}

	public TaskList getTaskList(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void changeTaskListName(String name, String newName) {
		// TODO Auto-generated method stub

	}
	
	public void deleteTask(String taskListId, String taskId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "from tasklist_task where tasklist_id = " + taskListId + "and tasks_id = " + taskId  ;
        session.delete(query);
        session.getTransaction().commit();
        // Close session
        session.close();

	}
	
	public boolean hasTask(String taskListId, String taskId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void changeTaskDescription(String taskId, String taskDescription) {
		// TODO Auto-generated method stub

	}

	public void changeTaskStatus(String taskListId, String taskId, String status) {
		// TODO Auto-generated method stub

	}

	public String createTask(String taskListId, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task getTask(String taskListId, String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteTaskList(String Id) {
		Session session = sessionFactory.openSession();
		
		// Open Hibernate session
        session.beginTransaction();
        // Remover TaskList object
        String query = "from tasklist where id = " + Id ;
        session.delete(query);
        session.getTransaction().commit();
        // Close session
        session.close();

	}

}
