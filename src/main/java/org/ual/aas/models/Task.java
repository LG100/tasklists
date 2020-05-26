package org.ual.aas.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Task {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	private String description;
	private String state;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "taskList_id")
	private TaskList taskList_id;
		
	public Task() {
		super();
	}

	public Task(String description) {
		super();
		this.description = description;
		this.state = "doing";
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public TaskList getTaskList_id() {
		return taskList_id;
	}

	public void setTaskList_id(TaskList taskList_id) {
		this.taskList_id = taskList_id;
	}
	
}
