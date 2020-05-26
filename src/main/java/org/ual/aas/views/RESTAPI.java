package org.ual.aas.views; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ual.aas.controllers.TaskListController;
import org.ual.aas.models.TaskList;
import org.ual.aas.models.Task;

import javax.json.Json;
import javax.json.JsonObject;
import com.google.gson.Gson;


@WebServlet(name = "tasklists", urlPatterns = {"/*"})
public class RESTAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        	TaskListController controller = new TaskListController();
        	String uri = req.getRequestURI();
        	String[] splits = uri.split("/");

        if (splits.length == 3) { // GET /lists/
            List<TaskList> lists = controller.getTaskLists();

            String json = new Gson().toJson(lists);

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();

            writer.println(json);
            writer.close();
            controller.close();

        } else if (splits.length == 4) { // GET /lists/<list_id>/
            // ["", "tasklists", "lists", list_id]
//            String listId = splits[3];

            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            try{
                TaskList list = controller.getTaskList(jsonObject.getInt("list_id"));

                String json = new Gson().toJson(list);
                
                writer.println(json); // imprimir json 
            }catch (Exception e) {
            	System.out.println(e);
				// TODO: handle exception
            	writer.println("not exist");
			}
            writer.close();
            controller.close();

        } else if (splits.length == 5) { // GET /lists/<list_id>/<task_id>/
            // ["", "tasklists", "lists", list_id, task_id]

            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            try{
            Task task = controller.getTask( jsonObject.getInt("id"));

//            String json = new Gson().toJson(task);

            writer.println(task);
            }catch (Exception e) {
            	System.out.println(e);
				// TODO: handle exception
            	writer.println("not exist");
			}
            writer.close();
            controller.close();
        } else {
            // 404
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("application/json");
    	TaskListController controller = new TaskListController();
    	String uri = req.getRequestURI();
    	String[] splits = uri.split("/");
        
        if(splits.length == 3) { // POST /lists
        	// ["", "tasklists", "lists", list_id]
//        	String listId = splits[3];

             BufferedReader reader = req.getReader();
             JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();

             String json = jsonObject.getString("name"); // trabalhar Json - String name - ve o valor a frente 
             
//            String name = controller.createTaskList(json);
             
        	// 1. JSON  to Java object
             Gson gson = new Gson();
//			 cria uma nova lista 
             controller.createTaskList(json);
             resp.setContentType("application/json");
             PrintWriter writer = resp.getWriter();

             writer.println("" + json.toString());             
             writer.close();
             controller.close();
        	
        }
    	else if(splits.length == 4) { // POST /lists/<list_id>
    		// ["", "tasklists", "lists", list_id, task_id]
    		
            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            PrintWriter writer = resp.getWriter();
            try {
            String taskDescription = jsonObject.getString("name"); 
            String taskId = jsonObject.getString("id"); 
            String state = jsonObject.getString("state");
            
//          adiciona uma tarefa à lista. 
            resp.setContentType("application/json");
            controller.createTask(taskId, taskDescription, state); // TODO: validar metodo 
            writer.println("Tarefa adicionada : " +
            taskDescription.toString() 
            );
            
            writer.close();
            controller.close();
            } catch (Exception e) {
            	System.out.println(e);
            	writer.println("Nao adicionado.");
            }
            
    	}else if (splits.length == 5) { // POST /lists/<list_id>/<task_id>
            // ["", "tasklists", "lists", list_id, task_id]
    		
    		BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            PrintWriter writer = resp.getWriter();
            try {
            String taskId = jsonObject.getString("idtask");
            String status = jsonObject.getString("estado"); 
            
            resp.setContentType("application/json");
//			altera o estado da tarefa             
            controller.changeTaskStatus(taskId, status);
            writer.println("Tarefa alterada : " + 
            taskId.toString() +
            status.toString());
            
            writer.close();
            controller.close();
            } catch (Exception e) {
            	System.out.println(e);
            	writer.println("Nao adicionado.");
            }
    	}
    	else {
    		// 404
    	}
        controller.close();
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("application/json");
    	TaskListController controller = new TaskListController();
    	String uri = req.getRequestURI();
    	String[] splits = uri.split("/");
    	
    	if (splits.length == 4) { // GET /lists/<list_id>/
            // ["", "tasklists", "lists", list_id]

            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            try{
                String taskId = jsonObject.getString("id");
                String taskName = jsonObject.getString("nome");
                
                resp.setContentType("application/json");
                controller.changeTaskListName(taskId, taskName);
                
                writer.println("Nome da tarefa alterada : " + 
                taskId.toString() +
                taskName.toString());
                
                writer.close();
                controller.close();
            }catch (Exception e) {
            	System.out.println(e);
				// TODO: handle exception
            	writer.println("Alteracao nao efectuado.");
			}
            writer.close();
            controller.close();

        } else if (splits.length == 5) { // GET /lists/<list_id>/<task_id>/
            // ["", "tasklists", "lists", list_id, task_id]

            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            try{
            
            String taskId = jsonObject.getString("id");
            String taskDescription = jsonObject.getString("descricao");
            
//            String json = new Gson().toJson(task);
            
            controller.changeTaskDescription(taskId, taskDescription);
            writer.println("Nome da tarefa alterada : " + 
            taskId.toString() +
            taskDescription.toString());
            
            writer.close();
            controller.close();
            }catch (Exception e) {
            	System.out.println(e);
				// TODO: handle exception
            	writer.println("Alteracao nao efectuada");
			}
            writer.close();
            controller.close();
        } else {
            // 404
        }
     }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("application/json");
    	TaskListController controller = new TaskListController();
    	String uri = req.getRequestURI();
    	String[] splits = uri.split("/");

    	if (splits.length == 4) { // GET /lists/<list_id>/
            // ["", "tasklists", "lists", list_id]
            String listId = splits[3];

            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            try{
            	
            	String taskListId = jsonObject.getString("id");
                controller.deleteTaskList(taskListId);

//                String json = new Gson().toJson(list);
                
                writer.println(taskListId.toString());
            }catch (Exception e) {
            	System.out.println(e);
				// TODO: handle exception
            	writer.println("not exist");
			}
            writer.close();
            controller.close();

        } else if (splits.length == 5) { // GET /lists/<list_id>/<task_id>/
            // ["", "tasklists", "lists", list_id, task_id]

            BufferedReader reader = req.getReader();
            JsonObject jsonObject = (JsonObject) Json.createReader(reader).readObject();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            try{
//            Task task = controller.deleteTask( jsonObject.getInt("task_id"));
            	
            	String taskId = jsonObject.getString("idtask");
//	            String json = new Gson().toJson(task);
            	controller.deleteTask(taskId);
	
	            writer.println(taskId.toString());
            }catch (Exception e) {
            	System.out.println(e);
				// TODO: handle exception
            	writer.println("doesn't exist");
			}
            writer.close();
            controller.close();
        } else {
            // 404
        }
    	controller.close();
        
    }    
}
